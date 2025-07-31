package com.fisglobal.taxreporting.exception;

import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * This class used to throw the custom exception in application
 */
@SuppressWarnings("serial")
public class ValidationResultsException extends Exception {
    private ValidationResults validationResults;

    public ValidationResultsException(final ValidationResults validationResults) {
        this.validationResults = validationResults;
    }

    public ValidationResults getRequestValidationResult() {
        return validationResults;
    }
}
