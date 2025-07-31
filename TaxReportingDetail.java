package com.fisglobal.taxreporting.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb;


/**
 * This class contains
 * 1.create adjustment flag "hasPreviousBsbList" based on previous record list
 * 2.delete historicized record eligibility check flag "isSafeDelete"
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class TaxReportingDetail extends TrTableBsb {
    Boolean hasPreviousBsbList = false;

    Boolean isSafeDelete = false;

    Boolean hasLatestHistoricalBsb = false;
}
