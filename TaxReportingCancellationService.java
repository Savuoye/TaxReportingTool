package com.fisglobal.taxreporting.service.taxreportingcancellation;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting approval service used to cancel the tax report data in
 * different workflow status
 */
public interface TaxReportingCancellationService {
    /**
     * This method will cancel the tax report data in different melde status
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
     * @return Validation result details
     *
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public ValidationResults createCertificateCancellation(String mandsl, String keyIdNr, long keyMeldejahr,
            String keyMuster, long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException;
}
