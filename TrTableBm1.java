package com.fisglobal.taxreporting.controller.dto.bm1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.controller.dto.IntermediateTable;
import com.fisglobal.taxreporting.controller.dto.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.controller.dto.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.controller.dto.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb;
import com.fisglobal.taxreporting.controller.dto.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.controller.dto.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


/**
 * The DTO class for the TR_TABLE_BM1 database table.
 */
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        exclude = { "trTableBsbFK", "trTableBm1AamSet", "trTableBm1AkbSet", "trTableBm1AkeSet", "trTableBm1EikSet",
                "trTableBm1PivSet" })
@ToString(exclude = { "trTableBsbFK" })
public class TrTableBm1 implements Serializable, IntermediateTable, Comparable<TrTableBm1> {
    private static final long serialVersionUID = 1L;

    @Valid
    private TrTableBm1PK trTableBm1PK;

    @JsonBackReference
    private TrTableBsb trTableBsbFK;

    @Valid
    @JsonProperty("trTableBm1AamSet")
    @JsonManagedReference
    private List<TrTableBm1Aam> trTableBm1AamSet = new LinkedList<TrTableBm1Aam>();

    @Valid
    @JsonProperty("trTableBm1AkbSet")
    @JsonManagedReference
    private List<TrTableBm1Akb> trTableBm1AkbSet = new LinkedList<TrTableBm1Akb>();

    @Valid
    @JsonProperty("trTableBm1AkeSet")
    @JsonManagedReference
    private List<TrTableBm1Ake> trTableBm1AkeSet = new LinkedList<TrTableBm1Ake>();

    @Valid
    @JsonProperty("trTableBm1EikSet")
    @JsonManagedReference
    private List<TrTableBm1Eik> trTableBm1EikSet = new LinkedList<TrTableBm1Eik>();

    @Valid
    @JsonProperty("trTableBm1PivSet")
    @JsonManagedReference
    private List<TrTableBm1Piv> trTableBm1PivSet = new LinkedList<TrTableBm1Piv>();

    @Size(max = 1)
    private String aamVeraeussert;

    @Size(max = 1)
    private String akeVeraeussert;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String aktienErtrGew;

    @Pattern(
            regexp = "^$|[-]?\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String angerechAuslSteu;

    @Pattern(
            regexp = "^$|[-]?\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String anrechbAuslSteu;

    @Digits(integer = 9, fraction = 0, message = "Length must be less than or equal to 9")
    private BigDecimal anzahlAam;

    @Digits(integer = 9, fraction = 0, message = "Length must be less than or equal to 9")
    private BigDecimal anzahlAke;

    @Digits(integer = 9, fraction = 0, message = "Length must be less than or equal to 9")
    private BigDecimal anzahlEik;

    @Digits(integer = 9, fraction = 0, message = "Length must be less than or equal to 9")
    private BigDecimal anzahlPiv;

    @Size(max = 400)
    private String aulsKiGut;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String auslInvFonds;

    @Size(max = 400)
    private String auslKiAntr;

    @Size(max = 400)
    private String auslKiNamStadt;

    @Size(max = 400)
    private String auslKiVerw;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String depReAnzBesch;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String depReGesamtzahl;

    @Size(max = 12)
    private String depReIsin;

    @Size(max = 1)
    private String eikErstattet;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String entschaedigung;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ersatzBmg;

    @Size(max = 1)
    private String fehlerKennz;

    @Size(max = 1)
    private String formular;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String gewAktVorSon;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String gewAltanteile;

    @Size(max = 400)
    private String inlKiVerw;

    @NotNull(
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    @Pattern(
            regexp = "^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kapitalertrag;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kapstAbzug;

    @Size(max = 1)
    private String keinOriginalM1;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kistAbzug;

    @Pattern(regexp = "^$|^\\d{1,6}?$", message = "Length must be less than or equal to 6")
    private String kistKonf;

    @Size(max = 1)
    private String liefertiefe;

    @Size(max = 36)
    private String ordnungsnummer;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String p2KistAbzug;

    @Pattern(regexp = "^$|^\\d{1,6}?$", message = "Length must be less than or equal to 6")
    private String p2KistKonf;

    @Size(max = 1)
    private String pivVerwahrt;

    @Size(max = 1)
    private String privat;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String solzAbzug;

    @Pattern(regexp = "^$|^\\d{1,4}?$", message = "Length must be less than or equal to 4")
    private String stbJahr;

    @Size(max = 50)
    private String steuerbeschDv;

    @Size(max = 1)
    private String steuerlEinlagekto;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String stillhVorSon;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String stillhalterTermin;

    @Size(max = 1)
    private String stornierung;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String verbrSparerpausch;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String verlTermingesch;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String verlWertloUneinb;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String verlustAkt;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String verlustOhneAkt;

    @Size(max = 1)
    private String verlustbesch;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zahlungstag;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zeitraumBis;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zeitraumVon;

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
    public <T extends SubTable> void setTrTableAamSet(List<T> subTableSet) {
        List<TrTableBm1Aam> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm1Aam) {
            subTableSetConverted = (List<TrTableBm1Aam>) subTableSet;
        }

        if (getTrTableBm1AamSet() == null) {
            setTrTableBm1AamSet(subTableSetConverted);
            return;
        }

        List<TrTableAamPK> Bm1AamPKPrevious = getTrTableBm1AamSet().stream().map(TrTableBm1Aam::getTrTableAamPK)
                .collect(Collectors.toList());
        List<TrTableAamPK> Bm1AamPKAfter = subTableSetConverted.stream().map(TrTableBm1Aam::getTrTableAamPK)
                .collect(Collectors.toList());
        List<TrTableAamPK> Bm1AamPKIntersection;

        Bm1AamPKIntersection = new LinkedList<>(Bm1AamPKPrevious);
        Bm1AamPKIntersection.retainAll(Bm1AamPKAfter);

        for (TrTableAamPK pk : Bm1AamPKPrevious) {
            if (!Bm1AamPKAfter.contains(pk)) {
                setTrTableBm1AamSet(getTrTableBm1AamSet().stream()
                        .filter(bm1Aam -> !bm1Aam.getTrTableAamPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAamPK pk : Bm1AamPKAfter) {
            if (!Bm1AamPKPrevious.contains(pk)) {
                getTrTableBm1AamSet().addAll(
                        subTableSetConverted.stream().filter(bm1Aam -> bm1Aam.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm1AamSet().add(subTableSetConverted.stream()
                                    .filter(bm1Aam -> bm1Aam.getTrTableAamPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAamSet() {
        return (List<T>) trTableBm1AamSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkbSet(List<T> subTableSet) {
        List<TrTableBm1Akb> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm1Akb) {
            subTableSetConverted = (List<TrTableBm1Akb>) subTableSet;
        }

        if (getTrTableBm1AkbSet() == null) {
            setTrTableBm1AkbSet(subTableSetConverted);
            return;
        }

        List<TrTableAkbPK> Bm1AkbPKPrevious = getTrTableBm1AkbSet().stream().map(TrTableBm1Akb::getTrTableAkbPK)
                .collect(Collectors.toList());
        List<TrTableAkbPK> Bm1AkbPKAfter = subTableSetConverted.stream().map(TrTableBm1Akb::getTrTableAkbPK)
                .collect(Collectors.toList());
        List<TrTableAkbPK> Bm1AkbPKIntersection;

        Bm1AkbPKIntersection = new LinkedList<>(Bm1AkbPKPrevious);
        Bm1AkbPKIntersection.retainAll(Bm1AkbPKAfter);

        for (TrTableAkbPK pk : Bm1AkbPKPrevious) {
            if (!Bm1AkbPKAfter.contains(pk)) {
                setTrTableBm1AkbSet(getTrTableBm1AkbSet().stream()
                        .filter(bm1Akb -> !bm1Akb.getTrTableAkbPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAkbPK pk : Bm1AkbPKAfter) {
            if (!Bm1AkbPKPrevious.contains(pk)) {
                getTrTableBm1AkbSet().addAll(
                        subTableSetConverted.stream().filter(bm1Akb -> bm1Akb.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm1AkbSet().add(subTableSetConverted.stream()
                                    .filter(bm1Akb -> bm1Akb.getTrTableAkbPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAkbSet() {
        return (List<T>) trTableBm1AkbSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkeSet(List<T> subTableSet) {
        List<TrTableBm1Ake> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm1Ake) {
            subTableSetConverted = (List<TrTableBm1Ake>) subTableSet;
        }

        if (getTrTableBm1AkeSet() == null) {
            setTrTableBm1AkeSet(subTableSetConverted);
            return;
        }

        List<TrTableAkePK> Bm1AkePKPrevious = getTrTableBm1AkeSet().stream().map(TrTableBm1Ake::getTrTableAkePK)
                .collect(Collectors.toList());
        List<TrTableAkePK> Bm1AkePKAfter = subTableSetConverted.stream().map(TrTableBm1Ake::getTrTableAkePK)
                .collect(Collectors.toList());
        List<TrTableAkePK> Bm1AkePKIntersection;

        Bm1AkePKIntersection = new LinkedList<>(Bm1AkePKPrevious);
        Bm1AkePKIntersection.retainAll(Bm1AkePKAfter);

        for (TrTableAkePK pk : Bm1AkePKPrevious) {
            if (!Bm1AkePKAfter.contains(pk)) {
                setTrTableBm1AkeSet(getTrTableBm1AkeSet().stream()
                        .filter(bm1Ake -> !bm1Ake.getTrTableAkePK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAkePK pk : Bm1AkePKAfter) {
            if (!Bm1AkePKPrevious.contains(pk)) {
                getTrTableBm1AkeSet().addAll(
                        subTableSetConverted.stream().filter(bm1Ake -> bm1Ake.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm1AkeSet().add(subTableSetConverted.stream()
                                    .filter(bm1Ake -> bm1Ake.getTrTableAkePK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAkeSet() {
        return (List<T>) trTableBm1AkeSet;
    }

    @Override
    public <T extends SubTable> void setTrTableEikSet(List<T> subTableSet) {
        List<TrTableBm1Eik> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm1Eik) {
            subTableSetConverted = (List<TrTableBm1Eik>) subTableSet;
        }

        if (getTrTableBm1EikSet() == null) {
            setTrTableBm1EikSet(subTableSetConverted);
            return;
        }

        List<TrTableEikPK> Bm1EikPKPrevious = getTrTableBm1EikSet().stream().map(TrTableBm1Eik::getTrTableEikPK)
                .collect(Collectors.toList());
        List<TrTableEikPK> Bm1EikPKAfter = subTableSetConverted.stream().map(TrTableBm1Eik::getTrTableEikPK)
                .collect(Collectors.toList());
        List<TrTableEikPK> Bm1EikPKIntersection;

        Bm1EikPKIntersection = new LinkedList<>(Bm1EikPKPrevious);
        Bm1EikPKIntersection.retainAll(Bm1EikPKAfter);

        for (TrTableEikPK pk : Bm1EikPKPrevious) {
            if (!Bm1EikPKAfter.contains(pk)) {
                setTrTableBm1EikSet(getTrTableBm1EikSet().stream()
                        .filter(bm1Eik -> !bm1Eik.getTrTableEikPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableEikPK pk : Bm1EikPKAfter) {
            if (!Bm1EikPKPrevious.contains(pk)) {
                getTrTableBm1EikSet().addAll(
                        subTableSetConverted.stream().filter(bm1Eik -> bm1Eik.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm1EikSet().add(subTableSetConverted.stream()
                                    .filter(bm1Eik -> bm1Eik.getTrTableEikPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableEikSet() {
        return (List<T>) trTableBm1EikSet;
    }

    @Override
    public <T extends SubTable> void setTrTablePivSet(List<T> subTableSet) {
        List<TrTableBm1Piv> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm1Piv) {
            subTableSetConverted = (List<TrTableBm1Piv>) subTableSet;
        }

        if (getTrTableBm1PivSet() == null) {
            setTrTableBm1PivSet(subTableSetConverted);
            return;
        }

        List<TrTablePivPK> Bm1PivPKPrevious = getTrTableBm1PivSet().stream().map(TrTableBm1Piv::getTrTablePivPK)
                .collect(Collectors.toList());
        List<TrTablePivPK> Bm1PivPKAfter = subTableSetConverted.stream().map(TrTableBm1Piv::getTrTablePivPK)
                .collect(Collectors.toList());
        List<TrTablePivPK> Bm1PivPKIntersection;

        Bm1PivPKIntersection = new LinkedList<>(Bm1PivPKPrevious);
        Bm1PivPKIntersection.retainAll(Bm1PivPKAfter);

        for (TrTablePivPK pk : Bm1PivPKPrevious) {
            if (!Bm1PivPKAfter.contains(pk)) {
                setTrTableBm1PivSet(getTrTableBm1PivSet().stream()
                        .filter(bm1Piv -> !bm1Piv.getTrTablePivPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTablePivPK pk : Bm1PivPKAfter) {
            if (!Bm1PivPKPrevious.contains(pk)) {
                getTrTableBm1PivSet().addAll(
                        subTableSetConverted.stream().filter(bm1Piv -> bm1Piv.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm1PivSet().add(subTableSetConverted.stream()
                                    .filter(bm1Piv -> bm1Piv.getTrTablePivPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTablePivSet() {
        return (List<T>) trTableBm1PivSet;
    }
}
