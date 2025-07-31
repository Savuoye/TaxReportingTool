package com.fisglobal.taxreporting.entity.model.taxreporting.journaling;

import java.io.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


/**
 * The persistent class for the TR_TABLE_BSB database table.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "JOURNALING")
public class TrTableJournaling implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrTableJournalingPK trTableJournalingPK;

    @Column(name = "JOURNALING_VERSION")
    private int version;

    @Column(name = "USERNAME", length = 50, columnDefinition = "VARCHAR2(50)")
    @Size(max = 50)
    private String username;

    @Column(name = "USAGE", length = 4, columnDefinition = "VARCHAR2(4)")
    @Size(max = 4)
    private String usage;

    @Column(name = "SUMMARY", length = 255, columnDefinition = "VARCHAR2(255)")
    @Size(max = 255)
    private String summary;

    @Column(name = "PAYLOAD")
    @Lob
    private String payload;
}
