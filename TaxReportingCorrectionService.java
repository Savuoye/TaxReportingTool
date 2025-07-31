package com.fisglobal.taxreporting.service.taxreportingcorrection;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting correction service used to update the tax report record
 */
public interface TaxReportingCorrectionService {
    /**
     * Update method will accept BSB request object, validate the record and persist
     * in DB
     *
     * @param data
     *                   Tax report BSB object
     * @param action
     *                   Specify the action whether normal update or history update
     * 
     * @return Validation result response object
     *
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public ValidationResults updateTaxReportingData(TrTableBsb data, String action) throws ValidationResultsException;
}
