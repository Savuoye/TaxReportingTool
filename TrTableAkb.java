package com.fisglobal.taxreporting.entity.model.taxreporting.akb;

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


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableAkb implements SubTable, Serializable {
    @Valid
    @EmbeddedId
    private TrTableAkbPK trTableAkbPK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAkbPK.getMandSl(), trTableAkbPK.getKeyIdNr(), trTableAkbPK.getKeyMeldejahr(),
                trTableAkbPK.getKeyMuster(), trTableAkbPK.getKeyLaufnummer(), trTableAkbPK.getKeySysDatum(),
                trTableAkbPK.getKeyUhrzeit(), trTableAkbPK.getKeySatzart());
    }

    @Column(name = "AKB_ANT_ANZAHL")
    @Digits(
            integer = 13,
            fraction = 5,
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private BigDecimal akbAntAnzahl;

    @Column(name = "AKB_ANT_BEZEICHN", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String akbAntBezeichn;

    @Column(name = "AKB_ANT_GEWINN")
    @Digits(
            integer = 15,
            fraction = 3,
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private BigDecimal akbAntGewinn;

    @Column(name = "AKB_ANT_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String akbAntIsin;
}
