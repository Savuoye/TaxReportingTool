package com.fisglobal.taxreporting.entity.model.taxreporting.bsb;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


/**
 * The persistent class for the TR_TABLE_BSB database table. Contains only the
 * fields needed for review of tax reports process
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "TR_TABLE_BSB")
public class TrTableBsbReview implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrTableBsbPK trTableBsbPK;

    @Column(name = "STATUS", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String status;

    @Column(name = "MELDE_STATUS", length = 2, columnDefinition = "CHAR(2)")
    @Size(max = 2)
    private String meldeStatus;

    @Column(name = "AENDER_ZEIT")
    private BigDecimal aenderZeit;

    @Column(name = "AENDER_DATUM")
    private BigDecimal aenderDatum;

    @Column(name = "AENDER_ERFASSER", length = 8, columnDefinition = "CHAR(8)")
    @Size(max = 8)
    private String aenderErfasser;

    @Column(name = "BEDBSB_TIMESTAMP", length = 16, columnDefinition = "CHAR(16)")
    @Size(max = 16)
    private String bedbsbTimestamp;
}
