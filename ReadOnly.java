package com.fisglobal.taxreporting.service.taxreportingcorrection.impl;

import org.hibernate.Hibernate;

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
import com.fisglobal.taxreporting.util.FieldsCopy;


/**
 * Readonly class used to set some of the tax report data will not be modified
 * during update process
 */
public class ReadOnly {
    /**
     * This method will initialize the read only process
     *
     * @param frontendBsb
     *                        Updated BSB object from Client
     * @param databaseBsb
     *                        Existing BSB object from DB
     * 
     * @return boolean value for read only
     */
    public boolean apply(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        if (Hibernate.isInitialized(databaseBsb)) {
            databaseBsb = Hibernate.unproxy(databaseBsb, TrTableBsb.class);
        }
        applyBsb(frontendBsb, databaseBsb);
        applyOnAllHistorical(frontendBsb, databaseBsb);
        return true;
    }

    /**
     * Apply read only to historicized data which are in H status
     *
     * @param frontendBsb
     *                        Updated BSB object from Client
     * @param databaseBsb
     *                        Existing BSB object from DB
     */
    private void applyOnAllHistorical(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        FieldsCopy copy = new FieldsCopy();

        for (TrTableBm1 dbBm1 : databaseBsb.getTrTableBm1Set()) {
            for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                if (frontendBm1.getTrTableBm1PK().getKeySatzart().equals("H")
                        && dbBm1.getTrTableBm1PK().equals(frontendBm1.getTrTableBm1PK())) {
                    copy.copyAllTrTableBm1Fields(dbBm1, frontendBm1);

                    for (TrTableBm1Aam aamBm1Database : dbBm1.getTrTableBm1AamSet()) {
                        for (TrTableBm1Aam aamfrontend : frontendBm1.getTrTableBm1AamSet()) {
                            if (aamfrontend.getTrTableAamPK().getKeySatzart().equals("H")
                                    && aamBm1Database.getTrTableAamPK().equals(aamfrontend.getTrTableAamPK())) {
                                copy.copyAllTrTableBm1AamFields(aamBm1Database, aamfrontend);
                            }
                        }
                    }
                    for (TrTableBm1Akb akbBm1Database : dbBm1.getTrTableBm1AkbSet()) {
                        for (TrTableBm1Akb akbfrontend : frontendBm1.getTrTableBm1AkbSet()) {
                            if (akbfrontend.getTrTableAkbPK().getKeySatzart().equals("H")
                                    && akbBm1Database.getTrTableAkbPK().equals(akbfrontend.getTrTableAkbPK())) {
                                copy.copyAllTrTableBm1AkbFields(akbBm1Database, akbfrontend);
                            }
                        }
                    }

                    for (TrTableBm1Ake akeBm1Database : dbBm1.getTrTableBm1AkeSet()) {
                        for (TrTableBm1Ake akefrontend : frontendBm1.getTrTableBm1AkeSet()) {
                            if (akefrontend.getTrTableAkePK().getKeySatzart().equals("H")
                                    && akeBm1Database.getTrTableAkePK().equals(akefrontend.getTrTableAkePK())) {
                                copy.copyAllTrTableBm1AkeFields(akeBm1Database, akefrontend);
                            }
                        }
                    }

                    for (TrTableBm1Eik eikBm1Database : dbBm1.getTrTableBm1EikSet()) {
                        for (TrTableBm1Eik eikfrontend : frontendBm1.getTrTableBm1EikSet()) {
                            if (eikfrontend.getTrTableEikPK().getKeySatzart().equals("H")
                                    && eikBm1Database.getTrTableEikPK().equals(eikfrontend.getTrTableEikPK())) {
                                copy.copyAllTrTableBm1EikFields(eikBm1Database, eikfrontend);
                            }
                        }
                    }
                    for (TrTableBm1Piv pivBm1Database : dbBm1.getTrTableBm1PivSet()) {
                        for (TrTableBm1Piv pivfrontend : frontendBm1.getTrTableBm1PivSet()) {
                            if (pivfrontend.getTrTablePivPK().getKeySatzart().equals("H")
                                    && pivBm1Database.getTrTablePivPK().equals(pivfrontend.getTrTablePivPK())) {
                                copy.copyAllTrTableBm1PivFields(pivBm1Database, pivfrontend);
                            }
                        }
                    }
                }
            }
        }

        for (TrTableBm3 dbBm3 : databaseBsb.getTrTableBm3Set()) {
            for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                if (frontendBm3.getTrTableBm3PK().getKeySatzart().equals("H")
                        && dbBm3.getTrTableBm3PK().equals(frontendBm3.getTrTableBm3PK())) {

                    copy.copyAllTrTableBm3Fields(dbBm3, frontendBm3);

                    for (TrTableBm3Aam aamBm3Database : dbBm3.getTrTableBm3AamSet()) {
                        for (TrTableBm3Aam aamfrontend : frontendBm3.getTrTableBm3AamSet()) {
                            if (aamfrontend.getTrTableAamPK().getKeySatzart().equals("H")
                                    && aamBm3Database.getTrTableAamPK().equals(aamfrontend.getTrTableAamPK())) {
                                copy.copyAllTrTableBm3AamFields(aamBm3Database, aamfrontend);
                            }
                        }
                    }

                    for (TrTableBm3Akb akbBm3Database : dbBm3.getTrTableBm3AkbSet()) {
                        for (TrTableBm3Akb akbfrontend : frontendBm3.getTrTableBm3AkbSet()) {
                            if (akbfrontend.getTrTableAkbPK().getKeySatzart().equals("H")
                                    && akbBm3Database.getTrTableAkbPK().equals(akbfrontend.getTrTableAkbPK())) {
                                copy.copyAllTrTableBm3AkbFields(akbBm3Database, akbfrontend);
                            }
                        }
                    }

                    for (TrTableBm3Ake akeBm3Database : dbBm3.getTrTableBm3AkeSet()) {
                        for (TrTableBm3Ake akefrontend : frontendBm3.getTrTableBm3AkeSet()) {
                            if (akefrontend.getTrTableAkePK().getKeySatzart().equals("H")
                                    && akeBm3Database.getTrTableAkePK().equals(akefrontend.getTrTableAkePK())) {
                                copy.copyAllTrTableBm3AkeFields(akeBm3Database, akefrontend);
                            }
                        }
                    }

                    for (TrTableBm3Eik eikBm3Database : dbBm3.getTrTableBm3EikSet()) {
                        for (TrTableBm3Eik eikfrontend : frontendBm3.getTrTableBm3EikSet()) {
                            if (eikfrontend.getTrTableEikPK().getKeySatzart().equals("H")
                                    && eikBm3Database.getTrTableEikPK().equals(eikfrontend.getTrTableEikPK())) {
                                copy.copyAllTrTableBm3EikFields(eikBm3Database, eikfrontend);
                            }
                        }
                    }

                    for (TrTableBm3Piv pivBm3Database : dbBm3.getTrTableBm3PivSet()) {
                        for (TrTableBm3Piv pivfrontend : frontendBm3.getTrTableBm3PivSet()) {
                            if (pivfrontend.getTrTablePivPK().getKeySatzart().equals("H")
                                    && pivBm3Database.getTrTablePivPK().equals(pivfrontend.getTrTablePivPK())) {
                                copy.copyAllTrTableBm3PivFields(pivBm3Database, pivfrontend);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * These fields are set to readonly and also not shown in UI
     *
     * @param frontendBsb
     *                        Updated BSB object from Client
     * @param databaseBsb
     *                        Existing BSB object from DB
     */
    private void applyBsbNotShown(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        frontendBsb.setVersion(databaseBsb.getVersion());
        frontendBsb.setGkPlz(databaseBsb.getGkPlz());
        frontendBsb.setGkOrt(databaseBsb.getGkOrt());
        frontendBsb.setFStrasse(databaseBsb.getFStrasse());
        frontendBsb.setFHausnummer(databaseBsb.getFHausnummer());
        frontendBsb.setFHausnummerZus(databaseBsb.getFHausnummerZus());
        frontendBsb.setFAdressergaenzung(databaseBsb.getFAdressergaenzung());
        frontendBsb.setFPlz(databaseBsb.getFPlz());
        frontendBsb.setFOrt(databaseBsb.getFOrt());
        frontendBsb.setFPostfach(databaseBsb.getFPostfach());
        frontendBsb.setFPfPlz(databaseBsb.getFPfPlz());
        frontendBsb.setFPfOrt(databaseBsb.getFPfOrt());
        frontendBsb.setFAuslandsPlz(databaseBsb.getFAuslandsPlz());
        frontendBsb.setFStaatSchl(databaseBsb.getFStaatSchl());
        frontendBsb.setFStaatSchl(databaseBsb.getFStaatSchl());
        frontendBsb.setFLand(databaseBsb.getFLand());
        frontendBsb.setFGkPlz(databaseBsb.getFGkPlz());
        frontendBsb.setFGkWohnort(databaseBsb.getFGkWohnort());
        frontendBsb.setFAdrInfo(databaseBsb.getFAdrInfo());
        frontendBsb.setFTyp(databaseBsb.getFTyp());
        frontendBsb.setRolleWeiterePers(databaseBsb.getRolleWeiterePers());
        frontendBsb.setNwpGeschlecht(databaseBsb.getNwpGeschlecht());
        frontendBsb.setNwpTin(databaseBsb.getNwpTin());
        frontendBsb.setNwpNationalitaet(databaseBsb.getNwpNationalitaet());
        frontendBsb.setNwpName(databaseBsb.getNwpName());
        frontendBsb.setNwpVorname(databaseBsb.getNwpVorname());
        frontendBsb.setNwpGebNameVors(databaseBsb.getNwpGebNameVors());
        frontendBsb.setNwpTitel(databaseBsb.getNwpTitel());
        frontendBsb.setNwpGeburtsdatum(databaseBsb.getNwpGeburtsdatum());
        frontendBsb.setNwpGeburtsname(databaseBsb.getNwpGeburtsname());
        frontendBsb.setNwpGebNameVors(databaseBsb.getNwpGebNameVors());
        frontendBsb.setNwpGebNameZus(databaseBsb.getNwpGebNameZus());
        frontendBsb.setNwpGeburtsort(databaseBsb.getNwpGeburtsort());
        frontendBsb.setNwpGebLandSchl(databaseBsb.getNwpGebLandSchl());
        frontendBsb.setNwpGeburtsland(databaseBsb.getNwpGeburtsland());
        frontendBsb.setNwpAuswandDatum(databaseBsb.getNwpAuswandDatum());
        frontendBsb.setNwpAuswandDatum(databaseBsb.getNwpAuswandDatum());
        frontendBsb.setNwpSterbeDatum(databaseBsb.getNwpSterbeDatum());
        frontendBsb.setNwpPersoninfo(databaseBsb.getNwpPersoninfo());
        frontendBsb.setEhegLdnr(databaseBsb.getEhegLdnr());
        frontendBsb.setNnwpWid(databaseBsb.getNnwpWid());
        frontendBsb.setNnwpSteuernummer(databaseBsb.getNnwpSteuernummer());
        frontendBsb.setNnpFirmenname(databaseBsb.getNnpFirmenname());
        frontendBsb.setNnwpTyp(databaseBsb.getNnwpTyp());
        frontendBsb.setNnwpAusPerstId(databaseBsb.getNnwpAusPerstId());
        frontendBsb.setWpStrasse(databaseBsb.getWpStrasse());
        frontendBsb.setWpHausnummer(databaseBsb.getWpHausnummer());
        frontendBsb.setWpHausnummerZus(databaseBsb.getWpHausnummerZus());
        frontendBsb.setWpAdressergaenz(databaseBsb.getWpAdressergaenz());
        frontendBsb.setWpOrt(databaseBsb.getWpOrt());
        frontendBsb.setWpPlz(databaseBsb.getWpPlz());
        frontendBsb.setWpAuslandsPlz(databaseBsb.getWpAuslandsPlz());
        frontendBsb.setWpStaatSchl(databaseBsb.getWpStaatSchl());
        frontendBsb.setWpLandWP(databaseBsb.getWpLandWP());
        frontendBsb.setWpTyp(databaseBsb.getWpTyp());
        frontendBsb.setWpInfo(databaseBsb.getWpInfo());
        frontendBsb.setUpdateCheck(databaseBsb.getUpdateCheck());
    }

    /**
     * These fields are set to readonly and but it is shown in UI
     *
     * @param frontendBsb
     *                        Updated BSB object from Client
     * @param databaseBsb
     *                        Existing BSB object from DB
     */
    private void applyBsbShownButNotEditable(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        frontendBsb.setStatus(databaseBsb.getStatus());
        frontendBsb.setStornoKz(databaseBsb.getStornoKz());
        frontendBsb.setAnweisungsart(databaseBsb.getAnweisungsart());
        frontendBsb.setKmId(databaseBsb.getKmId());
        frontendBsb.setRefKmId(databaseBsb.getRefKmId());
        frontendBsb.setVerfName(databaseBsb.getVerfName());
        frontendBsb.setVerfStr(databaseBsb.getVerfStr());
        frontendBsb.setVerfHsnr(databaseBsb.getVerfHsnr());
        frontendBsb.setVerfHszu(databaseBsb.getVerfHszu());
        frontendBsb.setVerfAdrErg(databaseBsb.getVerfAdrErg());
        frontendBsb.setVerfOrt(databaseBsb.getVerfOrt());
        frontendBsb.setVerfPlz(databaseBsb.getVerfPlz());
        frontendBsb.setVerfPfNr(databaseBsb.getVerfPfNr());
        frontendBsb.setVerfPfPlz(databaseBsb.getVerfPfPlz());
        frontendBsb.setVerfPfOrt(databaseBsb.getVerfPfOrt());
        frontendBsb.setVerfGkPlz(databaseBsb.getVerfGkPlz());
        frontendBsb.setVerfGkOrt(databaseBsb.getVerfGkOrt());
        frontendBsb.setVerfTel(databaseBsb.getVerfTel());
        frontendBsb.setVerfEmail(databaseBsb.getVerfEmail());
        frontendBsb.setVerfOrdBegriff(databaseBsb.getVerfOrdBegriff());
        frontendBsb.setVerfBic(databaseBsb.getVerfBic());
        frontendBsb.setAufnehmName(databaseBsb.getAufnehmName());
        frontendBsb.setAufnehmStr(databaseBsb.getAufnehmStr());
        frontendBsb.setAufnehmHszu(databaseBsb.getAufnehmHszu());
        frontendBsb.setAufnehmAdrErg(databaseBsb.getAufnehmAdrErg());
        frontendBsb.setAufnehmOrt(databaseBsb.getAufnehmOrt());
        frontendBsb.setAufnehmAuslPlz(databaseBsb.getAufnehmAuslPlz());
        frontendBsb.setAufnehmStaatSl(databaseBsb.getAufnehmStaatSl());
        frontendBsb.setAufnehmLand(databaseBsb.getAufnehmLand());
        frontendBsb.setAufnehmPfNr(databaseBsb.getAufnehmPfNr());
        frontendBsb.setAufnehmPfPlz(databaseBsb.getAufnehmPfPlz());
        frontendBsb.setAufnehmPfOrt(databaseBsb.getAufnehmPfOrt());
        frontendBsb.setAufnehmGkPlz(databaseBsb.getAufnehmGkPlz());
        frontendBsb.setAufnehmGkOrt(databaseBsb.getAufnehmGkOrt());
        frontendBsb.setAufnehmTel(databaseBsb.getAufnehmTel());
        frontendBsb.setAufnehmEmail(databaseBsb.getAufnehmEmail());
        frontendBsb.setAufnehmOrdnBegr(databaseBsb.getAufnehmOrdnBegr());
        frontendBsb.setAufnehmBic(databaseBsb.getAufnehmBic());
        frontendBsb.setMeldejahr(databaseBsb.getMeldejahr());
        frontendBsb.setArt(databaseBsb.getArt());
        frontendBsb.setOrdnungsbegriff(databaseBsb.getOrdnungsbegriff());
        frontendBsb.setNp1Geschlecht(databaseBsb.getNp1Geschlecht());
        frontendBsb.setNp1Idnr(databaseBsb.getNp1Idnr());
        frontendBsb.setNp1Nationalitaet(databaseBsb.getNp1Nationalitaet());
        frontendBsb.setNp1Name(databaseBsb.getNp1Name());
        frontendBsb.setNp1Vorname(databaseBsb.getNp1Vorname());
        frontendBsb.setNp1Namensvorsatz(databaseBsb.getNp1Namensvorsatz());
        frontendBsb.setNp1Namenszusatz(databaseBsb.getNp1Namenszusatz());
        frontendBsb.setNp1Titel(databaseBsb.getNp1Titel());
        frontendBsb.setNp1Gebdat(databaseBsb.getNp1Gebdat());
        frontendBsb.setNp1Geburtsname(databaseBsb.getNp1Geburtsname());
        frontendBsb.setNp1GebnameVors(databaseBsb.getNp1GebnameVors());
        frontendBsb.setNp1GebnameZus(databaseBsb.getNp1GebnameZus());
        frontendBsb.setNp1Geburtsort(databaseBsb.getNp1Geburtsort());
        frontendBsb.setNp1GeburtslandSl(databaseBsb.getNp1GeburtslandSl());
        frontendBsb.setNp1Geburtsland(databaseBsb.getNp1Geburtsland());
        frontendBsb.setNp1AuswandDatum(databaseBsb.getNp1AuswandDatum());
        frontendBsb.setNp1SterbeDatum(databaseBsb.getNp1SterbeDatum());
        frontendBsb.setNp1Typ(databaseBsb.getNp1Typ());
        frontendBsb.setNp1AuslPersstId(databaseBsb.getNp1AuslPersstId());
        frontendBsb.setNp2Geschlecht(databaseBsb.getNp2Geschlecht());
        frontendBsb.setNp2Idnr(databaseBsb.getNp2Idnr());
        frontendBsb.setNp2Nationalitaet(databaseBsb.getNp2Nationalitaet());
        frontendBsb.setNp2Name(databaseBsb.getNp2Name());
        frontendBsb.setNp2Vorname(databaseBsb.getNp2Vorname());
        frontendBsb.setNp2Namensvorsatz(databaseBsb.getNp2Namensvorsatz());
        frontendBsb.setNp2Titel(databaseBsb.getNp2Titel());
        frontendBsb.setNp2Gebdat(databaseBsb.getNp2Gebdat());
        frontendBsb.setNp2GebnameVors(databaseBsb.getNp2GebnameVors());
        frontendBsb.setNp2GebnameZus(databaseBsb.getNp2GebnameZus());
        frontendBsb.setNp2Geburtsort(databaseBsb.getNp2Geburtsort());
        frontendBsb.setNp2GeburtslandSl(databaseBsb.getNp2GeburtslandSl());
        frontendBsb.setNp2Geburtsland(databaseBsb.getNp2Geburtsland());
        frontendBsb.setNp2AuswandDatum(databaseBsb.getNp2AuswandDatum());
        frontendBsb.setNp2SterbeDatum(databaseBsb.getNp2SterbeDatum());
        frontendBsb.setNp2Typ(databaseBsb.getNp2Typ());
        frontendBsb.setNp2AuslPersstId(databaseBsb.getNp2AuslPersstId());
        frontendBsb.setNnpWid(databaseBsb.getNnpWid());
        frontendBsb.setNnpSteuernummer(databaseBsb.getNnpSteuernummer());
        frontendBsb.setNnpFirmenname(databaseBsb.getNnpFirmenname());
        frontendBsb.setNnpTyp(databaseBsb.getNnpTyp());
        frontendBsb.setNnpSteuernummer(databaseBsb.getNnpSteuernummer());
        frontendBsb.setNnpTyp(databaseBsb.getNnpTyp());
        frontendBsb.setNnwpAusPerstId(databaseBsb.getNnwpAusPerstId());
        frontendBsb.setStrasse(databaseBsb.getStrasse());
        frontendBsb.setHausnummer(databaseBsb.getHausnummer());
        frontendBsb.setHausnummerZus(databaseBsb.getHausnummerZus());
        frontendBsb.setFHausnummerZus(databaseBsb.getFHausnummerZus());
        frontendBsb.setAdressergaenzung(databaseBsb.getAdressergaenzung());
        frontendBsb.setOrt(databaseBsb.getOrt());
        frontendBsb.setPlz(databaseBsb.getPlz());
        frontendBsb.setRolleWeiterePers(databaseBsb.getRolleWeiterePers());
        frontendBsb.setNwpTyp(databaseBsb.getNwpTyp());
        frontendBsb.setErgaenzendeStb(databaseBsb.getErgaenzendeStb());
        frontendBsb.setLebendKz(databaseBsb.getLebendKz());
        frontendBsb.setSteuerZuord(databaseBsb.getSteuerZuord());
        frontendBsb.setDokuArt(databaseBsb.getDokuArt());
        frontendBsb.setNdTicket(databaseBsb.getNdTicket());
        frontendBsb.setNdNrLfd(databaseBsb.getNdNrLfd());
        frontendBsb.setAntwortStatus(databaseBsb.getAntwortStatus());
        frontendBsb.setAnlageErfasser(databaseBsb.getAnlageErfasser());

        frontendBsb.setPartnerId(databaseBsb.getPartnerId());
        frontendBsb.setPersonRolle(databaseBsb.getPersonRolle());
        frontendBsb.setPersonId(databaseBsb.getPersonId());

        frontendBsb.setAuslandsplz(databaseBsb.getAuslandsplz());
        frontendBsb.setAufnehmHsnr(databaseBsb.getAufnehmHsnr());
        frontendBsb.setLand(databaseBsb.getLand());
        frontendBsb.setNnpAusPersstId(databaseBsb.getNnpAusPersstId());
        frontendBsb.setNnwpFirmenname(databaseBsb.getNnwpFirmenname());
        frontendBsb.setNp2Geburtsname(databaseBsb.getNp2Geburtsname());
        frontendBsb.setNp2Namenszusatz(databaseBsb.getNp2Namenszusatz());
        frontendBsb.setNwpNamensvorsatz(databaseBsb.getNwpNamensvorsatz());
        frontendBsb.setNwpNamenszusatz(databaseBsb.getNwpNamenszusatz());
        frontendBsb.setPfPlz(databaseBsb.getPfPlz());
        frontendBsb.setPfWohnort(databaseBsb.getPfWohnort());
        frontendBsb.setPostfach(databaseBsb.getPostfach());
        frontendBsb.setStaatSchl(databaseBsb.getStaatSchl());
        frontendBsb.setSteuerauslaender(databaseBsb.getSteuerauslaender());
        frontendBsb.setVersionDetail(databaseBsb.getVersionDetail());
        frontendBsb.setWeitereErlaueterg(databaseBsb.getWeitereErlaueterg());
        frontendBsb.setAntwortFehlerNr(databaseBsb.getAntwortFehlerNr());
        frontendBsb.setAufnehmPlz(databaseBsb.getAufnehmPlz());
        frontendBsb.setAuslandsplz(databaseBsb.getAuslandsplz());
        frontendBsb.setAnlageDatum(databaseBsb.getAnlageDatum());
        frontendBsb.setAnlageZeit(databaseBsb.getAnlageZeit());
        frontendBsb.setMeldungJjjjmmtt(databaseBsb.getMeldungJjjjmmtt());
        frontendBsb.setMeldungUhrHms(databaseBsb.getMeldungUhrHms());
        frontendBsb.setKontoId(databaseBsb.getKontoId());

    }

    /**
     * Apply readonly fields for BSB table record
     *
     * @param frontendBsb
     *                        Updated BSB object from Client
     * @param databaseBsb
     *                        Existing BSB object from DB
     */
    private void applyBsb(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        // TODO: move each of the following lines to one of the methods:
        applyBsbNotShown(frontendBsb, databaseBsb);
        applyBsbShownButNotEditable(frontendBsb, databaseBsb);
        for (TrTableBm1 dbBm1 : databaseBsb.getTrTableBm1Set()) {
            for (TrTableBm1 frontendBm1 : frontendBsb.getTrTableBm1Set()) {
                if (dbBm1.getTrTableBm1PK().equals(frontendBm1.getTrTableBm1PK())) {
                    applyBm1(frontendBm1, dbBm1);
                }
            }
        }

        for (TrTableBm3 dbBm3 : databaseBsb.getTrTableBm3Set()) {
            for (TrTableBm3 frontendBm3 : frontendBsb.getTrTableBm3Set()) {
                if (dbBm3.getTrTableBm3PK().equals(frontendBm3.getTrTableBm3PK())) {
                    applyBm3(frontendBm3, dbBm3);
                }
            }
        }

    }

    /**
     * Apply readonly fields for BM1 table record
     *
     * @param frontendBm1
     *                        Updated BM1 object from Client
     * @param databaseBm1
     *                        Existing BM1 object from DB
     */
    private void applyBm1(TrTableBm1 frontendBm1, TrTableBm1 databaseBm1) {
        applyBm1NotShown(frontendBm1, databaseBm1);
        applyBm1ShownButNotEditable(frontendBm1, databaseBm1);
    }

    /**
     * Apply readonly fields for BM1 table record but it is showing in UI
     *
     * @param frontendBm1
     *                        Updated BM1 object from Client
     * @param databaseBm1
     *                        Existing BM1 object from DB
     */
    private void applyBm1ShownButNotEditable(TrTableBm1 frontendBm1, TrTableBm1 databaseBm1) {
        frontendBm1.setKeinOriginalM1(databaseBm1.getKeinOriginalM1());

        frontendBm1.setAamVeraeussert(databaseBm1.getAamVeraeussert());
        frontendBm1.setAkeVeraeussert(databaseBm1.getAkeVeraeussert());
        frontendBm1.setAnzahlAam(databaseBm1.getAnzahlAam());
        frontendBm1.setAnzahlAke(databaseBm1.getAnzahlAke());
        frontendBm1.setAnzahlEik(databaseBm1.getAnzahlEik());
        frontendBm1.setAnzahlPiv(databaseBm1.getAnzahlPiv());
        frontendBm1.setAulsKiGut(databaseBm1.getAulsKiGut());
        frontendBm1.setAuslKiAntr(databaseBm1.getAuslKiAntr());
        frontendBm1.setAuslKiNamStadt(databaseBm1.getAuslKiNamStadt());
        frontendBm1.setAuslKiVerw(databaseBm1.getAuslKiVerw());
        frontendBm1.setEikErstattet(databaseBm1.getEikErstattet());
        frontendBm1.setFehlerKennz(databaseBm1.getFehlerKennz());
        frontendBm1.setFormular(databaseBm1.getFormular());
        frontendBm1.setInlKiVerw(databaseBm1.getInlKiVerw());
        frontendBm1.setLiefertiefe(databaseBm1.getLiefertiefe());
        frontendBm1.setPivVerwahrt(databaseBm1.getPivVerwahrt());
        frontendBm1.setStornierung(databaseBm1.getStornierung());
    }

    /**
     * Apply readonly fields for BM1 table record but it is not showing in UI
     *
     * @param frontendBm1
     *                        Updated BM1 object from Client
     * @param databaseBm1
     *                        Existing BM1 object from DB
     */
    private void applyBm1NotShown(TrTableBm1 frontendBm1, TrTableBm1 databaseBm1) {
        // frontendBm1.setEntschaedigung(databaseBm1.getEntschaedigung());
    }

    /**
     * Apply readonly fields for BM3 table record
     *
     * @param frontendBm3
     *                        Updated BM3 object from Client
     * @param databaseBm3
     *                        Existing BM3 object from DB
     */
    private void applyBm3(TrTableBm3 frontendBm3, TrTableBm3 databaseBm3) {
        applyBm3NotShown(frontendBm3, databaseBm3);
        applyBm3ShownButNotEditable(frontendBm3, databaseBm3);
    }

    /**
     * Apply readonly fields for BM3 table record but it is showing in UI
     *
     * @param frontendBm3
     *                        Updated BM3 object from Client
     * @param databaseBm3
     *                        Existing BM3 object from DB
     */
    private void applyBm3ShownButNotEditable(TrTableBm3 frontendBm3, TrTableBm3 databaseBm3) {
        frontendBm3.setAkbVeraeussert(databaseBm3.getAkbVeraeussert());
        frontendBm3.setAkeVeraeussert(databaseBm3.getAkeVeraeussert());
        frontendBm3.setAnzahlAkb(databaseBm3.getAnzahlAkb());
        frontendBm3.setAnzahlAke(databaseBm3.getAnzahlAke());
        frontendBm3.setAnzahlEik(databaseBm3.getAnzahlEik());
        frontendBm3.setAnzahlPiv(databaseBm3.getAnzahlPiv());
        frontendBm3.setAulsKiGut(databaseBm3.getAulsKiGut());
        frontendBm3.setAuslKiAntr(databaseBm3.getAuslKiAntr());
        frontendBm3.setAuslKiNamStadt(databaseBm3.getAuslKiNamStadt());
        frontendBm3.setAuslKiVerw(databaseBm3.getAuslKiVerw());
        frontendBm3.setEikErstattet(databaseBm3.getEikErstattet());
        frontendBm3.setEstb(databaseBm3.getEstb());
        frontendBm3.setFehlerKennz(databaseBm3.getFehlerKennz());
        frontendBm3.setFormular(databaseBm3.getFormular());
        frontendBm3.setInlKiVerw(databaseBm3.getInlKiVerw());
        frontendBm3.setKeinOriginalM3(databaseBm3.getKeinOriginalM3());
        frontendBm3.setLiefertiefe(databaseBm3.getLiefertiefe());
        frontendBm3.setPivVerwahrt(databaseBm3.getPivVerwahrt());
        frontendBm3.setSdBezugWP(databaseBm3.getSdBezugWP());
        frontendBm3.setSdIsin(databaseBm3.getSdIsin());
        frontendBm3.setSdWkn(databaseBm3.getSdWkn());
        frontendBm3.setStornierung(databaseBm3.getStornierung());
        frontendBm3.setZusGefassteStb(databaseBm3.getZusGefassteStb());
        frontendBm3.setZusZeitraumBis(databaseBm3.getZusZeitraumBis());
        frontendBm3.setZusZeitraumVon(databaseBm3.getZusZeitraumVon());

        frontendBm3.setSfDatum(databaseBm3.getSfDatum());
        frontendBm3.setSfBetEinnahm(databaseBm3.getSfBetEinnahm());
        frontendBm3.setSfSonsEink(databaseBm3.getSfSonsEink());
        frontendBm3.setSfBetrag(databaseBm3.getSfBetrag());
        frontendBm3.setSfSchuldBezWp(databaseBm3.getSfSchuldBezWp());
        frontendBm3.setSfWkn(databaseBm3.getSfWkn());
        frontendBm3.setSfIsin(databaseBm3.getSfIsin());
        frontendBm3.setSfBezeichn(databaseBm3.getSfBezeichn());
        frontendBm3.setSfAnteile(databaseBm3.getSfAnteile());
        frontendBm3.setSfAnlBezugWP(databaseBm3.getSfAnlBezugWP());
        frontendBm3.setSfAnlAnzahl(databaseBm3.getSfAnlAnzahl());
        frontendBm3.setSfAnlKapest(databaseBm3.getSfAnlKapest());
        frontendBm3.setSfAnlSolz(databaseBm3.getSfAnlSolz());

    }

    /**
     * Apply readonly fields for BM3 table record and also it is not showing in UI
     *
     * @param frontendBm3
     *                        Updated BM3 object from Client
     * @param databaseBm3
     *                        Existing BM3 object from DB
     */
    private void applyBm3NotShown(TrTableBm3 frontendBm3, TrTableBm3 databaseBm3) {
        frontendBm3.setLiefertiefe(databaseBm3.getLiefertiefe());
        frontendBm3.setFormular(databaseBm3.getFormular());
        frontendBm3.setFehlerKennz(databaseBm3.getFehlerKennz());

    }
}
