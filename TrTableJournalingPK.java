package com.fisglobal.taxreporting.entity.model.taxreporting.journaling;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The primary key class for the TR_TABLE_BSB database table.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TrTableJournalingPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "MAND_SL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String mandSl;

    @Column(name = "KEY_ID_NR", length = 13, columnDefinition = "CHAR(13)")
    @Size(max = 13)
    @JsonProperty("keyIdNr")
    private String keyIdNr;

    @Column(name = "KEY_MELDEJAHR")
    private long keyMeldejahr;

    @Column(name = "KEY_MUSTER", length = 8, columnDefinition = "CHAR(8)")
    @Size(max = 8)
    private String keyMuster;

    @Column(name = "KEY_LAUFNUMMER")
    private long keyLaufnummer;

    @Column(name = "KEY_SYS_DATUM")
    private long keySysDatum;

    @Column(name = "KEY_UHRZEIT")
    private long keyUhrzeit;

    @Column(name = "TIME", length = 16, columnDefinition = "CHAR(16)")
    @Size(max = 16)
    private String time;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrTableJournalingPK other = (TrTableJournalingPK) obj;
        return Objects.equals(keyIdNr, other.keyIdNr) && keyLaufnummer == other.keyLaufnummer
                && keyMeldejahr == other.keyMeldejahr && Objects.equals(keyMuster, other.keyMuster)
                && keySysDatum == other.keySysDatum && keyUhrzeit == other.keyUhrzeit
                && Objects.equals(mandSl, other.mandSl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyIdNr, keyLaufnummer, keyMeldejahr, keyMuster, keySysDatum, keyUhrzeit, mandSl, time);
    }
}
