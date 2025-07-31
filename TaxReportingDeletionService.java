package com.fisglobal.taxreporting.service.taxreportingdeletion;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting deletion service will remove the subtables record
 */
public interface TaxReportingDeletionService {
    /**
     * This method will process the deletion of tax report subtables record and
     * update the BSB record
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
     * @return Response code
     *
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public ValidationResults deleteTaxReportingData(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException;
}
