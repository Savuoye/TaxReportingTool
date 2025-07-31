package com.fisglobal.taxreporting.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;


/**
 * This repository interface used to define a sql queries to fetch the data from
 * BSB table
 */
@Repository
public interface TaxReportingRepository
        extends CrudRepository<TrTableBsb, TrTableBsbPK>, JpaSpecificationExecutor<TrTableBsb> {
    /**
     * Query used to fetch the BSB record along with BM1 subtable records based on a
     * given parameter
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
     */
    @Query("select b from TrTableBsb b \n" + "left join b.trTableBm1Set bm1 on bm1.trTableBm1PK.keyIdNr = :keyIdNr \n"
            + "where b.trTableBsbPK.mandSl = :mandSl \n" + "and b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n"
            + "and b.trTableBsbPK.keySysDatum = :keySysDatum \n" + "and b.trTableBsbPK.keyUhrzeit = :keyUhrzeit \n"
            + "and b.trTableBsbPK.keyMuster = :keyMuster \n" + "and b.trTableBsbPK.keyIdNr = :keyIdNr")
    TrTableBsb findByMandSlAndKeyIdNRBm1(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit);

    /**
     * Query used to fetch the BSB record along with BM3 subtable records based on a
     * given parameter
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
     */
    @Query("select b from TrTableBsb b \n" + "left join b.trTableBm3Set bm3 on bm3.trTableBm3PK.keyIdNr = :keyIdNr \n"
            + "where b.trTableBsbPK.mandSl = :mandSl \n" + "and b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n"
            + "and b.trTableBsbPK.keySysDatum = :keySysDatum \n" + "and b.trTableBsbPK.keyUhrzeit = :keyUhrzeit \n"
            + "and b.trTableBsbPK.keyMuster = :keyMuster \n" + "and b.trTableBsbPK.keyIdNr = :keyIdNr")
    TrTableBsb findByMandSlAndKeyIdNRBm3(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("keySysDatum") long keySysDatum,
            @Param("keyUhrzeit") long keyUhrzeit);

    /**
     * Query used to fetch the list of BSB record along with subtable records based
     * on a given parameter
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
     * 
     * @return Set of Tax report BSB object
     */
    @Query("select b from TrTableBsb b \n" + "left join fetch b.trTableBm1Set \n" + "left join fetch b.trTableBm3Set \n"
            + "where b.trTableBsbPK.mandSl = :mandSl \n" + "and b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n" + "and b.trTableBsbPK.keyMuster = :keyMuster \n"
            + "and b.trTableBsbPK.keyIdNr = :keyIdNr\n" + "and b.kmId is not null\n" + "and b.ndTicket is not null\n"
            + "and b.status in ('B', 'K', 'S')"
            + "order by b.trTableBsbPK.keySysDatum desc, b.trTableBsbPK.keyUhrzeit desc")
    Set<TrTableBsb> findByKeyNokeySysDatumNorUhrzeit(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer);

    /**
     * Query used to get the count of previous record by passing required parameter
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
     * @param statusList
     *                          Status of BSB record
     * 
     * @return Count of BSB records
     */
    @Query("select count(*) from TrTableBsb b \n" + "where b.trTableBsbPK.mandSl = :mandSl \n"
            + "and b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n"
            + "and trim(b.trTableBsbPK.keyMuster) = trim(:keyMuster) \n"
            + "and trim(b.trTableBsbPK.keyIdNr) = trim(:keyIdNr)\n" + "and b.kmId is not null\n"
            + "and b.ndTicket is not null\n" + "and b.status in (:statusList)")
    Long findPreviousBSBRecordCount(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("statusList") List<String> statusList);

    /**
     * Query used to get the count of bsb with given status
     * to check for tax certificate deletion eligibility
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
     * @param statusList
     *                          Status of BSB record
     * 
     * @return Count of BSB records
     */
    @Query("select count(*) from TrTableBsb b \n" + "where b.trTableBsbPK.mandSl = :mandSl \n"
            + "and b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n" + "and b.trTableBsbPK.keyMuster = :keyMuster \n"
            + "and b.trTableBsbPK.keyIdNr = :keyIdNr\n" + "and b.status in (:statusList)")
    Long findBSBCountToCheckDeletionEligibility(@Param("mandSl") String mandSl, @Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("statusList") List<String> statusList);

    /**
     * Query used to get the count of previous record by passing B status
     * 
     * @param keyIdNr
     *                          Tax report Key id number
     * @param keyMeldejahr
     *                          Tax report filed year
     * @param keyMuster
     *                          Tax report type whether BM1 / BM3
     * @param keyLaufnummer
     *                          Sequence number for Key id number
     * @param statusList
     *                          Status of BSB record
     * 
     * @return Count of BSB records
     */
    @Query("select count(*) from TrTableBsb b \n" + "where b.trTableBsbPK.keyMeldejahr = :keyMeldejahr \n"
            + "and b.trTableBsbPK.keyLaufnummer = :keyLaufnummer \n" + "and b.trTableBsbPK.keyMuster = :keyMuster \n"
            + "and b.trTableBsbPK.keyIdNr = :keyIdNr\n" + "and b.status in (:statusList)")
    Long findPreviousBSBRecordCountForCorrection(@Param("keyIdNr") String keyIdNr,
            @Param("keyMeldejahr") long keyMeldejahr, @Param("keyMuster") String keyMuster,
            @Param("keyLaufnummer") long keyLaufnummer, @Param("statusList") List<String> statusList);

    /**
     * Query used to get the count of previous record by passing B status
     * 
     * @param keyIdNr
     *                    Tax report Key id number
     * 
     * @return Count of BSB records
     */
    @Query(
            nativeQuery = true,
            value = "select MAND_SL as mandSl,  \n"
                    + "    KEY_ID_NR as keyIdNr, KEY_MELDEJAHR as keyMeldejahr, KEY_MUSTER as keyMuster, KEY_LAUFNUMMER as  \n"
                    + "    keyLaufnummer, KEY_SYS_DATUM as keySysDatum, KEY_UHRZEIT as keyUhrzeit, NP2_IDNR as np2Idnr, MELDE_STATUS as meldeStatus,  \n"
                    + "    ANTWORT_STATUS as antwortStatus, NP1_STERBE_DATUM as np1SterbeDatum, NP2_STERBE_DATUM as np2SterbeDatum, KONTO_ID as kontoId,STATUS as status, \n"
                    + "    case  \n" + "    when STATUS = 'H' AND ROWNUMBER_H = 1 then 'true'  \n"
                    + "    when NON_H_COUNT > 0 then 'false' \n" + "    else 'false' end AS isLatestHistorical   \n"
                    + "from ( \n"
                    + "  select p.MAND_SL, p.KEY_ID_NR, p.KEY_MELDEJAHR, p.KEY_MUSTER, p.KEY_LAUFNUMMER, p.KEY_SYS_DATUM, p.KEY_UHRZEIT, p.NP2_IDNR, p.MELDE_STATUS,  \n"
                    + "    p.ANTWORT_STATUS, p.NP1_STERBE_DATUM, p.NP2_STERBE_DATUM, p.KONTO_ID,  \n"
                    + "    p.STATUS,     \n"
                    + "    count(case when p.STATUS!='H' then 1 End ) over (PARTITION by p.KEY_MELDEJAHR, p.KEY_LAUFNUMMER) As NON_H_COUNT, \n"
                    + "    ROW_NUMBER() OVER (PARTITION BY p.KEY_MELDEJAHR, p.KEY_LAUFNUMMER ORDER BY p.KEY_SYS_DATUM desc,p.KEY_UHRZEIT desc) as ROWNUMBER_H \n"
                    + "        from kortr_sch.tr_table_bsb p  \n"
                    + "        LEFT JOIN kortr_sch.tr_table_bm1 c1 ON (p.MAND_SL=c1.MAND_SL and p.KEY_ID_NR=c1.KEY_ID_NR  and p.KEY_MELDEJAHR=c1.KEY_MELDEJAHR and p.KEY_MUSTER=c1.KEY_MUSTER and p.KEY_LAUFNUMMER=c1.KEY_LAUFNUMMER and p.KEY_SYS_DATUM=c1.KEY_SYS_DATUM  and p.KEY_UHRZEIT=c1.KEY_UHRZEIT and p.STATUS=c1.KEY_SATZART) \n"
                    + "        LEFT JOIN kortr_sch.tr_table_bm3 c3 ON (p.MAND_SL=c3.MAND_SL and p.KEY_ID_NR=c3.KEY_ID_NR and p.KEY_MELDEJAHR=c3.KEY_MELDEJAHR and p.KEY_MUSTER=c3.KEY_MUSTER and p.KEY_LAUFNUMMER=c3.KEY_LAUFNUMMER  and p.KEY_SYS_DATUM=c3.KEY_SYS_DATUM and p.KEY_UHRZEIT=c3.KEY_UHRZEIT and  p.STATUS=c3.KEY_SATZART) \n"
                    + "    WHERE p.KEY_ID_NR = :keyIdNr \n"
                    + "    order by p.KEY_LAUFNUMMER, p.KEY_SYS_DATUM desc, p.KEY_UHRZEIT DESC ) ")
    List<Object[]> findLatestHistoryRecordForCorrection(@Param("keyIdNr") String keyIdNr);

    /**
     * Query used to fetch the list of BSB record passing financial year by exluding
     * request data
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
     * @param meldejahr
     *                          Tax report year
     *
     * @return List of tax report BSB object
     */
    @Query(
            nativeQuery = true,
            value = "select"
                    + " p.MAND_SL as mandSl, p.KEY_ID_NR as keyIdNr, p.KEY_MELDEJAHR as keyMeldejahr, p.KEY_MUSTER as keyMuster, p.KEY_LAUFNUMMER as keyLaufnummer, \n"
                    + " p.KEY_SYS_DATUM as keySysDatum, p.KEY_UHRZEIT as keyUhrzeit, p.NP2_IDNR as np2Idnr, p.MELDE_STATUS as meldeStatus, \n "
                    + " p.ANTWORT_STATUS as antwortStatus, p.NP1_STERBE_DATUM as np1SterbeDatum, p.NP2_STERBE_DATUM as np2SterbeDatum, p.KONTO_ID as kontoId, p.STATUS as status"
                    + " from kortr_sch.tr_table_bsb p where (p.MAND_SL,p.KEY_ID_NR,p.KEY_MELDEJAHR,p.KEY_MUSTER,p.KEY_LAUFNUMMER,p.KEY_SYS_DATUM,p.KEY_UHRZEIT) NOT IN ( SELECT \n"
                    + " x.MAND_SL, x.KEY_ID_NR, x.KEY_MELDEJAHR, x.KEY_MUSTER, x.KEY_LAUFNUMMER, x.KEY_SYS_DATUM, x.KEY_UHRZEIT FROM KORTR_SCH.TR_TABLE_BSB x \n"
                    + " where  x.MAND_SL =:mandSl and x.KEY_ID_NR =:keyIdNr and x.KEY_MELDEJAHR =:keyMeldejahr and x.KEY_MUSTER =:keyMuster \n"
                    + "	and x.KEY_LAUFNUMMER =:keyLaufnummer and x.KEY_SYS_DATUM =:keySysDatum and x.KEY_UHRZEIT =:keyUhrzeit)  and  p.MAND_SL =:mandSl \n"
                    + " and p.KEY_ID_NR =:keyIdNr and p.KEY_MELDEJAHR =:keyMeldejahr and p.KEY_MUSTER =:keyMuster and p.KEY_LAUFNUMMER =:keyLaufnummer and p.MELDEJAHR =:meldejahr ")
    List<Object[]> findBSBSameYearKeyExcludingRequestRecord(@Param("mandSl") String mandSl,
            @Param("keyIdNr") String keyIdNr, @Param("keyMeldejahr") long keyMeldejahr,
            @Param("keyMuster") String keyMuster, @Param("keyLaufnummer") long keyLaufnummer,
            @Param("keySysDatum") long keySysDatum, @Param("keyUhrzeit") long keyUhrzeit,
            @Param("meldejahr") long meldejahr);
}
