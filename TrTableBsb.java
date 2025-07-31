package com.fisglobal.taxreporting.controller.dto.bsb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortComparator;
import org.springframework.validation.annotation.Validated;

import com.fisglobal.taxreporting.controller.dto.bm1.TrTableBm1;
import com.fisglobal.taxreporting.controller.dto.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.Bm1Comparator;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.Bm3Comparator;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.util.TrTableBsbLoggingUtil;


/**
 * The DTO class for the TR_TABLE_BSB database table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "trTableBm1Set", "trTableBm3Set" })
@Validated
public class TrTableBsb implements Serializable {
    private static final long serialVersionUID = 1L;

    @Valid
    private TrTableBsbPK trTableBsbPK;

    @Valid
    @JsonProperty("trTableBm1Set")
    @JsonManagedReference
    @SortComparator(Bm1Comparator.class)
    private SortedSet<TrTableBm1> trTableBm1Set = new TreeSet<>();

    @Valid
    @JsonProperty("trTableBm3Set")
    @JsonManagedReference
    @SortComparator(Bm3Comparator.class)
    private SortedSet<TrTableBm3> trTableBm3Set = new TreeSet<>();

    @Size(max = 46)
    private String adressergaenzung;

    @NotNull(message = "Length must be less than or equal to 8")
    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal aenderDatum;

    @NotNull
    @Size(max = 8)
    private String aenderErfasser;

    @NotNull(message = "Length must be less than or equal to 6")
    @Digits(integer = 6, fraction = 0, message = "Length must be less than or equal to 6")
    private BigDecimal aenderZeit;

    @NotNull(message = "Length must be less than or equal to 8")
    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal anlageDatum;

    @NotNull
    @Size(max = 8)
    private String anlageErfasser;

    @NotNull(message = "Length must be less than or equal to 6")
    @Digits(integer = 6, fraction = 0, message = "Length must be less than or equal to 6")
    private BigDecimal anlageZeit;

    @NotNull
    @Size(max = 400)
    private String anlass;

    @Size(max = 9)
    private String antwortFehlerNr;

    @Size(max = 2)
    private String antwortStatus;

    @NotNull
    @Size(max = 20)
    private String anweisungsart;

    @Size(max = 5)
    private String art;

    @Size(max = 46)
    private String aufnehmAdrErg;

    @Size(max = 12)
    private String aufnehmAuslPlz;

    @Size(max = 50)
    private String aufnehmBic;

    @Size(max = 254)
    private String aufnehmEmail;

    @Size(max = 72)
    private String aufnehmGkOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal aufnehmGkPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal aufnehmHsnr;

    @Size(max = 20)
    private String aufnehmHszu;

    @Size(max = 72)
    private String aufnehmLand;

    @Size(max = 72)
    private String aufnehmName;

    @Size(max = 5)
    private String aufnehmOrdnBegr;

    @Size(max = 72)
    private String aufnehmOrt;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal aufnehmPfNr;

    @Size(max = 72)
    private String aufnehmPfOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal aufnehmPfPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal aufnehmPlz;

    @Size(max = 3)
    private String aufnehmStaatSl;

    @Size(max = 72)
    private String aufnehmStr;

    @Size(max = 30)
    private String aufnehmTel;

    @Size(max = 12)
    private String auslandsplz;

    @NotNull(message = "Length must be less than or equal to 8")
    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal ausstellungsdat;

    @NotNull
    @Size(max = 16)
    private String bedbsbTimestamp;

    @NotNull
    @Size(max = 1)
    private String dokuArt;

    @Digits(integer = 11, fraction = 0, message = "Length must be less than or equal to 11")
    private BigDecimal ehegLdnr;

    @Size(max = 1)
    private String ergaenzendeStb;

    @Size(max = 250)
    private String fAdrInfo;

    @Size(max = 46)
    private String fAdressergaenzung;

    @Size(max = 12)
    private String fAuslandsPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal fGkPlz;

    @Size(max = 72)
    private String fGkWohnort;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal fHausnummer;

    @Size(max = 20)
    private String fHausnummerZus;

    @Size(max = 72)
    private String fLand;

    @Size(max = 72)
    private String fOrt;

    @Size(max = 72)
    private String fPfOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal fPfPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal fPlz;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal fPostfach;

    @Size(max = 3)
    private String fStaatSchl;

    @Size(max = 72)
    private String fStrasse;

    @Size(max = 30)
    private String fTyp;

    @Size(max = 72)
    private String gkOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal gkPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal hausnummer;

    @Size(max = 20)
    private String hausnummerZus;

    @NotNull
    @Size(max = 100)
    private String kmId;

    @Size(max = 72)
    private String land;

    @NotNull
    @Size(max = 2)
    private String meldeStatus;

    @NotNull(message = "Length must be less than or equal to 4")
    @Digits(integer = 4, fraction = 0, message = "Length must be less than or equal to 4")
    private BigDecimal meldejahr;

    @NotNull(message = "Length must be less than or equal to 8")
    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal meldungJjjjmmtt;

    @NotNull(message = "Length must be less than or equal to 6")
    @Digits(integer = 6, fraction = 0, message = "Length must be less than or equal to 6")
    private BigDecimal meldungUhrHms;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal ndNrLfd;

    @Size(max = 32)
    private String ndTicket;

    @Size(max = 250)
    private String nnpAusPersstId;

    @NotNull
    @Size(max = 120)
    private String nnpFirmenname;

    @Size(max = 13)
    private String nnpSteuernummer;

    @Size(max = 30)
    private String nnpTyp;

    @Size(max = 17)
    private String nnpWid;

    @Size(max = 40)
    private String nnwpAusPerstId;

    @Size(max = 120)
    private String nnwpFirmenname;

    @Digits(integer = 13, fraction = 0, message = "Length must be less than or equal to 13")
    private BigDecimal nnwpSteuernummer;

    @Size(max = 30)
    private String nnwpTyp;

    @Size(max = 17)
    private String nnwpWid;

    @Size(max = 250)
    private String np1AuslPersstId;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np1AuswandDatum;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np1Gebdat;

    @Size(max = 25)
    private String np1GebnameVors;

    @Size(max = 60)
    private String np1GebnameZus;

    @Size(max = 72)
    private String np1Geburtsland;

    @Size(max = 3)
    private String np1GeburtslandSl;

    @Size(max = 72)
    private String np1Geburtsname;

    @Size(max = 72)
    private String np1Geburtsort;

    @Size(max = 1)
    private String np1Geschlecht;

    @Size(max = 11)
    private String np1Idnr;

    @NotNull
    @Size(max = 72)
    private String np1Name;

    @Size(max = 10)
    private String np1Namensvorsatz;

    @Size(max = 10)
    private String np1Namenszusatz;

    @Size(max = 50)
    private String np1Nationalitaet;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np1SterbeDatum;

    @Size(max = 30)
    private String np1Titel;

    @Size(max = 30)
    private String np1Typ;

    @NotNull
    @Size(max = 72)
    private String np1Vorname;

    @Size(max = 250)
    private String np2AuslPersstId;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np2AuswandDatum;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np2Gebdat;

    @Size(max = 25)
    private String np2GebnameVors;

    @Size(max = 60)
    private String np2GebnameZus;

    @Size(max = 72)
    private String np2Geburtsland;

    @Size(max = 3)
    private String np2GeburtslandSl;

    @Size(max = 72)
    private String np2Geburtsname;

    @Size(max = 72)
    private String np2Geburtsort;

    @Size(max = 1)
    private String np2Geschlecht;

    @Size(max = 11)
    private String np2Idnr;

    @Size(max = 72)
    private String np2Name;

    @Size(max = 10)
    private String np2Namensvorsatz;

    @Size(max = 10)
    private String np2Namenszusatz;

    @Size(max = 50)
    private String np2Nationalitaet;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal np2SterbeDatum;

    @Size(max = 30)
    private String np2Titel;

    @Size(max = 30)
    private String np2Typ;

    @Size(max = 72)
    private String np2Vorname;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal nwpAuswandDatum;

    @Size(max = 3)
    private String nwpGebLandSchl;

    @Size(max = 25)
    private String nwpGebNameVors;

    @Size(max = 60)
    private String nwpGebNameZus;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal nwpGeburtsdatum;

    @Size(max = 72)
    private String nwpGeburtsland;

    @Size(max = 72)
    private String nwpGeburtsname;

    @Size(max = 72)
    private String nwpGeburtsort;

    @Size(max = 1)
    private String nwpGeschlecht;

    @Size(max = 72)
    private String nwpName;

    @Size(max = 25)
    private String nwpNamensvorsatz;

    @Size(max = 60)
    private String nwpNamenszusatz;

    @Size(max = 50)
    private String nwpNationalitaet;

    @Size(max = 250)
    private String nwpPersoninfo;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal nwpSterbeDatum;

    @Size(max = 11)
    private String nwpTin;

    @Size(max = 30)
    private String nwpTitel;

    @Size(max = 30)
    private String nwpTyp;

    @Size(max = 72)
    private String nwpVorname;

    @Size(max = 50)
    private String ordnungsbegriff;

    @NotNull
    @Size(max = 72)
    private String ort;

    @Size(max = 20)
    private String partnerId;

    @NotNull
    @Size(max = 20)
    private String personId;

    @NotNull
    @Size(max = 6)
    private String personRolle;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal pfPlz;

    @Size(max = 12)
    private String pfWohnort;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal plz;

    @Digits(integer = 8, fraction = 0, message = "Length must be less than or equal to 8")
    private BigDecimal postfach;

    @Size(max = 100)
    private String refKmId;

    @Size(max = 2)
    private String rolleWeiterePers;

    @Size(max = 3)
    private String staatSchl;

    @NotNull
    @Size(max = 1)
    private String status;

    @Size(max = 100)
    private String statusKommentar;

    @NotNull
    @Size(max = 1)
    private String steuerZuord;

    @NotNull
    @Size(max = 1)
    private String steuerauslaender;

    @NotNull
    @Size(max = 1)
    private String stornoKz;

    @NotNull
    @Size(max = 72)
    private String strasse;

    @NotNull(message = "Length must be less than or equal to 14")
    @Digits(integer = 14, fraction = 0, message = "Length must be less than or equal to 14")
    private BigDecimal updateCheck;

    @Size(max = 46)
    private String verfAdrErg;

    @Size(max = 30)
    private String verfBic;

    @Size(max = 128)
    private String verfEmail;

    @Size(max = 72)
    private String verfGkOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal verfGkPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal verfHsnr;

    @Size(max = 20)
    private String verfHszu;

    @NotNull
    @Size(max = 72)
    private String verfName;

    @Size(max = 5)
    private String verfOrdBegriff;

    @NotNull
    @Size(max = 72)
    private String verfOrt;

    @Digits(integer = 6, fraction = 0, message = "Length must be less than or equal to 6")
    private BigDecimal verfPfNr;

    @Size(max = 12)
    private String verfPfOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal verfPfPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal verfPlz;

    @NotNull
    @Size(max = 72)
    private String verfStr;

    @Size(max = 30)
    private String verfTel;

    @NotNull(message = "Length must be less than or equal to 1")
    @Digits(integer = 1, fraction = 0, message = "Length must be less than or equal to 1")
    private BigDecimal version;

    @NotNull(message = "Length must be less than or equal to 1")
    @Digits(integer = 1, fraction = 0, message = "Length must be less than or equal to 1")
    private BigDecimal versionDetail;

    @Size(max = 400)
    private String weitereErlaueterg;

    @Size(max = 46)
    private String wpAdressergaenz;

    @Size(max = 12)
    private String wpAuslandsPlz;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal wpHausnummer;

    @Size(max = 20)
    private String wpHausnummerZus;

    @Size(max = 250)
    private String wpInfo;

    @Size(max = 72)
    private String wpLandWP;

    @Size(max = 72)
    private String wpOrt;

    @Digits(integer = 5, fraction = 0, message = "Length must be less than or equal to 5")
    private BigDecimal wpPlz;

    @Size(max = 3)
    private String wpStaatSchl;

    @Size(max = 72)
    private String wpStrasse;

    @Size(max = 30)
    private String wpTyp;

    @Size(max = 20)
    private String kontoId;

    @Size(max = 1)
    private String lebendKz;

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
