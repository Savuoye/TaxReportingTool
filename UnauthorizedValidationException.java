package com.fisglobal.taxreporting.exception;

import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * This class used to set the error response when unauthorized issues occur
 */
@SuppressWarnings("serial")
public class UnauthorizedValidationException extends ValidationResultsException {
    public UnauthorizedValidationException(ValidationResults validationResults) {
        super(validationResults);
    }
}
