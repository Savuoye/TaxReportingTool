package com.fisglobal.taxreporting.controller.dto.aam;

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
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableAam implements SubTable, Serializable {
    @Valid
    private TrTableAamPK trTableAamPK;

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String aamAntAnzahl;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableAamPK.getMandSl(), trTableAamPK.getKeyIdNr(), trTableAamPK.getKeyMeldejahr(),
                trTableAamPK.getKeyMuster(), trTableAamPK.getKeyLaufnummer(), trTableAamPK.getKeySysDatum(),
                trTableAamPK.getKeyUhrzeit(), trTableAamPK.getKeySatzart());
    }

    @Size(max = 25)
    private String aamAntBezeichn;

    @Pattern(
            regexp = "^$|[-]?\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String aamAntGewFiktiv;

    @Size(max = 1)
    private String aamAntGewNiErm;

    @Size(max = 12)
    private String aamAntIsin;

    @Pattern(
            regexp = "^$|[-]?\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String aamGewVerMillfo;
}
