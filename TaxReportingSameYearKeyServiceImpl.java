package com.fisglobal.taxreporting.service.taxreportingsameyear.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.model.TrTableSearch;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.taxreportingsameyear.TaxReportingSameYearKeyService;
import com.fisglobal.taxreporting.util.BigDecimalUtils;
import com.fisglobal.taxreporting.util.StringUtils;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * This service class fetch the same year key by excluding request data
 */
@Service
public class TaxReportingSameYearKeyServiceImpl implements TaxReportingSameYearKeyService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingSameYearKeyServiceImpl.class);

    @Autowired
    TaxReportingRepository taxReportingRepository;

    @Override
    public void addTaxReportingSameYearKeys(ValidationResults validationResults) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingReviewServiceImpl] [addTaxReportingSameYearKeys] Enter into add taxreport same year key :{}",
                validationResults.getTrTableBsb().taxCertificateInfo());

        TrTableBsbPK trTableBsbPK = validationResults.getTrTableBsb().getTrTableBsbPK();

        List<Object[]> taxReportingBSBSameYearDBList = taxReportingRepository.findBSBSameYearKeyExcludingRequestRecord(
                trTableBsbPK.getMandSl(), trTableBsbPK.getKeyIdNr(), trTableBsbPK.getKeyMeldejahr(),
                trTableBsbPK.getKeyMuster(), trTableBsbPK.getKeyLaufnummer(), trTableBsbPK.getKeySysDatum(),
                trTableBsbPK.getKeyUhrzeit(),
                validationResults.getTrTableBsb().getMeldejahr() != null
                        ? validationResults.getTrTableBsb().getMeldejahr().longValue()
                        : null);

        List<TrTableSearch> taxReportsList = getTrTableSearchDtoList(taxReportingBSBSameYearDBList);

        validationResults.setSameYearPrimaryKeys(taxReportsList);

        LOG.trace("[TaxReportingReviewServiceImpl] [addTaxReportingSameYearKeys] Exit add taxreport same year key :{}",
                validationResults.getTrTableBsb().taxCertificateInfo());

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
                "[TaxReportingSameYearKeyServiceImpl] [getTrTableSearchDtoList] Enter into construct taxreport search list");

        List<TrTableSearch> taxReportsList = new ArrayList<TrTableSearch>();

        for (Object[] result : queryResults) {
            TrTableSearch trTableSearchDto = new TrTableSearch(StringUtils.getStringValue(result[0]),
                    StringUtils.getStringValue(result[1]), BigDecimalUtils.getBigDecimalValue(result[2]).longValue(),
                    StringUtils.getStringValue(result[3]), BigDecimalUtils.getBigDecimalValue(result[4]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[5]).longValue(),
                    BigDecimalUtils.getBigDecimalValue(result[6]).longValue(), StringUtils.getStringValue(result[7]),
                    StringUtils.getStringValue(result[8]), StringUtils.getStringValue(result[9]),
                    BigDecimalUtils.getBigDecimalValue(result[10]), BigDecimalUtils.getBigDecimalValue(result[11]),
                    StringUtils.getStringValue(result[12]), StringUtils.getStringValue(result[13]));
            taxReportsList.add(trTableSearchDto);
        }

        LOG.trace(
                "[TaxReportingSameYearKeyServiceImpl] [getTrTableSearchDtoList] Exit construct taxreport search list");

        return taxReportsList;
    }
}
