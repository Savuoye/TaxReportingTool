package com.fisglobal.taxreporting.service.taxreportingsearch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fisglobal.taxreporting.enums.AntwortStatus;
import com.fisglobal.taxreporting.enums.MeldeStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.MandatoryFieldValidationException;
import com.fisglobal.taxreporting.exception.RecordNotFoundException;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.model.TrTableSearch;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.taxreportingsearch.TaxReportingSearchParameters;
import com.fisglobal.taxreporting.service.taxreportingsearch.TaxReportingSearchService;
import com.fisglobal.taxreporting.service.taxreportingsearch.model.TaxReports;
import com.fisglobal.taxreporting.util.BigDecimalUtils;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.StringUtils;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Serrch service implementation class will validate the input parameters and
 * get the data from DB
 */
@Service
public class TaxReportingSearchServiceImpl implements TaxReportingSearchService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingSearchServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TaxReportingRepository taxReportingRepository;

    @SuppressWarnings("unlikely-arg-type")
    public void mandatoryFieldValidationCheck(String meldeStatus, String keyIdNr) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryFieldValidationCheck] Enter into KeyIdNr validation for melde status");

        ValidationResults validationResults = new ValidationResults();

        if (meldeStatus != null && MeldeStatus.fromValue(meldeStatus) == null) {
            if (keyIdNr == null) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_113,
                        ValidationMessage.ERROR_CODE_113_DESC, null);
                throw new MandatoryFieldValidationException(validationResults);
            }
        }

        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryFieldValidationCheck] Exit KeyIdNr validation for melde status");
    }

    /**
     * @param keyIdNr
     *                                        value of keyIdNr from input params
     * @param keyMeldejahr
     *                                        value of keyMeldejahr from input params
     * @param meldeStatus
     *                                        value of meldeStatus from input params
     * @param antwortStatus
     *                                        value of antwortStatus from input params
     * @param pageNumber
     *                                        value of pageNumber from input params
     * @param pageLimit
     *                                        value of pageLimit from input params
     * @param totalCountWithoutPagingFlag
     *                                        value of totalCountWithoutPagingFlag from input params
     * 
     * @return returns the TaxReports object
     * 
     * @throws RecordNotFoundException
     *                                     throws record not found exception if no records were
     *                                     found during
     *                                     the search
     */

    @SuppressWarnings({ "unchecked", "unlikely-arg-type" })
    public TaxReports searchByTaxReportParameters(String keyIdNr, Long keyMeldejahr, String meldeStatus,
            String antwortStatus, int pageNumber, int pageLimit, boolean totalCountWithoutPagingFlag)
            throws RecordNotFoundException {
        LOG.trace("[TaxReportingSearchServiceImpl] [searchByTaxReportParameters] Enter into searchTaxReports service");

        ValidationResults validationResults = new ValidationResults();

        TaxReportingSearchParameters searchParams = new TaxReportingSearchParameters(keyIdNr, keyMeldejahr, meldeStatus,
                antwortStatus);

        // Pagination - pageNumber and limit
        Pageable pageable = PageRequest.of(pageNumber - 1, pageLimit);
        if (keyIdNr != null && keyMeldejahr == null && meldeStatus == null && antwortStatus == null) {
            pageable = PageRequest.of(pageNumber - 1, Integer.MAX_VALUE);
        }
        StringBuilder queryBuilder;
        List<Object[]> queryResults;
        Long totalRecordsCount = null;

        if (totalCountWithoutPagingFlag) {
            // Form a native query string based on the specification to get the total number
            // of records count
            queryBuilder = getQueryStringWithSpecifications(searchParams, true);

            // Get the total number of records from Native query
            totalRecordsCount = getTotalCountFromNativeQuery(searchParams, queryBuilder.toString());
        }

        // Form a native query string based on the specification
        queryBuilder = getQueryStringWithSpecifications(searchParams, false);

        // Get the Results from Native query
        queryResults = getResultListFromNativeQuery(searchParams, pageable, queryBuilder.toString());

        // Populate TrTableSearchDto - to return in API response
        List<TrTableSearch> taxReportsList = getTrTableSearchDtoList(queryResults);

        Optional<TrTableSearch> trTableSearchObject = taxReportsList.stream().filter(e -> e.getIsLatestHistorical())
                .findAny();

        if (trTableSearchObject.isPresent()) {
            LOG.trace(
                    "[TaxReportingSearchServiceImpl] [searchByTaxReportParameters] Latest historical data is available");

            for (TrTableSearch trTableSearch : taxReportsList) {

                if (trTableSearch.getIsLatestHistorical()) {

                    String keyIdNrValue = org.apache.commons.lang3.StringUtils.rightPad(trTableSearch.getKeyIdNr(),
                            CommonConstants.KEY_ID_NR_LENGTH);
                    String keyMusterValue = org.apache.commons.lang3.StringUtils.rightPad(trTableSearch.getKeyMuster(),
                            CommonConstants.KEY_MUSTER_LENGTH);

                    Long previousRecordCount = taxReportingRepository.findPreviousBSBRecordCountForCorrection(
                            keyIdNrValue, trTableSearch.getKeyMeldejahr(), keyMusterValue,
                            trTableSearch.getKeyLaufnummer(), CommonConstants.previousStatusCheckForCorrection);

                    if (previousRecordCount > 0) {
                        LOG.trace(
                                "[TaxReportingSearchServiceImpl] [searchByTaxReportParameters] Setting isLatestHistorical to false");
                        trTableSearch.setIsLatestHistorical(false);
                    }
                }
            }
        }

        // throws RecordNotFoundException in case records not found in database
        if (taxReportsList.isEmpty()) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_101,
                    ValidationMessage.ERROR_CODE_101_DESC, null);
            throw new RecordNotFoundException(validationResults);
        }

        TaxReports taxReports = new TaxReports(taxReportsList, totalRecordsCount);

        LOG.trace("[TaxReportingSearchServiceImpl] [searchByTaxReportParameters] Exit searchTaxReports service");

        return taxReports;
    }

    // Populate TrTableSearchDto - to return in API response
    private List<TrTableSearch> getTrTableSearchDtoList(List<Object[]> queryResults) {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [getTrTableSearchDtoList] Enter in to construct taxreport search list");
        List<TrTableSearch> taxReportsList = new ArrayList<TrTableSearch>();

        for (Object[] result : queryResults) {
            TrTableSearch trTableSearchDto = new TrTableSearch(StringUtils.getStringValue(result[0]),
                    StringUtils.getStringValue(result[1]), BigDecimalUtils.getBigDecimalValue(result[2]).longValue(),
                    StringUtils.getStringValue(result[3]), BigDecimalUtils.getBigDecimalValue(result[4]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[5]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[6]).longValue(), StringUtils.getStringValue(result[7]),
                    StringUtils.getStringValue(result[8]), StringUtils.getStringValue(result[9]),
                    BigDecimalUtils.getBigDecimalValue(result[10]), BigDecimalUtils.getBigDecimalValue(result[11]),
                    StringUtils.getStringValue(result[12]), StringUtils.getStringValue(result[13]),
                    Boolean.valueOf(StringUtils.getStringValue(result[14])));
            taxReportsList.add(trTableSearchDto);
        }
        LOG.trace("[TaxReportingSearchServiceImpl] [getTrTableSearchDtoList] Exit construct taxreport search list");
        return taxReportsList;
    }

    // Get the Results from Native query
    @SuppressWarnings("unchecked")
    private List<Object[]> getResultListFromNativeQuery(TaxReportingSearchParameters searchParams, Pageable pageable,
            String queryString) {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [getResultListFromNativeQuery] Enter in to query execution for pageable");

        String keyIdNr = searchParams.getKeyIdNr();
        Long keyMeldejahr = searchParams.getKeyMeldejahr();
        String meldeStatus = searchParams.getMeldeStatus();
        String antwortStatus = searchParams.getAntwortStatus();

        // Page offset
        long offset = pageable.getOffset();

        // Page limit
        int pageLimit = pageable.getPageSize();

        Query q = entityManager.createNativeQuery(queryString);

        if (keyIdNr != null) {
            keyIdNr = org.apache.commons.lang3.StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
            q.setParameter("keyIdNrValue", keyIdNr);
        }
        if (keyMeldejahr != null)
            q.setParameter("keyMeldejahrValue", keyMeldejahr);
        if (meldeStatus != null)
            q.setParameter("meldeStatusValue", meldeStatus);
        if (antwortStatus != null)
            q.setParameter("antwortStatusValue", antwortStatus);

        q.setParameter("offset", (int) offset);
        q.setParameter("limit", pageLimit);

        List<Object[]> results = q.getResultList();

        LOG.trace("[TaxReportingSearchServiceImpl] [getResultListFromNativeQuery] Exit query execution for pageable");

        return results;
    }

    // Get the Total Records Counts from Native query
    @SuppressWarnings("unchecked")
    private long getTotalCountFromNativeQuery(TaxReportingSearchParameters searchParams, String queryString) {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [getTotalCountFromNativeQuery] Enter in to query execution for count");

        String keyIdNr = searchParams.getKeyIdNr();
        Long keyMeldejahr = searchParams.getKeyMeldejahr();
        String meldeStatus = searchParams.getMeldeStatus();
        String antwortStatus = searchParams.getAntwortStatus();

        Query q = entityManager.createNativeQuery(queryString);

        if (keyIdNr != null) {
            keyIdNr = org.apache.commons.lang3.StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
            q.setParameter("keyIdNrValue", keyIdNr);
        }

        if (keyMeldejahr != null)
            q.setParameter("keyMeldejahrValue", keyMeldejahr);
        if (meldeStatus != null)
            q.setParameter("meldeStatusValue", meldeStatus);
        if (antwortStatus != null)
            q.setParameter("antwortStatusValue", antwortStatus);

        List<BigDecimal> results = q.getResultList();

        long count = results.get(0).longValue();

        LOG.trace("[TaxReportingSearchServiceImpl] [getTotalCountFromNativeQuery] Exit query execution for count");

        return count;
    }

    // Form a native query string based on the specification
    private StringBuilder getQueryStringWithSpecifications(TaxReportingSearchParameters searchParams,
            boolean totalRecordsWithoutPagingFlag) {
        String keyIdNr = searchParams.getKeyIdNr();
        Long keyMeldejahr = searchParams.getKeyMeldejahr();
        String meldeStatus = searchParams.getMeldeStatus();
        String antwortStatus = searchParams.getAntwortStatus();

        StringBuilder tempResult = new StringBuilder();

        if ((keyIdNr != null && keyMeldejahr == null && meldeStatus == null && antwortStatus == null)
                || (keyIdNr != null && keyMeldejahr != null && meldeStatus == null && antwortStatus == null)) {
            tempResult = getQueryStringWithKeyIdNr(searchParams, totalRecordsWithoutPagingFlag);
        } else {
            tempResult = getQueryStringWithGenericSpecifications(searchParams, totalRecordsWithoutPagingFlag);
        }

        return tempResult;

    }

    private StringBuilder getQueryStringWithKeyIdNr(TaxReportingSearchParameters searchParams,
            boolean totalRecordsWithoutPagingFlag) {
        LOG.trace("[TaxReportingSearchServiceImpl] [getQueryStringWithKeyIdNr] Enter in to construct query");

        // Select
        StringBuilder stringBuilder = new StringBuilder();

        if (totalRecordsWithoutPagingFlag) {
            stringBuilder.append("select count(*) from ( ");
        }
        stringBuilder.append("SELECT MAND_SL as mandSl, KEY_ID_NR as keyIdNr, KEY_MELDEJAHR as keyMeldejahr, "
                + "KEY_MUSTER as keyMuster, KEY_LAUFNUMMER as keyLaufnummer, KEY_SYS_DATUM as keySysDatum, "
                + "KEY_UHRZEIT as keyUhrzeit, NP2_IDNR as np2Idnr, MELDE_STATUS as meldeStatus, "
                + "ANTWORT_STATUS as antwortStatus, NP1_STERBE_DATUM as np1SterbeDatum, NP2_STERBE_DATUM as np2SterbeDatum, "
                + "KONTO_ID as kontoId, STATUS as status, " + "CASE "
                + "WHEN STATUS = 'H' AND ROWNUMBER_H = 1 THEN 'true' " + "WHEN NON_H_COUNT > 0 THEN 'false' "
                + "ELSE 'false' END AS isLatestHistorical "
                + "FROM (SELECT p.MAND_SL, p.KEY_ID_NR, p.KEY_MELDEJAHR, p.KEY_MUSTER, p.KEY_LAUFNUMMER, p.KEY_SYS_DATUM, "
                + "p.KEY_UHRZEIT, p.NP2_IDNR, p.MELDE_STATUS, p.ANTWORT_STATUS, p.NP1_STERBE_DATUM, p.NP2_STERBE_DATUM, p.KONTO_ID, "
                + "p.STATUS, COUNT(CASE WHEN p.STATUS != 'H' THEN 1 END) OVER (PARTITION BY p.KEY_MELDEJAHR, p.KEY_LAUFNUMMER) AS NON_H_COUNT, "
                + "ROW_NUMBER() OVER (PARTITION BY p.KEY_MELDEJAHR, p.KEY_LAUFNUMMER ORDER BY p.KEY_SYS_DATUM DESC, p.KEY_UHRZEIT DESC) AS ROWNUMBER_H "
                + "FROM kortr_sch.tr_table_bsb p ");

        stringBuilder.append(
                "LEFT JOIN kortr_sch.tr_table_bm1 c1 ON (p.MAND_SL = c1.MAND_SL AND p.KEY_ID_NR = c1.KEY_ID_NR AND p.KEY_MELDEJAHR = c1.KEY_MELDEJAHR AND p.KEY_MUSTER = c1.KEY_MUSTER AND p.KEY_LAUFNUMMER = c1.KEY_LAUFNUMMER AND p.KEY_SYS_DATUM = c1.KEY_SYS_DATUM AND p.KEY_UHRZEIT = c1.KEY_UHRZEIT AND p.STATUS = c1.KEY_SATZART) "
                        + "LEFT JOIN kortr_sch.tr_table_bm3 c3 ON (p.MAND_SL = c3.MAND_SL AND p.KEY_ID_NR = c3.KEY_ID_NR AND p.KEY_MELDEJAHR = c3.KEY_MELDEJAHR AND p.KEY_MUSTER = c3.KEY_MUSTER AND p.KEY_LAUFNUMMER = c3.KEY_LAUFNUMMER AND p.KEY_SYS_DATUM = c3.KEY_SYS_DATUM AND p.KEY_UHRZEIT = c3.KEY_UHRZEIT AND p.STATUS = c3.KEY_SATZART) ");
        stringBuilder.append("WHERE REGEXP_LIKE(p.KEY_ID_NR, '^[[:space:]]*'||:keyIdNrValue||'[[:space:]]*$')");

        if (searchParams.getKeyMeldejahr() != null) {
            stringBuilder.append(" AND p.KEY_MELDEJAHR=:keyMeldejahrValue");
        }

        if (!totalRecordsWithoutPagingFlag) {
            // Order by
            stringBuilder.append(" ORDER BY p.KEY_LAUFNUMMER, p.KEY_SYS_DATUM desc, p.KEY_UHRZEIT desc");

            // offset and limit
            stringBuilder.append(" OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY");

            stringBuilder.append(" )");
        }

        if (totalRecordsWithoutPagingFlag) {
            stringBuilder.append(" ORDER BY p.KEY_LAUFNUMMER, p.KEY_SYS_DATUM desc, p.KEY_UHRZEIT desc");
            stringBuilder.append(" ) )");
        }
        LOG.trace("[TaxReportingSearchServiceImpl] [getQueryStringWithKeyIdNr] Exit construct query");

        return stringBuilder;
    }

    private StringBuilder getQueryStringWithGenericSpecifications(TaxReportingSearchParameters searchParams,
            boolean totalRecordsWithoutPagingFlag) {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [getQueryStringWithGenericSpecifications] Enter in to construct query");

        String keyIdNr = searchParams.getKeyIdNr();
        Long keyMeldejahr = searchParams.getKeyMeldejahr();
        String meldeStatus = searchParams.getMeldeStatus();
        String antwortStatus = searchParams.getAntwortStatus();

        // To check if where condition needed when forming query String
        boolean whereClauseExists = false;
        if (keyIdNr != null || keyMeldejahr != null || meldeStatus != null || antwortStatus != null) {
            whereClauseExists = true;
        }

        // Select
        StringBuilder stringBuilder = new StringBuilder();

        if (totalRecordsWithoutPagingFlag) {
            stringBuilder.append("SELECT COUNT(*) from kortr_sch.tr_table_bsb p");
        } else {
            stringBuilder.append("SELECT "
                    + " p.MAND_SL as mandSl, p.KEY_ID_NR as keyIdNr, p.KEY_MELDEJAHR as keyMeldejahr, p.KEY_MUSTER as keyMuster, p.KEY_LAUFNUMMER as keyLaufnummer, p.KEY_SYS_DATUM as keySysDatum, p.KEY_UHRZEIT as keyUhrzeit,"
                    + " p.NP2_IDNR as np2Idnr, p.MELDE_STATUS as meldeStatus, p.ANTWORT_STATUS as antwortStatus, p.NP1_STERBE_DATUM as np1SterbeDatum, p.NP2_STERBE_DATUM as np2SterbeDatum, p.KONTO_ID as kontoId, p.STATUS as status, 'false' as isLatestHistorical"
                    + " FROM kortr_sch.tr_table_bsb p");
        }
        stringBuilder.append(
                " LEFT JOIN kortr_sch.tr_table_bm1 c1 ON (p.MAND_SL=c1.MAND_SL and p.KEY_ID_NR=c1.KEY_ID_NR and p.KEY_MELDEJAHR=c1.KEY_MELDEJAHR and p.KEY_MUSTER=c1.KEY_MUSTER and p.KEY_LAUFNUMMER=c1.KEY_LAUFNUMMER and p.KEY_SYS_DATUM=c1.KEY_SYS_DATUM and p.KEY_UHRZEIT=c1.KEY_UHRZEIT and p.STATUS=c1.KEY_SATZART)"
                        + " LEFT JOIN kortr_sch.tr_table_bm3 c3 ON (p.MAND_SL=c3.MAND_SL and p.KEY_ID_NR=c3.KEY_ID_NR and p.KEY_MELDEJAHR=c3.KEY_MELDEJAHR and p.KEY_MUSTER=c3.KEY_MUSTER and p.KEY_LAUFNUMMER=c3.KEY_LAUFNUMMER and p.KEY_SYS_DATUM=c3.KEY_SYS_DATUM and p.KEY_UHRZEIT=c3.KEY_UHRZEIT and  p.STATUS=c3.KEY_SATZART)");

        // Where condition
        if (whereClauseExists) {
            stringBuilder.append(" WHERE(");

            /*
             * REGEXP_LIKE - is used for comparison now to handle spaces in the database.
             * But if the REGEXP will hinder the performance with more records, then we may
             * need to use = operator only and handle the spaces in our code(set trailing
             * spaces before sending to query).
             */
            boolean conditionExists = false;
            if (keyIdNr != null) {
                // stringBuilder.append("p.KEY_ID_NR=:keyIdNrValue");
                stringBuilder.append("REGEXP_LIKE(p.KEY_ID_NR, '^[[:space:]]*'||:keyIdNrValue||'[[:space:]]*$')");
                conditionExists = true;
            }
            if (keyMeldejahr != null) {
                if (conditionExists)
                    stringBuilder.append(" AND ");
                stringBuilder.append("p.KEY_MELDEJAHR=:keyMeldejahrValue");
                conditionExists = true;
            }
            if (meldeStatus != null) {
                if (conditionExists)
                    stringBuilder.append(" AND ");
                // stringBuilder.append("p.MELDE_STATUS=:meldeStatusValue");
                stringBuilder
                        .append("REGEXP_LIKE(p.MELDE_STATUS, '^[[:space:]]*'||:meldeStatusValue||'[[:space:]]*$')");
                conditionExists = true;
            }
            if (antwortStatus != null) {
                if (conditionExists)
                    stringBuilder.append(" AND ");
                // stringBuilder.append("p.ANTWORT_STATUS=:antwortStatusValue");
                stringBuilder
                        .append("REGEXP_LIKE(p.ANTWORT_STATUS, '^[[:space:]]*'||:antwortStatusValue||'[[:space:]]*$')");
                conditionExists = true;
            }
            /*
             * Note: antwortStatus in database can have null as value since in DB the field
             * has Nullable true. So if antwortStatus with null value to be also searched
             * then query should use IS NULL condition.
             */
            stringBuilder.append(")");
        }

        if (!totalRecordsWithoutPagingFlag) {
            // Order by
            stringBuilder.append(" ORDER BY" + " p.KEY_SYS_DATUM desc, p.KEY_UHRZEIT desc");

            // offset and limit
            stringBuilder.append(" OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY");
        }

        LOG.trace("[TaxReportingSearchServiceImpl] [getQueryStringWithSpecifications] Exit construct query");

        return stringBuilder;
    }

    @Override
    public void mandatoryValidationForAntwortStatus(String antwortStatus, String keyIdNr)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryValidationForAntwortStatus] Enter into keyIdNr validation for antwortStatus");

        if (!(AntwortStatus._02.toString().equals(antwortStatus)
                || AntwortStatus._01.toString().equals(antwortStatus))) {
            if (keyIdNr == null) {
                ValidationResults validationResults = new ValidationResults();
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_113,
                        ValidationMessage.ERROR_CODE_113_DESC, null);
                throw new MandatoryFieldValidationException(validationResults);
            }
        }

        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryValidationForAntwortStatus] Exit keyIdNr validation for antwortStatus");

    }

    @Override
    public void mandatoryValidationForNonEmptyParameter(String keyIdNr, Long keyMeldejahr, String meldeStatus,
            String antwortStatus) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryValidationForNonEmptyParameter] Enter into non empty parameter validation");

        if (keyIdNr == null && meldeStatus == null && antwortStatus == null) {
            ValidationResults validationResults = new ValidationResults();
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_106,
                    ValidationMessage.ERROR_CODE_106_DESC, null);
            throw new MandatoryFieldValidationException(validationResults);
        }

        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryValidationForNonEmptyParameter] Exit non empty parameter validation");
    }

    @Override
    public void mandatoryValidationForkeyMeldejahr(String keyIdNr, String meldeStatus, String antwortStatus)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingSearchServiceImpl] [mandatoryValidationForkeyMeldejahr] Enter into meldejahr validation");

        if (keyIdNr == null && meldeStatus == null && antwortStatus == null) {
            ValidationResults validationResults = new ValidationResults();
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_106,
                    ValidationMessage.ERROR_CODE_106_DESC, null);
            throw new MandatoryFieldValidationException(validationResults);
        }

        LOG.trace("[TaxReportingSearchServiceImpl] [mandatoryValidationForkeyMeldejahr] Exit meldejahr validation");
    }
}
