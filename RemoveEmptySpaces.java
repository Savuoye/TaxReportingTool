package com.fisglobal.taxreporting.util;

import java.math.BigDecimal;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.*;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.*;
import com.fisglobal.taxreporting.enums.BsbStatus;


public class RemoveEmptySpaces {
    public boolean clean(TrTableBsb bsb) {
        if (bsb == null) {
            return false;
        }
        if (bsb.getUpdateCheck() == null) {
            bsb.setUpdateCheck(BigDecimal.ZERO);
        }
        if (bsb.getUpdateCheck().compareTo(BigDecimal.ONE) == 0) {
            return false;
        }

        if (bsb.getTrTableBm1Set() != null) {
            bsb.getTrTableBm1Set().forEach(bm1 -> {
                this.cleanTrTableBm1(bm1);
                bm1.getTrTableBm1AamSet().forEach(this::cleanTrTableBm1Aam);
                bm1.getTrTableBm1AkbSet().forEach(this::cleanTrTableBm1Akb);
                bm1.getTrTableBm1AkeSet().forEach(this::cleanTrTableBm1Ake);
                bm1.getTrTableBm1EikSet().forEach(this::cleanTrTableBm1Eik);
                bm1.getTrTableBm1PivSet().forEach(this::cleanTrTableBm1Piv);
            });
        }
        if (bsb.getTrTableBm3Set() != null) {
            bsb.getTrTableBm3Set().forEach(bm3 -> {
                this.cleanTrTableBm3(bm3);
                bm3.getTrTableBm3AamSet().forEach(this::cleanTrTableBm3Aam);
                bm3.getTrTableBm3AkbSet().forEach(this::cleanTrTableBm3Akb);
                bm3.getTrTableBm3AkeSet().forEach(this::cleanTrTableBm3Ake);
                bm3.getTrTableBm3EikSet().forEach(this::cleanTrTableBm3Eik);
                bm3.getTrTableBm3PivSet().forEach(this::cleanTrTableBm3Piv);
            });
        }
        this.cleanTrTableBsb(bsb);
        // this.cleanTrTableBsbPK(bsb.getTrTableBsbPK());

        bsb.setUpdateCheck(BigDecimal.ONE);
        return true;
    }

    public void cleanTrTableBsbPK(TrTableBsbPK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBsb(TrTableBsb entity) {
        if (entity == null) {
            return;
        }
        entity.setAdressergaenzung(
                StringUtils.ofNullable(entity.getAdressergaenzung()).map(StringUtils::strip).orElse(null));
        entity.setAenderErfasser(
                StringUtils.ofNullable(entity.getAenderErfasser()).map(StringUtils::strip).orElse(" "));
        if (!(entity.getStatus().equals(BsbStatus.H.name())) || (!entity.getStatus().equals(BsbStatus.H.name())
                && !StringUtils.isBlank(entity.getAnlageErfasser()))) {
            entity.setAnlageErfasser(
                    StringUtils.ofNullable(entity.getAnlageErfasser()).map(StringUtils::strip).orElse(" "));
        }
        if (!(entity.getStatus().equals(BsbStatus.H.name()))
                || (entity.getStatus().equals(BsbStatus.H.name()) && !StringUtils.isBlank(entity.getAnlass()))) {
            entity.setAnlass(StringUtils.ofNullable(entity.getAnlass()).map(StringUtils::strip).orElse(" "));
        }
        entity.setAntwortFehlerNr(
                StringUtils.ofNullable(entity.getAntwortFehlerNr()).map(StringUtils::strip).orElse(null));
        entity.setAntwortStatus(StringUtils.ofNullable(entity.getAntwortStatus()).map(StringUtils::strip).orElse(null));
        entity.setAnweisungsart(StringUtils.ofNullable(entity.getAnweisungsart()).map(StringUtils::strip).orElse(" "));
        entity.setArt(StringUtils.ofNullable(entity.getArt()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmAdrErg(StringUtils.ofNullable(entity.getAufnehmAdrErg()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmAuslPlz(
                StringUtils.ofNullable(entity.getAufnehmAuslPlz()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmBic(StringUtils.ofNullable(entity.getAufnehmBic()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmEmail(StringUtils.ofNullable(entity.getAufnehmEmail()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmGkOrt(StringUtils.ofNullable(entity.getAufnehmGkOrt()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmHszu(StringUtils.ofNullable(entity.getAufnehmHszu()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmLand(StringUtils.ofNullable(entity.getAufnehmLand()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmName(StringUtils.ofNullable(entity.getAufnehmName()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmOrdnBegr(
                StringUtils.ofNullable(entity.getAufnehmOrdnBegr()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmOrt(StringUtils.ofNullable(entity.getAufnehmOrt()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmPfOrt(StringUtils.ofNullable(entity.getAufnehmPfOrt()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmStaatSl(
                StringUtils.ofNullable(entity.getAufnehmStaatSl()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmStr(StringUtils.ofNullable(entity.getAufnehmStr()).map(StringUtils::strip).orElse(null));
        entity.setAufnehmTel(StringUtils.ofNullable(entity.getAufnehmTel()).map(StringUtils::strip).orElse(null));
        entity.setAuslandsplz(StringUtils.ofNullable(entity.getAuslandsplz()).map(StringUtils::strip).orElse(null));
        entity.setBedbsbTimestamp(
                StringUtils.ofNullable(entity.getBedbsbTimestamp()).map(StringUtils::strip).orElse(" "));
        entity.setDokuArt(StringUtils.ofNullable(entity.getDokuArt()).map(StringUtils::strip).orElse(" "));
        entity.setErgaenzendeStb(
                StringUtils.ofNullable(entity.getErgaenzendeStb()).map(StringUtils::strip).orElse(null));
        entity.setFAdrInfo(StringUtils.ofNullable(entity.getFAdrInfo()).map(StringUtils::strip).orElse(null));
        entity.setFAdressergaenzung(
                StringUtils.ofNullable(entity.getFAdressergaenzung()).map(StringUtils::strip).orElse(null));
        entity.setFAuslandsPlz(StringUtils.ofNullable(entity.getFAuslandsPlz()).map(StringUtils::strip).orElse(null));
        entity.setFGkWohnort(StringUtils.ofNullable(entity.getFGkWohnort()).map(StringUtils::strip).orElse(null));
        entity.setFHausnummerZus(
                StringUtils.ofNullable(entity.getFHausnummerZus()).map(StringUtils::strip).orElse(null));
        entity.setFLand(StringUtils.ofNullable(entity.getFLand()).map(StringUtils::strip).orElse(null));
        entity.setFOrt(StringUtils.ofNullable(entity.getFOrt()).map(StringUtils::strip).orElse(null));
        entity.setFPfOrt(StringUtils.ofNullable(entity.getFPfOrt()).map(StringUtils::strip).orElse(null));
        entity.setFStaatSchl(StringUtils.ofNullable(entity.getFStaatSchl()).map(StringUtils::strip).orElse(null));
        entity.setFStrasse(StringUtils.ofNullable(entity.getFStrasse()).map(StringUtils::strip).orElse(null));
        entity.setFTyp(StringUtils.ofNullable(entity.getFTyp()).map(StringUtils::strip).orElse(null));
        entity.setGkOrt(StringUtils.ofNullable(entity.getGkOrt()).map(StringUtils::strip).orElse(null));
        entity.setHausnummerZus(StringUtils.ofNullable(entity.getHausnummerZus()).map(StringUtils::strip).orElse(null));
        entity.setKmId(StringUtils.ofNullable(entity.getKmId()).map(StringUtils::strip).orElse(" "));
        entity.setLand(StringUtils.ofNullable(entity.getLand()).map(StringUtils::strip).orElse(null));
        entity.setMeldeStatus(StringUtils.ofNullable(entity.getMeldeStatus()).map(StringUtils::strip).orElse("  "));
        entity.setNdTicket(StringUtils.ofNullable(entity.getNdTicket()).map(StringUtils::strip).orElse(null));
        entity.setNnpAusPersstId(
                StringUtils.ofNullable(entity.getNnpAusPersstId()).map(StringUtils::strip).orElse(null));
        entity.setNnpFirmenname(StringUtils.ofNullable(entity.getNnpFirmenname()).map(StringUtils::strip).orElse(" "));
        entity.setNnpSteuernummer(
                StringUtils.ofNullable(entity.getNnpSteuernummer()).map(StringUtils::strip).orElse(null));
        entity.setNnpTyp(StringUtils.ofNullable(entity.getNnpTyp()).map(StringUtils::strip).orElse(null));
        entity.setNnpWid(StringUtils.ofNullable(entity.getNnpWid()).map(StringUtils::strip).orElse(null));
        entity.setNnwpAusPerstId(
                StringUtils.ofNullable(entity.getNnwpAusPerstId()).map(StringUtils::strip).orElse(null));
        entity.setNnwpFirmenname(
                StringUtils.ofNullable(entity.getNnwpFirmenname()).map(StringUtils::strip).orElse(null));
        entity.setNnwpTyp(StringUtils.ofNullable(entity.getNnwpTyp()).map(StringUtils::strip).orElse(null));
        entity.setNnwpWid(StringUtils.ofNullable(entity.getNnwpWid()).map(StringUtils::strip).orElse(null));
        entity.setNp1AuslPersstId(
                StringUtils.ofNullable(entity.getNp1AuslPersstId()).map(StringUtils::strip).orElse(null));
        entity.setNp1GebnameVors(
                StringUtils.ofNullable(entity.getNp1GebnameVors()).map(StringUtils::strip).orElse(null));
        entity.setNp1GebnameZus(StringUtils.ofNullable(entity.getNp1GebnameZus()).map(StringUtils::strip).orElse(null));
        entity.setNp1Geburtsland(
                StringUtils.ofNullable(entity.getNp1Geburtsland()).map(StringUtils::strip).orElse(null));
        entity.setNp1GeburtslandSl(
                StringUtils.ofNullable(entity.getNp1GeburtslandSl()).map(StringUtils::strip).orElse(null));
        entity.setNp1Geburtsname(
                StringUtils.ofNullable(entity.getNp1Geburtsname()).map(StringUtils::strip).orElse(null));
        entity.setNp1Geburtsort(StringUtils.ofNullable(entity.getNp1Geburtsort()).map(StringUtils::strip).orElse(null));
        entity.setNp1Geschlecht(StringUtils.ofNullable(entity.getNp1Geschlecht()).map(StringUtils::strip).orElse(null));
        entity.setNp1Idnr(StringUtils.ofNullable(entity.getNp1Idnr()).map(StringUtils::strip).orElse(null));
        entity.setNp1Name(StringUtils.ofNullable(entity.getNp1Name()).map(StringUtils::strip).orElse(" "));
        entity.setNp1Namensvorsatz(
                StringUtils.ofNullable(entity.getNp1Namensvorsatz()).map(StringUtils::strip).orElse(null));
        entity.setNp1Namenszusatz(
                StringUtils.ofNullable(entity.getNp1Namenszusatz()).map(StringUtils::strip).orElse(null));
        entity.setNp1Nationalitaet(
                StringUtils.ofNullable(entity.getNp1Nationalitaet()).map(StringUtils::strip).orElse(null));
        entity.setNp1Titel(StringUtils.ofNullable(entity.getNp1Titel()).map(StringUtils::strip).orElse(null));
        entity.setNp1Typ(StringUtils.ofNullable(entity.getNp1Typ()).map(StringUtils::strip).orElse(null));
        entity.setNp1Vorname(StringUtils.ofNullable(entity.getNp1Vorname()).map(StringUtils::strip).orElse(" "));
        entity.setNp2AuslPersstId(
                StringUtils.ofNullable(entity.getNp2AuslPersstId()).map(StringUtils::strip).orElse(null));
        entity.setNp2GebnameVors(
                StringUtils.ofNullable(entity.getNp2GebnameVors()).map(StringUtils::strip).orElse(null));
        entity.setNp2GebnameZus(StringUtils.ofNullable(entity.getNp2GebnameZus()).map(StringUtils::strip).orElse(null));
        entity.setNp2Geburtsland(
                StringUtils.ofNullable(entity.getNp2Geburtsland()).map(StringUtils::strip).orElse(null));
        entity.setNp2GeburtslandSl(
                StringUtils.ofNullable(entity.getNp2GeburtslandSl()).map(StringUtils::strip).orElse(null));
        entity.setNp2Geburtsname(
                StringUtils.ofNullable(entity.getNp2Geburtsname()).map(StringUtils::strip).orElse(null));
        entity.setNp2Geburtsort(StringUtils.ofNullable(entity.getNp2Geburtsort()).map(StringUtils::strip).orElse(null));
        entity.setNp2Geschlecht(StringUtils.ofNullable(entity.getNp2Geschlecht()).map(StringUtils::strip).orElse(null));
        entity.setNp2Idnr(StringUtils.ofNullable(entity.getNp2Idnr()).map(StringUtils::strip).orElse(null));
        entity.setNp2Name(StringUtils.ofNullable(entity.getNp2Name()).map(StringUtils::strip).orElse(null));
        entity.setNp2Namensvorsatz(
                StringUtils.ofNullable(entity.getNp2Namensvorsatz()).map(StringUtils::strip).orElse(null));
        entity.setNp2Namenszusatz(
                StringUtils.ofNullable(entity.getNp2Namenszusatz()).map(StringUtils::strip).orElse(null));
        entity.setNp2Nationalitaet(
                StringUtils.ofNullable(entity.getNp2Nationalitaet()).map(StringUtils::strip).orElse(null));
        entity.setNp2Titel(StringUtils.ofNullable(entity.getNp2Titel()).map(StringUtils::strip).orElse(null));
        entity.setNp2Typ(StringUtils.ofNullable(entity.getNp2Typ()).map(StringUtils::strip).orElse(null));
        entity.setNp2Vorname(StringUtils.ofNullable(entity.getNp2Vorname()).map(StringUtils::strip).orElse(null));
        entity.setNwpGebLandSchl(
                StringUtils.ofNullable(entity.getNwpGebLandSchl()).map(StringUtils::strip).orElse(null));
        entity.setNwpGebNameVors(
                StringUtils.ofNullable(entity.getNwpGebNameVors()).map(StringUtils::strip).orElse(null));
        entity.setNwpGebNameZus(StringUtils.ofNullable(entity.getNwpGebNameZus()).map(StringUtils::strip).orElse(null));
        entity.setNwpGeburtsland(
                StringUtils.ofNullable(entity.getNwpGeburtsland()).map(StringUtils::strip).orElse(null));
        entity.setNwpGeburtsname(
                StringUtils.ofNullable(entity.getNwpGeburtsname()).map(StringUtils::strip).orElse(null));
        entity.setNwpGeburtsort(StringUtils.ofNullable(entity.getNwpGeburtsort()).map(StringUtils::strip).orElse(null));
        entity.setNwpGeschlecht(StringUtils.ofNullable(entity.getNwpGeschlecht()).map(StringUtils::strip).orElse(null));
        entity.setNwpName(StringUtils.ofNullable(entity.getNwpName()).map(StringUtils::strip).orElse(null));
        entity.setNwpNamensvorsatz(
                StringUtils.ofNullable(entity.getNwpNamensvorsatz()).map(StringUtils::strip).orElse(null));
        entity.setNwpNamenszusatz(
                StringUtils.ofNullable(entity.getNwpNamenszusatz()).map(StringUtils::strip).orElse(null));
        entity.setNwpNationalitaet(
                StringUtils.ofNullable(entity.getNwpNationalitaet()).map(StringUtils::strip).orElse(null));
        entity.setNwpPersoninfo(StringUtils.ofNullable(entity.getNwpPersoninfo()).map(StringUtils::strip).orElse(null));
        entity.setNwpTitel(StringUtils.ofNullable(entity.getNwpTitel()).map(StringUtils::strip).orElse(null));
        entity.setNwpTyp(StringUtils.ofNullable(entity.getNwpTyp()).map(StringUtils::strip).orElse(null));
        entity.setNwpVorname(StringUtils.ofNullable(entity.getNwpVorname()).map(StringUtils::strip).orElse(null));
        entity.setOrdnungsbegriff(
                StringUtils.ofNullable(entity.getOrdnungsbegriff()).map(StringUtils::strip).orElse(null));
        entity.setOrt(StringUtils.ofNullable(entity.getOrt()).map(StringUtils::strip).orElse(" "));
        entity.setPartnerId(StringUtils.ofNullable(entity.getPartnerId()).map(StringUtils::strip).orElse(null));
        entity.setPersonId(StringUtils.ofNullable(entity.getPersonId()).map(StringUtils::strip).orElse(" "));
        entity.setPersonRolle(StringUtils.ofNullable(entity.getPersonRolle()).map(StringUtils::strip).orElse(" "));
        entity.setPfWohnort(StringUtils.ofNullable(entity.getPfWohnort()).map(StringUtils::strip).orElse(null));
        entity.setRefKmId(StringUtils.ofNullable(entity.getRefKmId()).map(StringUtils::strip).orElse(null));
        entity.setRolleWeiterePers(
                StringUtils.ofNullable(entity.getRolleWeiterePers()).map(StringUtils::strip).orElse(null));
        entity.setStaatSchl(StringUtils.ofNullable(entity.getStaatSchl()).map(StringUtils::strip).orElse(null));
        entity.setStatus(StringUtils.ofNullable(entity.getStatus()).map(StringUtils::strip).orElse(" "));
        entity.setStatusKommentar(
                StringUtils.ofNullable(entity.getStatusKommentar()).map(StringUtils::strip).orElse(null));
        entity.setSteuerZuord(StringUtils.ofNullable(entity.getSteuerZuord()).map(StringUtils::strip).orElse(" "));
        entity.setSteuerauslaender(
                StringUtils.ofNullable(entity.getSteuerauslaender()).map(StringUtils::strip).orElse(" "));
        entity.setStornoKz(StringUtils.ofNullable(entity.getStornoKz()).map(StringUtils::strip).orElse(" "));
        entity.setStrasse(StringUtils.ofNullable(entity.getStrasse()).map(StringUtils::strip).orElse(" "));
        entity.setVerfAdrErg(StringUtils.ofNullable(entity.getVerfAdrErg()).map(StringUtils::strip).orElse(null));
        entity.setVerfBic(StringUtils.ofNullable(entity.getVerfBic()).map(StringUtils::strip).orElse(null));
        entity.setVerfEmail(StringUtils.ofNullable(entity.getVerfEmail()).map(StringUtils::strip).orElse(null));
        entity.setVerfGkOrt(StringUtils.ofNullable(entity.getVerfGkOrt()).map(StringUtils::strip).orElse(null));
        entity.setVerfHszu(StringUtils.ofNullable(entity.getVerfHszu()).map(StringUtils::strip).orElse(null));
        entity.setVerfName(StringUtils.ofNullable(entity.getVerfName()).map(StringUtils::strip).orElse(" "));
        entity.setVerfOrdBegriff(
                StringUtils.ofNullable(entity.getVerfOrdBegriff()).map(StringUtils::strip).orElse(null));
        entity.setVerfOrt(StringUtils.ofNullable(entity.getVerfOrt()).map(StringUtils::strip).orElse(" "));
        entity.setVerfPfOrt(StringUtils.ofNullable(entity.getVerfPfOrt()).map(StringUtils::strip).orElse(null));
        entity.setVerfStr(StringUtils.ofNullable(entity.getVerfStr()).map(StringUtils::strip).orElse(" "));
        entity.setVerfTel(StringUtils.ofNullable(entity.getVerfTel()).map(StringUtils::strip).orElse(null));
        entity.setWeitereErlaueterg(
                StringUtils.ofNullable(entity.getWeitereErlaueterg()).map(StringUtils::strip).orElse(null));
        entity.setWpAdressergaenz(
                StringUtils.ofNullable(entity.getWpAdressergaenz()).map(StringUtils::strip).orElse(null));
        entity.setWpAuslandsPlz(StringUtils.ofNullable(entity.getWpAuslandsPlz()).map(StringUtils::strip).orElse(null));
        entity.setWpHausnummerZus(
                StringUtils.ofNullable(entity.getWpHausnummerZus()).map(StringUtils::strip).orElse(null));
        entity.setWpInfo(StringUtils.ofNullable(entity.getWpInfo()).map(StringUtils::strip).orElse(null));
        entity.setWpLandWP(StringUtils.ofNullable(entity.getWpLandWP()).map(StringUtils::strip).orElse(null));
        entity.setWpOrt(StringUtils.ofNullable(entity.getWpOrt()).map(StringUtils::strip).orElse(null));
        entity.setWpStaatSchl(StringUtils.ofNullable(entity.getWpStaatSchl()).map(StringUtils::strip).orElse(null));
        entity.setWpStrasse(StringUtils.ofNullable(entity.getWpStrasse()).map(StringUtils::strip).orElse(null));
        entity.setWpTyp(StringUtils.ofNullable(entity.getWpTyp()).map(StringUtils::strip).orElse(null));
        entity.setKontoId(StringUtils.ofNullable(entity.getKontoId()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm1(TrTableBm1 entity) {
        if (entity == null) {
            return;
        }
        entity.setAamVeraeussert(
                StringUtils.ofNullable(entity.getAamVeraeussert()).map(StringUtils::strip).orElse(null));
        entity.setAkeVeraeussert(
                StringUtils.ofNullable(entity.getAkeVeraeussert()).map(StringUtils::strip).orElse(null));
        entity.setAulsKiGut(StringUtils.ofNullable(entity.getAulsKiGut()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiAntr(StringUtils.ofNullable(entity.getAuslKiAntr()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiNamStadt(
                StringUtils.ofNullable(entity.getAuslKiNamStadt()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiVerw(StringUtils.ofNullable(entity.getAuslKiVerw()).map(StringUtils::strip).orElse(null));
        entity.setDepReIsin(StringUtils.ofNullable(entity.getDepReIsin()).map(StringUtils::strip).orElse(null));
        entity.setEikErstattet(StringUtils.ofNullable(entity.getEikErstattet()).map(StringUtils::strip).orElse(null));
        entity.setFehlerKennz(StringUtils.ofNullable(entity.getFehlerKennz()).map(StringUtils::strip).orElse(null));
        entity.setFormular(StringUtils.ofNullable(entity.getFormular()).map(StringUtils::strip).orElse(null));
        entity.setInlKiVerw(StringUtils.ofNullable(entity.getInlKiVerw()).map(StringUtils::strip).orElse(null));
        entity.setKeinOriginalM1(
                StringUtils.ofNullable(entity.getKeinOriginalM1()).map(StringUtils::strip).orElse(null));
        entity.setLiefertiefe(StringUtils.ofNullable(entity.getLiefertiefe()).map(StringUtils::strip).orElse(null));
        entity.setOrdnungsnummer(
                StringUtils.ofNullable(entity.getOrdnungsnummer()).map(StringUtils::strip).orElse(null));
        entity.setPivVerwahrt(StringUtils.ofNullable(entity.getPivVerwahrt()).map(StringUtils::strip).orElse(null));
        entity.setPrivat(StringUtils.ofNullable(entity.getPrivat()).map(StringUtils::strip).orElse(null));
        entity.setSteuerbeschDv(StringUtils.ofNullable(entity.getSteuerbeschDv()).map(StringUtils::strip).orElse(null));
        entity.setSteuerlEinlagekto(
                StringUtils.ofNullable(entity.getSteuerlEinlagekto()).map(StringUtils::strip).orElse(null));
        entity.setStornierung(StringUtils.ofNullable(entity.getStornierung()).map(StringUtils::strip).orElse(null));
        entity.setVerlustbesch(StringUtils.ofNullable(entity.getVerlustbesch()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm1PK(TrTableBm1PK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm3PK(TrTableBm3PK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm3(TrTableBm3 entity) {
        if (entity == null) {
            return;
        }
        entity.setAbstandSteuerabz(
                StringUtils.ofNullable(entity.getAbstandSteuerabz()).map(StringUtils::strip).orElse(null));
        entity.setAkbVeraeussert(
                StringUtils.ofNullable(entity.getAkbVeraeussert()).map(StringUtils::strip).orElse(null));
        entity.setAkeVeraeussert(
                StringUtils.ofNullable(entity.getAkeVeraeussert()).map(StringUtils::strip).orElse(null));
        entity.setAulsKiGut(StringUtils.ofNullable(entity.getAulsKiGut()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiAntr(StringUtils.ofNullable(entity.getAuslKiAntr()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiNamStadt(
                StringUtils.ofNullable(entity.getAuslKiNamStadt()).map(StringUtils::strip).orElse(null));
        entity.setAuslKiVerw(StringUtils.ofNullable(entity.getAuslKiVerw()).map(StringUtils::strip).orElse(null));
        entity.setAuslSpezInv(StringUtils.ofNullable(entity.getAuslSpezInv()).map(StringUtils::strip).orElse(null));
        entity.setDepReIsin(StringUtils.ofNullable(entity.getDepReIsin()).map(StringUtils::strip).orElse(null));
        entity.setEikErstattet(StringUtils.ofNullable(entity.getEikErstattet()).map(StringUtils::strip).orElse(null));
        entity.setEstb(StringUtils.ofNullable(entity.getEstb()).map(StringUtils::strip).orElse(null));
        entity.setFehlerKennz(StringUtils.ofNullable(entity.getFehlerKennz()).map(StringUtils::strip).orElse(null));
        entity.setFormular(StringUtils.ofNullable(entity.getFormular()).map(StringUtils::strip).orElse(null));
        entity.setInlKiVerw(StringUtils.ofNullable(entity.getInlKiVerw()).map(StringUtils::strip).orElse(null));
        entity.setInvVorhanden(StringUtils.ofNullable(entity.getInvVorhanden()).map(StringUtils::strip).orElse(null));
        entity.setKStabzugP43Nr1(
                StringUtils.ofNullable(entity.getKStabzugP43Nr1()).map(StringUtils::strip).orElse(null));
        entity.setKeinOriginalM3(
                StringUtils.ofNullable(entity.getKeinOriginalM3()).map(StringUtils::strip).orElse(null));
        entity.setLiefertiefe(StringUtils.ofNullable(entity.getLiefertiefe()).map(StringUtils::strip).orElse(null));
        entity.setOrdnungsnummer(
                StringUtils.ofNullable(entity.getOrdnungsnummer()).map(StringUtils::strip).orElse(null));
        entity.setPivVerwahrt(StringUtils.ofNullable(entity.getPivVerwahrt()).map(StringUtils::strip).orElse(null));
        entity.setSdBezugWP(StringUtils.ofNullable(entity.getSdBezugWP()).map(StringUtils::strip).orElse(null));
        entity.setSdIsin(StringUtils.ofNullable(entity.getSdIsin()).map(StringUtils::strip).orElse(null));
        entity.setSdWkn(StringUtils.ofNullable(entity.getSdWkn()).map(StringUtils::strip).orElse(null));
        entity.setSfAnlBezugWP(StringUtils.ofNullable(entity.getSfAnlBezugWP()).map(StringUtils::strip).orElse(null));
        entity.setSfBetEinnahm(StringUtils.ofNullable(entity.getSfBetEinnahm()).map(StringUtils::strip).orElse(null));
        entity.setSfBezeichn(StringUtils.ofNullable(entity.getSfBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setSfIsin(StringUtils.ofNullable(entity.getSfIsin()).map(StringUtils::strip).orElse(null));
        entity.setSfSchuldBezWp(StringUtils.ofNullable(entity.getSfSchuldBezWp()).map(StringUtils::strip).orElse(null));
        entity.setSfSonsEink(StringUtils.ofNullable(entity.getSfSonsEink()).map(StringUtils::strip).orElse(null));
        entity.setSfWkn(StringUtils.ofNullable(entity.getSfWkn()).map(StringUtils::strip).orElse(null));
        entity.setStornierung(StringUtils.ofNullable(entity.getStornierung()).map(StringUtils::strip).orElse(null));
        entity.setZusGefassteStb(
                StringUtils.ofNullable(entity.getZusGefassteStb()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableAamPK(TrTableAamPK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));
        entity.setKeyAbrechNr(StringUtils.ofNullable(entity.getKeyAbrechNr()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm1Aam(TrTableBm1Aam entity) {
        if (entity == null) {
            return;
        }
        entity.setAamAntBezeichn(
                StringUtils.ofNullable(entity.getAamAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAamAntGewNiErm(
                StringUtils.ofNullable(entity.getAamAntGewNiErm()).map(StringUtils::strip).orElse(null));
        entity.setAamAntIsin(StringUtils.ofNullable(entity.getAamAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm3Aam(TrTableBm3Aam entity) {
        if (entity == null) {
            return;
        }
        entity.setAamAntBezeichn(
                StringUtils.ofNullable(entity.getAamAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAamAntGewNiErm(
                StringUtils.ofNullable(entity.getAamAntGewNiErm()).map(StringUtils::strip).orElse(null));
        entity.setAamAntIsin(StringUtils.ofNullable(entity.getAamAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm3Akb(TrTableBm3Akb entity) {
        if (entity == null) {
            return;
        }
        entity.setAkbAntBezeichn(
                StringUtils.ofNullable(entity.getAkbAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAkbAntIsin(StringUtils.ofNullable(entity.getAkbAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm1Akb(TrTableBm1Akb entity) {
        if (entity == null) {
            return;
        }
        entity.setAkbAntBezeichn(
                StringUtils.ofNullable(entity.getAkbAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAkbAntIsin(StringUtils.ofNullable(entity.getAkbAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableAkbPK(TrTableAkbPK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));
        entity.setKeyAbrechNr(StringUtils.ofNullable(entity.getKeyAbrechNr()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableAkePK(TrTableAkePK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));
        entity.setKeyAbrechNr(StringUtils.ofNullable(entity.getKeyAbrechNr()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm3Ake(TrTableBm3Ake entity) {
        if (entity == null) {
            return;
        }
        entity.setAkeAntBezeichn(
                StringUtils.ofNullable(entity.getAkeAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAkeAntEbmgNErm(
                StringUtils.ofNullable(entity.getAkeAntEbmgNErm()).map(StringUtils::strip).orElse(null));
        entity.setAkeAntIsin(StringUtils.ofNullable(entity.getAkeAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm1Ake(TrTableBm1Ake entity) {
        if (entity == null) {
            return;
        }
        entity.setAkeAntBezeichn(
                StringUtils.ofNullable(entity.getAkeAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setAkeAntEbmgNErm(
                StringUtils.ofNullable(entity.getAkeAntEbmgNErm()).map(StringUtils::strip).orElse(null));
        entity.setAkeAntIsin(StringUtils.ofNullable(entity.getAkeAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableEikPK(TrTableEikPK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));
        entity.setKeyAbrechNr(StringUtils.ofNullable(entity.getKeyAbrechNr()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm1Eik(TrTableBm1Eik entity) {
        if (entity == null) {
            return;
        }
        entity.setEikAntBezeichn(
                StringUtils.ofNullable(entity.getEikAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setEikAntIsin(StringUtils.ofNullable(entity.getEikAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm3Eik(TrTableBm3Eik entity) {
        if (entity == null) {
            return;
        }
        entity.setEikAntBezeichn(
                StringUtils.ofNullable(entity.getEikAntBezeichn()).map(StringUtils::strip).orElse(null));
        entity.setEikAntIsin(StringUtils.ofNullable(entity.getEikAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTablePivPK(TrTablePivPK entity) {
        if (entity == null) {
            return;
        }
        entity.setMandSl(StringUtils.ofNullable(entity.getMandSl()).map(StringUtils::strip).orElse(" "));
        entity.setKeyIdNr(StringUtils.ofNullable(entity.getKeyIdNr()).map(StringUtils::strip).orElse(" "));
        entity.setKeyMuster(StringUtils.ofNullable(entity.getKeyMuster()).map(StringUtils::strip).orElse(" "));
        entity.setKeySatzart(StringUtils.ofNullable(entity.getKeySatzart()).map(StringUtils::strip).orElse(" "));
        entity.setKeyAbrechNr(StringUtils.ofNullable(entity.getKeyAbrechNr()).map(StringUtils::strip).orElse(" "));

    }

    private void cleanTrTableBm3Piv(TrTableBm3Piv entity) {
        if (entity == null) {
            return;
        }
        entity.setPivAntBezeichng(
                StringUtils.ofNullable(entity.getPivAntBezeichng()).map(StringUtils::strip).orElse(null));
        entity.setPivAntIsin(StringUtils.ofNullable(entity.getPivAntIsin()).map(StringUtils::strip).orElse(null));

    }

    private void cleanTrTableBm1Piv(TrTableBm1Piv entity) {
        if (entity == null) {
            return;
        }
        entity.setPivAntBezeichng(
                StringUtils.ofNullable(entity.getPivAntBezeichng()).map(StringUtils::strip).orElse(null));
        entity.setPivAntIsin(StringUtils.ofNullable(entity.getPivAntIsin()).map(StringUtils::strip).orElse(null));

    }
}
