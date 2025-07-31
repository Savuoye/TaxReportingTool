package com.fisglobal.taxreporting.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The dto class for the search with fields of TR_TABLE_BSB, TR_TABLE_BM1,
 * TR_TABLE_BM3 tables
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrTableSearch {
    private String mandSl;

    private String keyIdNr;

    private long keyMeldejahr;

    private String keyMuster;

    private long keyLaufnummer;

    private long keySysDatum;

    private long keyUhrzeit;

    private String np2Idnr;

    private String meldeStatus;

    private String antwortStatus;

    private BigDecimal np1SterbeDatum;

    private BigDecimal np2SterbeDatum;

    private String kontoId;

    private String status;

    @JsonInclude(Include.NON_NULL)
    private Boolean isLatestHistorical;

    public TrTableSearch(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster, long keyLaufnummer,
            long keySysDatum, long keyUhrzeit, String np2Idnr, String meldeStatus, String antwortStatus,
            BigDecimal np1SterbeDatum, BigDecimal np2SterbeDatum, String kontoId, String status) {
        super();
        this.mandSl = mandSl;
        this.keyIdNr = keyIdNr;
        this.keyMeldejahr = keyMeldejahr;
        this.keyMuster = keyMuster;
        this.keyLaufnummer = keyLaufnummer;
        this.keySysDatum = keySysDatum;
        this.keyUhrzeit = keyUhrzeit;
        this.np2Idnr = np2Idnr;
        this.meldeStatus = meldeStatus;
        this.antwortStatus = antwortStatus;
        this.np1SterbeDatum = np1SterbeDatum;
        this.np2SterbeDatum = np2SterbeDatum;
        this.kontoId = kontoId;
        this.status = status;
    }
}
