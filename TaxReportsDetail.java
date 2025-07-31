package com.fisglobal.taxreporting.service.taxreportingsearch.model;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;


/**
 * This class hold tax report data in detail
 */
@Valid
public class TaxReportsDetail {
    @JsonProperty("taxDetailReports")
    @Valid
    private TrTableBsb taxDetailReports = null;

    @Schema(description = "")
    @Valid
    public TrTableBsb getTaxDetailReports() {
        return taxDetailReports;
    }

    public void setTaxDetailReports(TrTableBsb taxDetailReports) {
        this.taxDetailReports = taxDetailReports;
    }
}
