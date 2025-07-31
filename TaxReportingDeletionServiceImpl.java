package com.fisglobal.taxreporting.service.taxreportingdeletion.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.service.taxreportingdeletion.TaxReportingDeletionService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting delete service implementation class will remove the subtables
 * record and update the BSB record
 */
@Service
public class TaxReportingDeletionServiceImpl implements TaxReportingDeletionService {
    private static KORLogger LOG = KORLogger.getLogger(TaxReportingDeletionServiceImpl.class);

    @Autowired
    public TaxReportingRepository taxReportingRepository;

    @Autowired
    private CurrentTimeProvider timeProvider;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    @Override
    public ValidationResults deleteTaxReportingData(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException {
        LOG.trace("[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Enter into delete tax report service");

        TrTableBsb result = taxReportingDetailService.fetchTrTableBsb(mandSl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(result);

        if (result.getStatus().equals("B") && (result.getMeldeStatus().equals("00")
                || result.getMeldeStatus().equals("01") || result.getMeldeStatus().equals("02")
                || result.getMeldeStatus().equals("03") || result.getMeldeStatus().equals("04")
                || result.getMeldeStatus().equals("05") || result.getMeldeStatus().equals("06"))) {

            result.setStatus("H");
            result.setStornoKz(" ");
            result.setAenderDatum(BigDecimal.ZERO);
            result.setAenderErfasser(" ");
            result.setAenderZeit(BigDecimal.ZERO);
            result.setAnlageDatum(BigDecimal.ZERO);
            result.setAnlageErfasser(" ");
            result.setAnlageZeit(BigDecimal.ZERO);
            result.setAnlass(" ");
            result.setAnweisungsart(CommonConstants.BSB_ANWEISUNGSART_NEU);
            result.setAusstellungsdat(BigDecimal.ZERO);
            result.setKmId(" ");
            result.setNdNrLfd(null);
            result.setNdTicket(null);

            result.setAntwortStatus(null);
            result.setAntwortFehlerNr(null);

            result.setMeldeStatus("  ");
            result.setRefKmId(null);
            result.setBedbsbTimestamp(timeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_BED_TIMESTAMP_FORMAT));

            LOG.trace(
                    "[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Enter subtables tax report service for BSB: {}",
                    result.taxCertificateInfo());

            result.getTrTableBm1Set().removeIf(trTableBm1 -> trTableBm1.getTrTableBm1PK().getKeySatzart().equals("B"));
            result.getTrTableBm3Set().removeIf(trTableBm3 -> trTableBm3.getTrTableBm3PK().getKeySatzart().equals("B"));

            LOG.trace(
                    "[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Exit subtables tax report  service for BSB: {}",
                    result.taxCertificateInfo());
            taxReportingRepository.save(result);
        } else if (result.getStatus().equals("K") && (result.getMeldeStatus().equals("00")
                || result.getMeldeStatus().equals("01") || result.getMeldeStatus().equals("02")
                || result.getMeldeStatus().equals("03") || result.getMeldeStatus().equals("04")
                || result.getMeldeStatus().equals("05") || result.getMeldeStatus().equals("06"))) {
            result.setStatus("H");
            result.setStornoKz(" ");
            result.setAenderDatum(BigDecimal.ZERO);
            result.setAenderErfasser(" ");
            result.setAenderZeit(BigDecimal.ZERO);
            result.setAnlageDatum(BigDecimal.ZERO);
            result.setAnlageErfasser(" ");
            result.setAnlageZeit(BigDecimal.ZERO);
            result.setAnlass(" ");
            result.setAnweisungsart(CommonConstants.BSB_ANWEISUNGSART_NEU);
            result.setAusstellungsdat(BigDecimal.ZERO);
            result.setKmId(" ");
            result.setNdNrLfd(null);
            result.setNdTicket(null);

            result.setAntwortStatus(null);
            result.setAntwortFehlerNr(null);

            result.setMeldeStatus("  ");
            result.setRefKmId(null);
            result.setBedbsbTimestamp(timeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_BED_TIMESTAMP_FORMAT));

            LOG.trace(
                    "[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Enter subtables tax report service for BSB: {}",
                    result.taxCertificateInfo());
            result.getTrTableBm1Set().removeIf(trTableBm1 -> trTableBm1.getTrTableBm1PK().getKeySatzart().equals("K"));
            result.getTrTableBm3Set().removeIf(trTableBm3 -> trTableBm3.getTrTableBm3PK().getKeySatzart().equals("K"));

            LOG.trace(
                    "[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Exit subtables tax report  service for BSB: {}",
                    result.taxCertificateInfo());

            taxReportingRepository.save(result);
        } else if (result.getStatus().equals("S") && (result.getMeldeStatus().equals("00")
                || result.getMeldeStatus().equals("01") || result.getMeldeStatus().equals("02")
                || result.getMeldeStatus().equals("03") || result.getMeldeStatus().equals("04")
                || result.getMeldeStatus().equals("05") || result.getMeldeStatus().equals("06"))) {
            result.setStatus("H");
            result.setNdTicket(" ");
            result.setNdNrLfd(BigDecimal.ZERO);
            result.setMeldeStatus("  ");
            result.setMeldungJjjjmmtt(BigDecimal.ZERO);
            result.setMeldungUhrHms(BigDecimal.ZERO);
            result.setAntwortStatus(null);
            result.setAntwortFehlerNr(null);
            result.setAnlageDatum(BigDecimal.ZERO);
            result.setAenderZeit(BigDecimal.ZERO);
            result.setAenderErfasser(" ");
            result.setAenderDatum(BigDecimal.ZERO);
            result.setAenderZeit(BigDecimal.ZERO);
            result.setBedbsbTimestamp(timeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_BED_TIMESTAMP_FORMAT));
            result.setStornoKz(" ");
            result.setAnweisungsart(CommonConstants.BSB_ANWEISUNGSART_NEU);
            result.setKmId(" ");
            result.setRefKmId(null);

            result.setAusstellungsdat(BigDecimal.ZERO);
            result.setAnlass(" ");
            result.setAnlageZeit(BigDecimal.ZERO);
            result.setAnlageErfasser(" ");
            result.setNdTicket(null);
            result.setNdNrLfd(null);

            for (TrTableBm1 bm1 : result.getTrTableBm1Set()) {
                bm1.setStornierung(null);
            }

            for (TrTableBm3 bm3 : result.getTrTableBm3Set()) {
                bm3.setStornierung(null);
            }

            taxReportingRepository.save(result);
        }

        else {
            BusinessValidation.businessValidationException(ResponseMessage.MSG_CODE_104,
                    ResponseMessage.MSG_CODE_104_DESC);
        }

        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, result,
                CommonConstants.DELETE_ACTION);

        LOG.trace(
                "[TaxReportingDeletionServiceImpl] [deleteTaxReportingData] Exit delete tax report service for BSB: {}",
                result.taxCertificateInfo());

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_108,
                ResponseMessage.MSG_CODE_108_DESC, null);

        taxReportingResponseService.taxReportingCommonResponse(validationResults, result);
        return validationResults;

    }
}
