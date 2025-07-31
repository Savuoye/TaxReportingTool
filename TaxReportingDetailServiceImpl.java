package com.fisglobal.taxreporting.service.taxreportingdetail.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.annotation.RequestScope;

import com.fisglobal.taxreporting.controller.dto.TaxReportingDetail;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.BsbStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.model.TrTableSearch;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.util.BigDecimalUtils;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.RemoveEmptySpaces;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting detail service implementation class will get all the details of
 * tax report record
 */
@Service
@RequestScope
public class TaxReportingDetailServiceImpl implements TaxReportingDetailService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingDetailService.class);

    @Autowired
    private TaxReportingRepository taxReportingRepository;

    @SuppressWarnings("unused")
    @Transactional(readOnly = false)
    public TrTableBsb getAllDetailTaxReports(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException {
        LOG.trace("[TaxReportingDetailServiceImpl] [getAllDetailTaxReports] Enter in to getAllDetailTaxReports method");

        this.mandatoryParametersCheck(mandSl, keyIdNr, keyMuster);
        ValidationResults validationResults = new ValidationResults();

        boolean tableisCleaned = false;

        TrTableBsb trTableBsb = fetchTrTableBsb(mandSl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum,
                keyUhrzeit);

        LOG.trace("[TaxReportingDetailServiceImpl] [getAllDetailTaxReports] BSB: {}", trTableBsb.taxCertificateInfo());
        RemoveEmptySpaces removeEmptySpaces = new RemoveEmptySpaces();
        tableisCleaned = removeEmptySpaces.clean(trTableBsb);

        if (tableisCleaned) {
            updateChangedSpaces(trTableBsb);
        }

        LOG.trace("[TaxReportingDetailServiceImpl] [getAllDetailTaxReports] Exit getAllDetailTaxReports method");

        return trTableBsb;

    }

    private void mandatoryParametersCheck(String mandSl, String keyIdNr, String keyMuster)
            throws ValidationResultsException {
        LOG.trace("[TaxReportingDetailServiceImpl] [mandatoryParametersCheck] Enter into mandatory parameter method");

        ValidationResults validationResults = new ValidationResults();
        if (mandSl == null || keyIdNr == null || keyMuster == null) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_100,
                    ValidationMessage.ERROR_CODE_100_DESC, null);
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace("[TaxReportingDetailServiceImpl] [mandatoryParametersCheck] Exit mandatory method");
    }

    private void updateChangedSpaces(TrTableBsb bsb) {
        LOG.trace("[TaxReportingDetailServiceImpl] [updateChangedSpaces] Spaces updated");
        taxReportingRepository.save(bsb);
    }

    @Override
    public TaxReportingDetail getPreviousBSBRecordCount(
            com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsb) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingDetailServiceImpl] [getPreviousBSBRecordCount] Enter into previous BSB list for BSB: {}",
                trTableBsb.taxCertificateInfo());

        TaxReportingDetail trDetail = new TaxReportingDetail();

        if (BsbStatus.H.toString().equals(trTableBsb.getStatus())) {
            Long previousRecordCount = taxReportingRepository.findPreviousBSBRecordCount(
                    trTableBsb.getTrTableBsbPK().getMandSl(), trTableBsb.getTrTableBsbPK().getKeyIdNr(),
                    trTableBsb.getTrTableBsbPK().getKeyMeldejahr(), trTableBsb.getTrTableBsbPK().getKeyMuster(),
                    trTableBsb.getTrTableBsbPK().getKeyLaufnummer(), CommonConstants.validStatusForCreateAdjustment);

            LOG.trace("[TaxReportingDetailServiceImpl] [getPreviousBSBRecordCount] PreviousRecordCount:{} ",
                    previousRecordCount);

            if (previousRecordCount != null && previousRecordCount > 0) {
                trDetail.setHasPreviousBsbList(true);
            }
        }

        BeanUtils.copyProperties(trTableBsb, trDetail);

        LOG.trace("[TaxReportingDetailServiceImpl] [getPreviousBSBRecordCount] Exit previous BSB list for BSB: {}",
                trTableBsb.taxCertificateInfo());
        return trDetail;
    }

    @Override
    public TaxReportingDetail setDeleteHistoricizedEligibilityFlag(TaxReportingDetail taxReportingDetail)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingDetailServiceImpl] [setDeleteHistoricizedEligibilityFlag] Enter into setDeleteHistoricizedEligibilityFlag method");

        boolean isSafeDelete = false;

        // Check if is a historicized record(status is "H" and MeldeStatus is empty)
        if (taxReportingDetail.getStatus().equals("H")
                && (CommonConstants.EMPTY_VALUE.equals(taxReportingDetail.getMeldeStatus().trim()))) {

            String mandsl = taxReportingDetail.getTrTableBsbPK().getMandSl();
            String keyIdNr = taxReportingDetail.getTrTableBsbPK().getKeyIdNr();
            long keyMeldejahr = taxReportingDetail.getTrTableBsbPK().getKeyMeldejahr();
            String keyMuster = taxReportingDetail.getTrTableBsbPK().getKeyMuster();
            long keyLaufnummer = taxReportingDetail.getTrTableBsbPK().getKeyLaufnummer();

            // Get the number of tax certificate having bsb records with
            // combination of Keys(mandsl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer)
            // other than status H"
            Long bsbCount = taxReportingRepository.findBSBCountToCheckDeletionEligibility(mandsl, keyIdNr, keyMeldejahr,
                    keyMuster, keyLaufnummer, CommonConstants.invalidStatusForHistorydeletion);

            boolean hasOtherThanHistoricizedRecord = false;

            if (bsbCount > 0) {
                hasOtherThanHistoricizedRecord = true;
            }

            // Check Eligibility for deletion
            if (!hasOtherThanHistoricizedRecord) {
                isSafeDelete = true;
                LOG.trace(
                        "[TaxReportingDetailServiceImpl] [setDeleteHistoricizedEligibilityFlag] doesn't have records in bsb Other than historicized Record(status H)");
            }
        }
        // Set the IsSafeDelete field value
        taxReportingDetail.setIsSafeDelete(isSafeDelete);

        LOG.trace(
                "[TaxReportingDetailServiceImpl] [setDeleteHistoricizedEligibilityFlag] Exit setDeleteHistoricizedEligibilityFlag method");

        return taxReportingDetail;
    }

    @Override
    public TrTableBsb fetchTrTableBsb(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException {
        LOG.trace("[TaxReportingDetailServiceImpl] [fetchTrTableBsb] Enter in to fetchTrTableBsb method");

        mandSl = StringUtils.rightPad(mandSl, CommonConstants.MAND_SL_LENGTH);
        keyIdNr = StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
        keyMuster = StringUtils.rightPad(keyMuster, CommonConstants.KEY_MUSTER_LENGTH);

        TrTableBsb trTableBsb = null;
        if (keyMuster.equals("JSTB-III")) {
            trTableBsb = taxReportingRepository.findByMandSlAndKeyIdNRBm3(mandSl, keyIdNr, keyMeldejahr, keyMuster,
                    keyLaufnummer, keySysDatum, keyUhrzeit);
        } else {
            trTableBsb = taxReportingRepository.findByMandSlAndKeyIdNRBm1(mandSl, keyIdNr, keyMeldejahr, keyMuster,
                    keyLaufnummer, keySysDatum, keyUhrzeit);
        }

        if (ObjectUtils.isEmpty(trTableBsb)) {
            LOG.error("[TaxReportingAdjustmentCertificateService] [fetchTrTableBsb] No records found");

            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_101,
                    ValidationMessage.ERROR_CODE_101_DESC);
        }

        return trTableBsb;

    }

    @Override
    public TaxReportingDetail setLatestHistoricalFlag(TaxReportingDetail taxReportingDetail)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingDetailServiceImpl] [setLatestHistoricalFlag] Enter into construct taxreport latest history flag ");
        Long previousRecordCount = taxReportingRepository.findPreviousBSBRecordCountForCorrection(
                taxReportingDetail.getTrTableBsbPK().getKeyIdNr(),
                taxReportingDetail.getTrTableBsbPK().getKeyMeldejahr(),
                taxReportingDetail.getTrTableBsbPK().getKeyMuster(),
                taxReportingDetail.getTrTableBsbPK().getKeyLaufnummer(),
                CommonConstants.previousStatusCheckForCorrection);

        if (previousRecordCount > 0) {
            LOG.trace("[TaxReportingDetailServiceImpl] [setLatestHistoricalFlag] Already a record is in B status ");
            return taxReportingDetail;
        }
        List<Object[]> taxReportingBSBLatestHistoryList = taxReportingRepository
                .findLatestHistoryRecordForCorrection(taxReportingDetail.getTrTableBsbPK().getKeyIdNr());
        final String keyMusterValue = taxReportingDetail.getTrTableBsbPK().getKeyMuster();
        List<TrTableSearch> taxReportsList = getTrTableSearchDtoList(taxReportingBSBLatestHistoryList);
        Optional<TrTableSearch> trTableSearchResult = taxReportsList.stream()
                .filter(taxReportSearch -> taxReportSearch.getIsLatestHistorical().equals(Boolean.TRUE.booleanValue())
                        && taxReportSearch.getKeyLaufnummer() == taxReportingDetail.getTrTableBsbPK().getKeyLaufnummer()
                        && taxReportSearch.getKeyMeldejahr() == taxReportingDetail.getTrTableBsbPK().getKeyMeldejahr()
                        && com.fisglobal.taxreporting.util.StringUtils.compareAfterTrim(taxReportSearch.getKeyMuster(),
                                keyMusterValue)
                        && taxReportSearch.getKeySysDatum() == taxReportingDetail.getTrTableBsbPK().getKeySysDatum()
                        && taxReportSearch.getKeyUhrzeit() == taxReportingDetail.getTrTableBsbPK().getKeyUhrzeit())
                .findAny();

        if (trTableSearchResult.isPresent()) {
            taxReportingDetail.setHasLatestHistoricalBsb(true);
        }
        LOG.trace(
                "[TaxReportingDetailServiceImpl] [setLatestHistoricalFlag] Exit construct taxreport latest history flag ");
        return taxReportingDetail;
    }

    /**
     * This method will construct the response details
     *
     * @param queryResults
     *                         List DB object
     * 
     * @return List of TrTable Search object
     */
    private List<TrTableSearch> getTrTableSearchDtoList(List<Object[]> queryResults) {
        LOG.trace(
                "[TaxReportingDetailServiceImpl] [getTrTableSearchDtoList] Enter into construct taxreport search list");

        List<TrTableSearch> taxReportsList = new ArrayList<TrTableSearch>();

        for (Object[] result : queryResults) {
            TrTableSearch trTableSearchDto = new TrTableSearch(
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[0]),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[1]),
                    BigDecimalUtils.getBigDecimalValue(result[2]).longValue(),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[3]),
                    BigDecimalUtils.getBigDecimalValue(result[4]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[5]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[6]).longValue(),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[7]),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[8]),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[9]),
                    BigDecimalUtils.getBigDecimalValue(result[10]), BigDecimalUtils.getBigDecimalValue(result[11]),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[12]),
                    com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[13]),
                    Boolean.valueOf(com.fisglobal.taxreporting.util.StringUtils.getStringValue(result[14])));
            taxReportsList.add(trTableSearchDto);
        }

        LOG.trace("[TaxReportingDetailServiceImpl] [getTrTableSearchDtoList] Exit construct taxreport search list");

        return taxReportsList;
    }
}
