package com.fisglobal.taxreporting.entity.model.taxreporting.bm3;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.entity.model.taxreporting.IntermediateTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm3Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm3Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


/**
 * The persistent class for the TR_TABLE_BM3 database table.
 */
@Entity
@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        exclude = { "trTableBsbFK", "trTableBm3AamSet", "trTableBm3AkbSet", "trTableBm3AkeSet", "trTableBm3EikSet",
                "trTableBm3PivSet" })
@Table(name = "TR_TABLE_BM3")
public class TrTableBm3 implements Serializable, IntermediateTable, Comparable<TrTableBm3> {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrTableBm3PK trTableBm3PK;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "MAND_SL", referencedColumnName = "MAND_SL", insertable = false, updatable = false),
            @JoinColumn(name = "KEY_ID_NR", referencedColumnName = "KEY_ID_NR", insertable = false, updatable = false),
            @JoinColumn(
                    name = "KEY_MELDEJAHR",
                    referencedColumnName = "KEY_MELDEJAHR",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_MUSTER",
                    referencedColumnName = "KEY_MUSTER",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_LAUFNUMMER",
                    referencedColumnName = "KEY_LAUFNUMMER",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_SYS_DATUM",
                    referencedColumnName = "KEY_SYS_DATUM",
                    insertable = false,
                    updatable = false),
            @JoinColumn(
                    name = "KEY_UHRZEIT",
                    referencedColumnName = "KEY_UHRZEIT",
                    insertable = false,
                    updatable = false) })
    @JsonBackReference
    private TrTableBsb trTableBsbFK;

    @JsonProperty("trTableBm3AamSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm3FK", orphanRemoval = true)
    @OrderBy("trTableAamPK.keyAbrechNr ASC")
    private Set<TrTableBm3Aam> trTableBm3AamSet = new LinkedHashSet<TrTableBm3Aam>();

    @JsonProperty("trTableBm3AkbSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm3FK", orphanRemoval = true)
    @OrderBy("trTableAkbPK.keyAbrechNr ASC")
    private Set<TrTableBm3Akb> trTableBm3AkbSet = new LinkedHashSet<TrTableBm3Akb>();

    @JsonProperty("trTableBm3AkeSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm3FK", orphanRemoval = true)
    @OrderBy("trTableAkePK.keyAbrechNr ASC")
    private Set<TrTableBm3Ake> trTableBm3AkeSet = new LinkedHashSet<TrTableBm3Ake>();

    @JsonProperty("trTableBm3EikSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm3FK", orphanRemoval = true)
    @OrderBy("trTableEikPK.keyAbrechNr ASC")
    private Set<TrTableBm3Eik> trTableBm3EikSet = new LinkedHashSet<TrTableBm3Eik>();

    @JsonProperty("trTableBm3PivSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm3FK", orphanRemoval = true)
    @OrderBy("trTablePivPK.keyAbrechNr ASC")
    private Set<TrTableBm3Piv> trTableBm3PivSet = new LinkedHashSet<TrTableBm3Piv>();

    @Column(name = "ABSTAND_STEUERABZ", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String abstandSteuerabz;

    @Column(name = "ABZ_DREI_FUE_P43_3")
    private BigDecimal abzDreiFueP433;

    @Column(name = "AKB_VERAEUSSERT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String akbVeraeussert;

    @Column(name = "AKE_VERAEUSSERT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String akeVeraeussert;

    @Column(name = "ANZAHL_AKB")
    private BigDecimal anzahlAkb;

    @Column(name = "ANZAHL_AKE")
    private BigDecimal anzahlAke;

    @Column(name = "ANZAHL_EIK")
    private BigDecimal anzahlEik;

    @Column(name = "ANZAHL_PIV")
    private BigDecimal anzahlPiv;

    @Column(name = "AULS_KI_GUT", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String aulsKiGut;

    @Column(name = "AUSL_INV_K_STABZUG")
    private BigDecimal auslInvKStabzug;

    @Column(name = "AUSL_KI_ANTR", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiAntr;

    @Column(name = "AUSL_KI_NAM_STADT", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiNamStadt;

    @Column(name = "AUSL_KI_VERW", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiVerw;

    @Column(name = "AUSL_SPEZ_INV", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String auslSpezInv;

    @Column(name = "DEP_RE_ANZ_BESCH")
    private BigDecimal depReAnzBesch;

    @Column(name = "DEP_RE_GESAMTZAHL")
    private BigDecimal depReGesamtzahl;

    @Column(name = "DEP_RE_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String depReIsin;

    @Column(name = "EIK_ERSTATTET", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String eikErstattet;

    @Column(name = "EINLAGEKONTO_SUM")
    private BigDecimal einlagekontoSum;

    @Column(name = "ERSATZ_BMG")
    private BigDecimal ersatzBmg;

    @Column(name = "ERT_BESCHR_STPFL_1")
    private BigDecimal ertBeschrStpfl1;

    @Column(name = "ERT_BESCHR_STPFL_2")
    private BigDecimal ertBeschrStpfl2;

    @Column(name = "ERTRAG_P19_REIT_1")
    private BigDecimal ertragP19Reit1;

    @Column(name = "ERTRAG_P19_REIT_6")
    private BigDecimal ertragP19Reit6;

    @Column(name = "ERTRAG_P43_1")
    private BigDecimal ertragP431;

    @Column(name = "ERTRAG_P43_2")
    private BigDecimal ertragP432;

    @Column(name = "ERTRAG_P43_3")
    private BigDecimal ertragP433;

    @Column(name = "ERTRAG_P43_4")
    private BigDecimal ertragP434;

    @Column(name = "ERTRAG_P43_5")
    private BigDecimal ertragP435;

    @Column(name = "ERTRAG_P43_6")
    private BigDecimal ertragP436;

    @Column(name = "ERTRAG_P43_7")
    private BigDecimal ertragP437;

    @Column(name = "ERTRAG_P43_8")
    private BigDecimal ertragP438;

    @Column(name = "ERTRAG_P43_9")
    private BigDecimal ertragP439;

    @Column(name = "ERTRAG_TEV_P43_1")
    private BigDecimal ertragTevP431;

    @Column(name = "ERTRAG_TEV_P43_6")
    private BigDecimal ertragTevP436;

    @Column(name = "ERTRAG_TEV_P43_9")
    private BigDecimal ertragTevP439;

    @Column(name = "ESTB", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String estb;

    @Column(name = "FEHLER_KENNZ", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String fehlerKennz;

    @Column(name = "FORMULAR", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String formular;

    @Column(name = "GEWINN_VER_INV")
    private BigDecimal gewinnVerInv;

    @Column(name = "INL_KI_VERW", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String inlKiVerw;

    @Column(name = "INV_16_12_AIMMF")
    private BigDecimal inv1612Aimmf;

    @Column(name = "INV_16_12_AIMMF_VP")
    private BigDecimal inv1612AimmfVp;

    @Column(name = "INV_16_12_AKTF")
    private BigDecimal inv1612Aktf;

    @Column(name = "INV_16_12_AKTF_VP")
    private BigDecimal inv1612AktfVp;

    @Column(name = "INV_16_12_IMMF")
    private BigDecimal inv1612Immf;

    @Column(name = "INV_16_12_IMMF_VP")
    private BigDecimal inv1612ImmfVp;

    @Column(name = "INV_16_12_MISF")
    private BigDecimal inv1612Misf;

    @Column(name = "INV_16_12_MISF_VP")
    private BigDecimal inv1612MisfVp;

    @Column(name = "INV_16_12_SONF")
    private BigDecimal inv1612Sonf;

    @Column(name = "INV_16_12_SONF_VP")
    private BigDecimal inv1612SonfVp;

    @Column(name = "INV_16_3_AIMMF")
    private BigDecimal inv163Aimmf;

    @Column(name = "INV_16_3_AKTF")
    private BigDecimal inv163Aktf;

    @Column(name = "INV_16_3_IMMF")
    private BigDecimal inv163Immf;

    @Column(name = "INV_16_3_MISF")
    private BigDecimal inv163Misf;

    @Column(name = "INV_16_3_SONF")
    private BigDecimal inv163Sonf;

    @Column(name = "INV_ERT_16_12")
    private BigDecimal invErt1612;

    @Column(name = "INV_ERT_16_3")
    private BigDecimal invErt163;

    @Column(name = "INV_VORHANDEN", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String invVorhanden;

    @Column(name = "K_STABZUG_P43_NR1", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String kStabzugP43Nr1;

    @Column(name = "K_STABZUG_P43_NR5", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String kStabzugP43Nr5;

    @Column(name = "KAP_DREI_FUE_P43_3")
    private BigDecimal kapDreiFueP433;

    @Column(name = "KAPEST")
    private BigDecimal kapest;

    @Column(name = "KEIN_ORIGINAL_M3", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String keinOriginalM3;

    @Column(name = "LIEFERTIEFE", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String liefertiefe;

    @Column(name = "ORDNUNGSNUMMER", length = 36, columnDefinition = "CHAR(36)")
    @Size(max = 36)
    private String ordnungsnummer;

    @Column(name = "PIV_VERWAHRT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String pivVerwahrt;

    @Column(name = "SD_BEZUG_W_P", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String sdBezugWP;

    @Column(name = "SD_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String sdIsin;

    @Column(name = "SD_WKN", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String sdWkn;

    @Column(name = "SF_ANL_ANZAHL")
    private BigDecimal sfAnlAnzahl;

    @Column(name = "SF_ANL_BEZUG_W_P", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String sfAnlBezugWP;

    @Column(name = "SF_ANL_KAPEST")
    private BigDecimal sfAnlKapest;

    @Column(name = "SF_ANL_SOLZ")
    private BigDecimal sfAnlSolz;

    @Column(name = "SF_ANTEILE")
    private BigDecimal sfAnteile;

    @Column(name = "SF_BET_EINNAHM", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String sfBetEinnahm;

    @Column(name = "SF_BETRAG")
    private BigDecimal sfBetrag;

    @Column(name = "SF_BEZEICHN", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String sfBezeichn;

    @Column(name = "SF_DATUM")
    private BigDecimal sfDatum;

    @Column(name = "SF_ISIN", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String sfIsin;

    @Column(name = "SF_SCHULD_BEZ_WP", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String sfSchuldBezWp;

    @Column(name = "SF_SONS_EINK", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String sfSonsEink;

    @Column(name = "SF_WKN", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String sfWkn;

    @Column(name = "SOLZ")
    private BigDecimal solz;

    @Column(name = "STORNIERUNG", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String stornierung;

    @Column(name = "ZAHLUNGSTAG")
    private BigDecimal zahlungstag;

    @Column(name = "ZAHLUNGSZEITR_BIS")
    private BigDecimal zahlungszeitrBis;

    @Column(name = "ZAHLUNGSZEITR_VON")
    private BigDecimal zahlungszeitrVon;

    @Column(name = "ZUS_GEFASSTE_STB", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String zusGefassteStb;

    @Column(name = "ZUS_ZEITRAUM_BIS")
    private BigDecimal zusZeitraumBis;

    @Column(name = "ZUS_ZEITRAUM_VON")
    private BigDecimal zusZeitraumVon;

    @Column(name = "ABZ_DREI_FUE_P43_1")
    private BigDecimal abzDreiFueP431;

    @Column(name = "KAP_DREI_FUE_P43_1")
    private BigDecimal kapDreiFueP431;

    @Override
    public int compareTo(TrTableBm3 o2) {
        long t1_time = this.trTableBm3PK.getKeySysDatum() * 1_000_000 + this.trTableBm3PK.getKeyUhrzeit();
        long t2_time = o2.trTableBm3PK.getKeySysDatum() * 1_000_000 + o2.trTableBm3PK.getKeyUhrzeit();
        if (t1_time != t2_time) {
            return Long.compare(t1_time, t2_time);
        }
        String t1 = this.trTableBm3PK.getKeySatzart();
        String t2 = o2.trTableBm3PK.getKeySatzart();
        if (t1.equals(t2)) {
            return 0;
        }
        if (t1.equals("H")) {
            return -1;
        }
        if (t2.equals("H")) {
            return 1;
        }
        if (t1.equals("B") && t2.equals("K")) {
            return -1;
        }
        if (t1.equals("K") && t2.equals("B")) {
            return 1;
        }
        if (t1.equals("B") && t2.equals("S")) {
            return -1;
        }
        if (t1.equals("S") && t2.equals("B")) {
            return 1;
        }
        if (t1.equals("K") && t2.equals("S")) {
            return -1;
        }
        if (t1.equals("S") && t2.equals("K")) {
            return 1;
        }
        return 0;
    }

    @Override
    public UniqueKey getKey() {
        return new UniqueKey(trTableBm3PK.getMandSl(), trTableBm3PK.getKeyIdNr(), trTableBm3PK.getKeyMeldejahr(),
                trTableBm3PK.getKeyMuster(), trTableBm3PK.getKeyLaufnummer(), trTableBm3PK.getKeySysDatum(),
                trTableBm3PK.getKeyUhrzeit(), trTableBm3PK.getKeySatzart());

    }

    @Override
    public <T extends SubTable> void setTrTableAamSet(Set<T> subTableSet) {
        Set<TrTableBm3Aam> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm3Aam) {
            subTableSetConverted = (Set<TrTableBm3Aam>) subTableSet;
        }

        if (getTrTableBm3AamSet() == null) {
            setTrTableBm3AamSet(subTableSetConverted);
            return;
        }

        Set<TrTableAamPK> Bm3AamPKPrevious = getTrTableBm3AamSet().stream().map(TrTableBm3Aam::getTrTableAamPK)
                .collect(Collectors.toSet());
        Set<TrTableAamPK> Bm3AamPKAfter = subTableSetConverted.stream().map(TrTableBm3Aam::getTrTableAamPK)
                .collect(Collectors.toSet());
        Set<TrTableAamPK> Bm3AamPKIntersection;

        Bm3AamPKIntersection = new HashSet<>(Bm3AamPKPrevious);
        Bm3AamPKIntersection.retainAll(Bm3AamPKAfter);

        for (TrTableAamPK pk : Bm3AamPKPrevious) {
            if (!Bm3AamPKAfter.contains(pk)) {
                setTrTableBm3AamSet(getTrTableBm3AamSet().stream()
                        .filter(Bm3Aam -> !Bm3Aam.getTrTableAamPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAamPK pk : Bm3AamPKAfter) {
            if (!Bm3AamPKPrevious.contains(pk)) {
                getTrTableBm3AamSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Aam -> Bm3Aam.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm3AamPKIntersection.size() > 0) {
            for (TrTableAamPK pk : Bm3AamPKIntersection) {
                if (getTrTableBm3AamSet().stream().filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm3AamSet().stream().filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk)).findFirst().get())) {
                            setTrTableBm3AamSet(getTrTableBm3AamSet().stream()
                                    .filter(Bm3Aam -> !Bm3Aam.getTrTableAamPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm3AamSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAamSet() {
        return (Set<T>) trTableBm3AamSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkbSet(Set<T> subTableSet) {
        Set<TrTableBm3Akb> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm3Akb) {
            subTableSetConverted = (Set<TrTableBm3Akb>) subTableSet;
        }

        if (getTrTableBm3AkbSet() == null) {
            setTrTableBm3AkbSet(subTableSetConverted);
            return;
        }

        Set<TrTableAkbPK> Bm3AkbPKPrevious = getTrTableBm3AkbSet().stream().map(TrTableBm3Akb::getTrTableAkbPK)
                .collect(Collectors.toSet());
        Set<TrTableAkbPK> Bm3AkbPKAfter = subTableSetConverted.stream().map(TrTableBm3Akb::getTrTableAkbPK)
                .collect(Collectors.toSet());
        Set<TrTableAkbPK> Bm3AkbPKIntersection;

        Bm3AkbPKIntersection = new HashSet<>(Bm3AkbPKPrevious);
        Bm3AkbPKIntersection.retainAll(Bm3AkbPKAfter);

        for (TrTableAkbPK pk : Bm3AkbPKPrevious) {
            if (!Bm3AkbPKAfter.contains(pk)) {
                setTrTableBm3AkbSet(getTrTableBm3AkbSet().stream()
                        .filter(Bm3Akb -> !Bm3Akb.getTrTableAkbPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAkbPK pk : Bm3AkbPKAfter) {
            if (!Bm3AkbPKPrevious.contains(pk)) {
                getTrTableBm3AkbSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Akb -> Bm3Akb.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm3AkbPKIntersection.size() > 0) {
            for (TrTableAkbPK pk : Bm3AkbPKIntersection) {
                if (getTrTableBm3AkbSet().stream().filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm3AkbSet().stream().filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk)).findFirst().get())) {
                            setTrTableBm3AkbSet(getTrTableBm3AkbSet().stream()
                                    .filter(Bm3Akb -> !Bm3Akb.getTrTableAkbPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm3AkbSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAkbSet() {
        return (Set<T>) trTableBm3AkbSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkeSet(Set<T> subTableSet) {
        Set<TrTableBm3Ake> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm3Ake) {
            subTableSetConverted = (Set<TrTableBm3Ake>) subTableSet;
        }

        if (getTrTableBm3AkeSet() == null) {
            setTrTableBm3AkeSet(subTableSetConverted);
            return;
        }

        Set<TrTableAkePK> Bm3AkePKPrevious = getTrTableBm3AkeSet().stream().map(TrTableBm3Ake::getTrTableAkePK)
                .collect(Collectors.toSet());
        Set<TrTableAkePK> Bm3AkePKAfter = subTableSetConverted.stream().map(TrTableBm3Ake::getTrTableAkePK)
                .collect(Collectors.toSet());
        Set<TrTableAkePK> Bm3AkePKIntersection;

        Bm3AkePKIntersection = new HashSet<>(Bm3AkePKPrevious);
        Bm3AkePKIntersection.retainAll(Bm3AkePKAfter);

        for (TrTableAkePK pk : Bm3AkePKPrevious) {
            if (!Bm3AkePKAfter.contains(pk)) {
                setTrTableBm3AkeSet(getTrTableBm3AkeSet().stream()
                        .filter(Bm3Ake -> !Bm3Ake.getTrTableAkePK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAkePK pk : Bm3AkePKAfter) {
            if (!Bm3AkePKPrevious.contains(pk)) {
                getTrTableBm3AkeSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Ake -> Bm3Ake.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm3AkePKIntersection.size() > 0) {
            for (TrTableAkePK pk : Bm3AkePKIntersection) {
                if (getTrTableBm3AkeSet().stream().filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm3AkeSet().stream().filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk)).findFirst().get())) {
                            setTrTableBm3AkeSet(getTrTableBm3AkeSet().stream()
                                    .filter(Bm3Ake -> !Bm3Ake.getTrTableAkePK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm3AkeSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAkeSet() {
        return (Set<T>) trTableBm3AkeSet;
    }

    @Override
    public <T extends SubTable> void setTrTableEikSet(Set<T> subTableSet) {
        Set<TrTableBm3Eik> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm3Eik) {
            subTableSetConverted = (Set<TrTableBm3Eik>) subTableSet;
        }

        if (getTrTableBm3EikSet() == null) {
            setTrTableBm3EikSet(subTableSetConverted);
            return;
        }

        Set<TrTableEikPK> Bm3EikPKPrevious = getTrTableBm3EikSet().stream().map(TrTableBm3Eik::getTrTableEikPK)
                .collect(Collectors.toSet());
        Set<TrTableEikPK> Bm3EikPKAfter = subTableSetConverted.stream().map(TrTableBm3Eik::getTrTableEikPK)
                .collect(Collectors.toSet());
        Set<TrTableEikPK> Bm3EikPKIntersection;

        Bm3EikPKIntersection = new HashSet<>(Bm3EikPKPrevious);
        Bm3EikPKIntersection.retainAll(Bm3EikPKAfter);

        for (TrTableEikPK pk : Bm3EikPKPrevious) {
            if (!Bm3EikPKAfter.contains(pk)) {
                setTrTableBm3EikSet(getTrTableBm3EikSet().stream()
                        .filter(Bm3Eik -> !Bm3Eik.getTrTableEikPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableEikPK pk : Bm3EikPKAfter) {
            if (!Bm3EikPKPrevious.contains(pk)) {
                getTrTableBm3EikSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Eik -> Bm3Eik.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm3EikPKIntersection.size() > 0) {
            for (TrTableEikPK pk : Bm3EikPKIntersection) {
                if (getTrTableBm3EikSet().stream().filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm3EikSet().stream().filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk)).findFirst().get())) {
                            setTrTableBm3EikSet(getTrTableBm3EikSet().stream()
                                    .filter(Bm3Eik -> !Bm3Eik.getTrTableEikPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm3EikSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableEikSet() {
        return (Set<T>) trTableBm3EikSet;
    }

    @Override
    public <T extends SubTable> void setTrTablePivSet(Set<T> subTableSet) {
        Set<TrTableBm3Piv> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm3Piv) {
            subTableSetConverted = (Set<TrTableBm3Piv>) subTableSet;
        }

        if (getTrTableBm3PivSet() == null) {
            setTrTableBm3PivSet(subTableSetConverted);
            return;
        }

        Set<TrTablePivPK> Bm3PivPKPrevious = getTrTableBm3PivSet().stream().map(TrTableBm3Piv::getTrTablePivPK)
                .collect(Collectors.toSet());
        Set<TrTablePivPK> Bm3PivPKAfter = subTableSetConverted.stream().map(TrTableBm3Piv::getTrTablePivPK)
                .collect(Collectors.toSet());
        Set<TrTablePivPK> Bm3PivPKIntersection;

        Bm3PivPKIntersection = new HashSet<>(Bm3PivPKPrevious);
        Bm3PivPKIntersection.retainAll(Bm3PivPKAfter);

        for (TrTablePivPK pk : Bm3PivPKPrevious) {
            if (!Bm3PivPKAfter.contains(pk)) {
                setTrTableBm3PivSet(getTrTableBm3PivSet().stream()
                        .filter(Bm3Piv -> !Bm3Piv.getTrTablePivPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTablePivPK pk : Bm3PivPKAfter) {
            if (!Bm3PivPKPrevious.contains(pk)) {
                getTrTableBm3PivSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Piv -> Bm3Piv.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm3PivPKIntersection.size() > 0) {
            for (TrTablePivPK pk : Bm3PivPKIntersection) {
                if (getTrTableBm3PivSet().stream().filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm3PivSet().stream().filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk)).findFirst().get())) {
                            setTrTableBm3PivSet(getTrTableBm3PivSet().stream()
                                    .filter(Bm3Piv -> !Bm3Piv.getTrTablePivPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm3PivSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTablePivSet() {
        return (Set<T>) trTableBm3PivSet;
    }
}
