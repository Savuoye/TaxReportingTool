package com.fisglobal.taxreporting.entity.model.taxreporting.eik;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;


/**
 * The persistent class for the TR_TABLE_EIK database table.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableEik implements SubTable, Serializable {
    @Valid
    @EmbeddedId
    private TrTableEikPK trTableEikPK;

    @Override
    public UniqueKey getKey() {
        // TODO Auto-generated method stub
        return new UniqueKey(trTableEikPK.getMandSl(), trTableEikPK.getKeyIdNr(), trTableEikPK.getKeyMeldejahr(),
                trTableEikPK.getKeyMuster(), trTableEikPK.getKeyLaufnummer(), trTableEikPK.getKeySysDatum(),
                trTableEikPK.getKeyUhrzeit(), trTableEikPK.getKeySatzart());
    }

    @Column(name = "EIK_ANT_ANZAHL")
    @Digits(
            integer = 13,
            fraction = 5,
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private BigDecimal eikAntAnzahl;

    @Column(name = "EIK_ANT_AUSSCHUETT")
    @Digits(
            integer = 9,
            fraction = 9,
            message = "Integer length must be less than or equal to 9, and the fractional part length must be less than or equal to 9")
    private BigDecimal eikAntAusschuett;

    @Column(name = "EIK_ANT_BEZEICHN", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String eikAntBezeichn;

    @Column(name = "EIK_ANT_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String eikAntIsin;
}
