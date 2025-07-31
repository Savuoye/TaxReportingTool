package com.fisglobal.taxreporting.service.taxreportingresponse;

import com.fisglobal.taxreporting.controller.dto.TaxReportingDetail;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationResultsException;
import com.fisglobal.taxreporting.validation.ValidationResults;


/**
 * Tax reporting response service used to construct response object for all api
 */
public interface TaxReportingResponseService {
    /**
     * This method will construct common response
     *
     * @param validationResults
     *                              Taxreporting response object
     * @param bsbAfter
     *                              Tax report BSB object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public void taxReportingCommonResponse(ValidationResults validationResults, TrTableBsb bsbAfter)
            throws ValidationResultsException;

    /**
     * This method will construct details response
     *
     * @param trTableBsb
     *                       Tax report BSB object
     * 
     * @return TaxreportingDetail response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TaxReportingDetail taxreportingDetailResponse(TrTableBsb trTableBsb) throws ValidationResultsException;

    /**
     * This method will construct response for delete historicized record API
     *
     * @param validationResults
     *                              response object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public void taxReportingHistoryDeleteResponse(ValidationResults validationResults)
            throws ValidationResultsException;
}
