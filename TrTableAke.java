package com.fisglobal.taxreporting.entity.model.taxreporting.ake;

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
public abstract class TrTableAke implements SubTable, Serializable {
    @Valid
    @EmbeddedId
    private TrTableAkePK trTableAkePK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAkePK.getMandSl(), trTableAkePK.getKeyIdNr(), trTableAkePK.getKeyMeldejahr(),
                trTableAkePK.getKeyMuster(), trTableAkePK.getKeyLaufnummer(), trTableAkePK.getKeySysDatum(),
                trTableAkePK.getKeyUhrzeit(), trTableAkePK.getKeySatzart());
    }

    @Column(name = "AKE_ANT_ANZAHL")
    @Digits(
            integer = 13,
            fraction = 5,
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private BigDecimal akeAntAnzahl;

    @Column(name = "AKE_ANT_BEZEICHN", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String akeAntBezeichn;

    @Column(name = "AKE_ANT_EBMG_N_ERM", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String akeAntEbmgNErm;

    @Column(name = "AKE_ANT_ERS_BMG")
    @Digits(
            integer = 9,
            fraction = 9,
            message = "Integer length must be less than or equal to 9, and the fractional part length must be less than or equal to 9")
    private BigDecimal akeAntErsBmg;

    @Column(name = "AKE_ANT_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String akeAntIsin;
}
