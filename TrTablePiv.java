package com.fisglobal.taxreporting.controller.dto.piv;

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
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


/**
 * The persistent class for the TR_TABLE_PIV database table.
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class TrTablePiv implements SubTable, Serializable {
    @Valid
    private TrTablePivPK trTablePivPK;

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTablePivPK.getMandSl(), trTablePivPK.getKeyIdNr(), trTablePivPK.getKeyMeldejahr(),
                trTablePivPK.getKeyMuster(), trTablePivPK.getKeyLaufnummer(), trTablePivPK.getKeySysDatum(),
                trTablePivPK.getKeyUhrzeit(), trTablePivPK.getKeySatzart());
    }

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String pivAntAnzahl;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String pivAntAusErloes;

    @Size(max = 25)
    private String pivAntBezeichng;

    @Size(max = 12)
    private String pivAntIsin;
}
