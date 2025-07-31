package com.fisglobal.taxreporting.controller.dto.eik;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3;


/**
 * The DTO class for the TR_TABLE_EIK database table for records associated from
 * TR_TABLE_BM3.
 */
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TrTableBm3Eik extends TrTableEik implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonBackReference
    private TrTableBm3 trTableBm3FK;
}
