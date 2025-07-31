package com.fisglobal.taxreporting.exception;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.enums.BsbStatus;
import com.fisglobal.taxreporting.enums.KeyMusterType;
import com.fisglobal.taxreporting.enums.ValidationClassEnum;
import com.fisglobal.taxreporting.service.infrastructure.CurrentTimeProvider;
import com.fisglobal.taxreporting.util.CommonConstants;
import com.fisglobal.taxreporting.validation.ValidationResults;

import de.kordoba.framework.common.log.KORLogger;


/**
 * This class handle business validation
 */
public class BusinessValidation {
    private static final KORLogger LOG = KORLogger.getLogger(BusinessValidation.class);

    public static ValidatorFactory factory = null;
    public static Validator validator = null;

    /**
     * This method will accept error message and error description and throw the
     * Exception
     *
     * @param errcode
     *                             passing error code
     * @param errorDescription
     *                             Passing error Description
     * 
     * @throws ValidationResultsException
     *                                        throwing exception
     */
    public static void businessValidationException(String errcode, String errorDescription)
            throws ValidationResultsException {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, errcode, errorDescription, null);
        throw new ValidationResultsException(validationResults);
    }

    /**
     * This method will accept error message and error description and throw the
     * exception
     *
     * @param errcode
     *                             passing error code
     * @param errorDescription
     *                             Passing error Description
     * @param affectedField
     *                             Passing affected field
     * 
     * @throws ValidationResultsException
     *                                        throwing exception
     */
    public static void businessValidationException(String errcode, String errorDescription, String affectedField)
            throws ValidationResultsException {
        ValidationResults validationResults = new ValidationResults();
        validationResults.addValidationError(ValidationClassEnum.ERROR, errcode, errorDescription, affectedField);
        throw new ValidationResultsException(validationResults);
    }

    /**
     * This method will validate duplicate request present in subtables
     *
     * @param data
     *                        passing the original data from client
     * @param requestData
     *                        client data suppressed duplicate entry
     * 
     * @throws ValidationResultsException
     *                                        throwing exception
     */
    public static void duplicateDataValidation(com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb data,
            TrTableBsb requestData) throws ValidationResultsException {
        LOG.trace("[BusinessValidation] [duplicateDataValidation] Enter into duplicate validation");
        Map<TrTableBm1PK, Map<String, Integer>> subtablesBM1CountMapper = new HashMap<>();
        Map<TrTableBm3PK, Map<String, Integer>> subtablesBM3CountMapper = new HashMap<>();

        Set<String> duplicateSubtableSet = new HashSet<>();
        Set<String> invlaidSubtableSet = new HashSet<>();

        data.getTrTableBm1Set().stream().forEach(bm1Data -> {
            Map<String, Integer> subtablesCountMapper = new HashMap<>();
            subtablesCountMapper.put(CommonConstants.BM1_TR_TABLE_AAM_SET, bm1Data.getTrTableAamSet().size());
            subtablesCountMapper.put(CommonConstants.BM1_TR_TABLE_AKE_SET, bm1Data.getTrTableAkeSet().size());
            subtablesCountMapper.put(CommonConstants.BM1_TR_TABLE_EIK_SET, bm1Data.getTrTableEikSet().size());
            subtablesCountMapper.put(CommonConstants.BM1_TR_TABLE_PIV_SET, bm1Data.getTrTablePivSet().size());
            subtablesBM1CountMapper.put(bm1Data.getTrTableBm1PK(), subtablesCountMapper);
        });
        data.getTrTableBm3Set().stream().forEach(bm3Data -> {
            Map<String, Integer> subtablesCountMapper = new HashMap<>();
            subtablesCountMapper.put(CommonConstants.BM3_TR_TABLE_AKB_SET, bm3Data.getTrTableAkbSet().size());
            subtablesCountMapper.put(CommonConstants.BM3_TR_TABLE_AKE_SET, bm3Data.getTrTableAkeSet().size());
            subtablesCountMapper.put(CommonConstants.BM3_TR_TABLE_EIK_SET, bm3Data.getTrTableEikSet().size());
            subtablesCountMapper.put(CommonConstants.BM3_TR_TABLE_PIV_SET, bm3Data.getTrTablePivSet().size());
            subtablesBM3CountMapper.put(bm3Data.getTrTableBm3PK(), subtablesCountMapper);
        });

        requestData.getTrTableBm1Set().stream().forEach(bm1RequestData -> {
            Map<String, Integer> subtablesCountMapper = subtablesBM1CountMapper.get(bm1RequestData.getTrTableBm1PK());
            if (!CollectionUtils.isEmpty(bm1RequestData.getTrTableAkbSet())) {
                invlaidSubtableSet.add(CommonConstants.BM3_TR_TABLE_AKB_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM1_TR_TABLE_AAM_SET).intValue() > bm1RequestData
                    .getTrTableAamSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM1_TR_TABLE_AAM_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM1_TR_TABLE_AKE_SET).intValue() > bm1RequestData
                    .getTrTableAkeSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM1_TR_TABLE_AKE_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM1_TR_TABLE_EIK_SET).intValue() > bm1RequestData
                    .getTrTableEikSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM1_TR_TABLE_EIK_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM1_TR_TABLE_PIV_SET).intValue() > bm1RequestData
                    .getTrTablePivSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM1_TR_TABLE_PIV_SET);
            }
        });

        requestData.getTrTableBm3Set().stream().forEach(bm3RequestData -> {
            Map<String, Integer> subtablesCountMapper = subtablesBM3CountMapper.get(bm3RequestData.getTrTableBm3PK());
            if (!CollectionUtils.isEmpty(bm3RequestData.getTrTableAamSet())) {
                invlaidSubtableSet.add(CommonConstants.BM1_TR_TABLE_AAM_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM3_TR_TABLE_AKB_SET).intValue() > bm3RequestData
                    .getTrTableAkbSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM3_TR_TABLE_AKB_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM3_TR_TABLE_AKE_SET).intValue() > bm3RequestData
                    .getTrTableAkeSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM3_TR_TABLE_AKE_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM3_TR_TABLE_EIK_SET).intValue() > bm3RequestData
                    .getTrTableEikSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM3_TR_TABLE_EIK_SET);
            }
            if (subtablesCountMapper.get(CommonConstants.BM3_TR_TABLE_PIV_SET).intValue() > bm3RequestData
                    .getTrTablePivSet().size()) {
                duplicateSubtableSet.add(CommonConstants.BM3_TR_TABLE_PIV_SET);
            }
        });
        if (!CollectionUtils.isEmpty(duplicateSubtableSet)) {
            ValidationResults validationResults = new ValidationResults();
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_114,
                    ValidationMessage.ERROR_CODE_114_DESC, duplicateSubtableSet.toString());
            throw new ValidationResultsException(validationResults);
        }
        if (!CollectionUtils.isEmpty(invlaidSubtableSet)) {
            ValidationResults validationResults = new ValidationResults();
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_115,
                    ValidationMessage.ERROR_CODE_115_DESC, duplicateSubtableSet.toString());
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace("[BusinessValidation] [duplicateDataValidation] Exit duplicate validation");
    }

    /**
     * Validate the input TrTableBsb request object
     *
     * @param data
     *                 passing the original data from client
     * 
     * @throws ValidationResultsException
     *                                        throwing exception
     */
    public static void inputRequestFieldValidation(com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb data)
            throws ValidationResultsException {
        LOG.trace("[BusinessValidation] [inputRequestFieldValidation] Enter into input request validation");

        ValidationResults validationResults = new ValidationResults();

        Validator validator = getValidatorInstance();

        Set<ConstraintViolation<com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb>> bsbDataConstraintValidationResult = validator
                .validate(data);
        if (!bsbDataConstraintValidationResult.isEmpty()) {

            bsbDataConstraintValidationResult.forEach(constraintValidation -> {

                if (!ObjectUtils.isEmpty(constraintValidation.getMessage())
                        && constraintValidation.getMessage().toLowerCase().contains("length")) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_119,
                            constraintValidation.getMessage(), constraintValidation.getPropertyPath().toString());
                } else {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_120,
                            constraintValidation.getMessage(), constraintValidation.getPropertyPath().toString());
                }
            });
        }
        if (!validationResults.getValidationResults().isEmpty()) {
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace("[BusinessValidation] [inputRequestFieldValidation] Exit input request validation");
    }

    /**
     * If Initialize ValidatorFactory or Validator instance is null then it will
     * instantiate both ValidatorFactory and Validator
     *
     * @return validator instance
     */
    public static Validator getValidatorInstance() {
        if (factory == null || validator == null) {
            factory = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();
            validator = factory.usingContext().getValidator();
        }

        return validator;
    }

    /**
     * This method will validate the syntax and length validation
     * 
     * @param trTableBsb
     *                       Tax report bsb object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public static void applyBusinessValidation(TrTableBsb trTableBsb) throws ValidationResultsException {
        LOG.trace("[BusinessValidation] [applyBusinessValidation] Enter into business validation for BSB: {}",
                trTableBsb.taxCertificateInfo());

        ValidationResults validationResults = new ValidationResults();
        String keyIdNr = trTableBsb.getTrTableBsbPK().getKeyIdNr();
        Long keyMeldejahr = trTableBsb.getTrTableBsbPK().getKeyMeldejahr();
        String keyMuster = trTableBsb.getTrTableBsbPK().getKeyMuster();
        if (ObjectUtils.isEmpty(keyIdNr)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_126,
                    ValidationMessage.ERROR_CODE_126_DESC, CommonConstants.TR_TABLE_BSB_KEY_ID_NR);
        }
        if (ObjectUtils.isEmpty(keyMeldejahr)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_126,
                    ValidationMessage.ERROR_CODE_126_DESC, CommonConstants.TR_TABLE_BSB_KEY_MELDEJAHR);
        }
        if (ObjectUtils.isEmpty(keyMuster)) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_126,
                    ValidationMessage.ERROR_CODE_126_DESC, CommonConstants.TR_TABLE_BSB_KEY_MUSTER);
        }

        Pattern pattern = Pattern.compile("^[0-9]{10,13}$");
        boolean keyIdNrValidation = pattern.matcher(keyIdNr.trim().toString()).matches();
        if (!keyIdNrValidation) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_127,
                    ValidationMessage.ERROR_CODE_127_DESC, CommonConstants.TR_TABLE_BSB_KEY_ID_NR);
        }

        pattern = Pattern.compile("(\\d{4})");
        boolean keyMeldejahrValidation = pattern.matcher(keyMeldejahr.toString()).matches();
        if (!keyMeldejahrValidation) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_128,
                    ValidationMessage.ERROR_CODE_128_DESC, CommonConstants.TR_TABLE_BSB_KEY_MELDEJAHR);
        }

        String currentYear = new CurrentTimeProvider().getCurrentTime(CommonConstants.CURRENT_YEAR_FORMAT);
        if (!(keyMeldejahr >= CommonConstants.YEAR_2024 && keyMeldejahr <= Long.valueOf(currentYear))) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_131,
                    ValidationMessage.ERROR_CODE_131_DESC, CommonConstants.TR_TABLE_BSB_KEY_MELDEJAHR);
        }

        if (KeyMusterType.fromValue(keyMuster.trim()) == null) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_129,
                    ValidationMessage.ERROR_CODE_129_DESC, CommonConstants.TR_TABLE_BSB_KEY_MUSTER);
        }

        if (!CommonConstants.HISTORICIZED_CREATION_STATUS.equals(trTableBsb.getStatus())) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_104,
                    ValidationMessage.ERROR_CODE_104_DESC + CommonConstants.HISTORICIZED_CREATION_STATUS,
                    CommonConstants.TR_TABLE_BSB_STATUS);
        }
        if (!CommonConstants.EMPTY_VALUE.equals(trTableBsb.getMeldeStatus().trim())) {
            validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_102,
                    ValidationMessage.ERROR_CODE_102_DESC + "empty", CommonConstants.TR_TABLE_BSB_MELDE_STATUS);
        }

        checksumValidator(trTableBsb, validationResults);

        historicalSubtableValidation(trTableBsb, validationResults);

        if (validationResults.getValidationResults().size() > 0) {
            LOG.error(
                    "[BusinessValidation] [applyBusinessValidation] Error occured in to business validation for BSB: {}",
                    trTableBsb.taxCertificateInfo());
            throw new ValidationResultsException(validationResults);
        }

        LOG.trace("[BusinessValidation] [applyBusinessValidation] Exit business validation for BSB: {}",
                trTableBsb.taxCertificateInfo());

    }

    /**
     * This method used to validate subtable status for historical record. If the status is not in H
     * then exception will be thrown
     * 
     * @param trTableBsb
     *                              Tax report bsb object
     * @param validationResults
     *                              throws validation exception details
     */
    private static void historicalSubtableValidation(TrTableBsb trTableBsb, ValidationResults validationResults) {
        trTableBsb.getTrTableBm1Set().stream().forEach(bm1Data -> {
            if (!bm1Data.getTrTableBm1PK().getKeySatzart().equals(BsbStatus.H.name())) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                        ValidationMessage.ERROR_CODE_136_DESC, "tr_table_bm1." + CommonConstants.KEY_STAZART);
            }
            bm1Data.getTrTableBm1AamSet().stream().forEach(aamData -> {
                if (!aamData.getTrTableAamPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_aam." + CommonConstants.KEY_STAZART);
                }
            });
            bm1Data.getTrTableBm1AkeSet().stream().forEach(akeData -> {
                if (!akeData.getTrTableAkePK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_ake." + CommonConstants.KEY_STAZART);
                }
            });
            bm1Data.getTrTableBm1EikSet().stream().forEach(eikData -> {
                if (!eikData.getTrTableEikPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_eik." + CommonConstants.KEY_STAZART);
                }
            });
            bm1Data.getTrTableBm1PivSet().stream().forEach(pivData -> {
                if (!pivData.getTrTablePivPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_piv." + CommonConstants.KEY_STAZART);
                }
            });
        });

        trTableBsb.getTrTableBm3Set().stream().forEach(bm3Data -> {
            if (!bm3Data.getTrTableBm3PK().getKeySatzart().equals(BsbStatus.H.name())) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                        ValidationMessage.ERROR_CODE_136_DESC, "tr_table_bm3." + CommonConstants.KEY_STAZART);
            }
            bm3Data.getTrTableBm3AkbSet().stream().forEach(akbData -> {
                if (!akbData.getTrTableAkbPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_akb." + CommonConstants.KEY_STAZART);
                }
            });
            bm3Data.getTrTableBm3AkeSet().stream().forEach(akeData -> {
                if (!akeData.getTrTableAkePK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_ake." + CommonConstants.KEY_STAZART);
                }
            });
            bm3Data.getTrTableBm3EikSet().stream().forEach(eikData -> {
                if (!eikData.getTrTableEikPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_eik." + CommonConstants.KEY_STAZART);
                }
            });
            bm3Data.getTrTableBm3PivSet().stream().forEach(pivData -> {
                if (!pivData.getTrTablePivPK().getKeySatzart().equals(BsbStatus.H.name())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_136,
                            ValidationMessage.ERROR_CODE_136_DESC, "tr_table_piv." + CommonConstants.KEY_STAZART);
                }
            });
        });
    }

    /**
     * This method will validate checksum validation for BSB object
     * 
     * @param trTableBsb
     *                              Tax report bsb object
     * @param validationResults
     *                              throws validation exception details
     */
    private static void checksumValidator(TrTableBsb trTableBsb, ValidationResults validationResults) {
        LOG.trace("[BusinessValidation] [checksumValidator] Enter into checksumValidator for BSB: {}",
                trTableBsb.taxCertificateInfo());

        if (!ObjectUtils.isEmpty(trTableBsb.getNp1Idnr())) {
            if (!trTableBsb.getNp1Idnr().matches("(\\d{11})")) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_133,
                        ValidationMessage.ERROR_CODE_133_DESC, CommonConstants.TR_TABLE_BSB_NP1_ID_NR);
            } else {
                if (!checkSumValidation(trTableBsb.getNp1Idnr())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_132,
                            ValidationMessage.ERROR_CODE_132_DESC, CommonConstants.TR_TABLE_BSB_NP1_ID_NR);
                }
            }
        }

        if (!ObjectUtils.isEmpty(trTableBsb.getNp2Idnr())) {
            if (!trTableBsb.getNp2Idnr().matches("(\\d{11})")) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_133,
                        ValidationMessage.ERROR_CODE_133_DESC, CommonConstants.TR_TABLE_BSB_NP2_ID_NR);
            } else {
                if (!checkSumValidation(trTableBsb.getNp2Idnr())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_132,
                            ValidationMessage.ERROR_CODE_132_DESC, CommonConstants.TR_TABLE_BSB_NP2_ID_NR);
                }
            }
        }

        if (!ObjectUtils.isEmpty(trTableBsb.getNwpTin())) {
            if (!trTableBsb.getNwpTin().matches("(\\d{11})")) {
                validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_133,
                        ValidationMessage.ERROR_CODE_133_DESC, CommonConstants.TR_TABLE_BSB_NWP_TIN);
            } else {
                if (!checkSumValidation(trTableBsb.getNwpTin())) {
                    validationResults.addValidationError(ValidationClassEnum.ERROR, ValidationMessage.ERROR_CODE_132,
                            ValidationMessage.ERROR_CODE_132_DESC, CommonConstants.TR_TABLE_BSB_NWP_TIN);
                }
            }
        }

        LOG.trace("[BusinessValidation] [checksumValidator] Exit checksumValidator for BSB: {}",
                trTableBsb.taxCertificateInfo());
    }

    /**
     * Checksum validation for given string. Checksum of (last-1) digit will be equal to last digit
     * will checksum value.
     * 
     * @param checkSumId
     *                       Input field for validation
     * 
     * @return
     *             Field is true or false
     */
    private static boolean checkSumValidation(final String checkSumId) {
        LOG.trace("[BusinessValidation] [checkSumValidation] Enter into checksum validation for checkSumId {}",
                checkSumId);

        int IDNR_LENGTH = CommonConstants.IDNR_NUMBER_LENGTH + CommonConstants.IDNR_CHECKSUM_LENGTH;
        final String idnrNumber = checkSumId.substring(0, CommonConstants.IDNR_NUMBER_LENGTH);
        final String idnrChecksum = checkSumId.substring(CommonConstants.IDNR_NUMBER_LENGTH, 11);
        int idnrCheckDigit = Integer.valueOf(idnrChecksum).intValue();

        int p = idnrNumber.codePoints().map(codePoint -> codePoint - '0').reduce(CommonConstants.IDNR_NUMBER_LENGTH,
                (product, digit) -> {

                    int sum = (digit + product) % CommonConstants.IDNR_NUMBER_LENGTH;
                    if (sum == 0) {
                        sum = CommonConstants.IDNR_NUMBER_LENGTH;
                    }
                    product = (2 * sum) % IDNR_LENGTH;

                    return product;
                });

        int computedChecksum = (IDNR_LENGTH - p) % 10;

        if (computedChecksum != idnrCheckDigit) {
            LOG.error("IdNrNumber {}, check digit missmatch (given: {}, computed: {})!", idnrNumber, idnrCheckDigit,
                    computedChecksum);
        }
        LOG.trace("[BusinessValidation] [checkSumValidation] Exit checksum validation for checkSumId {}", checkSumId);
        return computedChecksum == idnrCheckDigit;
    }
}
