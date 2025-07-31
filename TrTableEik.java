package com.fisglobal.taxreporting.controller.dto.eik;

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
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;


/**
 * The persistent class for the TR_TABLE_EIK database table.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTableEik implements SubTable, Serializable {
    @Valid
    private TrTableEikPK trTableEikPK;

    @Override
    public UniqueKey getKey() {
        // TODO Auto-generated method stub
        return new UniqueKey(trTableEikPK.getMandSl(), trTableEikPK.getKeyIdNr(), trTableEikPK.getKeyMeldejahr(),
                trTableEikPK.getKeyMuster(), trTableEikPK.getKeyLaufnummer(), trTableEikPK.getKeySysDatum(),
                trTableEikPK.getKeyUhrzeit(), trTableEikPK.getKeySatzart());
    }

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String eikAntAnzahl;

    @Pattern(
            regexp = "^$|^\\d{1,9}(\\.\\d{1,9})?$",
            message = "Integer length must be less than or equal to 9, and the fractional part length must be less than or equal to 9")
    private String eikAntAusschuett;

    @Size(max = 25)
    private String eikAntBezeichn;

    @Size(max = 12)
    private String eikAntIsin;
}
