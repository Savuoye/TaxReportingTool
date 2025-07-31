package com.fisglobal.taxreporting.util;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm3Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm3Piv;


public class FieldsCopy {
    public void copyAllTrTableBsbFields(TrTableBsb source, TrTableBsb destination) {
        destination.setAdressergaenzung(source.getAdressergaenzung());
        destination.setAenderDatum(source.getAenderDatum());
        destination.setAenderErfasser(source.getAenderErfasser());
        destination.setAenderZeit(source.getAenderZeit());
        destination.setAnlageDatum(source.getAnlageDatum());
        destination.setAnlageErfasser(source.getAnlageErfasser());
        destination.setAnlageZeit(source.getAnlageZeit());
        destination.setAnlass(source.getAnlass());
        destination.setAntwortFehlerNr(source.getAntwortFehlerNr());
        destination.setAntwortStatus(source.getAntwortStatus());
        destination.setAnweisungsart(source.getAnweisungsart());
        destination.setArt(source.getArt());
        destination.setAufnehmAdrErg(source.getAufnehmAdrErg());
        destination.setAufnehmAuslPlz(source.getAufnehmAuslPlz());
        destination.setAufnehmBic(source.getAufnehmBic());
        destination.setAufnehmEmail(source.getAufnehmEmail());
        destination.setAufnehmGkOrt(source.getAufnehmGkOrt());
        destination.setAufnehmGkPlz(source.getAufnehmGkPlz());
        destination.setAufnehmHsnr(source.getAufnehmHsnr());
        destination.setAufnehmHszu(source.getAufnehmHszu());
        destination.setAufnehmLand(source.getAufnehmLand());
        destination.setAufnehmName(source.getAufnehmName());
        destination.setAufnehmOrdnBegr(source.getAufnehmOrdnBegr());
        destination.setAufnehmOrt(source.getAufnehmOrt());
        destination.setAufnehmPfNr(source.getAufnehmPfNr());
        destination.setAufnehmPfOrt(source.getAufnehmPfOrt());
        destination.setAufnehmPfPlz(source.getAufnehmPfPlz());
        destination.setAufnehmPlz(source.getAufnehmPlz());
        destination.setAufnehmStaatSl(source.getAufnehmStaatSl());
        destination.setAufnehmStr(source.getAufnehmStr());
        destination.setAufnehmTel(source.getAufnehmTel());
        destination.setAuslandsplz(source.getAuslandsplz());
        destination.setAusstellungsdat(source.getAusstellungsdat());
        destination.setBedbsbTimestamp(source.getBedbsbTimestamp());
        destination.setDokuArt(source.getDokuArt());
        destination.setEhegLdnr(source.getEhegLdnr());
        destination.setErgaenzendeStb(source.getErgaenzendeStb());
        destination.setFAdrInfo(source.getFAdrInfo());
        destination.setFAdressergaenzung(source.getFAdressergaenzung());
        destination.setFAuslandsPlz(source.getFAuslandsPlz());
        destination.setFGkPlz(source.getFGkPlz());
        destination.setFGkWohnort(source.getFGkWohnort());
        destination.setFHausnummer(source.getFHausnummer());
        destination.setFHausnummerZus(source.getFHausnummerZus());
        destination.setFLand(source.getFLand());
        destination.setFOrt(source.getFOrt());
        destination.setFPfOrt(source.getFPfOrt());
        destination.setFPfPlz(source.getFPfPlz());
        destination.setFPlz(source.getFPlz());
        destination.setFPostfach(source.getFPostfach());
        destination.setFStaatSchl(source.getFStaatSchl());
        destination.setFStrasse(source.getFStrasse());
        destination.setFTyp(source.getFTyp());
        destination.setGkOrt(source.getGkOrt());
        destination.setGkPlz(source.getGkPlz());
        destination.setHausnummer(source.getHausnummer());
        destination.setHausnummerZus(source.getHausnummerZus());
        destination.setKmId(source.getKmId());
        destination.setLand(source.getLand());
        destination.setMeldeStatus(source.getMeldeStatus());
        destination.setMeldejahr(source.getMeldejahr());
        destination.setMeldungJjjjmmtt(source.getMeldungJjjjmmtt());
        destination.setMeldungUhrHms(source.getMeldungUhrHms());
        destination.setNdNrLfd(source.getNdNrLfd());
        destination.setNdTicket(source.getNdTicket());
        destination.setNnpAusPersstId(source.getNnpAusPersstId());
        destination.setNnpFirmenname(source.getNnpFirmenname());
        destination.setNnpSteuernummer(source.getNnpSteuernummer());
        destination.setNnpTyp(source.getNnpTyp());
        destination.setNnpWid(source.getNnpWid());
        destination.setNnwpAusPerstId(source.getNnwpAusPerstId());
        destination.setNnwpFirmenname(source.getNnwpFirmenname());
        destination.setNnwpSteuernummer(source.getNnwpSteuernummer());
        destination.setNnwpTyp(source.getNnwpTyp());
        destination.setNnwpWid(source.getNnwpWid());
        destination.setNp1AuslPersstId(source.getNp1AuslPersstId());
        destination.setNp1AuswandDatum(source.getNp1AuswandDatum());
        destination.setNp1Gebdat(source.getNp1Gebdat());
        destination.setNp1GebnameVors(source.getNp1GebnameVors());
        destination.setNp1GebnameZus(source.getNp1GebnameZus());
        destination.setNp1Geburtsland(source.getNp1Geburtsland());
        destination.setNp1GeburtslandSl(source.getNp1GeburtslandSl());
        destination.setNp1Geburtsname(source.getNp1Geburtsname());
        destination.setNp1Geburtsort(source.getNp1Geburtsort());
        destination.setNp1Geschlecht(source.getNp1Geschlecht());
        destination.setNp1Idnr(source.getNp1Idnr());
        destination.setNp1Name(source.getNp1Name());
        destination.setNp1Namensvorsatz(source.getNp1Namensvorsatz());
        destination.setNp1Namenszusatz(source.getNp1Namenszusatz());
        destination.setNp1Nationalitaet(source.getNp1Nationalitaet());
        destination.setNp1SterbeDatum(source.getNp1SterbeDatum());
        destination.setNp1Titel(source.getNp1Titel());
        destination.setNp1Typ(source.getNp1Typ());
        destination.setNp1Vorname(source.getNp1Vorname());
        destination.setNp2AuslPersstId(source.getNp2AuslPersstId());
        destination.setNp2AuswandDatum(source.getNp2AuswandDatum());
        destination.setNp2Gebdat(source.getNp2Gebdat());
        destination.setNp2GebnameVors(source.getNp2GebnameVors());
        destination.setNp2GebnameZus(source.getNp2GebnameZus());
        destination.setNp2Geburtsland(source.getNp2Geburtsland());
        destination.setNp2GeburtslandSl(source.getNp2GeburtslandSl());
        destination.setNp2Geburtsname(source.getNp2Geburtsname());
        destination.setNp2Geburtsort(source.getNp2Geburtsort());
        destination.setNp2Geschlecht(source.getNp2Geschlecht());
        destination.setNp2Idnr(source.getNp2Idnr());
        destination.setNp2Name(source.getNp2Name());
        destination.setNp2Namensvorsatz(source.getNp2Namensvorsatz());
        destination.setNp2Namenszusatz(source.getNp2Namenszusatz());
        destination.setNp2Nationalitaet(source.getNp2Nationalitaet());
        destination.setNp2SterbeDatum(source.getNp2SterbeDatum());
        destination.setNp2Titel(source.getNp2Titel());
        destination.setNp2Typ(source.getNp2Typ());
        destination.setNp2Vorname(source.getNp2Vorname());
        destination.setNwpAuswandDatum(source.getNwpAuswandDatum());
        destination.setNwpGebLandSchl(source.getNwpGebLandSchl());
        destination.setNwpGebNameVors(source.getNwpGebNameVors());
        destination.setNwpGebNameZus(source.getNwpGebNameZus());
        destination.setNwpGeburtsdatum(source.getNwpGeburtsdatum());
        destination.setNwpGeburtsland(source.getNwpGeburtsland());
        destination.setNwpGeburtsname(source.getNwpGeburtsname());
        destination.setNwpGeburtsort(source.getNwpGeburtsort());
        destination.setNwpGeschlecht(source.getNwpGeschlecht());
        destination.setNwpName(source.getNwpName());
        destination.setNwpNamensvorsatz(source.getNwpNamensvorsatz());
        destination.setNwpNamenszusatz(source.getNwpNamenszusatz());
        destination.setNwpNationalitaet(source.getNwpNationalitaet());
        destination.setNwpPersoninfo(source.getNwpPersoninfo());
        destination.setNwpSterbeDatum(source.getNwpSterbeDatum());
        destination.setNwpTin(source.getNwpTin());
        destination.setNwpTitel(source.getNwpTitel());
        destination.setNwpTyp(source.getNwpTyp());
        destination.setNwpVorname(source.getNwpVorname());
        destination.setOrdnungsbegriff(source.getOrdnungsbegriff());
        destination.setOrt(source.getOrt());
        destination.setPartnerId(source.getPartnerId());
        destination.setPersonId(source.getPersonId());
        destination.setPersonRolle(source.getPersonRolle());
        destination.setPfPlz(source.getPfPlz());
        destination.setPfWohnort(source.getPfWohnort());
        destination.setPlz(source.getPlz());
        destination.setPostfach(source.getPostfach());
        destination.setRefKmId(source.getRefKmId());
        destination.setRolleWeiterePers(source.getRolleWeiterePers());
        destination.setStaatSchl(source.getStaatSchl());
        destination.setStatus(source.getStatus());
        destination.setStatusKommentar(source.getStatusKommentar());
        destination.setSteuerZuord(source.getSteuerZuord());
        destination.setSteuerauslaender(source.getSteuerauslaender());
        destination.setStornoKz(source.getStornoKz());
        destination.setStrasse(source.getStrasse());
        destination.setUpdateCheck(source.getUpdateCheck());
        destination.setVerfAdrErg(source.getVerfAdrErg());
        destination.setVerfBic(source.getVerfBic());
        destination.setVerfEmail(source.getVerfEmail());
        destination.setVerfGkOrt(source.getVerfGkOrt());
        destination.setVerfGkPlz(source.getVerfGkPlz());
        destination.setVerfHsnr(source.getVerfHsnr());
        destination.setVerfHszu(source.getVerfHszu());
        destination.setVerfName(source.getVerfName());
        destination.setVerfOrdBegriff(source.getVerfOrdBegriff());
        destination.setVerfOrt(source.getVerfOrt());
        destination.setVerfPfNr(source.getVerfPfNr());
        destination.setVerfPfOrt(source.getVerfPfOrt());
        destination.setVerfPfPlz(source.getVerfPfPlz());
        destination.setVerfPlz(source.getVerfPlz());
        destination.setVerfStr(source.getVerfStr());
        destination.setVerfTel(source.getVerfTel());
        destination.setVersion(source.getVersion());
        destination.setVersionDetail(source.getVersionDetail());
        destination.setWeitereErlaueterg(source.getWeitereErlaueterg());
        destination.setWpAdressergaenz(source.getWpAdressergaenz());
        destination.setWpAuslandsPlz(source.getWpAuslandsPlz());
        destination.setWpHausnummer(source.getWpHausnummer());
        destination.setWpHausnummerZus(source.getWpHausnummerZus());
        destination.setWpInfo(source.getWpInfo());
        destination.setWpLandWP(source.getWpLandWP());
        destination.setWpOrt(source.getWpOrt());
        destination.setWpPlz(source.getWpPlz());
        destination.setWpStaatSchl(source.getWpStaatSchl());
        destination.setWpStrasse(source.getWpStrasse());
        destination.setWpTyp(source.getWpTyp());
        destination.setKontoId(source.getKontoId());
        destination.setLebendKz(source.getLebendKz());
    }

    public void copyAllTrTableBm1Fields(TrTableBm1 source, TrTableBm1 destination) {
        destination.setAamVeraeussert(source.getAamVeraeussert());
        destination.setAkeVeraeussert(source.getAkeVeraeussert());
        destination.setAktienErtrGew(source.getAktienErtrGew());
        destination.setAngerechAuslSteu(source.getAngerechAuslSteu());
        destination.setAnrechbAuslSteu(source.getAnrechbAuslSteu());
        destination.setAnzahlAam(source.getAnzahlAam());
        destination.setAnzahlAke(source.getAnzahlAke());
        destination.setAnzahlEik(source.getAnzahlEik());
        destination.setAnzahlPiv(source.getAnzahlPiv());
        destination.setAulsKiGut(source.getAulsKiGut());
        destination.setAuslInvFonds(source.getAuslInvFonds());
        destination.setAuslKiAntr(source.getAuslKiAntr());
        destination.setAuslKiNamStadt(source.getAuslKiNamStadt());
        destination.setAuslKiVerw(source.getAuslKiVerw());
        destination.setDepReAnzBesch(source.getDepReAnzBesch());
        destination.setDepReGesamtzahl(source.getDepReGesamtzahl());
        destination.setDepReIsin(source.getDepReIsin());
        destination.setEikErstattet(source.getEikErstattet());
        destination.setEntschaedigung(source.getEntschaedigung());
        destination.setErsatzBmg(source.getErsatzBmg());
        destination.setFehlerKennz(source.getFehlerKennz());
        destination.setFormular(source.getFormular());
        destination.setGewAktVorSon(source.getGewAktVorSon());
        destination.setGewAltanteile(source.getGewAltanteile());
        destination.setInlKiVerw(source.getInlKiVerw());
        destination.setKapitalertrag(source.getKapitalertrag());
        destination.setKapstAbzug(source.getKapstAbzug());
        destination.setKeinOriginalM1(source.getKeinOriginalM1());
        destination.setKistAbzug(source.getKistAbzug());
        destination.setKistKonf(source.getKistKonf());
        destination.setLiefertiefe(source.getLiefertiefe());
        destination.setOrdnungsnummer(source.getOrdnungsnummer());
        destination.setP2KistAbzug(source.getP2KistAbzug());
        destination.setP2KistKonf(source.getP2KistKonf());
        destination.setPivVerwahrt(source.getPivVerwahrt());
        destination.setPrivat(source.getPrivat());
        destination.setSolzAbzug(source.getSolzAbzug());
        destination.setStbJahr(source.getStbJahr());
        destination.setSteuerbeschDv(source.getSteuerbeschDv());
        destination.setSteuerlEinlagekto(source.getSteuerlEinlagekto());
        destination.setStillhVorSon(source.getStillhVorSon());
        destination.setStillhalterTermin(source.getStillhalterTermin());
        destination.setStornierung(source.getStornierung());
        destination.setVerbrSparerpausch(source.getVerbrSparerpausch());
        destination.setVerlTermingesch(source.getVerlTermingesch());
        destination.setVerlWertloUneinb(source.getVerlWertloUneinb());
        destination.setVerlustAkt(source.getVerlustAkt());
        destination.setVerlustOhneAkt(source.getVerlustOhneAkt());
        destination.setVerlustbesch(source.getVerlustbesch());
        destination.setZahlungstag(source.getZahlungstag());
        destination.setZeitraumBis(source.getZeitraumBis());
        destination.setZeitraumVon(source.getZeitraumVon());
    }

    public void copyAllTrTableBm3Fields(TrTableBm3 source, TrTableBm3 destination) {
        destination.setAbstandSteuerabz(source.getAbstandSteuerabz());
        destination.setAbzDreiFueP433(source.getAbzDreiFueP433());
        destination.setAkbVeraeussert(source.getAkbVeraeussert());
        destination.setAkeVeraeussert(source.getAkeVeraeussert());
        destination.setAnzahlAkb(source.getAnzahlAkb());
        destination.setAnzahlAke(source.getAnzahlAke());
        destination.setAnzahlEik(source.getAnzahlEik());
        destination.setAnzahlPiv(source.getAnzahlPiv());
        destination.setAulsKiGut(source.getAulsKiGut());
        destination.setAuslInvKStabzug(source.getAuslInvKStabzug());
        destination.setAuslKiAntr(source.getAuslKiAntr());
        destination.setAuslKiNamStadt(source.getAuslKiNamStadt());
        destination.setAuslKiVerw(source.getAuslKiVerw());
        destination.setAuslSpezInv(source.getAuslSpezInv());
        destination.setDepReAnzBesch(source.getDepReAnzBesch());
        destination.setDepReGesamtzahl(source.getDepReGesamtzahl());
        destination.setDepReIsin(source.getDepReIsin());
        destination.setEikErstattet(source.getEikErstattet());
        destination.setEinlagekontoSum(source.getEinlagekontoSum());
        destination.setErsatzBmg(source.getErsatzBmg());
        destination.setErtBeschrStpfl1(source.getErtBeschrStpfl1());
        destination.setErtBeschrStpfl2(source.getErtBeschrStpfl2());
        destination.setErtragP19Reit1(source.getErtragP19Reit1());
        destination.setErtragP19Reit6(source.getErtragP19Reit6());
        destination.setErtragP431(source.getErtragP431());
        destination.setErtragP432(source.getErtragP432());
        destination.setErtragP433(source.getErtragP433());
        destination.setErtragP434(source.getErtragP434());
        destination.setErtragP435(source.getErtragP435());
        destination.setErtragP436(source.getErtragP436());
        destination.setErtragP437(source.getErtragP437());
        destination.setErtragP438(source.getErtragP438());
        destination.setErtragP439(source.getErtragP439());
        destination.setErtragTevP431(source.getErtragTevP431());
        destination.setErtragTevP436(source.getErtragTevP436());
        destination.setErtragTevP439(source.getErtragTevP439());
        destination.setEstb(source.getEstb());
        destination.setFehlerKennz(source.getFehlerKennz());
        destination.setFormular(source.getFormular());
        destination.setGewinnVerInv(source.getGewinnVerInv());
        destination.setInlKiVerw(source.getInlKiVerw());
        destination.setInv1612Aimmf(source.getInv1612Aimmf());
        destination.setInv1612AimmfVp(source.getInv1612AimmfVp());
        destination.setInv1612Aktf(source.getInv1612Aktf());
        destination.setInv1612AktfVp(source.getInv1612AktfVp());
        destination.setInv1612Immf(source.getInv1612Immf());
        destination.setInv1612ImmfVp(source.getInv1612ImmfVp());
        destination.setInv1612Misf(source.getInv1612Misf());
        destination.setInv1612MisfVp(source.getInv1612MisfVp());
        destination.setInv1612Sonf(source.getInv1612Sonf());
        destination.setInv1612SonfVp(source.getInv1612SonfVp());
        destination.setInv163Aimmf(source.getInv163Aimmf());
        destination.setInv163Aktf(source.getInv163Aktf());
        destination.setInv163Immf(source.getInv163Immf());
        destination.setInv163Misf(source.getInv163Misf());
        destination.setInv163Sonf(source.getInv163Sonf());
        destination.setInvErt1612(source.getInvErt1612());
        destination.setInvErt163(source.getInvErt163());
        destination.setInvVorhanden(source.getInvVorhanden());
        destination.setKStabzugP43Nr1(source.getKStabzugP43Nr1());
        destination.setKStabzugP43Nr5(source.getKStabzugP43Nr5());
        destination.setKapDreiFueP433(source.getKapDreiFueP433());
        destination.setKapest(source.getKapest());
        destination.setKeinOriginalM3(source.getKeinOriginalM3());
        destination.setLiefertiefe(source.getLiefertiefe());
        destination.setOrdnungsnummer(source.getOrdnungsnummer());
        destination.setPivVerwahrt(source.getPivVerwahrt());
        destination.setSdBezugWP(source.getSdBezugWP());
        destination.setSdIsin(source.getSdIsin());
        destination.setSdWkn(source.getSdWkn());
        destination.setSfAnlAnzahl(source.getSfAnlAnzahl());
        destination.setSfAnlBezugWP(source.getSfAnlBezugWP());
        destination.setSfAnlKapest(source.getSfAnlKapest());
        destination.setSfAnlSolz(source.getSfAnlSolz());
        destination.setSfAnteile(source.getSfAnteile());
        destination.setSfBetEinnahm(source.getSfBetEinnahm());
        destination.setSfBetrag(source.getSfBetrag());
        destination.setSfBezeichn(source.getSfBezeichn());
        destination.setSfDatum(source.getSfDatum());
        destination.setSfIsin(source.getSfIsin());
        destination.setSfSchuldBezWp(source.getSfSchuldBezWp());
        destination.setSfSonsEink(source.getSfSonsEink());
        destination.setSfWkn(source.getSfWkn());
        destination.setSolz(source.getSolz());
        destination.setStornierung(source.getStornierung());
        destination.setZahlungstag(source.getZahlungstag());
        destination.setZahlungszeitrBis(source.getZahlungszeitrBis());
        destination.setZahlungszeitrVon(source.getZahlungszeitrVon());
        destination.setZusGefassteStb(source.getZusGefassteStb());
        destination.setZusZeitraumBis(source.getZusZeitraumBis());
        destination.setZusZeitraumVon(source.getZusZeitraumVon());
        destination.setAbzDreiFueP431(source.getAbzDreiFueP431());
        destination.setKapDreiFueP431(source.getKapDreiFueP431());
    }

    public void copyAllTrTableBm1AamFields(TrTableBm1Aam source, TrTableBm1Aam destination) {
        destination.setAamAntAnzahl(source.getAamAntAnzahl());
        destination.setAamAntBezeichn(source.getAamAntBezeichn());
        destination.setAamAntGewFiktiv(source.getAamAntGewFiktiv());
        destination.setAamAntGewNiErm(source.getAamAntGewNiErm());
        destination.setAamAntIsin(source.getAamAntIsin());
        destination.setAamGewVerMillfo(source.getAamGewVerMillfo());
    }

    public void copyAllTrTableBm3AamFields(TrTableBm3Aam source, TrTableBm3Aam destination) {
        destination.setAamAntAnzahl(source.getAamAntAnzahl());
        destination.setAamAntBezeichn(source.getAamAntBezeichn());
        destination.setAamAntGewFiktiv(source.getAamAntGewFiktiv());
        destination.setAamAntGewNiErm(source.getAamAntGewNiErm());
        destination.setAamAntIsin(source.getAamAntIsin());
        destination.setAamGewVerMillfo(source.getAamGewVerMillfo());
    }

    public void copyAllTrTableBm3AkbFields(TrTableBm3Akb source, TrTableBm3Akb destination) {
        destination.setAkbAntAnzahl(source.getAkbAntAnzahl());
        destination.setAkbAntBezeichn(source.getAkbAntBezeichn());
        destination.setAkbAntGewinn(source.getAkbAntGewinn());
        destination.setAkbAntIsin(source.getAkbAntIsin());
    }

    public void copyAllTrTableBm1AkbFields(TrTableBm1Akb source, TrTableBm1Akb destination) {
        destination.setAkbAntAnzahl(source.getAkbAntAnzahl());
        destination.setAkbAntBezeichn(source.getAkbAntBezeichn());
        destination.setAkbAntGewinn(source.getAkbAntGewinn());
        destination.setAkbAntIsin(source.getAkbAntIsin());
    }

    public void copyAllTrTableBm3AkeFields(TrTableBm3Ake source, TrTableBm3Ake destination) {
        destination.setAkeAntAnzahl(source.getAkeAntAnzahl());
        destination.setAkeAntBezeichn(source.getAkeAntBezeichn());
        destination.setAkeAntEbmgNErm(source.getAkeAntEbmgNErm());
        destination.setAkeAntErsBmg(source.getAkeAntErsBmg());
        destination.setAkeAntIsin(source.getAkeAntIsin());
    }

    public void copyAllTrTableBm1AkeFields(TrTableBm1Ake source, TrTableBm1Ake destination) {
        destination.setAkeAntAnzahl(source.getAkeAntAnzahl());
        destination.setAkeAntBezeichn(source.getAkeAntBezeichn());
        destination.setAkeAntEbmgNErm(source.getAkeAntEbmgNErm());
        destination.setAkeAntErsBmg(source.getAkeAntErsBmg());
        destination.setAkeAntIsin(source.getAkeAntIsin());
    }

    public void copyAllTrTableBm3EikFields(TrTableBm3Eik source, TrTableBm3Eik destination) {
        destination.setEikAntAnzahl(source.getEikAntAnzahl());
        destination.setEikAntAusschuett(source.getEikAntAusschuett());
        destination.setEikAntBezeichn(source.getEikAntBezeichn());
        destination.setEikAntIsin(source.getEikAntIsin());
    }

    public void copyAllTrTableBm1EikFields(TrTableBm1Eik source, TrTableBm1Eik destination) {
        destination.setEikAntAnzahl(source.getEikAntAnzahl());
        destination.setEikAntAusschuett(source.getEikAntAusschuett());
        destination.setEikAntBezeichn(source.getEikAntBezeichn());
        destination.setEikAntIsin(source.getEikAntIsin());
    }

    public void copyAllTrTableBm3PivFields(TrTableBm3Piv source, TrTableBm3Piv destination) {
        destination.setPivAntAnzahl(source.getPivAntAnzahl());
        destination.setPivAntAusErloes(source.getPivAntAusErloes());
        destination.setPivAntBezeichng(source.getPivAntBezeichng());
        destination.setPivAntIsin(source.getPivAntIsin());
    }

    public void copyAllTrTableBm1PivFields(TrTableBm1Piv source, TrTableBm1Piv destination) {
        destination.setPivAntAnzahl(source.getPivAntAnzahl());
        destination.setPivAntAusErloes(source.getPivAntAusErloes());
        destination.setPivAntBezeichng(source.getPivAntBezeichng());
        destination.setPivAntIsin(source.getPivAntIsin());
    }
}
