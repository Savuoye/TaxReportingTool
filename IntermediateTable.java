package com.fisglobal.taxreporting.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;


/**
 * This interface used to define the subtable manipulation
 */
public interface IntermediateTable extends SubTable {
    @JsonIgnore
    public <T extends SubTable> void setTrTableAamSet(List<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> List<T> getTrTableAamSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableAkbSet(List<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> List<T> getTrTableAkbSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableAkeSet(List<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> List<T> getTrTableAkeSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableEikSet(List<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> List<T> getTrTableEikSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTablePivSet(List<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> List<T> getTrTablePivSet();
}
