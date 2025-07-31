package com.fisglobal.taxreporting.exception;

/**
 * This class will hold the static error code and error description
 */
public class ValidationMessage {
    public static final String ERROR_CODE_100 = "mandatoryFieldValidation";
    public static final String ERROR_CODE_100_DESC = "Missing Required Parameters";

    public static final String ERROR_CODE_101 = "noTaxReportsFound";
    public static final String ERROR_CODE_101_DESC = "No Tax Reports Records Found For Required Parameter";

    public static final String ERROR_CODE_102 = "noTaxReportsFoundForMeldeStatus";
    public static final String ERROR_CODE_102_DESC = "No Tax Reports Records Found For Melde Status ";

    public static final String ERROR_CODE_103 = "noHistoryTaxReportsFound";
    public static final String ERROR_CODE_103_DESC = "No History Tax Reports Records Found";

    public static final String ERROR_CODE_104 = "noTaxReportsFoundForStatus";
    public static final String ERROR_CODE_104_DESC = "No Tax Reports Records Found for status ";

    public static final String ERROR_CODE_105 = "correctionOrAdjustmentNotPossible";
    public static final String ERROR_CODE_105_DESC = "BSB.MELDE_STATUS should be Keine Aktion(00)";

    public static final String ERROR_CODE_106 = "requiredParameterIsEmpty";
    public static final String ERROR_CODE_106_DESC = "At least one parameter (keyIdNr, meldestatus, or antwortstatus) is required";

    public static final String ERROR_CODE_107 = "invalidMeldeStatusForApprovalOperation";
    public static final String ERROR_CODE_107_DESC = "BSB.MELDE_STATUS (04), (05), (06), (11), (12) and (13) are invalid for approval operation";

    public static final String ERROR_CODE_108 = "unauthorizedUserForApproval";
    public static final String ERROR_CODE_108_DESC = "Approver user should be different from last accessed user";

    public static final String ERROR_CODE_109 = "approvalOperationNotPossible";
    public static final String ERROR_CODE_109_DESC = "The record is not marked as For Review";

    public static final String ERROR_CODE_110 = "adjustmentOperationNotPossible";
    public static final String ERROR_CODE_110_DESC = "The record is available in cancellation status (S)";

    public static final String ERROR_CODE_111 = "invalidInputValueForAction";
    public static final String ERROR_CODE_111_DESC = "Valid values are OK or NOK";

    public static final String ERROR_CODE_112 = "invalidInputValueForStatus";
    public static final String ERROR_CODE_112_DESC = "BSB.STATUS Valid values are B, K and S";

    public static final String ERROR_CODE_113 = "mandatoryFieldValidationForSearch";
    public static final String ERROR_CODE_113_DESC = "keyIdNr is mandatory to search tax report with this parameters";

    public static final String ERROR_CODE_114 = "duplicateNewEntries";
    public static final String ERROR_CODE_114_DESC = "Duplicate new entries for the same correction not allowed";

    public static final String ERROR_CODE_115 = "invalidNewEntries";
    public static final String ERROR_CODE_115_DESC = "Invalid new entries. AAM subtables not allowed in BM3 and AKB subtables not allowed in BM1";

    public static final String ERROR_CODE_116 = "noChangeInHistoryAndCurrentRecord";
    public static final String ERROR_CODE_116_DESC = "No Changes in historicized and current record";

    public static final String ERROR_CODE_117 = "journalingDiffError";
    public static final String ERROR_CODE_117_DESC = "Couldn't process the journaling diff";

    public static final String ERROR_CODE_118 = "payloadSerializationError";
    public static final String ERROR_CODE_118_DESC = "Couldn't serialize the payload";

    public static final String ERROR_CODE_119 = "inputValidationForNumber";
    public static final String ERROR_CODE_120 = "inputValidationForString";

    public static final String ERROR_CODE_121 = "personIdNotAvailable";
    public static final String ERROR_CODE_121_DESC = "ID Person is missing";

    public static final String ERROR_CODE_122 = "kontoIdNotAvailable";
    public static final String ERROR_CODE_122_DESC = "ID Konto is missing";

    public static final String ERROR_CODE_123 = "recordAvailableInStatusB";
    public static final String ERROR_CODE_123_DESC = "Previous record is already available in BSB.STATUS B";

    public static final String ERROR_CODE_124 = "latestHistoryRecordIsNotAvaialble";
    public static final String ERROR_CODE_124_DESC = "Latest historical Tax Reports Records not Found For Required Parameter";

    public static final String ERROR_CODE_125 = "steuerZuordIsNotAvaialble";
    public static final String ERROR_CODE_125_DESC = "Steuerliche Zuordnung is missing";

    // Steuerliche Zuordnung is missing

    public static final String ERROR_CODE_126 = "mandatoryValidationForHistoricizedCreation";
    public static final String ERROR_CODE_126_DESC = "keyIdNr, keyMeldejahr and keyMuster is mandatory for tax report historicized record creation";

    public static final String ERROR_CODE_127 = "keyIdNrValidationForHistoricizedCreation";
    public static final String ERROR_CODE_127_DESC = "keyIdNr size should be 10 to 13 for tax report historicized record creation";

    public static final String ERROR_CODE_128 = "keyMeldejahrValidationForHistoricizedCreation";
    public static final String ERROR_CODE_128_DESC = "keyMeldejahr size should be 4 for tax report historicized record creation";

    public static final String ERROR_CODE_129 = "keyMusterValidationForHistoricizedCreation";
    public static final String ERROR_CODE_129_DESC = "keyMuster should be JSTB-I or JSTB-III for tax report historicized record creation";

    public static final String ERROR_CODE_130 = "keyNotUnique";
    public static final String ERROR_CODE_130_DESC = "Key not unique for creation of the tax certificate (historicized)";

    public static final String ERROR_CODE_131 = "keyMeldejahrYearValidation";
    public static final String ERROR_CODE_131_DESC = "keyMeldejahr value should starts from 2024 to current year for historicized record creation";

    public static final String ERROR_CODE_132 = "checksumValidation";
    public static final String ERROR_CODE_132_DESC = "Checksum validation is failed for given field";

    public static final String ERROR_CODE_133 = "checksumLengthValidation";
    public static final String ERROR_CODE_133_DESC = "Field value should be number and its length should be 11 for Checksum validation";

    public static final String ERROR_CODE_134 = "deletionOfHistoricizedTaxCertificateNotPossible";
    public static final String ERROR_CODE_134_DESC = "Not eligible for deletion - The status of tax certificate is not H";

    public static final String ERROR_CODE_135 = "deletionOfHistoricizedTaxCertificateNotPossible";
    public static final String ERROR_CODE_135_DESC = "Not eligible for deletion - The tax certificate have bsb records with combination of Keys(mandsl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer) other than status H";

    public static final String ERROR_CODE_136 = "updationOfHistoricizedTaxCertificateNotPossible";
    public static final String ERROR_CODE_136_DESC = "Not eligible for updation - The keySatzart in tax certificate subtables not in H";
}
