package com.fisglobal.taxreporting.entity.model.taxreporting.aam;

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
public abstract class TrTableAam implements SubTable, Serializable {
    @Valid
    @EmbeddedId
    private TrTableAamPK trTableAamPK;

    @Column(name = "AAM_ANT_ANZAHL")
    @Digits(
            integer = 13,
            fraction = 5,
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private BigDecimal aamAntAnzahl;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAamPK.getMandSl(), trTableAamPK.getKeyIdNr(), trTableAamPK.getKeyMeldejahr(),
                trTableAamPK.getKeyMuster(), trTableAamPK.getKeyLaufnummer(), trTableAamPK.getKeySysDatum(),
                trTableAamPK.getKeyUhrzeit(), trTableAamPK.getKeySatzart());
    }

    @Column(name = "AAM_ANT_BEZEICHN", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String aamAntBezeichn;

    @Column(name = "AAM_ANT_GEW_FIKTIV")
    @Digits(
            integer = 15,
            fraction = 3,
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private BigDecimal aamAntGewFiktiv;

    @Column(name = "AAM_ANT_GEW_NI_ERM", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String aamAntGewNiErm;

    @Column(name = "AAM_ANT_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String aamAntIsin;

    @Column(name = "AAM_GEW_VER_MILLFO")
    @Digits(
            integer = 15,
            fraction = 3,
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private BigDecimal aamGewVerMillfo;
}
