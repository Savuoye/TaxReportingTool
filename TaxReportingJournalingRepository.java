package com.fisglobal.taxreporting.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.journaling.TrTableJournaling;


/**
 * Repository interface used to define a sql queries to fetch the data from
 * journaling table
 */
@Repository
public interface TaxReportingJournalingRepository
        extends CrudRepository<TrTableJournaling, TrTableBsbPK>, JpaSpecificationExecutor<TrTableJournaling> {
    /**
     * Query used to fetch the single journaling data based on the parameter value
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
     * @param time
     *                          created time
     * 
     * @return Journaling response object
     */
    @Query("select j from TrTableJournaling j \n" + "where j.trTableJournalingPK.mandSl = :mandSl \n"
            + "and j.trTableJournalingPK.keyMeldejahr = :keyMeldejahr \n"
            + "and j.trTableJournalingPK.keyLaufnummer = :keyLaufnummer \n"
            + "and j.trTableJournalingPK.keySysDatum = :keySysDatum \n"
            + "and j.trTableJournalingPK.keyUhrzeit = :keyUhrzeit \n"
            + "and j.trTableJournalingPK.keyMuster = :keyMuster \n" + "and j.trTableJournalingPK.keyIdNr = :keyIdNr \n"
            + "and j.trTableJournalingPK.time = :time")
    TrTableJournaling findByMandSlAndKeyIdNR(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit, @Param("time") Date time);

    /**
     * Query used to fetch the list of jounaling record based on parameter
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
     * @return List of Journaling response object
     */
    @Query("select j from TrTableJournaling j \n" + "where j.trTableJournalingPK.mandSl = :mandSl \n"
            + "and j.trTableJournalingPK.keyMeldejahr = :keyMeldejahr \n"
            + "and j.trTableJournalingPK.keyLaufnummer = :keyLaufnummer \n"
            + "and j.trTableJournalingPK.keySysDatum = :keySysDatum \n"
            + "and j.trTableJournalingPK.keyUhrzeit = :keyUhrzeit \n"
            + "and j.trTableJournalingPK.keyMuster = :keyMuster \n" + "and j.trTableJournalingPK.keyIdNr = :keyIdNr \n"
            + "order by j.trTableJournalingPK.time desc")
    List<TrTableJournaling> findLastByMandSlAndKeyIdNR(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit);
}
