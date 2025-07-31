package com.fisglobal.taxreporting.controller.dto.ake;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableAke implements SubTable, Serializable {
    @Valid
    private TrTableAkePK trTableAkePK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAkePK.getMandSl(), trTableAkePK.getKeyIdNr(), trTableAkePK.getKeyMeldejahr(),
                trTableAkePK.getKeyMuster(), trTableAkePK.getKeyLaufnummer(), trTableAkePK.getKeySysDatum(),
                trTableAkePK.getKeyUhrzeit(), trTableAkePK.getKeySatzart());
    }

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String akeAntAnzahl;

    @Size(max = 25)
    private String akeAntBezeichn;

    @Size(max = 1)
    private String akeAntEbmgNErm;

    @Pattern(
            regexp = "^$|^\\d{1,9}(\\.\\d{1,9})?$",
            message = "Integer length must be less than or equal to 9, and the fractional part length must be less than or equal to 9")
    private String akeAntErsBmg;

    @Size(max = 12)
    private String akeAntIsin;
}
