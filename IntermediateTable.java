package com.fisglobal.taxreporting.entity.model.taxreporting;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface IntermediateTable extends SubTable {
    @JsonIgnore
    public <T extends SubTable> void setTrTableAamSet(Set<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> Set<T> getTrTableAamSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableAkbSet(Set<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> Set<T> getTrTableAkbSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableAkeSet(Set<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> Set<T> getTrTableAkeSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTableEikSet(Set<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> Set<T> getTrTableEikSet();

    @JsonIgnore
    public <T extends SubTable> void setTrTablePivSet(Set<T> subTableSet);

    @JsonIgnore
    public <T extends SubTable> Set<T> getTrTablePivSet();
}
