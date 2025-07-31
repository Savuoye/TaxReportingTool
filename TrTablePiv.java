package com.fisglobal.taxreporting.entity.model.taxreporting.piv;

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
 * The persistent class for the TR_TABLE_PIV database table.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTablePiv implements SubTable, Serializable {
    @Valid
    @EmbeddedId
    private TrTablePivPK trTablePivPK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTablePivPK.getMandSl(), trTablePivPK.getKeyIdNr(), trTablePivPK.getKeyMeldejahr(),
                trTablePivPK.getKeyMuster(), trTablePivPK.getKeyLaufnummer(), trTablePivPK.getKeySysDatum(),
                trTablePivPK.getKeyUhrzeit(), trTablePivPK.getKeySatzart());
    }

    @Column(name = "PIV_ANT_ANZAHL")
    @Digits(
            integer = 13,
            fraction = 5,
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private BigDecimal pivAntAnzahl;

    @Column(name = "PIV_ANT_AUS_ERLOES")
    @Digits(
            integer = 15,
            fraction = 3,
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private BigDecimal pivAntAusErloes;

    @Column(name = "PIV_ANT_BEZEICHNG", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String pivAntBezeichng;

    @Column(name = "PIV_ANT_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String pivAntIsin;
}
