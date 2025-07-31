package com.fisglobal.taxreporting.service.taxreportingreview;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting review service will find the changes in correction and
 * historicized record and start the workflow process
 */
public interface TaxReportingReviewService {
    /**
     * This method will compare correction and historicized record and initialize
     * the workflow for tax report record
     *
     * @param mandSl
     *                          Section of tax report
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param keySysDatum
     *                          Tax report created date
     * @param keyUhrzeit
     *                          Tax report created time
     * 
     * @return Validation result response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public ValidationResults markForReview(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException;
}
