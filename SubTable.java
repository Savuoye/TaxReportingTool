package com.fisglobal.taxreporting.entity.model.taxreporting;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface SubTable {
    @JsonIgnore
    public UniqueKey getKey();
}
