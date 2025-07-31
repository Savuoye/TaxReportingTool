package com.fisglobal.taxreporting.service.taxreportingsearch.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.model.TrTableSearch;


/**
 * This class holds list of tax report search response details
 */
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxReports {
    @JsonProperty("taxreports")
    @Valid
    private List<TrTableSearch> taxreports = null;

    @JsonProperty("totalCountWithoutPaging")
    private Long totalCountWithoutPaging = null;

    public void addTaxReportsItem(TrTableSearch reportsItem) {
        if (this.taxreports == null) {
            this.taxreports = new ArrayList<TrTableSearch>();
        }
        this.taxreports.add(reportsItem);
    }
}
