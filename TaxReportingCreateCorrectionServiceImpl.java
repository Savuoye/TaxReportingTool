package com.fisglobal.taxreporting.service.taxreportingcreatecertificate.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.fisglobal.taxreporting.enums.MeldeStatus;
import com.fisglobal.taxreporting.enums.SteuerZuordStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.BusinessValidation;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.model.TrTableSearch;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.service.taxreportingcreatecertificate.TaxReportingCreateCorrectionService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.BigDecimalUtils;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.CreationFields;
import com.fisglobal.taxreporting.util.RemoveEmptySpaces;
import com.fisglobal.taxreporting.util.StringUtils;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax reporting create correction implementation class used to create new record in
 * subtables with status B and update the BSB record status
 */
@Service
public class TaxReportingCreateCorrectionServiceImpl implements TaxReportingCreateCorrectionService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingCreateCorrectionServiceImpl.class);

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
    public ValidationResults createCorrectionCertificate(String mandsl, String keyIdNr, long keyMeldejahr,
            String keyMuster, long keyLaufnummer, long keySysDatum, long keyUhrzeit)
            throws ValidationResultsException, JsonProcessingException {
        LOG.trace(
                "[TaxReportingCreateCorrectionServiceImpl] [createCorrectionCertificate] Enter into create tax report service");

        TrTableBsb currentBsb = taxReportingDetailService.fetchTrTableBsb(mandsl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        if (!ObjectUtils.isEmpty(currentBsb)) {
            LOG.trace(
                    "[TaxReportingCreateCorrectionServiceImpl] [createCorrectionCertificate] create tax report service BSB: {}",
                    currentBsb.taxCertificateInfo());
        }

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(currentBsb);

        if (ObjectUtils.isEmpty(currentBsb)) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_101,
                    ValidationMessage.ERROR_CODE_101_DESC);
        }
        if (!currentBsb.getStatus().equals(BsbStatus.H.toString())) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + BsbStatus.H.toString(),
                    CommonConstants.TR_TABLE_BSB_STATUS);
        }

        keyIdNr = org.apache.commons.lang3.StringUtils.rightPad(keyIdNr, CommonConstants.KEY_ID_NR_LENGTH);
        keyMuster = org.apache.commons.lang3.StringUtils.rightPad(keyMuster, CommonConstants.KEY_MUSTER_LENGTH);

        Long previousRecordCount = taxReportingRepository.findPreviousBSBRecordCountForCorrection(
                currentBsb.getTrTableBsbPK().getKeyIdNr(), currentBsb.getTrTableBsbPK().getKeyMeldejahr(),
                currentBsb.getTrTableBsbPK().getKeyMuster(), currentBsb.getTrTableBsbPK().getKeyLaufnummer(),
                CommonConstants.previousStatusCheckForCorrection);

        if (previousRecordCount > 0) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_123,
                    ValidationMessage.ERROR_CODE_123_DESC, CommonConstants.TR_TABLE_BSB_STATUS);
        }

        List<Object[]> taxReportingBSBLatestHistoryList = taxReportingRepository
                .findLatestHistoryRecordForCorrection(currentBsb.getTrTableBsbPK().getKeyIdNr());
        final String keyMusterValue = keyMuster;
        List<TrTableSearch> taxReportsList = getTrTableSearchDtoList(taxReportingBSBLatestHistoryList);
        Optional<TrTableSearch> trTableSearchResult = taxReportsList.stream()
                .filter(taxReportSearch -> taxReportSearch.getIsLatestHistorical().equals(Boolean.TRUE.booleanValue())
                        && taxReportSearch.getKeyLaufnummer() == keyLaufnummer
                        && taxReportSearch.getKeyMeldejahr() == keyMeldejahr
                        && StringUtils.compareAfterTrim(taxReportSearch.getKeyMuster(), keyMusterValue)
                        && taxReportSearch.getKeySysDatum() == keySysDatum
                        && taxReportSearch.getKeyUhrzeit() == keyUhrzeit)
                .findAny();

        if (!trTableSearchResult.isPresent()) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_124,
                    ValidationMessage.ERROR_CODE_124_DESC, CommonConstants.IS_LATEST_HISTORICAL);
        }

        constructKmId(currentBsb);
        constructNdTicket(currentBsb);

        currentBsb.setStatus(BsbStatus.B.toString());
        currentBsb.setMeldeStatus(MeldeStatus._00.toString());
        currentBsb.setAnlass(CommonConstants.BSB_ANLASS_DESCRIPTION);
        currentBsb.setDokuArt(CommonConstants.BSB_DOKU_ART);

        creationFields.update(currentBsb);

        currentBsb.setAusstellungsdat(
                new BigDecimal(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_DATE_FORMAT)));

        LOG.trace(
                "[TaxReportingCreateCorrectionServiceImpl] [createCorrectionCertificate] Enter into subtable creation");

        for (TrTableBm1 bm1 : currentBsb.getTrTableBm1Set()) {
            if (bm1.getTrTableBm1PK().getKeySatzart().equals(BsbStatus.H.toString())) {
                TrTableBm1 result = cloneBm1Deep(bm1);
                result.getTrTableBm1PK().setKeySatzart(BsbStatus.B.toString());
                currentBsb.getTrTableBm1Set().add(result);

                Set<TrTableBm1Aam> bm1aamSet = result.getTrTableBm1AamSet();

                for (TrTableBm1Aam bm1Aam : bm1aamSet) {
                    bm1Aam.getTrTableAamPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm1Akb> bm1akbSet = result.getTrTableBm1AkbSet();

                for (TrTableBm1Akb bm1Akb : bm1akbSet) {
                    bm1Akb.getTrTableAkbPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm1Ake> bm1akeSet = result.getTrTableBm1AkeSet();

                for (TrTableBm1Ake bm1Ake : bm1akeSet) {
                    bm1Ake.getTrTableAkePK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm1Eik> bm1eikSet = result.getTrTableBm1EikSet();

                for (TrTableBm1Eik bm1Eik : bm1eikSet) {
                    bm1Eik.getTrTableEikPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm1Piv> bm1PivSet = result.getTrTableBm1PivSet();

                for (TrTableBm1Piv bm1Piv : bm1PivSet) {
                    bm1Piv.getTrTablePivPK().setKeySatzart(BsbStatus.B.toString());
                }

                currentBsb.getTrTableBm1Set().add(result);
            }
        }

        for (TrTableBm3 bm3 : currentBsb.getTrTableBm3Set()) {
            if (bm3.getTrTableBm3PK().getKeySatzart().equals(BsbStatus.H.toString())) {
                TrTableBm3 result = cloneBm3Deep(bm3);
                result.getTrTableBm3PK().setKeySatzart(BsbStatus.B.toString());
                currentBsb.getTrTableBm3Set().add(result);

                Set<TrTableBm3Aam> bm3aamSet = result.getTrTableBm3AamSet();
                for (TrTableBm3Aam bm3Aam : bm3aamSet) {
                    bm3Aam.getTrTableAamPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm3Akb> bm3akbSet = result.getTrTableBm3AkbSet();

                for (TrTableBm3Akb bm3Akb : bm3akbSet) {
                    bm3Akb.getTrTableAkbPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm3Ake> bm3akeSet = result.getTrTableBm3AkeSet();

                for (TrTableBm3Ake bm3Ake : bm3akeSet) {
                    bm3Ake.getTrTableAkePK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm3Eik> bm3eikSet = result.getTrTableBm3EikSet();

                for (TrTableBm3Eik bm3Eik : bm3eikSet) {
                    bm3Eik.getTrTableEikPK().setKeySatzart(BsbStatus.B.toString());
                }

                Set<TrTableBm3Piv> bm3PivSet = result.getTrTableBm3PivSet();

                for (TrTableBm3Piv bm3Piv : bm3PivSet) {
                    bm3Piv.getTrTablePivPK().setKeySatzart(BsbStatus.B.toString());
                }

                currentBsb.getTrTableBm3Set().add(result);
            }
        }

        LOG.trace("[TaxReportingCreateCorrectionServiceImpl] [createCorrectionCertificate] Exit subtable creation");
        RemoveEmptySpaces removeEmptySpaces = new RemoveEmptySpaces();
        removeEmptySpaces.clean(currentBsb);
        taxReportingRepository.save(currentBsb);
        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, currentBsb,
                CommonConstants.CORRECTION_ACTION);
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_110,
                ResponseMessage.MSG_CODE_110_DESC, null);
        taxReportingResponseService.taxReportingCommonResponse(validationResults, currentBsb);

        LOG.trace(
                "[TaxReportingCreateCorrectionServiceImpl] [createCorrectionCertificate] Exit create tax report service");

        return validationResults;
    }

    private void constructKmId(TrTableBsb currentBsb) throws ValidationResultsException {
        StringBuffer kmIdBuffer = new StringBuffer();
        kmIdBuffer.append(currentBsb.getMeldejahr());
        kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        kmIdBuffer.append(CommonConstants.BSB);
        kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        String steuerZuord = currentBsb.getSteuerZuord();
        if (ObjectUtils.isEmpty(steuerZuord) || ObjectUtils.isEmpty(SteuerZuordStatus.fromValue(steuerZuord))) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_125,
                    ValidationMessage.ERROR_CODE_125_DESC, CommonConstants.TR_TABLE_BSB_STEUER_ZUORD);
        }
        if (SteuerZuordStatus.P.toString().equals(steuerZuord)) {
            if (StringUtils.isBlank(currentBsb.getPersonId())) {
                BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_121,
                        ValidationMessage.ERROR_CODE_121_DESC, CommonConstants.TR_TABLE_BSB_PERSON_ID);
            }
            if (currentBsb.getPersonId().length() > 10) {
                kmIdBuffer.append(currentBsb.getPersonId().substring(0, 10));
            } else {
                kmIdBuffer.append(currentBsb.getPersonId());
            }
            kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        } else {
            if (StringUtils.isBlank(currentBsb.getKontoId())) {
                BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_122,
                        ValidationMessage.ERROR_CODE_122_DESC, CommonConstants.TR_TABLE_BSB_KONTO_ID);
            }
            if (!ObjectUtils.isEmpty(currentBsb.getKontoId()) && currentBsb.getKontoId().length() > 10) {
                kmIdBuffer.append(currentBsb.getKontoId().substring(0, 10));
                kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
            } else if (!ObjectUtils.isEmpty(currentBsb.getKontoId())) {
                kmIdBuffer.append(currentBsb.getKontoId());
                kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
            }
        }

        kmIdBuffer.append(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_DATE_FORMAT));
        kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        kmIdBuffer.append(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_TIME_FORMAT));
        kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        kmIdBuffer.append(currentBsb.getTrTableBsbPK().getKeyIdNr().trim());
        kmIdBuffer.append(CommonConstants.HYPEN_VALUE);
        kmIdBuffer.append(CommonConstants.LAUFENDE_NUMMER);

        currentBsb.setKmId(kmIdBuffer.toString());
    }

    private void constructNdTicket(TrTableBsb currentBsb) {
        String steuerZuord = currentBsb.getSteuerZuord();
        StringBuffer ndTicketBuffer = new StringBuffer();
        if (SteuerZuordStatus.P.toString().equals(steuerZuord)) {
            if (currentBsb.getPersonId().length() > 10) {
                ndTicketBuffer.append(currentBsb.getPersonId().substring(0, 10));
            } else {
                ndTicketBuffer.append(currentBsb.getPersonId());
            }
            ndTicketBuffer.append(CommonConstants.HYPEN_VALUE);
        } else {
            if (!ObjectUtils.isEmpty(currentBsb.getKontoId()) && currentBsb.getKontoId().length() > 10) {
                ndTicketBuffer.append(currentBsb.getKontoId().substring(0, 10));
                ndTicketBuffer.append(CommonConstants.HYPEN_VALUE);
            } else if (!ObjectUtils.isEmpty(currentBsb.getKontoId())) {
                ndTicketBuffer.append(currentBsb.getKontoId());
                ndTicketBuffer.append(CommonConstants.HYPEN_VALUE);
            }
        }

        ndTicketBuffer.append(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_DATE_FORMAT));
        ndTicketBuffer.append(CommonConstants.HYPEN_VALUE);
        ndTicketBuffer.append(currentTimeProvider.getCurrentTime(CommonConstants.TR_TABLE_BSB_TIME_FORMAT));

        currentBsb.setNdTicket(ndTicketBuffer.toString());
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
                "[TaxReportingCreateCorrectionServiceImpl] [getTrTableSearchDtoList] Enter into construct taxreport search list");

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

        LOG.trace(
                "[TaxReportingCreateCorrectionServiceImpl] [getTrTableSearchDtoList] Exit construct taxreport search list");

        return taxReportsList;
    }
}
