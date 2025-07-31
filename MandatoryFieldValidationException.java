package com.fisglobal.taxreporting.exception;

import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * This class used to set the error response when mandatory validation issues
 * occur
 */
@SuppressWarnings("serial")
public class MandatoryFieldValidationException extends ValidationResultsException {
    public MandatoryFieldValidationException(ValidationResults validationResults) {
        super(validationResults);
    }
}
