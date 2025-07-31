package com.fisglobal.taxreporting.controller.dto.bm3;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.controller.dto.IntermediateTable;
import com.fisglobal.taxreporting.controller.dto.aam.TrTableBm3Aam;
import com.fisglobal.taxreporting.controller.dto.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.controller.dto.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.controller.dto.bsb.TrTableBsb;
import com.fisglobal.taxreporting.controller.dto.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.controller.dto.piv.TrTableBm3Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.SubTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.UniqueKey;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3PK;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


/**
 * The DTO class for the TR_TABLE_BM3 database table.
 */
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        exclude = { "trTableBsbFK", "trTableBm3AamSet", "trTableBm3AkbSet", "trTableBm3AkeSet", "trTableBm3EikSet",
                "trTableBm3PivSet" })
public class TrTableBm3 implements Serializable, IntermediateTable, Comparable<TrTableBm3> {
    private static final long serialVersionUID = 1L;

    @Valid
    private TrTableBm3PK trTableBm3PK;

    @JsonBackReference
    private TrTableBsb trTableBsbFK;

    @Valid
    @JsonProperty("trTableBm3AamSet")
    @JsonManagedReference
    private List<TrTableBm3Aam> trTableBm3AamSet = new LinkedList<TrTableBm3Aam>();

    @Valid
    @JsonProperty("trTableBm3AkbSet")
    @JsonManagedReference
    private List<TrTableBm3Akb> trTableBm3AkbSet = new LinkedList<TrTableBm3Akb>();

    @Valid
    @JsonProperty("trTableBm3AkeSet")
    @JsonManagedReference
    private List<TrTableBm3Ake> trTableBm3AkeSet = new LinkedList<TrTableBm3Ake>();

    @Valid
    @JsonProperty("trTableBm3EikSet")
    @JsonManagedReference
    private List<TrTableBm3Eik> trTableBm3EikSet = new LinkedList<TrTableBm3Eik>();

    @Valid
    @JsonProperty("trTableBm3PivSet")
    @JsonManagedReference
    private List<TrTableBm3Piv> trTableBm3PivSet = new LinkedList<TrTableBm3Piv>();

    @Size(max = 1)
    private String abstandSteuerabz;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String abzDreiFueP433;

    @Size(max = 1)
    private String akbVeraeussert;

    @Size(max = 1)
    private String akeVeraeussert;

    @Digits(integer = 9, fraction = 0, message = "Length must be less than or equal to 9")
    private BigDecimal anzahlAkb;

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
    private String auslInvKStabzug;

    @Size(max = 400)
    private String auslKiAntr;

    @Size(max = 400)
    private String auslKiNamStadt;

    @Size(max = 400)
    private String auslKiVerw;

    @Size(max = 1)
    private String auslSpezInv;

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String depReAnzBesch;

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String depReGesamtzahl;

    @Size(max = 12)
    private String depReIsin;

    @Size(max = 1)
    private String eikErstattet;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String einlagekontoSum;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ersatzBmg;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertBeschrStpfl1;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertBeschrStpfl2;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP19Reit1;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP19Reit6;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP431;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP432;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP433;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP434;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP435;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP436;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP437;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP438;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragP439;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragTevP431;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragTevP436;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String ertragTevP439;

    @Size(max = 1)
    private String estb;

    @Size(max = 1)
    private String fehlerKennz;

    @Size(max = 1)
    private String formular;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String gewinnVerInv;

    @Size(max = 400)
    private String inlKiVerw;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612Aimmf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612AimmfVp;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612Aktf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612AktfVp;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612Immf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612ImmfVp;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612Misf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612MisfVp;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612Sonf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv1612SonfVp;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv163Aimmf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv163Aktf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv163Immf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv163Misf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String inv163Sonf;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String invErt1612;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String invErt163;

    @Size(max = 1)
    private String invVorhanden;

    @Size(max = 1)
    private String kStabzugP43Nr1;

    @Size(max = 1)
    private String kStabzugP43Nr5;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kapDreiFueP433;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kapest;

    @Size(max = 1)
    private String keinOriginalM3;

    @Size(max = 1)
    private String liefertiefe;

    @Size(max = 36)
    private String ordnungsnummer;

    @Size(max = 1)
    private String pivVerwahrt;

    @Size(max = 6)
    private String sdBezugWP;

    @Size(max = 12)
    private String sdIsin;

    @Size(max = 6)
    private String sdWkn;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String sfAnlAnzahl;

    @Size(max = 6)
    private String sfAnlBezugWP;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String sfAnlKapest;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String sfAnlSolz;

    @Pattern(
            regexp = "^$|^\\d{1,13}(\\.\\d{1,5})?$",
            message = "Integer length must be less than or equal to 13, and the fractional part length must be less than or equal to 5")
    private String sfAnteile;

    @Size(max = 1)
    private String sfBetEinnahm;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String sfBetrag;

    @Size(max = 400)
    private String sfBezeichn;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal sfDatum;

    @Size(max = 12)
    private String sfIsin;

    @Size(max = 6)
    private String sfSchuldBezWp;

    @Size(max = 1)
    private String sfSonsEink;

    @Size(max = 6)
    private String sfWkn;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String solz;

    @Size(max = 1)
    private String stornierung;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zahlungstag;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zahlungszeitrBis;

    @Pattern(regexp = "^$|^\\d{1,8}?$", message = "Length must be less than or equal to 8")
    private String zahlungszeitrVon;

    @Size(max = 1)
    private String zusGefassteStb;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal zusZeitraumBis;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal zusZeitraumVon;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String abzDreiFueP431;

    @Pattern(
            regexp = "^$|^\\d{1,15}(\\.\\d{1,3})?$",
            message = "Integer length must be less than or equal to 15, and the fractional part length must be less than or equal to 3")
    private String kapDreiFueP431;

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
    public <T extends SubTable> void setTrTableAamSet(List<T> subTableSet) {
        List<TrTableBm3Aam> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm3Aam) {
            subTableSetConverted = (List<TrTableBm3Aam>) subTableSet;
        }

        if (getTrTableBm3AamSet() == null) {
            setTrTableBm3AamSet(subTableSetConverted);
            return;
        }

        List<TrTableAamPK> Bm3AamPKPrevious = getTrTableBm3AamSet().stream().map(TrTableBm3Aam::getTrTableAamPK)
                .collect(Collectors.toList());
        List<TrTableAamPK> Bm3AamPKAfter = subTableSetConverted.stream().map(TrTableBm3Aam::getTrTableAamPK)
                .collect(Collectors.toList());
        List<TrTableAamPK> Bm3AamPKIntersection;

        Bm3AamPKIntersection = new LinkedList<>(Bm3AamPKPrevious);
        Bm3AamPKIntersection.retainAll(Bm3AamPKAfter);

        for (TrTableAamPK pk : Bm3AamPKPrevious) {
            if (!Bm3AamPKAfter.contains(pk)) {
                setTrTableBm3AamSet(getTrTableBm3AamSet().stream()
                        .filter(Bm3Aam -> !Bm3Aam.getTrTableAamPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAamPK pk : Bm3AamPKAfter) {
            if (!Bm3AamPKPrevious.contains(pk)) {
                getTrTableBm3AamSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Aam -> Bm3Aam.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm3AamSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Aam -> Bm3Aam.getTrTableAamPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAamSet() {
        return (List<T>) trTableBm3AamSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkbSet(List<T> subTableSet) {
        List<TrTableBm3Akb> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm3Akb) {
            subTableSetConverted = (List<TrTableBm3Akb>) subTableSet;
        }

        if (getTrTableBm3AkbSet() == null) {
            setTrTableBm3AkbSet(subTableSetConverted);
            return;
        }

        List<TrTableAkbPK> Bm3AkbPKPrevious = getTrTableBm3AkbSet().stream().map(TrTableBm3Akb::getTrTableAkbPK)
                .collect(Collectors.toList());
        List<TrTableAkbPK> Bm3AkbPKAfter = subTableSetConverted.stream().map(TrTableBm3Akb::getTrTableAkbPK)
                .collect(Collectors.toList());
        List<TrTableAkbPK> Bm3AkbPKIntersection;

        Bm3AkbPKIntersection = new LinkedList<>(Bm3AkbPKPrevious);
        Bm3AkbPKIntersection.retainAll(Bm3AkbPKAfter);

        for (TrTableAkbPK pk : Bm3AkbPKPrevious) {
            if (!Bm3AkbPKAfter.contains(pk)) {
                setTrTableBm3AkbSet(getTrTableBm3AkbSet().stream()
                        .filter(Bm3Akb -> !Bm3Akb.getTrTableAkbPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAkbPK pk : Bm3AkbPKAfter) {
            if (!Bm3AkbPKPrevious.contains(pk)) {
                getTrTableBm3AkbSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Akb -> Bm3Akb.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm3AkbSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Akb -> Bm3Akb.getTrTableAkbPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAkbSet() {
        return (List<T>) trTableBm3AkbSet;
    }

    @Override
    public <T extends SubTable> void setTrTableAkeSet(List<T> subTableSet) {
        List<TrTableBm3Ake> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm3Ake) {
            subTableSetConverted = (List<TrTableBm3Ake>) subTableSet;
        }

        if (getTrTableBm3AkeSet() == null) {
            setTrTableBm3AkeSet(subTableSetConverted);
            return;
        }

        List<TrTableAkePK> Bm3AkePKPrevious = getTrTableBm3AkeSet().stream().map(TrTableBm3Ake::getTrTableAkePK)
                .collect(Collectors.toList());
        List<TrTableAkePK> Bm3AkePKAfter = subTableSetConverted.stream().map(TrTableBm3Ake::getTrTableAkePK)
                .collect(Collectors.toList());
        List<TrTableAkePK> Bm3AkePKIntersection;

        Bm3AkePKIntersection = new LinkedList<>(Bm3AkePKPrevious);
        Bm3AkePKIntersection.retainAll(Bm3AkePKAfter);

        for (TrTableAkePK pk : Bm3AkePKPrevious) {
            if (!Bm3AkePKAfter.contains(pk)) {
                setTrTableBm3AkeSet(getTrTableBm3AkeSet().stream()
                        .filter(Bm3Ake -> !Bm3Ake.getTrTableAkePK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableAkePK pk : Bm3AkePKAfter) {
            if (!Bm3AkePKPrevious.contains(pk)) {
                getTrTableBm3AkeSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Ake -> Bm3Ake.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm3AkeSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Ake -> Bm3Ake.getTrTableAkePK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableAkeSet() {
        return (List<T>) trTableBm3AkeSet;
    }

    @Override
    public <T extends SubTable> void setTrTableEikSet(List<T> subTableSet) {
        List<TrTableBm3Eik> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm3Eik) {
            subTableSetConverted = (List<TrTableBm3Eik>) subTableSet;
        }

        if (getTrTableBm3EikSet() == null) {
            setTrTableBm3EikSet(subTableSetConverted);
            return;
        }

        List<TrTableEikPK> Bm3EikPKPrevious = getTrTableBm3EikSet().stream().map(TrTableBm3Eik::getTrTableEikPK)
                .collect(Collectors.toList());
        List<TrTableEikPK> Bm3EikPKAfter = subTableSetConverted.stream().map(TrTableBm3Eik::getTrTableEikPK)
                .collect(Collectors.toList());
        List<TrTableEikPK> Bm3EikPKIntersection;

        Bm3EikPKIntersection = new LinkedList<>(Bm3EikPKPrevious);
        Bm3EikPKIntersection.retainAll(Bm3EikPKAfter);

        for (TrTableEikPK pk : Bm3EikPKPrevious) {
            if (!Bm3EikPKAfter.contains(pk)) {
                setTrTableBm3EikSet(getTrTableBm3EikSet().stream()
                        .filter(Bm3Eik -> !Bm3Eik.getTrTableEikPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTableEikPK pk : Bm3EikPKAfter) {
            if (!Bm3EikPKPrevious.contains(pk)) {
                getTrTableBm3EikSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Eik -> Bm3Eik.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm3EikSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Eik -> Bm3Eik.getTrTableEikPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTableEikSet() {
        return (List<T>) trTableBm3EikSet;
    }

    @Override
    public <T extends SubTable> void setTrTablePivSet(List<T> subTableSet) {
        List<TrTableBm3Piv> subTableSetConverted = new LinkedList<>();

        if (subTableSet instanceof TrTableBm3Piv) {
            subTableSetConverted = (List<TrTableBm3Piv>) subTableSet;
        }

        if (getTrTableBm3PivSet() == null) {
            setTrTableBm3PivSet(subTableSetConverted);
            return;
        }

        List<TrTablePivPK> Bm3PivPKPrevious = getTrTableBm3PivSet().stream().map(TrTableBm3Piv::getTrTablePivPK)
                .collect(Collectors.toList());
        List<TrTablePivPK> Bm3PivPKAfter = subTableSetConverted.stream().map(TrTableBm3Piv::getTrTablePivPK)
                .collect(Collectors.toList());
        List<TrTablePivPK> Bm3PivPKIntersection;

        Bm3PivPKIntersection = new LinkedList<>(Bm3PivPKPrevious);
        Bm3PivPKIntersection.retainAll(Bm3PivPKAfter);

        for (TrTablePivPK pk : Bm3PivPKPrevious) {
            if (!Bm3PivPKAfter.contains(pk)) {
                setTrTableBm3PivSet(getTrTableBm3PivSet().stream()
                        .filter(Bm3Piv -> !Bm3Piv.getTrTablePivPK().equals(pk)).collect(Collectors.toList()));
            }
        }

        for (TrTablePivPK pk : Bm3PivPKAfter) {
            if (!Bm3PivPKPrevious.contains(pk)) {
                getTrTableBm3PivSet().addAll(
                        subTableSetConverted.stream().filter(Bm3Piv -> Bm3Piv.equals(pk)).collect(Collectors.toList()));
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
                                    .collect(Collectors.toList()));
                            getTrTableBm3PivSet().add(subTableSetConverted.stream()
                                    .filter(Bm3Piv -> Bm3Piv.getTrTablePivPK().equals(pk)).findFirst().get());
                        }
                    }
                }
            }
        }
    }

    @Override
    public <T extends SubTable> List<T> getTrTablePivSet() {
        return (List<T>) trTableBm3PivSet;
    }
}
