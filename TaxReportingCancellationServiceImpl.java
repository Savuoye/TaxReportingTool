package com.fisglobal.taxreporting.service.taxreportingcancellation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.util.TrTableBsbLoggingUtil;
import com.fisglobal.taxreporting.enums.BsbStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.taxreportingcancellation.TaxReportingCancellationService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.CreationFields;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting cancel implementation class will cancel the tax report data in
 * different workflow status
 */
@Service
public class TaxReportingCancellationServiceImpl implements TaxReportingCancellationService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingCancellationServiceImpl.class);

    @Autowired
    private TaxReportingRepository taxReportingRepository;

    @Autowired
    private CreationFields creationFields;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    public ValidationResults createCertificateCancellation(String mandsl, String keyIdNr, long keyMeldejahr,
            String keyMuster, long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingCancellationServiceImpl] [createCertificateCancellation] Enter into cancel tax report service");

        mandsl = StringUtils.rightPad(mandsl, CommonConstants.MAND_SL_LENGTH);
        keyIdNr = StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
        keyMuster = StringUtils.rightPad(keyMuster, CommonConstants.KEY_MUSTER_LENGTH);

        List<TrTableBsb> previousList = new ArrayList<>(taxReportingRepository.findByKeyNokeySysDatumNorUhrzeit(mandsl,
                keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer));

        TrTableBsb current = taxReportingDetailService.fetchTrTableBsb(mandsl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        if (!ObjectUtils.isEmpty(current)) {
            LOG.trace("[TaxReportingCancellationServiceImpl] [createCertificateCancellation] cancel tax report BSB: {}",
                    current.taxCertificateInfo());
        }

        if (!current.getStatus().equals(BsbStatus.H.name())) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + BsbStatus.H.name());
        }

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(current);

        List<String> reportingMeldeStatusList = List.of("11", "12", "13");

        creationFields.update(current);

        current.setStatus("S");

        current.setStornoKz("1");

        current.setMeldeStatus("00");

        current.setAusstellungsdat(current.getAnlageDatum());

        current.setAnlass("Fachliche Stornierung einer Steuerbescheinigung");

        for (TrTableBm1 bm1 : current.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals("H")) {
                bm1.setStornierung("1");
            }
        }

        for (TrTableBm3 bm3 : current.getTrTableBm3Set()) {
            if (bm3.getTrTableBm3PK().getKeySatzart().equals("H")) {
                bm3.setStornierung("1");
            }
        }

        if (previousList.size() > 0) {

            TrTableBsb previous = previousList.get(0);

            if (reportingMeldeStatusList.contains(previous.getMeldeStatus())) {

                current.setAnweisungsart(CommonConstants.BSB_ANWEISUNGSART_KORRECKTUR);
                current.setNdNrLfd(new BigDecimal("1"));

                if (!StringUtils.isEmpty(previous.getKmId())) {
                    current.setKmId(getFieldWithUpdatedIndex(previous.getKmId(), previous.getTrTableBsbPK(), true));
                }

                current.setRefKmId(previous.getKmId());

                if (!StringUtils.isEmpty(previous.getNdTicket())) {
                    current.setNdTicket(getFieldWithUpdatedIndex(previous.getNdTicket(), previous.getTrTableBsbPK()));
                }
            }
        } else {

            current.setKmId(current.getTrTableBsbPK().getKeyMeldejahr() + "-BSB-" + current.getAnlageDatum() + "-"
                    + current.getAnlageZeit() + "-TR-Tool-00001");

            current.setNdTicket(current.getAnlageDatum() + "-" + current.getAnlageZeit() + "-TR-Tool-1");
        }

        taxReportingRepository.save(current);

        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, current,
                CommonConstants.CANCEL_ACTION);

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_103,
                ResponseMessage.MSG_CODE_103_DESC, null);

        taxReportingResponseService.taxReportingCommonResponse(validationResults, current);
        LOG.trace(
                "[TaxReportingCancellationServiceImpl] [createCertificateCancellation] Exit cancel tax report service");

        return validationResults;

    }

    private String getFieldWithUpdatedIndex(String field, TrTableBsbPK trTableBsbPK) {
        return getFieldWithUpdatedIndex(field, trTableBsbPK, false);
    }

    private String getFieldWithUpdatedIndex(String field, TrTableBsbPK trTableBsbPK, boolean leftPad) {
        String KeyIdNr = trTableBsbPK.getKeyIdNr();
        long sysDatum = trTableBsbPK.getKeySysDatum();
        long uhrzeit = trTableBsbPK.getKeyUhrzeit();

        if (leftPad) { // km_id
            LOG.trace(
                    "[TaxReportingCancellationServiceImpl] [getFieldWithUpdatedIndex] Enter into index creation for field:{}",
                    TrTableBsbLoggingUtil.getTaxCertficateDetailsToLog(field, KeyIdNr, sysDatum, uhrzeit));
        } else { // nd_ticket
            LOG.trace(
                    "[TaxReportingCancellationServiceImpl] [getFieldWithUpdatedIndex] Enter into index creation for field:{}",
                    field);
        }

        String fieldTrimmed = field.trim();
        String tail = fieldTrimmed.substring(fieldTrimmed.lastIndexOf('-') + 1);
        int previousIndex = Integer.parseInt(tail);
        String head = fieldTrimmed.substring(0, fieldTrimmed.lastIndexOf('-'));

        if (leftPad) { // km_id
            LOG.trace(
                    "[TaxReportingCancellationServiceImpl] [getFieldWithUpdatedIndex] Exit index creation for field:{}",
                    TrTableBsbLoggingUtil.getTaxCertficateDetailsToLog(field, KeyIdNr, sysDatum, uhrzeit));
            return head + "-" + StringUtils.leftPad(String.valueOf(previousIndex + 1), tail.length(), '0');
        }

        LOG.trace("[TaxReportingCancellationServiceImpl] [getFieldWithUpdatedIndex] Exit index creation for field:{}",
                field);

        return head + "-" + (previousIndex + 1);

    }
}
