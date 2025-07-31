package com.fisglobal.taxreporting.service.taxreportingapproval;

import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting approval service used to approve the updated data in tax
 * reporting table
 */
public interface TaxReportingApprovalService {
    /**
     * This method used to approve the taxreport record based on the parameters. If
     * the action is OK then it will approve the reocord, else the action is NOK
     * then it will not approve the record
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
     * @param action
     *                          To approve ( OK ) or reject ( NOK ) the workflow record
     * 
     * @return Validation results details
     *
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    ValidationResults approveRecord(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit, String action) throws ValidationResultsException;

    /**
     * To validate the action values
     *
     * @param action
     *                   String value for approve ( OK ) or reject ( NOK ) the workflow
     *                   record
     *
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    void actionValuesCheck(String action) throws ValidationResultsException;

    /**
     * To validate the BSB status values
     *
     * @param status
     *                   String value of tax report record status
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    void bsbStatusValidCheck(String status) throws ValidationResultsException;

    /**
     * To validate the melde status values
     *
     * @param status
     *                   String value of tax report workflow status
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    void meldeStatusValidCheck(String status) throws ValidationResultsException;
}
