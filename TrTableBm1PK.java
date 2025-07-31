package com.fisglobal.taxreporting.entity.model.taxreporting.bm1;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The primary key class for the TR_TABLE_BM1 database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TrTableBm1PK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "MAND_SL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String mandSl;

    @Column(name = "KEY_ID_NR", length = 13, columnDefinition = "CHAR(13)")
    @Size(max = 13)
    private String keyIdNr;

    @Column(name = "KEY_MELDEJAHR")
    @Digits(integer = 4, fraction = 0, message = "Length must be less than or equal to 4")
    private long keyMeldejahr;

    @Column(name = "KEY_MUSTER", length = 8, columnDefinition = "CHAR(8)")
    @Size(max = 8)
    private String keyMuster;

    @Column(name = "KEY_LAUFNUMMER")
    @Digits(integer = 15, fraction = 0, message = "Length must be less than or equal to 15")
    private long keyLaufnummer;

    @Column(name = "KEY_SYS_DATUM")
    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private long keySysDatum;

    @Column(name = "KEY_UHRZEIT")
    @Digits(integer = 6, fraction = 0, message = "Length must be less than or equal to 6")
    private long keyUhrzeit;

    @Column(name = "KEY_SATZART", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String keySatzart;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrTableBm1PK other = (TrTableBm1PK) obj;
        return Objects.equals(keyIdNr, other.keyIdNr) && keyLaufnummer == other.keyLaufnummer
                && keyMeldejahr == other.keyMeldejahr && Objects.equals(keyMuster, other.keyMuster)
                && Objects.equals(keySatzart, other.keySatzart) && keySysDatum == other.keySysDatum
                && keyUhrzeit == other.keyUhrzeit && Objects.equals(mandSl, other.mandSl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyIdNr, keyLaufnummer, keyMeldejahr, keyMuster, keySatzart, keySysDatum, keyUhrzeit,
                mandSl);
    }
}
