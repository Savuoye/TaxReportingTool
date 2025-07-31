package com.fisglobal.taxreporting.controller.dto.akb;

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
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableAkb implements SubTable, Serializable {
    @Valid
    private TrTableAkbPK trTableAkbPK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAkbPK.getMandSl(), trTableAkbPK.getKeyIdNr(), trTableAkbPK.getKeyMeldejahr(),
                trTableAkbPK.getKeyMuster(), trTableAkbPK.getKeyLaufnummer(), trTableAkbPK.getKeySysDatum(),
                trTableAkbPK.getKeyUhrzeit(), trTableAkbPK.getKeySatzart());
    }

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String akbAntAnzahl;

    @Size(max = 25)
    private String akbAntBezeichn;

    @Pattern(
            regexp = "^$|[-]?\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String akbAntGewinn;

    @Size(max = 12)
    private String akbAntIsin;
}
