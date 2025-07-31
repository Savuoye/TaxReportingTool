package com.fisglobal.taxreporting.service.taxreportingsameyear;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting same service used to get all the data of same year
 */
public interface TaxReportingSameYearKeyService {
    public void addTaxReportingSameYearKeys(ValidationResults validationResults) throws ValidationResultsException;
}
