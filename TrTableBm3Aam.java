package com.fisglobal.taxreporting.entity.model.taxreporting.aam;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;


/**
 * The persistent class for the TR_TABLE_AAM database table for records
 * associated from TR_TABLE_BM3.
 */
@Entity
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TR_TABLE_AAM")
public class TrTableBm3Aam extends TrTableAam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "MAND_SL", referencedColumnName = "MAND_SL", insertable = false, updatable = false),
            @JoinColumn(name = "KEY_ID_NR", referencedColumnName = "KEY_ID_NR", insertable = false, updatable = false),
            @JoinColumn(
                    name = "KEY_MELDEJAHR",
                    referencedColumnName = "KEY_MELDEJAHR",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_MUSTER",
                    referencedColumnName = "KEY_MUSTER",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_LAUFNUMMER",
                    referencedColumnName = "KEY_LAUFNUMMER",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_SYS_DATUM",
                    referencedColumnName = "KEY_SYS_DATUM",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_UHRZEIT",
                    referencedColumnName = "KEY_UHRZEIT",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_SATZART",
                    referencedColumnName = "KEY_SATZART",
                    insertable = false,
                    updatable = false) })
    @JsonBackReference
    private TrTableBm3 trTableBm3FK;
}
