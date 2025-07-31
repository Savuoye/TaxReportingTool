package com.fisglobal.taxreporting.service.taxreportingapproval.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.MeldeStatus;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.exception.UnauthorizedValidationException;
import com.fisglobal.taxreporting.exception.ValidationMessage;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.repository.TaxReportingRepository;
import com.fisglobal.taxreporting.service.infrastructure.UserNameProvider;
import com.fisglobal.taxreporting.service.taxreportingapproval.TaxReportingApprovalService;
import com.fisglobal.taxreporting.service.taxreportingdetail.TaxReportingDetailService;
import com.fisglobal.taxreporting.service.taxreportingjournaling.TaxReportingJournalingService;
import com.fisglobal.taxreporting.service.taxreportingresponse.TaxReportingResponseService;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.util.LastModificationFields;
import com.fisglobal.taxreporting.validation.ResponseMessage;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * Tax report approval service class implement the approval functionality of
 * updated tax record
 */
@Service
@Transactional
public class TaxReportingApprovalServiceImpl implements TaxReportingApprovalService {
    private static final KORLogger LOG = KORLogger.getLogger(TaxReportingApprovalServiceImpl.class);

    @Autowired
    public TaxReportingRepository taxReportingRepository;

    @Autowired
    private LastModificationFields lastModificationFields;

    @Autowired
    private UserNameProvider userNameProvider;

    @Autowired
    private TaxReportingJournalingService taxReportingJournalingService;

    @Autowired
    private TaxReportingResponseService taxReportingResponseService;

    @Autowired
    private TaxReportingDetailService taxReportingDetailService;

    @Override
    public void actionValuesCheck(String action) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [actionValuesCheck] Enter into BSB action validation for action:{}",
                action);

        ValidationResults validationResults = new ValidationResults();

        if (!(action.equalsIgnoreCase("OK") || action.equalsIgnoreCase("NOK"))) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_111,
                    ValidationMessage.ERROR_CODE_111_DESC, CommonConstants.PARAMETER_ACTION);
            throw new ValidationResultsException(validationResults);
        }
        LOG.trace("[TaxReportingApprovalServiceImpl] [actionValuesCheck] Exit BSB action validation for action:{}",
                action);
    }

    @Override
    public void bsbStatusValidCheck(String status) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [bsbStatusValidCheck] Enter into BSB status validation for status:{}",
                status);

        ValidationResults validationResults = new ValidationResults();
        if (!CommonConstants.validStatusForReview.contains(status)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_112,
                    ValidationMessage.ERROR_CODE_112_DESC, CommonConstants.TR_TABLE_BSB_STATUS);
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace("[TaxReportingApprovalServiceImpl] [bsbStatusValidCheck] Exit BSB status validation for status:{}",
                status);
    }

    private void authorizationValidation(String databaseUserName) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [authorizationValidation] Enter into autorization validation for databaseUserName:{}",
                databaseUserName);

        if (userNameProvider.getAuthenticatedUserName().trim().equalsIgnoreCase(databaseUserName.trim())) {
            ValidationResults validationResults = new ValidationResults();
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_108,
                    ValidationMessage.ERROR_CODE_108_DESC, CommonConstants.TR_TABLE_BSB_AENDER_ERFASSER);
            throw new UnauthorizedValidationException(validationResults);
        }

        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [authorizationValidation] Exit autorization validation for databaseUserName:{}",
                databaseUserName);
    }

    public ValidationResults approveRecord(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit, String action) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [approveRecord] Enter into approve tax report service for action : {}",
                action);

        actionValuesCheck(action);

        TrTableBsb trTableBsb = taxReportingDetailService.fetchTrTableBsb(mandSl, keyIdNr, keyMeldejahr, keyMuster,
                keyLaufnummer, keySysDatum, keyUhrzeit);

        if (!ObjectUtils.isEmpty(trTableBsb)) {
            LOG.trace("[TaxReportingApprovalServiceImpl] [approveRecord] Approve tax report service for BSB: {}",
                    trTableBsb.taxCertificateInfo());
        }

        Map<String, Object> journalingBeforeBsb = taxReportingJournalingService
                .journalingProcessInitializationBSB(trTableBsb);

        String status = trTableBsb.getStatus();
        String meldestatus = trTableBsb.getMeldeStatus();
        String databaseUserName = trTableBsb.getAenderErfasser();

        authorizationValidation(databaseUserName);
        bsbStatusValidCheck(status);
        meldeStatusValidCheck(meldestatus);

        // Check status and update meldeStatus
        if (action.equalsIgnoreCase("OK")) {
            switch (status) {
                case "B":
                    meldestatus = MeldeStatus._04.toString();
                    break;

                case "K":
                    meldestatus = MeldeStatus._06.toString();
                    break;

                case "S":
                    meldestatus = MeldeStatus._05.toString();
                    break;

                default:
                    break;
            }
        } else {
            switch (status) {
                case "B":
                    meldestatus = MeldeStatus._00.toString();
                    break;

                case "K":
                    meldestatus = MeldeStatus._00.toString();
                    break;

                case "S":
                    meldestatus = MeldeStatus._00.toString();
                    break;

                default:
                    break;
            }
        }

        trTableBsb.setMeldeStatus(meldestatus);
        lastModificationFields.update(trTableBsb);

        taxReportingRepository.save(trTableBsb);

        taxReportingJournalingService.journalingProcessExecutionBSB(journalingBeforeBsb, trTableBsb,
                CommonConstants.APPROVE_ACTION);

        ValidationResults validationResults = new ValidationResults();
        if (action.equalsIgnoreCase("OK")) {
            validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_106,
                    ResponseMessage.MSG_CODE_106_DESC, null);
        }
        if (action.equalsIgnoreCase("NOK")) {
            validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_107,
                    ResponseMessage.MSG_CODE_107_DESC, null);
        }

        validationResults.addValidationError(ValidationClassEnum.INFO, ResponseMessage.MSG_CODE_100, meldestatus,
                CommonConstants.TR_TABLE_BSB_MELDE_STATUS);

        taxReportingResponseService.taxReportingCommonResponse(validationResults, trTableBsb);

        LOG.trace("[TaxReportingApprovalServiceImpl] [approveRecord] Exit approve tax report service for action: {}",
                action);
        return validationResults;

    }

    @Override
    public void meldeStatusValidCheck(String meldeStatus) throws ValidationResultsException {
        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [meldeStatusValidCheck] Enter into BSB Melde status validation for meldeStatus:{}",
                meldeStatus);

        ValidationResults validationResults = new ValidationResults();
        if (CommonConstants.invalidMeldeStatusForApprove.contains(meldeStatus)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_107,
                    ValidationMessage.ERROR_CODE_107_DESC, CommonConstants.TR_TABLE_BSB_MELDE_STATUS);
            throw new ValidationResultsException(validationResults);
        }
        if (MeldeStatus._00.toString().equals(meldeStatus)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_109,
                    ValidationMessage.ERROR_CODE_109_DESC, CommonConstants.TR_TABLE_BSB_MELDE_STATUS);
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace(
                "[TaxReportingApprovalServiceImpl] [meldeStatusValidCheck] Exit BSB Melde status validation for meldeStatus:{}",
                meldeStatus);

    }
}
