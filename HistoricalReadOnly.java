package com.fisglobal.taxreporting.service.taxreportingcorrection.impl;

import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;

import de.kordoba.framework.common.log.KORLogger;


/**
 * This class create readonly fields for bsb, bm1 and bm3
 */
public class HistoricalReadOnly {
    private static final KORLogger LOG = KORLogger.getLogger(HistoricalReadOnly.class);

    /**
     * This apply method will set the values from database to frontend request
     * 
     * @param frontendBsb
     *                        frontend bsb request object
     * @param databaseBsb
     *                        DB bsb object
     * 
     * @return Updated frontend object
     */
    public TrTableBsb apply(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        LOG.trace("[HistoricalReadOnly] [apply] Enter into historical readonly for BSB: {}",
                frontendBsb.taxCertificateInfo());
        applyBSBNotEditable(frontendBsb, databaseBsb);
        applyBSBNotShown(frontendBsb, databaseBsb);

        for (TrTableBm1 frontEndBm1 : frontendBsb.getTrTableBm1Set()) {
            for (TrTableBm1 dataBaseBm1 : databaseBsb.getTrTableBm1Set()) {
                if (frontEndBm1.getTrTableBm1PK().equals(dataBaseBm1.getTrTableBm1PK())) {
                    applyBm1NotEditable(frontEndBm1, dataBaseBm1);
                    applyBm1NotShown(frontEndBm1, dataBaseBm1);
                }
            }
        }

        for (TrTableBm3 frontEndBm3 : frontendBsb.getTrTableBm3Set()) {
            for (TrTableBm3 dataBaseBm3 : databaseBsb.getTrTableBm3Set()) {
                if (frontEndBm3.getTrTableBm3PK().equals(dataBaseBm3.getTrTableBm3PK())) {
                    applyBm3NotEditable(frontEndBm3, dataBaseBm3);
                    applyBm3NotShown(frontEndBm3, dataBaseBm3);
                }
            }
        }

        LOG.trace("[HistoricalReadOnly] [apply] Exit historical readonly for BSB: {}",
                frontendBsb.taxCertificateInfo());
        return frontendBsb;

    }

    /**
     * Bm3 not shown fields
     * 
     * @param frontEndBm3
     *                        front end Bm3 request
     * @param dataBaseBm3
     *                        database BM3 fields
     */
    private void applyBm3NotShown(TrTableBm3 frontEndBm3, TrTableBm3 dataBaseBm3) {
        frontEndBm3.setLiefertiefe(dataBaseBm3.getLiefertiefe());
        frontEndBm3.setFormular(dataBaseBm3.getFormular());
        frontEndBm3.setFehlerKennz(dataBaseBm3.getFehlerKennz());
        frontEndBm3.setEstb(dataBaseBm3.getEstb());
        frontEndBm3.setZusGefassteStb(dataBaseBm3.getZusGefassteStb());
        frontEndBm3.setZusZeitraumBis(dataBaseBm3.getZusZeitraumBis());
        frontEndBm3.setZusZeitraumVon(dataBaseBm3.getZusZeitraumVon());
        frontEndBm3.setAuslKiAntr(dataBaseBm3.getAuslKiAntr());
        frontEndBm3.setAulsKiGut(dataBaseBm3.getAulsKiGut());
        frontEndBm3.setInlKiVerw(dataBaseBm3.getInlKiVerw());
        frontEndBm3.setAuslKiVerw(dataBaseBm3.getAuslKiVerw());
        frontEndBm3.setAuslKiNamStadt(dataBaseBm3.getAuslKiNamStadt());
        frontEndBm3.setSdBezugWP(dataBaseBm3.getSdBezugWP());
        frontEndBm3.setSdWkn(dataBaseBm3.getSdWkn());
        frontEndBm3.setSdIsin(dataBaseBm3.getSdIsin());
        frontEndBm3.setAnzahlAkb(dataBaseBm3.getAnzahlAkb());
        frontEndBm3.setAnzahlAke(dataBaseBm3.getAnzahlAke());
        frontEndBm3.setAnzahlEik(dataBaseBm3.getAnzahlEik());
        frontEndBm3.setAnzahlPiv(dataBaseBm3.getAnzahlPiv());
        frontEndBm3.setSfDatum(dataBaseBm3.getSfDatum());
        frontEndBm3.setSfBetEinnahm(dataBaseBm3.getSfBetEinnahm());
        frontEndBm3.setSfSonsEink(dataBaseBm3.getSfSonsEink());
        frontEndBm3.setSfBetrag(dataBaseBm3.getSfBetrag());
        frontEndBm3.setSfSchuldBezWp(dataBaseBm3.getSfSchuldBezWp());
        frontEndBm3.setSfWkn(dataBaseBm3.getSfWkn());
        frontEndBm3.setSfIsin(dataBaseBm3.getSfIsin());
        frontEndBm3.setSfBezeichn(dataBaseBm3.getSfBezeichn());
        frontEndBm3.setSfAnteile(dataBaseBm3.getSfAnteile());
        frontEndBm3.setSfAnlBezugWP(dataBaseBm3.getSfAnlBezugWP());
        frontEndBm3.setSfAnlAnzahl(dataBaseBm3.getSfAnlAnzahl());
        frontEndBm3.setSfAnlKapest(dataBaseBm3.getSfAnlKapest());
        frontEndBm3.setSfAnlSolz(dataBaseBm3.getSfAnlSolz());
    }

    /**
     * BM3 not editable fields
     * 
     * @param frontEndBm3
     *                        front end Bm3 request
     * @param dataBaseBm3
     *                        database BM3 fields
     */
    private void applyBm3NotEditable(TrTableBm3 frontEndBm3, TrTableBm3 dataBaseBm3) {
        frontEndBm3.setStornierung(dataBaseBm3.getStornierung());
        frontEndBm3.setAkbVeraeussert(dataBaseBm3.getAkbVeraeussert());
        frontEndBm3.setAkeVeraeussert(dataBaseBm3.getAkeVeraeussert());
        frontEndBm3.setEikErstattet(dataBaseBm3.getEikErstattet());
        frontEndBm3.setPivVerwahrt(dataBaseBm3.getPivVerwahrt());
    }

    /**
     * BM1 not shown fields
     * 
     * @param frontEndBm1
     *                        front end Bm1 request
     * @param dataBaseBm1
     *                        database BM1 fields
     */
    private void applyBm1NotShown(TrTableBm1 frontEndBm1, TrTableBm1 dataBaseBm1) {
        frontEndBm1.setLiefertiefe(dataBaseBm1.getLiefertiefe());
        frontEndBm1.setFormular(dataBaseBm1.getFormular());
        frontEndBm1.setFehlerKennz(dataBaseBm1.getFehlerKennz());
        frontEndBm1.setAuslKiAntr(dataBaseBm1.getAuslKiAntr());
        frontEndBm1.setAulsKiGut(dataBaseBm1.getAulsKiGut());
        frontEndBm1.setInlKiVerw(dataBaseBm1.getInlKiVerw());
        frontEndBm1.setAuslKiVerw(dataBaseBm1.getAuslKiVerw());
        frontEndBm1.setAuslKiNamStadt(dataBaseBm1.getAuslKiNamStadt());
        frontEndBm1.setAnzahlAam(dataBaseBm1.getAnzahlAam());
        frontEndBm1.setAnzahlAke(dataBaseBm1.getAnzahlAke());
        frontEndBm1.setAnzahlEik(dataBaseBm1.getAnzahlEik());
        frontEndBm1.setAnzahlPiv(dataBaseBm1.getAnzahlPiv());
    }

    /**
     * BM1 not editable fields
     * 
     * @param frontEndBm1
     *                        Front end Bm1 request
     * @param dataBaseBm1
     *                        Database BM1 fields
     */
    private void applyBm1NotEditable(TrTableBm1 frontEndBm1, TrTableBm1 dataBaseBm1) {
        frontEndBm1.setStornierung(dataBaseBm1.getStornierung());
        frontEndBm1.setAamVeraeussert(dataBaseBm1.getAamVeraeussert());
        frontEndBm1.setAkeVeraeussert(dataBaseBm1.getAkeVeraeussert());
        frontEndBm1.setEikErstattet(dataBaseBm1.getEikErstattet());
        frontEndBm1.setPivVerwahrt(dataBaseBm1.getPivVerwahrt());
    }

    /**
     * BSB not showing fields
     * 
     * @param frontendBsb
     *                        Front end BSB request
     * @param databaseBsb
     *                        Database BSB fields
     */
    private void applyBSBNotShown(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        frontendBsb.setGkPlz(databaseBsb.getGkPlz());
        frontendBsb.setGkOrt(databaseBsb.getGkOrt());
    }

    /**
     * BSB not editable fields
     * 
     * @param frontendBsb
     *                        Front end BSB request
     * @param databaseBsb
     *                        Database BSB fields
     */
    private void applyBSBNotEditable(TrTableBsb frontendBsb, TrTableBsb databaseBsb) {
        frontendBsb.setStatus(databaseBsb.getStatus());
        frontendBsb.setMeldeStatus(databaseBsb.getMeldeStatus());
        frontendBsb.setAntwortStatus(databaseBsb.getAntwortStatus());
        frontendBsb.setAntwortFehlerNr(databaseBsb.getAntwortFehlerNr());
        frontendBsb.setAnweisungsart(databaseBsb.getAnweisungsart());
        frontendBsb.setKmId(databaseBsb.getKmId());
        frontendBsb.setRefKmId(databaseBsb.getRefKmId());
        frontendBsb.setDokuArt(databaseBsb.getDokuArt());
        frontendBsb.setVersion(databaseBsb.getVersion());
        frontendBsb.setVersionDetail(databaseBsb.getVersionDetail());

        frontendBsb.setAnlageDatum(databaseBsb.getAnlageDatum());
        frontendBsb.setAnlageErfasser(databaseBsb.getAnlageErfasser());
        frontendBsb.setAnlageZeit(databaseBsb.getAnlageZeit());
        frontendBsb.setNdNrLfd(databaseBsb.getNdNrLfd());
        frontendBsb.setNdTicket(databaseBsb.getNdTicket());

        frontendBsb.setAusstellungsdat(databaseBsb.getAusstellungsdat());
        frontendBsb.setAnlass(databaseBsb.getAnlass());

    }
}
