package com.fisglobal.taxreporting.service.taxreportingreview.impl;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
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
import com.fisglobal.taxreporting.service.infrastructure.UserNameProvider;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.service.taxreportingreview.TaxReportingReviewService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.LastModificationFields;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * This review class implemented to validate the correction and historicized
 * record and complete the review process
 */
@Service
public class TaxReportingReviewServiceImpl implements TaxReportingReviewService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingReviewServiceImpl.class);

    @Autowired
    public TaxReportingRepository taxReportingRepository;

    @Autowired
    public UserNameProvider userNameProvider;

    @Autowired
    public CurrentTimeProvider timeProvider;

    @Autowired
    LastModificationFields lastModificationFields;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    public ValidationResults markForReview(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException {
        LOG.trace("[TaxReportingReviewServiceImpl] [markDataForReview] Enter into review tax report service");

        TrTableBsb originalBsb = taxReportingDetailService.fetchTrTableBsb(mandSl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        if (!ObjectUtils.isEmpty(originalBsb)) {
            LOG.trace("[TaxReportingReviewServiceImpl] [markDataForReview] Review tax report BSB: {}",
                    originalBsb.taxCertificateInfo());
        }

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(originalBsb);

        String[] statusesList = { "00" };

        lastModificationFields.update(originalBsb);

        if (originalBsb.getStatus().equals("B") && Arrays.asList(statusesList).contains(originalBsb.getMeldeStatus())) {
            originalBsb.setMeldeStatus("01");
        } else if (originalBsb.getStatus().equals("K")
                && Arrays.asList(statusesList).contains(originalBsb.getMeldeStatus())) {
            originalBsb.setMeldeStatus("03");
        } else if (originalBsb.getStatus().equals("S")
                && Arrays.asList(statusesList).contains(originalBsb.getMeldeStatus())) {
            originalBsb.setMeldeStatus("02");
        } else {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_102,
                    ValidationMessage.ERROR_CODE_102_DESC + String.join(",", statusesList));
        }

        if (!originalBsb.getStatus().equals("S")) {
            compareHistoryAndCurrentRecord(originalBsb);
        }

        taxReportingRepository.save(originalBsb);

        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, originalBsb,
                CommonConstants.REVIEW_ACTION);

        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_101,
                ResponseMessage.MSG_CODE_101_DESC, null);
        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_100,
                originalBsb.getMeldeStatus(), "melde_status");

        taxReportingResponseService.taxReportingCommonResponse(validationResults, originalBsb);
        LOG.trace("[TaxReportingReviewServiceImpl] [markDataForReview] Exit review tax report service");

        return validationResults;
    }

    private void compareHistoryAndCurrentRecord(TrTableBsb originalBsb) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareHistoryAndCurrentRecord] Enter into compare current and history record for BSB: {}",
                originalBsb.taxCertificateInfo());

        Boolean isHistoryAndCurrentRecordSame = false;
        if (!CollectionUtils.isEmpty(originalBsb.getTrTableBm3Set())) {
            isHistoryAndCurrentRecordSame = compareBm3HistoryAndCurrentRecord(originalBsb);
        }
        if (!CollectionUtils.isEmpty(originalBsb.getTrTableBm1Set())) {
            isHistoryAndCurrentRecordSame = compareBm1HistoryAndCurrentRecord(originalBsb);
        }

        if (isHistoryAndCurrentRecordSame) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_116,
                    ValidationMessage.ERROR_CODE_116_DESC);
        }
        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareHistoryAndCurrentRecord] Exit compare current and history record for BSB: {}",
                originalBsb.taxCertificateInfo());
    }

    private Boolean compareBm3HistoryAndCurrentRecord(TrTableBsb originalBsb) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareBm3HistoryAndCurrentRecord] Enter into compare current and history BM3 record for BSB: {}",
                originalBsb.taxCertificateInfo());

        boolean isHistoryAndCurrentRecordSame = true;

        TrTableBm3 historicizedTrTableBm3 = null;
        TrTableBm3 currentTrTableBm3 = null;

        for (TrTableBm3 trTableBm3 : originalBsb.getTrTableBm3Set()) {
            if (trTableBm3.getTrTableBm3PK().getKeySatzart().equals(BsbStatus.H.toString())) {
                historicizedTrTableBm3 = trTableBm3;
            }
            if (trTableBm3.getTrTableBm3PK().getKeySatzart().equals(originalBsb.getStatus())
                    && !originalBsb.getStatus().equals(BsbStatus.S.toString())) {
                currentTrTableBm3 = trTableBm3;
            }
        }

        if (ObjectUtils.isEmpty(historicizedTrTableBm3)) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + BsbStatus.H.name());
        }

        boolean compareBm3Object = EqualsBuilder.reflectionEquals(historicizedTrTableBm3, currentTrTableBm3,
                CommonConstants.bm3ExcludeList);

        if (!compareBm3Object) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }
        isHistoryAndCurrentRecordSame = compareTrTableBm3Subtables(historicizedTrTableBm3, currentTrTableBm3);

        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareBm3HistoryAndCurrentRecord] Exit compare current and history BM3 record for BSB: {}",
                originalBsb.taxCertificateInfo());

        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm3Subtables(TrTableBm3 historicizedTrTableBm3, TrTableBm3 currentTrTableBm3) {
        boolean isHistoryAndCurrentRecordSame = true;

        isHistoryAndCurrentRecordSame = compareTrTableBm3Akb(historicizedTrTableBm3, currentTrTableBm3,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm3Ake(historicizedTrTableBm3, currentTrTableBm3,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm3Eik(historicizedTrTableBm3, currentTrTableBm3,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm3Piv(historicizedTrTableBm3, currentTrTableBm3,
                isHistoryAndCurrentRecordSame);

        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm3Akb(TrTableBm3 historicizedTrTableBm3, TrTableBm3 currentTrTableBm3,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm3.getTrTableBm3AkbSet().size() != currentTrTableBm3.getTrTableBm3AkbSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm3Akb historicizedTrtableBm3Akb : historicizedTrTableBm3.getTrTableBm3AkbSet()) {
            boolean compareAkbObject = false;
            for (TrTableBm3Akb currentTrTableBm3Akb : currentTrTableBm3.getTrTableBm3AkbSet()) {
                compareAkbObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm3Akb, currentTrTableBm3Akb,
                        CommonConstants.subTableExcludeList);
                if (compareAkbObject) {
                    break;
                }
            }

            if (!compareAkbObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm3Ake(TrTableBm3 historicizedTrTableBm3, TrTableBm3 currentTrTableBm3,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm3.getTrTableBm3AkeSet().size() != currentTrTableBm3.getTrTableBm3AkeSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm3Ake historicizedTrtableBm3Ake : historicizedTrTableBm3.getTrTableBm3AkeSet()) {
            boolean compareAkeObject = false;
            for (TrTableBm3Ake currentTrTableBm3Ake : currentTrTableBm3.getTrTableBm3AkeSet()) {
                compareAkeObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm3Ake, currentTrTableBm3Ake,
                        CommonConstants.subTableExcludeList);
                if (compareAkeObject) {
                    break;
                }
            }

            if (!compareAkeObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm3Eik(TrTableBm3 historicizedTrTableBm3, TrTableBm3 currentTrTableBm3,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm3.getTrTableBm3EikSet().size() != currentTrTableBm3.getTrTableBm3EikSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm3Eik historicizedTrtableBm3Eik : historicizedTrTableBm3.getTrTableBm3EikSet()) {
            boolean compareEikObject = false;
            for (TrTableBm3Eik currentTrTableBm3Eik : currentTrTableBm3.getTrTableBm3EikSet()) {
                compareEikObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm3Eik, currentTrTableBm3Eik,
                        CommonConstants.subTableExcludeList);
                if (compareEikObject) {
                    break;
                }
            }

            if (!compareEikObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm3Piv(TrTableBm3 historicizedTrTableBm3, TrTableBm3 currentTrTableBm3,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm3.getTrTableBm3PivSet().size() != currentTrTableBm3.getTrTableBm3PivSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm3Piv historicizedTrtableBm3Piv : historicizedTrTableBm3.getTrTableBm3PivSet()) {
            boolean comparePivObject = false;
            for (TrTableBm3Piv currentTrTableBm3Piv : currentTrTableBm3.getTrTableBm3PivSet()) {
                comparePivObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm3Piv, currentTrTableBm3Piv,
                        CommonConstants.subTableExcludeList);
                if (comparePivObject) {
                    break;
                }
            }

            if (!comparePivObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private Boolean compareBm1HistoryAndCurrentRecord(TrTableBsb originalBsb) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareBm1HistoryAndCurrentRecord] Enter into compare current and history BM1 record for BSB: {}",
                originalBsb.taxCertificateInfo());

        boolean isHistoryAndCurrentRecordSame = true;

        TrTableBm1 historicizedTrTableBm1 = null;
        TrTableBm1 currentTrTableBm1 = null;

        for (TrTableBm1 trTableBm1 : originalBsb.getTrTableBm1Set()) {
            if (trTableBm1.getTrTableBm1PK().getKeySatzart().equals(BsbStatus.H.toString())
                    && !originalBsb.getStatus().equals(BsbStatus.S.toString())) {
                historicizedTrTableBm1 = trTableBm1;
            }
            if (trTableBm1.getTrTableBm1PK().getKeySatzart().equals(originalBsb.getStatus())) {
                currentTrTableBm1 = trTableBm1;
            }
        }

        if (ObjectUtils.isEmpty(historicizedTrTableBm1)) {
            BusinessValidation.businessValidationException(ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + BsbStatus.H.name());
        }

        boolean compareBm1Object = EqualsBuilder.reflectionEquals(historicizedTrTableBm1, currentTrTableBm1,
                CommonConstants.bm1ExcludeList);

        if (!compareBm1Object) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        isHistoryAndCurrentRecordSame = compareTrTableBm1Subtables(historicizedTrTableBm1, currentTrTableBm1);

        LOG.trace(
                "[TaxReportingReviewServiceImpl] [compareBm1HistoryAndCurrentRecord] Exit compare current and history BM1 record for BSB: {}",
                originalBsb.taxCertificateInfo());

        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm1Subtables(TrTableBm1 historicizedTrTableBm1, TrTableBm1 currentTrTableBm1) {
        boolean isHistoryAndCurrentRecordSame = true;

        isHistoryAndCurrentRecordSame = compareTrTableBm1Aam(historicizedTrTableBm1, currentTrTableBm1,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm1Ake(historicizedTrTableBm1, currentTrTableBm1,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm1Eik(historicizedTrTableBm1, currentTrTableBm1,
                isHistoryAndCurrentRecordSame);

        isHistoryAndCurrentRecordSame = compareTrTableBm1Piv(historicizedTrTableBm1, currentTrTableBm1,
                isHistoryAndCurrentRecordSame);

        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm1Aam(TrTableBm1 historicizedTrTableBm1, TrTableBm1 currentTrTableBm1,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm1.getTrTableBm1AamSet().size() != currentTrTableBm1.getTrTableBm1AamSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm1Aam historicizedTrtableBm1Aam : historicizedTrTableBm1.getTrTableBm1AamSet()) {
            boolean compareAamObject = false;
            for (TrTableBm1Aam currentTrTableBm1Aam : currentTrTableBm1.getTrTableBm1AamSet()) {
                compareAamObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm1Aam, currentTrTableBm1Aam,
                        CommonConstants.subTableExcludeList);
                if (compareAamObject) {
                    break;
                }
            }

            if (!compareAamObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm1Ake(TrTableBm1 historicizedTrTableBm1, TrTableBm1 currentTrTableBm1,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm1.getTrTableBm1AkeSet().size() != currentTrTableBm1.getTrTableBm1AkeSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm1Ake historicizedTrtableBm1Ake : historicizedTrTableBm1.getTrTableBm1AkeSet()) {
            boolean compareAkeObject = false;
            for (TrTableBm1Ake currentTrTableBm1Ake : currentTrTableBm1.getTrTableBm1AkeSet()) {
                compareAkeObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm1Ake, currentTrTableBm1Ake,
                        CommonConstants.subTableExcludeList);
                if (compareAkeObject) {
                    break;
                }
            }

            if (!compareAkeObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm1Eik(TrTableBm1 historicizedTrTableBm1, TrTableBm1 currentTrTableBm1,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm1.getTrTableBm1EikSet().size() != currentTrTableBm1.getTrTableBm1EikSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm1Eik historicizedTrtableBm1Eik : historicizedTrTableBm1.getTrTableBm1EikSet()) {
            boolean compareEikObject = false;
            for (TrTableBm1Eik currentTrTableBm1Eik : currentTrTableBm1.getTrTableBm1EikSet()) {
                compareEikObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm1Eik, currentTrTableBm1Eik,
                        CommonConstants.subTableExcludeList);
                if (compareEikObject) {
                    break;
                }
            }

            if (!compareEikObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }

    private boolean compareTrTableBm1Piv(TrTableBm1 historicizedTrTableBm1, TrTableBm1 currentTrTableBm1,
            boolean isHistoryAndCurrentRecordSame) {
        if (historicizedTrTableBm1.getTrTableBm1PivSet().size() != currentTrTableBm1.getTrTableBm1PivSet().size()) {
            isHistoryAndCurrentRecordSame = false;
            return isHistoryAndCurrentRecordSame;
        }

        for (TrTableBm1Piv historicizedTrtableBm1Piv : historicizedTrTableBm1.getTrTableBm1PivSet()) {
            boolean comparePivObject = false;
            for (TrTableBm1Piv currentTrTableBm1Piv : currentTrTableBm1.getTrTableBm1PivSet()) {
                comparePivObject = EqualsBuilder.reflectionEquals(historicizedTrtableBm1Piv, currentTrTableBm1Piv,
                        CommonConstants.subTableExcludeList);
                if (comparePivObject) {
                    break;
                }
            }

            if (!comparePivObject) {
                isHistoryAndCurrentRecordSame = false;
                return isHistoryAndCurrentRecordSame;
            }
        }
        return isHistoryAndCurrentRecordSame;
    }
}
