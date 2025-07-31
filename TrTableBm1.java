package com.fisglobal.taxreporting.entity.model.taxreporting.bm1;

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

import lombok.*;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.entity.model.taxreporting.IntermediateTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


/**
 * The persistent class for the TR_TABLE_BM1 database table.
 */
@Entity
@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        exclude = { "trTableBsbFK", "trTableBm1AamSet", "trTableBm1AkbSet", "trTableBm1AkeSet", "trTableBm1EikSet",
                "trTableBm1PivSet" })
@ToString(exclude = { "trTableBsbFK" })
@Table(name = "TR_TABLE_BM1")
public class TrTableBm1 implements Serializable, IntermediateTable, Comparable<TrTableBm1> {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrTableBm1PK trTableBm1PK;

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

    @JsonProperty("trTableBm1AamSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm1FK", orphanRemoval = true)
    @OrderBy("trTableAamPK.keyAbrechNr ASC")
    private Set<TrTableBm1Aam> trTableBm1AamSet = new LinkedHashSet<TrTableBm1Aam>();

    @JsonProperty("trTableBm1AkbSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm1FK", orphanRemoval = true)
    @OrderBy("trTableAkbPK.keyAbrechNr ASC")
    private Set<TrTableBm1Akb> trTableBm1AkbSet = new LinkedHashSet<TrTableBm1Akb>();

    @JsonProperty("trTableBm1AkeSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm1FK", orphanRemoval = true)
    @OrderBy("trTableAkePK.keyAbrechNr ASC")
    private Set<TrTableBm1Ake> trTableBm1AkeSet = new LinkedHashSet<TrTableBm1Ake>();

    @JsonProperty("trTableBm1EikSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm1FK", orphanRemoval = true)
    @OrderBy("trTableEikPK.keyAbrechNr ASC")
    private Set<TrTableBm1Eik> trTableBm1EikSet = new LinkedHashSet<TrTableBm1Eik>();

    @JsonProperty("trTableBm1PivSet")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBm1FK", orphanRemoval = true)
    @OrderBy("trTablePivPK.keyAbrechNr ASC")
    private Set<TrTableBm1Piv> trTableBm1PivSet = new LinkedHashSet<TrTableBm1Piv>();

    @Column(name = "AAM_VERAEUSSERT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String aamVeraeussert;

    @Column(name = "AKE_VERAEUSSERT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String akeVeraeussert;

    @Column(name = "AKTIEN_ERTR_GEW")
    private BigDecimal aktienErtrGew;

    @Column(name = "ANGERECH_AUSL_STEU")
    private BigDecimal angerechAuslSteu;

    @Column(name = "ANRECHB_AUSL_STEU")
    private BigDecimal anrechbAuslSteu;

    @Column(name = "ANZAHL_AAM")
    private BigDecimal anzahlAam;

    @Column(name = "ANZAHL_AKE")
    private BigDecimal anzahlAke;

    @Column(name = "ANZAHL_EIK")
    private BigDecimal anzahlEik;

    @Column(name = "ANZAHL_PIV")
    private BigDecimal anzahlPiv;

    @Column(name = "AULS_KI_GUT", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String aulsKiGut;

    @Column(name = "AUSL_INV_FONDS")
    private BigDecimal auslInvFonds;

    @Column(name = "AUSL_KI_ANTR", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiAntr;

    @Column(name = "AUSL_KI_NAM_STADT", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiNamStadt;

    @Column(name = "AUSL_KI_VERW", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String auslKiVerw;

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

    @Column(name = "ENTSCHAEDIGUNG")
    private BigDecimal entschaedigung;

    @Column(name = "ERSATZ_BMG")
    private BigDecimal ersatzBmg;

    @Column(name = "FEHLER_KENNZ", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String fehlerKennz;

    @Column(name = "FORMULAR", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String formular;

    @Column(name = "GEW_AKT_VOR_SON")
    private BigDecimal gewAktVorSon;

    @Column(name = "GEW_ALTANTEILE")
    private BigDecimal gewAltanteile;

    @Column(name = "INL_KI_VERW", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String inlKiVerw;

    @Column(name = "KAPITALERTRAG")
    private BigDecimal kapitalertrag;

    @Column(name = "KAPST_ABZUG")
    private BigDecimal kapstAbzug;

    @Column(name = "KEIN_ORIGINAL_M1", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String keinOriginalM1;

    @Column(name = "KIST_ABZUG")
    private BigDecimal kistAbzug;

    @Column(name = "KIST_KONF")
    private BigDecimal kistKonf;

    @Column(name = "LIEFERTIEFE", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String liefertiefe;

    @Column(name = "ORDNUNGSNUMMER", length = 36, columnDefinition = "CHAR(36)")
    @Size(max = 36)
    private String ordnungsnummer;

    @Column(name = "P2_KIST_ABZUG")
    private BigDecimal p2KistAbzug;

    @Column(name = "P2_KIST_KONF")
    private BigDecimal p2KistKonf;

    @Column(name = "PIV_VERWAHRT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String pivVerwahrt;

    @Column(name = "PRIVAT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String privat;

    @Column(name = "SOLZ_ABZUG")
    private BigDecimal solzAbzug;

    @Column(name = "STB_JAHR")
    private BigDecimal stbJahr;

    @Column(name = "STEUERBESCH_DV", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String steuerbeschDv;

    @Column(name = "STEUERL_EINLAGEKTO", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String steuerlEinlagekto;

    @Column(name = "STILLH_VOR_SON")
    private BigDecimal stillhVorSon;

    @Column(name = "STILLHALTER_TERMIN")
    private BigDecimal stillhalterTermin;

    @Column(name = "STORNIERUNG", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String stornierung;

    @Column(name = "VERBR_SPARERPAUSCH")
    private BigDecimal verbrSparerpausch;

    @Column(name = "VERL_TERMINGESCH")
    private BigDecimal verlTermingesch;

    @Column(name = "VERL_WERTLO_UNEINB")
    private BigDecimal verlWertloUneinb;

    @Column(name = "VERLUST_AKT")
    private BigDecimal verlustAkt;

    @Column(name = "VERLUST_OHNE_AKT")
    private BigDecimal verlustOhneAkt;

    @Column(name = "VERLUSTBESCH", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String verlustbesch;

    @Column(name = "ZAHLUNGSTAG")
    private BigDecimal zahlungstag;

    @Column(name = "ZEITRAUM_BIS")
    private BigDecimal zeitraumBis;

    @Column(name = "ZEITRAUM_VON")
    private BigDecimal zeitraumVon;

    @Override
    public int compareTo(TrTableBm1 o2) {
        long t1_time = this.trTableBm1PK.getKeySysDatum() * 1_000_000 + this.trTableBm1PK.getKeyUhrzeit();
        long t2_time = o2.trTableBm1PK.getKeySysDatum() * 1_000_000 + o2.trTableBm1PK.getKeyUhrzeit();
        if (t1_time != t2_time) {
            return Long.compare(t1_time, t2_time);
        }
        String t1 = this.trTableBm1PK.getKeySatzart();
        String t2 = o2.trTableBm1PK.getKeySatzart();
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
        return new UniqueKey(trTableBm1PK.getMandSl(), trTableBm1PK.getKeyIdNr(), trTableBm1PK.getKeyMeldejahr(),
                trTableBm1PK.getKeyMuster(), trTableBm1PK.getKeyLaufnummer(), trTableBm1PK.getKeySysDatum(),
                trTableBm1PK.getKeyUhrzeit(), trTableBm1PK.getKeySatzart());

    }

    @Override
    public <T extends SubTable> void setTrTableAamSet(Set<T> subTableSet) {
        Set<TrTableBm1Aam> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm1Aam) {
            subTableSetConverted = (Set<TrTableBm1Aam>) subTableSet;
        }

        if (getTrTableBm1AamSet() == null) {
            setTrTableBm1AamSet(subTableSetConverted);
            return;
        }

        Set<TrTableAamPK> Bm1AamPKPrevious = getTrTableBm1AamSet().stream().map(TrTableBm1Aam::getTrTableAamPK)
                .collect(Collectors.toSet());
        Set<TrTableAamPK> Bm1AamPKAfter = subTableSetConverted.stream().map(TrTableBm1Aam::getTrTableAamPK)
                .collect(Collectors.toSet());
        Set<TrTableAamPK> Bm1AamPKIntersection;

        Bm1AamPKIntersection = new HashSet<>(Bm1AamPKPrevious);
        Bm1AamPKIntersection.retainAll(Bm1AamPKAfter);

        for (TrTableAamPK pk : Bm1AamPKPrevious) {
            if (!Bm1AamPKAfter.contains(pk)) {
                setTrTableBm1AamSet(getTrTableBm1AamSet().stream()
                        .filter(bm1Aam -> !bm1Aam.getTrTableAamPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAamPK pk : Bm1AamPKAfter) {
            if (!Bm1AamPKPrevious.contains(pk)) {
                getTrTableBm1AamSet().addAll(
                        subTableSetConverted.stream().filter(bm1Aam -> bm1Aam.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm1AamPKIntersection.size() > 0) {
            for (TrTableAamPK pk : Bm1AamPKIntersection) {
                if (getTrTableBm1AamSet().stream().filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm1AamSet().stream().filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk)).findFirst().get())) {
                            setTrTableBm1AamSet(getTrTableBm1AamSet().stream()
                                    .filter(bm1Aam -> !bm1Aam.getTrTableAamPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm1AamSet().add(subTableSetConverted.stream()
                                    .filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAamSet() {
        return (Set<T>) trTableBm1AamSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkbSet(Set<T> subTableSet) {
        Set<TrTableBm1Akb> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm1Akb) {
            subTableSetConverted = (Set<TrTableBm1Akb>) subTableSet;
        }

        if (getTrTableBm1AkbSet() == null) {
            setTrTableBm1AkbSet(subTableSetConverted);
            return;
        }

        Set<TrTableAkbPK> Bm1AkbPKPrevious = getTrTableBm1AkbSet().stream().map(TrTableBm1Akb::getTrTableAkbPK)
                .collect(Collectors.toSet());
        Set<TrTableAkbPK> Bm1AkbPKAfter = subTableSetConverted.stream().map(TrTableBm1Akb::getTrTableAkbPK)
                .collect(Collectors.toSet());
        Set<TrTableAkbPK> Bm1AkbPKIntersection;

        Bm1AkbPKIntersection = new HashSet<>(Bm1AkbPKPrevious);
        Bm1AkbPKIntersection.retainAll(Bm1AkbPKAfter);

        for (TrTableAkbPK pk : Bm1AkbPKPrevious) {
            if (!Bm1AkbPKAfter.contains(pk)) {
                setTrTableBm1AkbSet(getTrTableBm1AkbSet().stream()
                        .filter(bm1Akb -> !bm1Akb.getTrTableAkbPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAkbPK pk : Bm1AkbPKAfter) {
            if (!Bm1AkbPKPrevious.contains(pk)) {
                getTrTableBm1AkbSet().addAll(
                        subTableSetConverted.stream().filter(bm1Akb -> bm1Akb.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm1AkbPKIntersection.size() > 0) {
            for (TrTableAkbPK pk : Bm1AkbPKIntersection) {
                if (getTrTableBm1AkbSet().stream().filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm1AkbSet().stream().filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk)).findFirst().get())) {
                            setTrTableBm1AkbSet(getTrTableBm1AkbSet().stream()
                                    .filter(bm1Akb -> !bm1Akb.getTrTableAkbPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm1AkbSet().add(subTableSetConverted.stream()
                                    .filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAkbSet() {
        return (Set<T>) trTableBm1AkbSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkeSet(Set<T> subTableSet) {
        Set<TrTableBm1Ake> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm1Ake) {
            subTableSetConverted = (Set<TrTableBm1Ake>) subTableSet;
        }

        if (getTrTableBm1AkeSet() == null) {
            setTrTableBm1AkeSet(subTableSetConverted);
            return;
        }

        Set<TrTableAkePK> Bm1AkePKPrevious = getTrTableBm1AkeSet().stream().map(TrTableBm1Ake::getTrTableAkePK)
                .collect(Collectors.toSet());
        Set<TrTableAkePK> Bm1AkePKAfter = subTableSetConverted.stream().map(TrTableBm1Ake::getTrTableAkePK)
                .collect(Collectors.toSet());
        Set<TrTableAkePK> Bm1AkePKIntersection;

        Bm1AkePKIntersection = new HashSet<>(Bm1AkePKPrevious);
        Bm1AkePKIntersection.retainAll(Bm1AkePKAfter);

        for (TrTableAkePK pk : Bm1AkePKPrevious) {
            if (!Bm1AkePKAfter.contains(pk)) {
                setTrTableBm1AkeSet(getTrTableBm1AkeSet().stream()
                        .filter(bm1Ake -> !bm1Ake.getTrTableAkePK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableAkePK pk : Bm1AkePKAfter) {
            if (!Bm1AkePKPrevious.contains(pk)) {
                getTrTableBm1AkeSet().addAll(
                        subTableSetConverted.stream().filter(bm1Ake -> bm1Ake.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm1AkePKIntersection.size() > 0) {
            for (TrTableAkePK pk : Bm1AkePKIntersection) {
                if (getTrTableBm1AkeSet().stream().filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm1AkeSet().stream().filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk)).findFirst().get())) {
                            setTrTableBm1AkeSet(getTrTableBm1AkeSet().stream()
                                    .filter(bm1Ake -> !bm1Ake.getTrTableAkePK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm1AkeSet().add(subTableSetConverted.stream()
                                    .filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableAkeSet() {
        return (Set<T>) trTableBm1AkeSet;
    }

    @Override
    public <T extends SubTable> void setTrTableEikSet(Set<T> subTableSet) {
        Set<TrTableBm1Eik> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm1Eik) {
            subTableSetConverted = (Set<TrTableBm1Eik>) subTableSet;
        }

        if (getTrTableBm1EikSet() == null) {
            setTrTableBm1EikSet(subTableSetConverted);
            return;
        }

        Set<TrTableEikPK> Bm1EikPKPrevious = getTrTableBm1EikSet().stream().map(TrTableBm1Eik::getTrTableEikPK)
                .collect(Collectors.toSet());
        Set<TrTableEikPK> Bm1EikPKAfter = subTableSetConverted.stream().map(TrTableBm1Eik::getTrTableEikPK)
                .collect(Collectors.toSet());
        Set<TrTableEikPK> Bm1EikPKIntersection;

        Bm1EikPKIntersection = new HashSet<>(Bm1EikPKPrevious);
        Bm1EikPKIntersection.retainAll(Bm1EikPKAfter);

        for (TrTableEikPK pk : Bm1EikPKPrevious) {
            if (!Bm1EikPKAfter.contains(pk)) {
                setTrTableBm1EikSet(getTrTableBm1EikSet().stream()
                        .filter(bm1Eik -> !bm1Eik.getTrTableEikPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTableEikPK pk : Bm1EikPKAfter) {
            if (!Bm1EikPKPrevious.contains(pk)) {
                getTrTableBm1EikSet().addAll(
                        subTableSetConverted.stream().filter(bm1Eik -> bm1Eik.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm1EikPKIntersection.size() > 0) {
            for (TrTableEikPK pk : Bm1EikPKIntersection) {
                if (getTrTableBm1EikSet().stream().filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm1EikSet().stream().filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk)).findFirst().get())) {
                            setTrTableBm1EikSet(getTrTableBm1EikSet().stream()
                                    .filter(bm1Eik -> !bm1Eik.getTrTableEikPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm1EikSet().add(subTableSetConverted.stream()
                                    .filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTableEikSet() {
        return (Set<T>) trTableBm1EikSet;
    }

    @Override
    public <T extends SubTable> void setTrTablePivSet(Set<T> subTableSet) {
        Set<TrTableBm1Piv> subTableSetConverted = new LinkedHashSet<>();

        if (subTableSet instanceof TrTableBm1Piv) {
            subTableSetConverted = (Set<TrTableBm1Piv>) subTableSet;
        }

        if (getTrTableBm1PivSet() == null) {
            setTrTableBm1PivSet(subTableSetConverted);
            return;
        }

        Set<TrTablePivPK> Bm1PivPKPrevious = getTrTableBm1PivSet().stream().map(TrTableBm1Piv::getTrTablePivPK)
                .collect(Collectors.toSet());
        Set<TrTablePivPK> Bm1PivPKAfter = subTableSetConverted.stream().map(TrTableBm1Piv::getTrTablePivPK)
                .collect(Collectors.toSet());
        Set<TrTablePivPK> Bm1PivPKIntersection;

        Bm1PivPKIntersection = new HashSet<>(Bm1PivPKPrevious);
        Bm1PivPKIntersection.retainAll(Bm1PivPKAfter);

        for (TrTablePivPK pk : Bm1PivPKPrevious) {
            if (!Bm1PivPKAfter.contains(pk)) {
                setTrTableBm1PivSet(getTrTableBm1PivSet().stream()
                        .filter(bm1Piv -> !bm1Piv.getTrTablePivPK().equals(pk)).collect(Collectors.toSet()));
            }
        }

        for (TrTablePivPK pk : Bm1PivPKAfter) {
            if (!Bm1PivPKPrevious.contains(pk)) {
                getTrTableBm1PivSet().addAll(
                        subTableSetConverted.stream().filter(bm1Piv -> bm1Piv.equals(pk)).collect(Collectors.toSet()));
            }
        }

        if (Bm1PivPKIntersection.size() > 0) {
            for (TrTablePivPK pk : Bm1PivPKIntersection) {
                if (getTrTableBm1PivSet().stream().filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk)).findFirst()
                        .isPresent()) {
                    if (subTableSetConverted.stream().filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk)).findFirst()
                            .isPresent()) {
                        if (!getTrTableBm1PivSet().stream().filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk))
                                .findFirst().get().equals(subTableSetConverted.stream()
                                        .filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk)).findFirst().get())) {
                            setTrTableBm1PivSet(getTrTableBm1PivSet().stream()
                                    .filter(bm1Piv -> !bm1Piv.getTrTablePivPK().equals(pk))
                                    .collect(Collectors.toSet()));
                            getTrTableBm1PivSet().add(subTableSetConverted.stream()
                                    .filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> Set<T> getTrTablePivSet() {
        return (Set<T>) trTableBm1PivSet;
    }
}
