package com.fisglobal.taxreporting.entity.model.taxreporting.bsb;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortComparator;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.entity.model.taxreporting.IntermediateTable;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.util.TrTableBsbLoggingUtil;


/**
 * The persistent class for the TR_TABLE_BSB database table.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "trTableBm1Set", "trTableBm3Set" })
@Validated
@Table(name = "TR_TABLE_BSB")
public class TrTableBsb implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TrTableBsbPK trTableBsbPK;

    @JsonProperty("trTableBm1Set")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBsbFK", orphanRemoval = true)
    @SortComparator(Bm1Comparator.class)
    private SortedSet<TrTableBm1> trTableBm1Set = new TreeSet<>();

    @JsonProperty("trTableBm3Set")
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trTableBsbFK", orphanRemoval = true)
    @SortComparator(Bm3Comparator.class)
    private SortedSet<TrTableBm3> trTableBm3Set = new TreeSet<>();

    @Column(name = "ADRESSERGAENZUNG", length = 46, columnDefinition = "CHAR(46)")
    @Size(max = 46)
    private String adressergaenzung;

    @Column(name = "AENDER_DATUM")
    private BigDecimal aenderDatum;

    @Column(name = "AENDER_ERFASSER", length = 8, columnDefinition = "CHAR(8)")
    @Size(max = 8)
    private String aenderErfasser;

    @Column(name = "AENDER_ZEIT")
    private BigDecimal aenderZeit;

    @Column(name = "ANLAGE_DATUM")
    private BigDecimal anlageDatum;

    @Column(name = "ANLAGE_ERFASSER", length = 8, columnDefinition = "CHAR(8)")
    @Size(max = 8)
    private String anlageErfasser;

    @Column(name = "ANLAGE_ZEIT")
    private BigDecimal anlageZeit;

    @Column(name = "ANLASS", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String anlass;

    @Column(name = "ANTWORT_FEHLER_NR", length = 9, columnDefinition = "CHAR(9)")
    @Size(max = 9)
    private String antwortFehlerNr;

    @Column(name = "ANTWORT_STATUS", length = 2, columnDefinition = "CHAR(2)")
    @Size(max = 2)
    private String antwortStatus;

    @Column(name = "ANWEISUNGSART", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String anweisungsart;

    @Column(name = "ART", length = 5, columnDefinition = "CHAR(5)")
    @Size(max = 5)
    private String art;

    @Column(name = "AUFNEHM_ADR_ERG", length = 46, columnDefinition = "CHAR(46)")
    @Size(max = 46)
    private String aufnehmAdrErg;

    @Column(name = "AUFNEHM_AUSL_PLZ", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String aufnehmAuslPlz;

    @Column(name = "AUFNEHM_BIC", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String aufnehmBic;

    @Column(name = "AUFNEHM_EMAIL", length = 254, columnDefinition = "CHAR(254)")
    @Size(max = 254)
    private String aufnehmEmail;

    @Column(name = "AUFNEHM_GK_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmGkOrt;

    @Column(name = "AUFNEHM_GK_PLZ")
    private BigDecimal aufnehmGkPlz;

    @Column(name = "AUFNEHM_HSNR")
    private BigDecimal aufnehmHsnr;

    @Column(name = "AUFNEHM_HSZU", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String aufnehmHszu;

    @Column(name = "AUFNEHM_LAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmLand;

    @Column(name = "AUFNEHM_NAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmName;

    @Column(name = "AUFNEHM_ORDN_BEGR", length = 5, columnDefinition = "CHAR(5)")
    @Size(max = 5)
    private String aufnehmOrdnBegr;

    @Column(name = "AUFNEHM_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmOrt;

    @Column(name = "AUFNEHM_PF_NR")
    private BigDecimal aufnehmPfNr;

    @Column(name = "AUFNEHM_PF_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmPfOrt;

    @Column(name = "AUFNEHM_PF_PLZ")
    private BigDecimal aufnehmPfPlz;

    @Column(name = "AUFNEHM_PLZ")
    private BigDecimal aufnehmPlz;

    @Column(name = "AUFNEHM_STAAT_SL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String aufnehmStaatSl;

    @Column(name = "AUFNEHM_STR", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String aufnehmStr;

    @Column(name = "AUFNEHM_TEL", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String aufnehmTel;

    @Column(name = "AUSLANDSPLZ", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String auslandsplz;

    @Column(name = "AUSSTELLUNGSDAT")
    private BigDecimal ausstellungsdat;

    @Column(name = "BEDBSB_TIMESTAMP", length = 16, columnDefinition = "CHAR(16)")
    @Size(max = 16)
    private String bedbsbTimestamp;

    @Column(name = "DOKU_ART", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String dokuArt;

    @Column(name = "EHEG_LDNR")
    private BigDecimal ehegLdnr;

    @Column(name = "ERGAENZENDE_STB", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String ergaenzendeStb;

    @Column(name = "F_ADR_INFO", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String fAdrInfo;

    @Column(name = "F_ADRESSERGAENZUNG", length = 46, columnDefinition = "CHAR(46)")
    @Size(max = 46)
    private String fAdressergaenzung;

    @Column(name = "F_AUSLANDS_PLZ", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String fAuslandsPlz;

    @Column(name = "F_GK_PLZ")
    private BigDecimal fGkPlz;

    @Column(name = "F_GK_WOHNORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String fGkWohnort;

    @Column(name = "F_HAUSNUMMER")
    private BigDecimal fHausnummer;

    @Column(name = "F_HAUSNUMMER_ZUS", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String fHausnummerZus;

    @Column(name = "F_LAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String fLand;

    @Column(name = "F_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String fOrt;

    @Column(name = "F_PF_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String fPfOrt;

    @Column(name = "F_PF_PLZ")
    private BigDecimal fPfPlz;

    @Column(name = "F_PLZ")
    private BigDecimal fPlz;

    @Column(name = "F_POSTFACH")
    private BigDecimal fPostfach;

    @Column(name = "F_STAAT_SCHL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String fStaatSchl;

    @Column(name = "F_STRASSE", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String fStrasse;

    @Column(name = "F_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String fTyp;

    @Column(name = "GK_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String gkOrt;

    @Column(name = "GK_PLZ")
    private BigDecimal gkPlz;

    @Column(name = "HAUSNUMMER")
    private BigDecimal hausnummer;

    @Column(name = "HAUSNUMMER_ZUS", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String hausnummerZus;

    @Column(name = "KM_ID", length = 100, columnDefinition = "CHAR(100)")
    @Size(max = 100)
    private String kmId;

    @Column(name = "LAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String land;

    @Column(name = "MELDE_STATUS", length = 2, columnDefinition = "CHAR(2)")
    @Size(max = 2)
    private String meldeStatus;

    @Column(name = "MELDEJAHR")
    private BigDecimal meldejahr;

    @Column(name = "MELDUNG_JJJJMMTT")
    private BigDecimal meldungJjjjmmtt;

    @Column(name = "MELDUNG_UHR_HMS")
    private BigDecimal meldungUhrHms;

    @Column(name = "ND_NR_LFD")
    private BigDecimal ndNrLfd;

    @Column(name = "ND_TICKET", length = 32, columnDefinition = "CHAR(32)")
    @Size(max = 32)
    private String ndTicket;

    @Column(name = "NNP_AUS_PERSST_ID", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String nnpAusPersstId;

    @Column(name = "NNP_FIRMENNAME", length = 120, columnDefinition = "CHAR(120)")
    @Size(max = 120)
    private String nnpFirmenname;

    @Column(name = "NNP_STEUERNUMMER", length = 13, columnDefinition = "CHAR(13)")
    @Size(max = 13)
    private String nnpSteuernummer;

    @Column(name = "NNP_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String nnpTyp;

    @Column(name = "NNP_WID", length = 17, columnDefinition = "CHAR(17)")
    @Size(max = 17)
    private String nnpWid;

    @Column(name = "NNWP_AUS_PERST_ID", length = 40, columnDefinition = "CHAR(40)")
    @Size(max = 40)
    private String nnwpAusPerstId;

    @Column(name = "NNWP_FIRMENNAME", length = 120, columnDefinition = "CHAR(120)")
    @Size(max = 120)
    private String nnwpFirmenname;

    @Column(name = "NNWP_STEUERNUMMER")
    private BigDecimal nnwpSteuernummer;

    @Column(name = "NNWP_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String nnwpTyp;

    @Column(name = "NNWP_WID", length = 17, columnDefinition = "CHAR(17)")
    @Size(max = 17)
    private String nnwpWid;

    @Column(name = "NP1_AUSL_PERSST_ID", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String np1AuslPersstId;

    @Column(name = "NP1_AUSWAND_DATUM")
    private BigDecimal np1AuswandDatum;

    @Column(name = "NP1_GEBDAT")
    private BigDecimal np1Gebdat;

    @Column(name = "NP1_GEBNAME_VORS", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String np1GebnameVors;

    @Column(name = "NP1_GEBNAME_ZUS", length = 60, columnDefinition = "CHAR(60)")
    @Size(max = 60)
    private String np1GebnameZus;

    @Column(name = "NP1_GEBURTSLAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np1Geburtsland;

    @Column(name = "NP1_GEBURTSLAND_SL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String np1GeburtslandSl;

    @Column(name = "NP1_GEBURTSNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np1Geburtsname;

    @Column(name = "NP1_GEBURTSORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np1Geburtsort;

    @Column(name = "NP1_GESCHLECHT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String np1Geschlecht;

    @Column(name = "NP1_IDNR", length = 11, columnDefinition = "CHAR(11)")
    @Size(max = 11)
    private String np1Idnr;

    @Column(name = "NP1_NAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np1Name;

    @Column(name = "NP1_NAMENSVORSATZ", length = 10, columnDefinition = "CHAR(10)")
    @Size(max = 10)
    private String np1Namensvorsatz;

    @Column(name = "NP1_NAMENSZUSATZ", length = 10, columnDefinition = "CHAR(10)")
    @Size(max = 10)
    private String np1Namenszusatz;

    @Column(name = "NP1_NATIONALITAET", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String np1Nationalitaet;

    @Column(name = "NP1_STERBE_DATUM")
    private BigDecimal np1SterbeDatum;

    @Column(name = "NP1_TITEL", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String np1Titel;

    @Column(name = "NP1_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String np1Typ;

    @Column(name = "NP1_VORNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np1Vorname;

    @Column(name = "NP2_AUSL_PERSST_ID", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String np2AuslPersstId;

    @Column(name = "NP2_AUSWAND_DATUM")
    private BigDecimal np2AuswandDatum;

    @Column(name = "NP2_GEBDAT")
    private BigDecimal np2Gebdat;

    @Column(name = "NP2_GEBNAME_VORS", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String np2GebnameVors;

    @Column(name = "NP2_GEBNAME_ZUS", length = 60, columnDefinition = "CHAR(60)")
    @Size(max = 60)
    private String np2GebnameZus;

    @Column(name = "NP2_GEBURTSLAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np2Geburtsland;

    @Column(name = "NP2_GEBURTSLAND_SL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String np2GeburtslandSl;

    @Column(name = "NP2_GEBURTSNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np2Geburtsname;

    @Column(name = "NP2_GEBURTSORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np2Geburtsort;

    @Column(name = "NP2_GESCHLECHT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String np2Geschlecht;

    @Column(name = "NP2_IDNR", length = 11, columnDefinition = "CHAR(11)")
    @Size(max = 11)
    private String np2Idnr;

    @Column(name = "NP2_NAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np2Name;

    @Column(name = "NP2_NAMENSVORSATZ", length = 10, columnDefinition = "CHAR(10)")
    @Size(max = 10)
    private String np2Namensvorsatz;

    @Column(name = "NP2_NAMENSZUSATZ", length = 10, columnDefinition = "CHAR(10)")
    @Size(max = 10)
    private String np2Namenszusatz;

    @Column(name = "NP2_NATIONALITAET", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String np2Nationalitaet;

    @Column(name = "NP2_STERBE_DATUM")
    private BigDecimal np2SterbeDatum;

    @Column(name = "NP2_TITEL", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String np2Titel;

    @Column(name = "NP2_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String np2Typ;

    @Column(name = "NP2_VORNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String np2Vorname;

    @Column(name = "NWP_AUSWAND_DATUM")
    private BigDecimal nwpAuswandDatum;

    @Column(name = "NWP_GEB_LAND_SCHL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String nwpGebLandSchl;

    @Column(name = "NWP_GEB_NAME_VORS", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String nwpGebNameVors;

    @Column(name = "NWP_GEB_NAME_ZUS", length = 60, columnDefinition = "CHAR(60)")
    @Size(max = 60)
    private String nwpGebNameZus;

    @Column(name = "NWP_GEBURTSDATUM")
    private BigDecimal nwpGeburtsdatum;

    @Column(name = "NWP_GEBURTSLAND", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String nwpGeburtsland;

    @Column(name = "NWP_GEBURTSNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String nwpGeburtsname;

    @Column(name = "NWP_GEBURTSORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String nwpGeburtsort;

    @Column(name = "NWP_GESCHLECHT", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String nwpGeschlecht;

    @Column(name = "NWP_NAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String nwpName;

    @Column(name = "NWP_NAMENSVORSATZ", length = 25, columnDefinition = "CHAR(25)")
    @Size(max = 25)
    private String nwpNamensvorsatz;

    @Column(name = "NWP_NAMENSZUSATZ", length = 60, columnDefinition = "CHAR(60)")
    @Size(max = 60)
    private String nwpNamenszusatz;

    @Column(name = "NWP_NATIONALITAET", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String nwpNationalitaet;

    @Column(name = "NWP_PERSONINFO", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String nwpPersoninfo;

    @Column(name = "NWP_STERBE_DATUM")
    private BigDecimal nwpSterbeDatum;

    @Column(name = "NWP_TIN", length = 11, columnDefinition = "CHAR(11)")
    @Size(max = 11)
    private String nwpTin;

    @Column(name = "NWP_TITEL", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String nwpTitel;

    @Column(name = "NWP_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String nwpTyp;

    @Column(name = "NWP_VORNAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String nwpVorname;

    @Column(name = "ORDNUNGSBEGRIFF", length = 50, columnDefinition = "CHAR(50)")
    @Size(max = 50)
    private String ordnungsbegriff;

    @Column(name = "ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String ort;

    @Column(name = "PARTNER_ID", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String partnerId;

    @Column(name = "PERSON_ID", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String personId;

    @Column(name = "PERSON_ROLLE", length = 6, columnDefinition = "CHAR(6)")
    @Size(max = 6)
    private String personRolle;

    @Column(name = "PF_PLZ")
    private BigDecimal pfPlz;

    @Column(name = "PF_WOHNORT", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String pfWohnort;

    @Column(name = "PLZ")
    private BigDecimal plz;

    @Column(name = "POSTFACH")
    private BigDecimal postfach;

    @Column(name = "REF_KM_ID", length = 100, columnDefinition = "CHAR(100)")
    @Size(max = 100)
    private String refKmId;

    @Column(name = "ROLLE_WEITERE_PERS", length = 2, columnDefinition = "CHAR(2)")
    @Size(max = 2)
    private String rolleWeiterePers;

    @Column(name = "STAAT_SCHL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String staatSchl;

    @Column(name = "STATUS", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String status;

    @Column(name = "STATUS_KOMMENTAR", length = 100, columnDefinition = "CHAR(100)")
    @Size(max = 100)
    private String statusKommentar;

    @Column(name = "STEUER_ZUORD", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String steuerZuord;

    @Column(name = "STEUERAUSLAENDER", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String steuerauslaender;

    @Column(name = "STORNO_KZ", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String stornoKz;

    @Column(name = "STRASSE", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String strasse;

    @Column(name = "UPDATE_CHECK")
    private BigDecimal updateCheck;

    @Column(name = "VERF_ADR_ERG", length = 46, columnDefinition = "CHAR(46)")
    @Size(max = 46)
    private String verfAdrErg;

    @Column(name = "VERF_BIC", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String verfBic;

    @Column(name = "VERF_EMAIL", length = 128, columnDefinition = "CHAR(128)")
    @Size(max = 128)
    private String verfEmail;

    @Column(name = "VERF_GK_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String verfGkOrt;

    @Column(name = "VERF_GK_PLZ")
    private BigDecimal verfGkPlz;

    @Column(name = "VERF_HSNR")
    private BigDecimal verfHsnr;

    @Column(name = "VERF_HSZU", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String verfHszu;

    @Column(name = "VERF_NAME", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String verfName;

    @Column(name = "VERF_ORD_BEGRIFF", length = 5, columnDefinition = "CHAR(5)")
    @Size(max = 5)
    private String verfOrdBegriff;

    @Column(name = "VERF_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String verfOrt;

    @Column(name = "VERF_PF_NR")
    private BigDecimal verfPfNr;

    @Column(name = "VERF_PF_ORT", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String verfPfOrt;

    @Column(name = "VERF_PF_PLZ")
    private BigDecimal verfPfPlz;

    @Column(name = "VERF_PLZ")
    private BigDecimal verfPlz;

    @Column(name = "VERF_STR", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String verfStr;

    @Column(name = "VERF_TEL", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String verfTel;

    @Column(name = "VERSION")
    private BigDecimal version;

    @Column(name = "VERSION_DETAIL")
    private BigDecimal versionDetail;

    @Column(name = "WEITERE_ERLAUETERG", length = 400, columnDefinition = "CHAR(400)")
    @Size(max = 400)
    private String weitereErlaueterg;

    @Column(name = "WP_ADRESSERGAENZ", length = 46, columnDefinition = "CHAR(46)")
    @Size(max = 46)
    private String wpAdressergaenz;

    @Column(name = "WP_AUSLANDS_PLZ", length = 12, columnDefinition = "CHAR(12)")
    @Size(max = 12)
    private String wpAuslandsPlz;

    @Column(name = "WP_HAUSNUMMER")
    private BigDecimal wpHausnummer;

    @Column(name = "WP_HAUSNUMMER_ZUS", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String wpHausnummerZus;

    @Column(name = "WP_INFO", length = 250, columnDefinition = "CHAR(250)")
    @Size(max = 250)
    private String wpInfo;

    @Column(name = "WP_LAND_W_P", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String wpLandWP;

    @Column(name = "WP_ORT", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String wpOrt;

    @Column(name = "WP_PLZ")
    private BigDecimal wpPlz;

    @Column(name = "WP_STAAT_SCHL", length = 3, columnDefinition = "CHAR(3)")
    @Size(max = 3)
    private String wpStaatSchl;

    @Column(name = "WP_STRASSE", length = 72, columnDefinition = "CHAR(72)")
    @Size(max = 72)
    private String wpStrasse;

    @Column(name = "WP_TYP", length = 30, columnDefinition = "CHAR(30)")
    @Size(max = 30)
    private String wpTyp;

    @Column(name = "KONTO_ID", length = 20, columnDefinition = "CHAR(20)")
    @Size(max = 20)
    private String kontoId;

    @Column(name = "LEBEND_KZ", length = 1, columnDefinition = "CHAR(1)")
    @Size(max = 1)
    private String lebendKz;

    @JsonIgnore
    public <T extends IntermediateTable> Set<T> getIntermediateRows() {
        Set<T> result = new LinkedHashSet<>();
        if (this.getTrTableBsbPK().getKeyMuster() != null) {
            if (this.getTrTableBsbPK().getKeyMuster().trim().equals("JSTB-I")) {
                result.addAll((Collection<T>) trTableBm1Set);
            } else if (this.getTrTableBsbPK().getKeyMuster().trim().equals("JSTB-III")) {
                result.addAll((Collection<T>) trTableBm3Set);
            }
        }
        return result;
    }

    public TrTableBsb deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (TrTableBsb) ois.readObject();
    }

    public String taxCertificateInfo() {
        return TrTableBsbLoggingUtil.getTaxCertficateDetailsToLog(this.getKmId(), this.getTrTableBsbPK().getKeyIdNr(),
                this.getTrTableBsbPK().getKeySysDatum(), this.getTrTableBsbPK().getKeyUhrzeit());
    }
}
