package com.fisglobal.taxreporting.exception;

import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * This class used to set the error response when record not found in the table
 */
@SuppressWarnings("serial")
public class RecordNotFoundException extends ValidationResultsException {
    public RecordNotFoundException(ValidationResults validationResults) {
        super(validationResults);
    }
}
