package com.fisglobal.taxreporting.service.taxreportingcreatecertificate.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm3Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm3Piv;
import com.fisglobal.taxreporting.enums.BsbStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.service.taxreportingcreatecertificate.TaxReportingAdjustmentCertificate;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.CreationFields;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting adjustment implementation class used to create new record in
 * subtables
 */
@Service
public class TaxReportingAdjustmentCertificateService implements TaxReportingAdjustmentCertificate {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingAdjustmentCertificateService.class);

    @Autowired
    private TaxReportingRepository taxReportingRepository;

    @Autowired
    private CurrentTimeProvider currentTimeProvider;

    @Autowired
    private CreationFields creationFields;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    @SuppressWarnings("null")
    public ValidationResults createCertificate(String mandsl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit)
            throws ValidationResultsException, JsonProcessingException {
        LOG.trace(
                "[TaxReportingAdjustmentCertificateService] [createCertificate] Enter into create tax report service");

        TrTableBsb currentBsb = taxReportingDetailService.fetchTrTableBsb(mandsl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        if (!ObjectUtils.isEmpty(currentBsb)) {
            LOG.trace(
                    "[TaxReportingAdjustmentCertificateService] [createCertificate] create tax report service BSB: {}",
                    currentBsb.taxCertificateInfo());
        }

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(currentBsb);

        if (!currentBsb.getStatus().equals("H")) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + "H");
        }

        mandsl = StringUtils.rightPad(mandsl, CommonConstants.MAND_SL_LENGTH);
        keyIdNr = StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
        keyMuster = StringUtils.rightPad(keyMuster, CommonConstants.KEY_MUSTER_LENGTH);

        List<TrTableBsb> previousBsbList = new ArrayList<>(taxReportingRepository
                .findByKeyNokeySysDatumNorUhrzeit(mandsl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer));

        if (CollectionUtils.isEmpty(previousBsbList)) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_103,
                    ValidationMessage.ERROR_CODE_103_DESC);
        }

        if (!CollectionUtils.isEmpty(previousBsbList)) {
            Optional<TrTableBsb> trTableBsbObject = previousBsbList.stream()
                    .filter(bsbObject -> (BsbStatus.S.toString().equals(bsbObject.getStatus()))).findAny();
            if (trTableBsbObject.isPresent()) {
                BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_110,
                        ValidationMessage.ERROR_CODE_110_DESC, CommonConstants.TR_TABLE_BSB_STATUS);
            }
        }

        TrTableBsb previousBsb = previousBsbList.iterator().next();

        currentBsb.setStatus("K");

        String prevKmId = previousBsb.getKmId();
        currentBsb.setRefKmId(prevKmId);

        String prevKm_Id_Tail = prevKmId.substring(prevKmId.lastIndexOf('-') + 1).trim();
        int prevKmIdIndex = Integer.parseInt(prevKm_Id_Tail);
        String prev_head = prevKmId.substring(0, prevKmId.lastIndexOf('-'));

        prevKmIdIndex++;

        String new_km_id = prev_head + "-"
                + StringUtils.leftPad(String.valueOf(prevKmIdIndex), prevKm_Id_Tail.length(), '0');
        currentBsb.setKmId(new_km_id);

        String prevNdTicket = previousBsb.getNdTicket();
        int prevNdTicketIndex = Integer.parseInt(prevNdTicket.substring(prevNdTicket.lastIndexOf('-') + 1).trim());

        String tail = prevNdTicket.substring(0, prevNdTicket.lastIndexOf('-'));

        currentBsb.setNdTicket(tail + "-" + (prevNdTicketIndex + 1));

        currentBsb.setMeldeStatus("00");
        currentBsb.setAnweisungsart("Korrektur");
        currentBsb.setAnlass("Fachliche Berichtigung einer Steuerbescheinigung");

        creationFields.update(currentBsb);

        currentBsb.setAusstellungsdat(
                new BigDecimal(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_DATE_FORMAT)));
        currentBsb.setNdNrLfd(BigDecimal.ONE);

        LOG.trace("[TaxReportingAdjustmentCertificateService] [createCertificate] Enter into subtable creation");

        for (TrTableBm1 bm1 : currentBsb.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals("H")) {
                TrTableBm1 result = cloneBm1Deep(bm1);
                result.getTrTableBm1PK().setKeySatzart("K");
                currentBsb.getTrTableBm1Set().add(result);

                Set<TrTableBm1Aam> bm1aamSet = result.getTrTableBm1AamSet();

                for (TrTableBm1Aam bm1Aam : bm1aamSet) {
                    bm1Aam.getTrTableAamPK().setKeySatzart("K");
                }

                Set<TrTableBm1Akb> bm1akbSet = result.getTrTableBm1AkbSet();

                for (TrTableBm1Akb bm1Akb : bm1akbSet) {
                    bm1Akb.getTrTableAkbPK().setKeySatzart("K");
                }

                Set<TrTableBm1Ake> bm1akeSet = result.getTrTableBm1AkeSet();

                for (TrTableBm1Ake bm1Ake : bm1akeSet) {
                    bm1Ake.getTrTableAkePK().setKeySatzart("K");
                }

                Set<TrTableBm1Eik> bm1eikSet = result.getTrTableBm1EikSet();

                for (TrTableBm1Eik bm1Eik : bm1eikSet) {
                    bm1Eik.getTrTableEikPK().setKeySatzart("K");
                }

                Set<TrTableBm1Piv> bm1PivSet = result.getTrTableBm1PivSet();

                for (TrTableBm1Piv bm1Piv : bm1PivSet) {
                    bm1Piv.getTrTablePivPK().setKeySatzart("K");
                }

                currentBsb.getTrTableBm1Set().add(result);
            }
        }

        for (TrTableBm3 bm3 : currentBsb.getTrTableBm3Set()) {
            if (bm3.getTrTableBm3PK().getKeySatzart().equals("H")) {
                TrTableBm3 result = cloneBm3Deep(bm3);
                result.getTrTableBm3PK().setKeySatzart("K");
                currentBsb.getTrTableBm3Set().add(result);

                Set<TrTableBm3Aam> bm3aamSet = result.getTrTableBm3AamSet();
                for (TrTableBm3Aam bm3Aam : bm3aamSet) {
                    bm3Aam.getTrTableAamPK().setKeySatzart("K");
                }

                Set<TrTableBm3Akb> bm3akbSet = result.getTrTableBm3AkbSet();

                for (TrTableBm3Akb bm3Akb : bm3akbSet) {
                    bm3Akb.getTrTableAkbPK().setKeySatzart("K");
                }

                Set<TrTableBm3Ake> bm3akeSet = result.getTrTableBm3AkeSet();

                for (TrTableBm3Ake bm3Ake : bm3akeSet) {
                    bm3Ake.getTrTableAkePK().setKeySatzart("K");
                }

                Set<TrTableBm3Eik> bm3eikSet = result.getTrTableBm3EikSet();

                for (TrTableBm3Eik bm3Eik : bm3eikSet) {
                    bm3Eik.getTrTableEikPK().setKeySatzart("K");
                }

                Set<TrTableBm3Piv> bm3PivSet = result.getTrTableBm3PivSet();

                for (TrTableBm3Piv bm3Piv : bm3PivSet) {
                    bm3Piv.getTrTablePivPK().setKeySatzart("K");
                }

                currentBsb.getTrTableBm3Set().add(result);
            }
        }

        LOG.trace("[TaxReportingAdjustmentCertificateService] [createCertificate] Exit subtable creation");

        taxReportingRepository.save(currentBsb);
        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, currentBsb,
                CommonConstants.ADJUST_ACTION);

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_102,
                ResponseMessage.MSG_CODE_102_DESC, null);
        taxReportingResponseService.taxReportingCommonResponse(validationResults, currentBsb);

        LOG.trace("[TaxReportingAdjustmentCertificateService] [createCertificate] Exit create tax report service");

        return validationResults;
    }

    protected TrTableBm1 cloneBm1Deep(TrTableBm1 bm1) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TrTableBm1 bm2 = objectMapper.readValue(objectMapper.writeValueAsString(bm1), TrTableBm1.class);
        return bm2;
    }

    protected TrTableBm3 cloneBm3Deep(TrTableBm3 bm3) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TrTableBm3 trbm3 = objectMapper.readValue(objectMapper.writeValueAsString(bm3), TrTableBm3.class);
        return trbm3;
    }
}
