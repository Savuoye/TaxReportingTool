package com.fisglobal.taxreporting.service.taxreportingcreatecertificate;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting adjustment service used to create new record in subtables
 */
public interface TaxReportingAdjustmentCertificate {
    /**
     * This method will create subtables record in adjustment status
     *
     * @param mandsl
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
     * @throws JsonProcessingException
     *                                        throws json parsing error details details
     */
    public ValidationResults createCertificate(String mandsl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit)
            throws ValidationResultsException, JsonProcessingException;
}
