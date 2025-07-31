package com.fisglobal.taxreporting.service.taxreportingdetail;

import com.fisglobal.taxreporting.controller.dto.TaxReportingDetail;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.exception.ValidationResultsException;


/**
 * This service class used to get the full tax report details
 */
public interface TaxReportingDetailService {
    /**
     * This method will get full tax report details from DB
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
     * @return Tax report BSB object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TrTableBsb getAllDetailTaxReports(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException;

    /**
     * This method will set Delete Historicized Eligibility Flag
     *
     * @param taxReportingDetail
     *                               TaxReportingDetail object
     * 
     * @return TaxReportingDetail object with delete Historicized Eligibility Flag
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TaxReportingDetail setDeleteHistoricizedEligibilityFlag(TaxReportingDetail taxReportingDetail)
            throws ValidationResultsException;

    /**
     * This method will construct full tax report details along with create
     * adjustment field
     *
     * @param trTableBsb
     *                       TrTable BSB object
     * 
     * @return TaxReportingDetail object with create adjustment field
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TaxReportingDetail getPreviousBSBRecordCount(
            com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb trTableBsb) throws ValidationResultsException;

    /**
     * This method will get full tax report details from DB. Based on keymuster it call bm1 or bm3
     * subtables
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
     * @return Tax report BSB object
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TrTableBsb fetchTrTableBsb(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster,
            long keyLaufnummer, long keySysDatum, long keyUhrzeit) throws ValidationResultsException;

    /**
     * This method will set the latest historical Flag
     *
     * @param taxReportingDetail
     *                               TaxReportingDetail object
     * 
     * @return TaxReportingDetail object with delete Historicized Eligibility Flag
     * 
     * @throws ValidationResultsException
     *                                        throws validation exception details
     */
    public TaxReportingDetail setLatestHistoricalFlag(TaxReportingDetail taxReportingDetail)
            throws ValidationResultsException;
}
