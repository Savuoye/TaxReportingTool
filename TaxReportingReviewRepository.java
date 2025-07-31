package com.fisglobal.taxreporting.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbReview;


/**
 *
 */
@Repository
public interface TaxReportingReviewRepository
        extends CrudRepository<TrTableBsbReview, TrTableBsbPK>, JpaSpecificationExecutor<TrTableBsbReview> {
    /**
     * Query used to fetch the BSB record for review based on a given parameter
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
     * @return Tax report BSB review object
     */
    @Query("select bsb from TrTableBsbReview bsb \n" + "where bsb.trTableBsbPK.mandSl = :mandSl \n"
            + "and bsb.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and bsb.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n"
            + "and bsb.trTableBsbPK.keySysDatum = :keySysDatum \n" + "and bsb.trTableBsbPK.keyUhrzeit = :keyUhrzeit \n"
            + "and bsb.trTableBsbPK.keyMuster = :keyMuster \n" + "and bsb.trTableBsbPK.keyIdNr = :keyIdNr")
    TrTableBsbReview fetchTrTableBsb(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit);

    /**
     * Update query used to modify melde status of the BSB record based on a given
     * parameter
     *
     * @param mandSl
     *                            Section of tax report
     * @param keyIdNr
     *                            Tax report Key id number
     * @param keyMeldejahr
     *                            Tax report filed year
     * @param keyMuster
     *                            Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                            Sequence number for Key id number
     * @param keySysDatum
     *                            Tax report created date
     * @param keyUhrzeit
     *                            Tax report created time
     * @param meldeStatus
     *                            Workflow status of taxreport record
     * @param aenderDatum
     *                            Updated date
     * @param aenderZeit
     *                            Updated time
     * @param aenderErfasser
     *                            Updated user
     * @param bedbsbTimestamp
     *                            Updated timestamp
     */
    @Modifying
    @Query("update TrTableBsbReview bsb set bsb.meldeStatus=:meldeStatus\n" + ",bsb.aenderDatum=:aenderDatum\n"
            + ",bsb.aenderZeit=:aenderZeit\n" + ",bsb.aenderErfasser=:aenderErfasser\n"
            + ",bsb.bedbsbTimestamp=:bedbsbTimestamp\n" + "where bsb.trTableBsbPK.mandSl = :mandSl \n"
            + "and bsb.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and bsb.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n"
            + "and bsb.trTableBsbPK.keySysDatum = :keySysDatum \n" + "and bsb.trTableBsbPK.keyUhrzeit = :keyUhrzeit \n"
            + "and bsb.trTableBsbPK.keyMuster = :keyMuster \n" + "and bsb.trTableBsbPK.keyIdNr = :keyIdNr")
    void updateBsbAfterReview(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit, @Param("meldeStatus") String meldeStatus,
            @Param("aenderDatum") BigDecimal aenderDatum, @Param("aenderZeit") BigDecimal aenderZeit,
            @Param("aenderErfasser") String aenderErfasser, @Param("bedbsbTimestamp") String bedbsbTimestamp);
}
