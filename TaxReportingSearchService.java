package com.fisglobal.taxreporting.service.taxreportingsearch;

import com.fisglobal.taxreporting.exception.RecordNotFoundException;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.service.taxreportingsearch.model.TaxReports;


/**
 * Tax reporting search service will fetch the required tax report data from DB
 */
public interface TaxReportingSearchService {
    /**
     * @param keyIdNr
     *                                        value of keyIdNr from input params
     * @param keyMeldejahr
     *                                        value of keyMeldejahr from input params
     * @param meldeStatus
     *                                        value of meldeStatus from input params
     * @param antwortStatus
     *                                        value of antwortStatus from input params
     * @param pageNumber
     *                                        value of pageNumber from input params
     * @param pageLimit
     *                                        value of pageLimit from input params
     * @param totalCountWithoutPagingFlag
     *                                        value of totalCountWithoutPagingFlag from input params
     * 
     * @return returns the TaxReports object
     * 
     * @throws RecordNotFoundException
     *                                     throws record not found exception if no records were
     *                                     found during
     *                                     the search
     */
    public TaxReports searchByTaxReportParameters(String keyIdNr, Long keyMeldejahr, String meldeStatus,
            String antwortStatus, int pageNumber, int pageLimit, boolean totalCountWithoutPagingFlag)
            throws RecordNotFoundException;

    /**
     * To validate the melde status values
     *
     * @param meldeStatus
     *                        String value of tax report workflow status
     * @param keyIdNr
     *                        value of keyIdNr from input params
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public void mandatoryFieldValidationCheck(String meldeStatus, String keyIdNr) throws ValidationResultsException;

    /**
     * To validate the mandatory parameter for search
     *
     * @param keyIdNr
     *                          Value of keyIdNr from input params
     * @param keyMeldejahr
     *                          Value of keyMeldejahr from input params
     * @param meldeStatus
     *                          Value of meldeStatus from input params
     * @param antwortStatus
     *                          Value of antwortStatus from input params
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public void mandatoryValidationForNonEmptyParameter(String keyIdNr, Long keyMeldejahr, String meldeStatus,
            String antwortStatus) throws ValidationResultsException;

    /**
     * To validate the response status parameter for search
     *
     * @param antwortStatus
     *                          Value of antwortStatus from input params
     * @param keyIdNr
     *                          Value of keyIdNr from input params
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public void mandatoryValidationForAntwortStatus(String antwortStatus, String keyIdNr)
            throws ValidationResultsException;

    /**
     * To validate the tax report files year detail
     *
     * @param keyIdNr
     *                          Value of keyIdNr from input params
     * @param meldeStatus
     *                          Value of meldeStatus from input params
     * @param antwortStatus
     *                          Value of antwortStatus from input params
     * 
     * @throws ValidationResultsException
     *                                        throws validation error details
     */
    public void mandatoryValidationForkeyMeldejahr(String keyIdNr, String meldeStatus, String antwortStatus)
            throws ValidationResultsException;
}
