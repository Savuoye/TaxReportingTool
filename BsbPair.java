package com.fisglobal.taxreporting.util.journaling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fisglobal.taxreporting.util.CommonConstants;


/**
 * The dto class for the search with fields of TR_TABLE_BSB, TR_TABLE_BM1,
 * TR_TABLE_BM3 tables
 */
@Data
@AllArgsConstructor
public class BsbPair {
    private List<String> summaries;

    private Map<String, List<Map<String, Map<String, Map<String, Object>>>>> changes;

    public BsbPair() {
        this.summaries = new ArrayList<>();
        this.changes = new HashMap<>();
    }

    private int version = CommonConstants.JOURNALING_VERSION;
}
