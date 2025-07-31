package com.fisglobal.taxreporting.service.taxreportingsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class holds all the tax report search parameters
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxReportingSearchParameters {
    private String keyIdNr;

    private Long keyMeldejahr;

    private String meldeStatus;

    private String antwortStatus;
}
