package com.fisglobal.taxreporting.service.taxreportingresponse.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.taxreporting.controller.dto.TaxReportingDetail;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.BsbStatus;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.service.taxreportingsameyear.TaxReportingSameYearKeyService;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * This service implementation class construct the response object
 */
@Service
public class TaxReportingResponseServiceImpl implements TaxReportingResponseService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingResponseServiceImpl.class);

    @Autowired
    TaxReportingRepository taxReportingRepository;

    @Autowired
    private TaxReportingDetailService taxReportingServiceDetail;

    @Autowired
    private TaxReportingSameYearKeyService taxReportingSameYearKeyService;

    @Value("${taxreporting.response.sameYearKeys.enabled:false}")
    Boolean sameYearKeysEnabled;

    @Override
    public void taxReportingCommonResponse(ValidationResults validationResults, TrTableBsb bsbAfter)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxReportingCommonResponse] Enter in to taxReportingCommonResponse method for bsbAfter: {}",
                bsbAfter.taxCertificateInfo());

        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbDTO = new ObjectMapper()
                .convertValue(bsbAfter, com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb.class);

        validationResults.setTrTableBsb(trTableBsbDTO);

        if (sameYearKeysEnabled) {
            taxReportingSameYearKeyService.addTaxReportingSameYearKeys(validationResults);
        }

        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxReportingCommonResponse] Exit  taxReportingCommonResponse method for bsbAfter: {}",
                bsbAfter.taxCertificateInfo());
    }

    @Override
    public TaxReportingDetail taxreportingDetailResponse(TrTableBsb trTableBsb) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxreportingDetailResponse] Enter in to taxreportingDetailResponse method for BSB: {}",
                trTableBsb.taxCertificateInfo());
        com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsbDTO = new ObjectMapper()
                .convertValue(trTableBsb, com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb.class);

        TaxReportingDetail taxReportingDetail = taxReportingServiceDetail.getPreviousBSBRecordCount(trTableBsbDTO);

        taxReportingDetail = taxReportingServiceDetail.setDeleteHistoricizedEligibilityFlag(taxReportingDetail);

        if (taxReportingDetail.getStatus().equals(BsbStatus.H.name())) {
            taxReportingDetail = taxReportingServiceDetail.setLatestHistoricalFlag(taxReportingDetail);
        }

        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxreportingDetailResponse] Exit taxreportingDetailResponse method for BSB: {}",
                trTableBsb.taxCertificateInfo());
        return taxReportingDetail;
    }

    @Override
    public void taxReportingHistoryDeleteResponse(ValidationResults validationResults)
            throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxReportingHistoryDeleteResponse] Enter into taxReportingHistoryDeleteResponse method");

        taxReportingSameYearKeyService.addTaxReportingSameYearKeys(validationResults);
        LOG.trace(
                "[TaxReportingResponseServiceImpl] [taxReportingHistoryDeleteResponse] Exit  taxReportingHistoryDeleteResponse method");
    }
}
