package com.fisglobal.taxreporting.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableAamPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm1Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.aam.TrTableBm3Aam;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableAkbPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm1Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.akb.TrTableBm3Akb;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableAkePK;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm1Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.ake.TrTableBm3Ake;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm1.TrTableBm1;
import com.fisglobal.taxreporting.entity.model.taxreporting.bm3.TrTableBm3;
import com.fisglobal.taxreporting.entity.model.taxreporting.bsb.TrTableBsb;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm1Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableBm3Eik;
import com.fisglobal.taxreporting.entity.model.taxreporting.eik.TrTableEikPK;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm1Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTableBm3Piv;
import com.fisglobal.taxreporting.entity.model.taxreporting.piv.TrTablePivPK;


public class MapDifferentFieldsExceptKeys {
    public int updateTaxReportingData(TrTableBsb original, TrTableBsb dataToUpdate, String action) {
        int changedCount = 0;

        changedCount += getChangesTrTableBsb(original, dataToUpdate);

        for (TrTableBm1 toUpdateBm1Row : dataToUpdate.getTrTableBm1Set()) {
            Set<TrTableBm1Aam> toUpdatebm1RowAam = toUpdateBm1Row.getTrTableBm1AamSet();
            Map<TrTableAamPK, TrTableBm1Aam> toUpdatebm1RowAamMap = new HashMap<>();

            Set<TrTableBm1Akb> toUpdatebm1RowAkb = toUpdateBm1Row.getTrTableBm1AkbSet();
            Map<TrTableAkbPK, TrTableBm1Akb> toUpdatebm1RowAkbMap = new HashMap<>();

            Set<TrTableBm1Ake> toUpdatebm1RowAke = toUpdateBm1Row.getTrTableBm1AkeSet();
            Map<TrTableAkePK, TrTableBm1Ake> toUpdatebm1RowAkeMap = new HashMap<>();

            Set<TrTableBm1Eik> toUpdatebm1RowEik = toUpdateBm1Row.getTrTableBm1EikSet();
            Map<TrTableEikPK, TrTableBm1Eik> toUpdatebm1RowEikMap = new HashMap<>();

            Set<TrTableBm1Piv> toUpdatebm1RowPiv = toUpdateBm1Row.getTrTableBm1PivSet();
            Map<TrTablePivPK, TrTableBm1Piv> toUpdatebm1RowPivMap = new HashMap<>();

            for (TrTableBm1Aam bm1AamRow : toUpdatebm1RowAam) {
                toUpdatebm1RowAamMap.put(bm1AamRow.getTrTableAamPK(), bm1AamRow);
            }

            for (TrTableBm1Akb bm1AkbRow : toUpdatebm1RowAkb) {
                toUpdatebm1RowAkbMap.put(bm1AkbRow.getTrTableAkbPK(), bm1AkbRow);
            }

            for (TrTableBm1Ake bm1AkeRow : toUpdatebm1RowAke) {
                toUpdatebm1RowAkeMap.put(bm1AkeRow.getTrTableAkePK(), bm1AkeRow);
            }

            for (TrTableBm1Eik bm1EikRow : toUpdatebm1RowEik) {
                toUpdatebm1RowEikMap.put(bm1EikRow.getTrTableEikPK(), bm1EikRow);
            }

            for (TrTableBm1Piv bm1PivRow : toUpdatebm1RowPiv) {
                toUpdatebm1RowPivMap.put(bm1PivRow.getTrTablePivPK(), bm1PivRow);
            }

            for (TrTableBm1 originalbm1Row : original.getTrTableBm1Set()) {

                if (toUpdateBm1Row.getTrTableBm1PK().equals(originalbm1Row.getTrTableBm1PK())) {
                    changedCount += getChangesTrTableBm1(originalbm1Row, toUpdateBm1Row, action);

                    Set<TrTableBm1Aam> originalbm1RowAam = originalbm1Row.getTrTableBm1AamSet();
                    Map<TrTableAamPK, TrTableBm1Aam> originalbm1RowAamMap = new HashMap<>();

                    Set<TrTableBm1Akb> originalbm1RowAkb = originalbm1Row.getTrTableBm1AkbSet();
                    Map<TrTableAkbPK, TrTableBm1Akb> originalbm1RowAkbMap = new HashMap<>();

                    Set<TrTableBm1Ake> originalbm1RowAke = originalbm1Row.getTrTableBm1AkeSet();
                    Map<TrTableAkePK, TrTableBm1Ake> originalbm1RowAkeMap = new HashMap<>();

                    Set<TrTableBm1Eik> originalbm1RowEik = originalbm1Row.getTrTableBm1EikSet();
                    Map<TrTableEikPK, TrTableBm1Eik> originalbm1RowEikMap = new HashMap<>();

                    Set<TrTableBm1Piv> originalbm1RowPiv = originalbm1Row.getTrTableBm1PivSet();
                    Map<TrTablePivPK, TrTableBm1Piv> originalbm1RowPivMap = new HashMap<>();

                    for (TrTableBm1Aam bm1aamRow : originalbm1RowAam) {
                        originalbm1RowAamMap.put(bm1aamRow.getTrTableAamPK(), bm1aamRow);
                    }

                    for (TrTableBm1Akb bm1akbRow : originalbm1RowAkb) {
                        originalbm1RowAkbMap.put(bm1akbRow.getTrTableAkbPK(), bm1akbRow);
                    }

                    for (TrTableBm1Ake bm1akeRow : originalbm1RowAke) {
                        originalbm1RowAkeMap.put(bm1akeRow.getTrTableAkePK(), bm1akeRow);
                    }

                    for (TrTableBm1Eik bm1eikRow : originalbm1RowEik) {
                        originalbm1RowEikMap.put(bm1eikRow.getTrTableEikPK(), bm1eikRow);
                    }

                    for (TrTableBm1Piv bm1pivRow : originalbm1RowPiv) {
                        originalbm1RowPivMap.put(bm1pivRow.getTrTablePivPK(), bm1pivRow);
                    }

                    Map<TrTableAamPK, TrTableBm1Aam> commonAam = new HashMap<>(originalbm1RowAamMap);
                    commonAam.keySet().retainAll(toUpdatebm1RowAamMap.keySet());
                    for (TrTableAamPK commonPK : commonAam.keySet()) {

                        changedCount += getChangesTrTableBm1Aam(originalbm1RowAamMap.get(commonPK),
                                toUpdatebm1RowAamMap.get(commonPK));
                    }

                    Map<TrTableAkbPK, TrTableBm1Akb> commonAkb = new HashMap<>(originalbm1RowAkbMap);
                    commonAkb.keySet().retainAll(toUpdatebm1RowAkbMap.keySet());

                    for (TrTableAkbPK commonPK : commonAkb.keySet()) {
                        changedCount += getChangesTrTableBm1Akb(originalbm1RowAkbMap.get(commonPK),
                                toUpdatebm1RowAkbMap.get(commonPK));
                    }

                    Map<TrTableAkePK, TrTableBm1Ake> commonAke = new HashMap<>(originalbm1RowAkeMap);
                    commonAke.keySet().retainAll(toUpdatebm1RowAkeMap.keySet());

                    for (TrTableAkePK commonPK : commonAke.keySet()) {
                        changedCount += getChangesTrTableBm1Ake(originalbm1RowAkeMap.get(commonPK),
                                toUpdatebm1RowAkeMap.get(commonPK));
                    }

                    Map<TrTableEikPK, TrTableBm1Eik> commonEik = new HashMap<>(originalbm1RowEikMap);
                    commonEik.keySet().retainAll(toUpdatebm1RowEikMap.keySet());

                    for (TrTableEikPK commonPK : commonEik.keySet()) {
                        changedCount += getChangesTrTableBm1Eik(originalbm1RowEikMap.get(commonPK),
                                toUpdatebm1RowEikMap.get(commonPK));
                    }

                    Map<TrTablePivPK, TrTableBm1Piv> commonPiv = new HashMap<>(originalbm1RowPivMap);
                    commonPiv.keySet().retainAll(toUpdatebm1RowPivMap.keySet());

                    for (TrTablePivPK commonPK : commonPiv.keySet()) {
                        changedCount += getChangesTrTableBm1Piv(originalbm1RowPivMap.get(commonPK),
                                toUpdatebm1RowPivMap.get(commonPK));
                    }
                }
            }
        }

        for (TrTableBm3 toUpdateBm3Row : dataToUpdate.getTrTableBm3Set()) {
            Set<TrTableBm3Aam> toUpdatebm3RowAam = toUpdateBm3Row.getTrTableBm3AamSet();
            Map<TrTableAamPK, TrTableBm3Aam> toUpdatebm3RowAamMap = new HashMap<>();
            for (TrTableBm3Aam bm3AamRow : toUpdatebm3RowAam) {
                toUpdatebm3RowAamMap.put(bm3AamRow.getTrTableAamPK(), bm3AamRow);
            }

            Set<TrTableBm3Akb> toUpdatebm3RowAkb = toUpdateBm3Row.getTrTableBm3AkbSet();
            Map<TrTableAkbPK, TrTableBm3Akb> toUpdatebm3RowAkbMap = new HashMap<>();

            for (TrTableBm3Akb bm3AkbRow : toUpdatebm3RowAkb) {
                toUpdatebm3RowAkbMap.put(bm3AkbRow.getTrTableAkbPK(), bm3AkbRow);
            }

            Set<TrTableBm3Ake> toUpdatebm3RowAke = toUpdateBm3Row.getTrTableBm3AkeSet();
            Map<TrTableAkePK, TrTableBm3Ake> toUpdatebm3RowAkeMap = new HashMap<>();

            for (TrTableBm3Ake bm3AkeRow : toUpdatebm3RowAke) {
                toUpdatebm3RowAkeMap.put(bm3AkeRow.getTrTableAkePK(), bm3AkeRow);
            }

            Set<TrTableBm3Eik> toUpdatebm3RowEik = toUpdateBm3Row.getTrTableBm3EikSet();
            Map<TrTableEikPK, TrTableBm3Eik> toUpdatebm3RowEikMap = new HashMap<>();

            for (TrTableBm3Eik bm3EikRow : toUpdatebm3RowEik) {
                toUpdatebm3RowEikMap.put(bm3EikRow.getTrTableEikPK(), bm3EikRow);
            }

            Set<TrTableBm3Piv> toUpdatebm3RowPiv = toUpdateBm3Row.getTrTableBm3PivSet();
            Map<TrTablePivPK, TrTableBm3Piv> toUpdatebm3RowPivMap = new HashMap<>();

            for (TrTableBm3Piv bm3PivRow : toUpdatebm3RowPiv) {
                toUpdatebm3RowPivMap.put(bm3PivRow.getTrTablePivPK(), bm3PivRow);
            }
            for (TrTableBm3 originalbm3Row : original.getTrTableBm3Set()) {

                if (toUpdateBm3Row.getTrTableBm3PK().equals(originalbm3Row.getTrTableBm3PK())) {
                    changedCount += getChangesTrTableBm3(originalbm3Row, toUpdateBm3Row, action);

                    Set<TrTableBm3Aam> originalbm3RowAam = originalbm3Row.getTrTableBm3AamSet();
                    Map<TrTableAamPK, TrTableBm3Aam> originalbm3RowAamMap = new HashMap<>();
                    for (TrTableBm3Aam bm3aamRow : originalbm3RowAam) {
                        originalbm3RowAamMap.put(bm3aamRow.getTrTableAamPK(), bm3aamRow);
                    }

                    Set<TrTableBm3Akb> originalbm3RowAkb = originalbm3Row.getTrTableBm3AkbSet();
                    Map<TrTableAkbPK, TrTableBm3Akb> originalbm3RowAkbMap = new HashMap<>();
                    for (TrTableBm3Akb bm3aamRow : originalbm3RowAkb) {
                        originalbm3RowAkbMap.put(bm3aamRow.getTrTableAkbPK(), bm3aamRow);
                    }

                    Set<TrTableBm3Ake> originalbm3RowAke = originalbm3Row.getTrTableBm3AkeSet();
                    Map<TrTableAkePK, TrTableBm3Ake> originalbm3RowAkeMap = new HashMap<>();
                    for (TrTableBm3Ake bm3akeRow : originalbm3RowAke) {
                        originalbm3RowAkeMap.put(bm3akeRow.getTrTableAkePK(), bm3akeRow);
                    }

                    Set<TrTableBm3Eik> originalbm3RowEik = originalbm3Row.getTrTableBm3EikSet();
                    Map<TrTableEikPK, TrTableBm3Eik> originalbm3RowEikMap = new HashMap<>();
                    for (TrTableBm3Eik bm3eikRow : originalbm3RowEik) {
                        originalbm3RowEikMap.put(bm3eikRow.getTrTableEikPK(), bm3eikRow);
                    }

                    Set<TrTableBm3Piv> originalbm3RowPiv = originalbm3Row.getTrTableBm3PivSet();
                    Map<TrTablePivPK, TrTableBm3Piv> originalbm3RowPivMap = new HashMap<>();
                    for (TrTableBm3Piv bm3pivRow : originalbm3RowPiv) {
                        originalbm3RowPivMap.put(bm3pivRow.getTrTablePivPK(), bm3pivRow);
                    }

                    Map<TrTableAamPK, TrTableBm3Aam> commonAam = new HashMap<>(originalbm3RowAamMap);
                    commonAam.keySet().retainAll(toUpdatebm3RowAamMap.keySet());
                    for (TrTableAamPK commonPK : commonAam.keySet()) {

                        changedCount += getChangesTrTableBm3Aam(originalbm3RowAamMap.get(commonPK),
                                toUpdatebm3RowAamMap.get(commonPK));
                    }

                    Map<TrTableAkbPK, TrTableBm3Akb> commonAkb = new HashMap<>(originalbm3RowAkbMap);
                    commonAkb.keySet().retainAll(toUpdatebm3RowAkbMap.keySet());
                    for (TrTableAkbPK commonPK : commonAkb.keySet()) {

                        changedCount += getChangesTrTableBm3Akb(originalbm3RowAkbMap.get(commonPK),
                                toUpdatebm3RowAkbMap.get(commonPK));
                    }

                    Map<TrTableAkePK, TrTableBm3Ake> commonAke = new HashMap<>(originalbm3RowAkeMap);
                    commonAke.keySet().retainAll(toUpdatebm3RowAkeMap.keySet());
                    for (TrTableAkePK commonPK : commonAke.keySet()) {

                        changedCount += getChangesTrTableBm3Ake(originalbm3RowAkeMap.get(commonPK),
                                toUpdatebm3RowAkeMap.get(commonPK));
                    }

                    Map<TrTableEikPK, TrTableBm3Eik> commonEik = new HashMap<>(originalbm3RowEikMap);
                    commonEik.keySet().retainAll(toUpdatebm3RowEikMap.keySet());
                    for (TrTableEikPK commonPK : commonEik.keySet()) {

                        changedCount += getChangesTrTableBm3Eik(originalbm3RowEikMap.get(commonPK),
                                toUpdatebm3RowEikMap.get(commonPK));
                    }

                    Map<TrTablePivPK, TrTableBm3Piv> commonPiv = new HashMap<>(originalbm3RowPivMap);
                    commonPiv.keySet().retainAll(toUpdatebm3RowPivMap.keySet());
                    for (TrTablePivPK commonPK : commonPiv.keySet()) {

                        changedCount += getChangesTrTableBm3Piv(originalbm3RowPivMap.get(commonPK),
                                toUpdatebm3RowPivMap.get(commonPK));
                    }
                }
            }
        }

        return changedCount;
    }

    private int getChangesTrTableBsb(TrTableBsb originalData, TrTableBsb newData) {
        int hydrated = 0;

        // =================================
        // metadata adressergaenzung isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ADRESSERGAENZUNG tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAdressergaenzung())) {
            if (!StringUtils.isEmpty(newData.getAdressergaenzung())) {
                originalData.setAdressergaenzung(newData.getAdressergaenzung());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAdressergaenzung())
                    && !originalData.getAdressergaenzung().trim().equals(newData.getAdressergaenzung().trim())))
                    || (StringUtils.isEmpty(newData.getAdressergaenzung())
                            && !originalData.getAdressergaenzung().trim().equals(newData.getAdressergaenzung()))) {
                originalData.setAdressergaenzung(newData.getAdressergaenzung());
                hydrated++;
            }
        }

        // =================================
        // metadata anlageDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANLAGE_DATUM tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getAnlageDatum() == null) {
            if (newData.getAnlageDatum() != null) {
                originalData.setAnlageDatum(newData.getAnlageDatum());
                hydrated++;
            }
        } else {
            if (newData.getAnlageDatum() != null
                    && originalData.getAnlageDatum().compareTo(newData.getAnlageDatum()) != 0) {
                originalData.setAnlageDatum(newData.getAnlageDatum());
                hydrated++;
            } else if (newData.getAnlageDatum() == null) {
                originalData.setAnlageDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata anlageErfasser isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANLAGE_ERFASSER tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAnlageErfasser())) {
            if (!StringUtils.isEmpty(newData.getAnlageErfasser())) {
                originalData.setAnlageErfasser(newData.getAnlageErfasser());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAnlageErfasser())
                    && !originalData.getAnlageErfasser().trim().equals(newData.getAnlageErfasser().trim())))
                    || (StringUtils.isEmpty(newData.getAnlageErfasser())
                            && !originalData.getAnlageErfasser().trim().equals(newData.getAnlageErfasser()))) {
                originalData.setAnlageErfasser(newData.getAnlageErfasser());
                hydrated++;
            }
        }

        // =================================
        // metadata anlageZeit isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANLAGE_ZEIT tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getAnlageZeit() == null) {
            if (newData.getAnlageZeit() != null) {
                originalData.setAnlageZeit(newData.getAnlageZeit());
                hydrated++;
            }
        } else {
            if (newData.getAnlageZeit() != null
                    && originalData.getAnlageZeit().compareTo(newData.getAnlageZeit()) != 0) {
                originalData.setAnlageZeit(newData.getAnlageZeit());
                hydrated++;
            } else if (newData.getAnlageZeit() == null) {
                originalData.setAnlageZeit(null);
                hydrated++;
            }
        }

        // =================================
        // metadata anlass isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANLASS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAnlass())) {
            if (!StringUtils.isEmpty(newData.getAnlass())) {
                originalData.setAnlass(newData.getAnlass());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAnlass())
                    && !originalData.getAnlass().trim().equals(newData.getAnlass().trim())))
                    || (StringUtils.isEmpty(newData.getAnlass())
                            && !originalData.getAnlass().trim().equals(newData.getAnlass()))) {
                originalData.setAnlass(newData.getAnlass());
                hydrated++;
            }
        }

        // =================================
        // metadata antwortFehlerNr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANTWORT_FEHLER_NR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAntwortFehlerNr())) {
            if (!StringUtils.isEmpty(newData.getAntwortFehlerNr())) {
                originalData.setAntwortFehlerNr(newData.getAntwortFehlerNr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAntwortFehlerNr())
                    && !originalData.getAntwortFehlerNr().trim().equals(newData.getAntwortFehlerNr().trim())))
                    || (StringUtils.isEmpty(newData.getAntwortFehlerNr())
                            && !originalData.getAntwortFehlerNr().trim().equals(newData.getAntwortFehlerNr()))) {
                originalData.setAntwortFehlerNr(newData.getAntwortFehlerNr());
                hydrated++;
            }
        }

        // =================================
        // metadata antwortStatus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANTWORT_STATUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAntwortStatus())) {
            if (!StringUtils.isEmpty(newData.getAntwortStatus())) {
                originalData.setAntwortStatus(newData.getAntwortStatus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAntwortStatus())
                    && !originalData.getAntwortStatus().trim().equals(newData.getAntwortStatus().trim())))
                    || (StringUtils.isEmpty(newData.getAntwortStatus())
                            && !originalData.getAntwortStatus().trim().equals(newData.getAntwortStatus()))) {
                originalData.setAntwortStatus(newData.getAntwortStatus());
                hydrated++;
            }
        }

        // =================================
        // metadata anweisungsart isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ANWEISUNGSART tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAnweisungsart())) {
            if (!StringUtils.isEmpty(newData.getAnweisungsart())) {
                originalData.setAnweisungsart(newData.getAnweisungsart());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAnweisungsart())
                    && !originalData.getAnweisungsart().trim().equals(newData.getAnweisungsart().trim())))
                    || (StringUtils.isEmpty(newData.getAnweisungsart())
                            && !originalData.getAnweisungsart().trim().equals(newData.getAnweisungsart()))) {
                originalData.setAnweisungsart(newData.getAnweisungsart());
                hydrated++;
            }
        }

        // =================================
        // metadata art isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ART tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getArt())) {
            if (!StringUtils.isEmpty(newData.getArt())) {
                originalData.setArt(newData.getArt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getArt())
                    && !originalData.getArt().trim().equals(newData.getArt().trim())))
                    || (StringUtils.isEmpty(newData.getArt())
                            && !originalData.getArt().trim().equals(newData.getArt()))) {
                originalData.setArt(newData.getArt());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmAdrErg isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_ADR_ERG tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmAdrErg())) {
            if (!StringUtils.isEmpty(newData.getAufnehmAdrErg())) {
                originalData.setAufnehmAdrErg(newData.getAufnehmAdrErg());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmAdrErg())
                    && !originalData.getAufnehmAdrErg().trim().equals(newData.getAufnehmAdrErg().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmAdrErg())
                            && !originalData.getAufnehmAdrErg().trim().equals(newData.getAufnehmAdrErg()))) {
                originalData.setAufnehmAdrErg(newData.getAufnehmAdrErg());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmAuslPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_AUSL_PLZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmAuslPlz())) {
            if (!StringUtils.isEmpty(newData.getAufnehmAuslPlz())) {
                originalData.setAufnehmAuslPlz(newData.getAufnehmAuslPlz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmAuslPlz())
                    && !originalData.getAufnehmAuslPlz().trim().equals(newData.getAufnehmAuslPlz().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmAuslPlz())
                            && !originalData.getAufnehmAuslPlz().trim().equals(newData.getAufnehmAuslPlz()))) {
                originalData.setAufnehmAuslPlz(newData.getAufnehmAuslPlz());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmBic isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_BIC tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmBic())) {
            if (!StringUtils.isEmpty(newData.getAufnehmBic())) {
                originalData.setAufnehmBic(newData.getAufnehmBic());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmBic())
                    && !originalData.getAufnehmBic().trim().equals(newData.getAufnehmBic().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmBic())
                            && !originalData.getAufnehmBic().trim().equals(newData.getAufnehmBic()))) {
                originalData.setAufnehmBic(newData.getAufnehmBic());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmEmail isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_EMAIL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmEmail())) {
            if (!StringUtils.isEmpty(newData.getAufnehmEmail())) {
                originalData.setAufnehmEmail(newData.getAufnehmEmail());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmEmail())
                    && !originalData.getAufnehmEmail().trim().equals(newData.getAufnehmEmail().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmEmail())
                            && !originalData.getAufnehmEmail().trim().equals(newData.getAufnehmEmail()))) {
                originalData.setAufnehmEmail(newData.getAufnehmEmail());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmGkOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_GK_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmGkOrt())) {
            if (!StringUtils.isEmpty(newData.getAufnehmGkOrt())) {
                originalData.setAufnehmGkOrt(newData.getAufnehmGkOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmGkOrt())
                    && !originalData.getAufnehmGkOrt().trim().equals(newData.getAufnehmGkOrt().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmGkOrt())
                            && !originalData.getAufnehmGkOrt().trim().equals(newData.getAufnehmGkOrt()))) {
                originalData.setAufnehmGkOrt(newData.getAufnehmGkOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmGkPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_GK_PLZ tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAufnehmGkPlz() == null) {
            if (newData.getAufnehmGkPlz() != null) {
                originalData.setAufnehmGkPlz(newData.getAufnehmGkPlz());
                hydrated++;
            }
        } else {
            if (newData.getAufnehmGkPlz() != null
                    && originalData.getAufnehmGkPlz().compareTo(newData.getAufnehmGkPlz()) != 0) {
                originalData.setAufnehmGkPlz(newData.getAufnehmGkPlz());
                hydrated++;
            } else if (newData.getAufnehmGkPlz() == null) {
                originalData.setAufnehmGkPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmHsnr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_HSNR tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getAufnehmHsnr() == null) {
            if (newData.getAufnehmHsnr() != null) {
                originalData.setAufnehmHsnr(newData.getAufnehmHsnr());
                hydrated++;
            }
        } else {
            if (newData.getAufnehmHsnr() != null
                    && originalData.getAufnehmHsnr().compareTo(newData.getAufnehmHsnr()) != 0) {
                originalData.setAufnehmHsnr(newData.getAufnehmHsnr());
                hydrated++;
            } else if (newData.getAufnehmHsnr() == null) {
                originalData.setAufnehmHsnr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmHszu isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_HSZU tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmHszu())) {
            if (!StringUtils.isEmpty(newData.getAufnehmHszu())) {
                originalData.setAufnehmHszu(newData.getAufnehmHszu());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmHszu())
                    && !originalData.getAufnehmHszu().trim().equals(newData.getAufnehmHszu().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmHszu())
                            && !originalData.getAufnehmHszu().trim().equals(newData.getAufnehmHszu()))) {
                originalData.setAufnehmHszu(newData.getAufnehmHszu());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmLand isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_LAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmLand())) {
            if (!StringUtils.isEmpty(newData.getAufnehmLand())) {
                originalData.setAufnehmLand(newData.getAufnehmLand());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmLand())
                    && !originalData.getAufnehmLand().trim().equals(newData.getAufnehmLand().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmLand())
                            && !originalData.getAufnehmLand().trim().equals(newData.getAufnehmLand()))) {
                originalData.setAufnehmLand(newData.getAufnehmLand());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmName isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_NAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmName())) {
            if (!StringUtils.isEmpty(newData.getAufnehmName())) {
                originalData.setAufnehmName(newData.getAufnehmName());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmName())
                    && !originalData.getAufnehmName().trim().equals(newData.getAufnehmName().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmName())
                            && !originalData.getAufnehmName().trim().equals(newData.getAufnehmName()))) {
                originalData.setAufnehmName(newData.getAufnehmName());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmOrdnBegr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_ORDN_BEGR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmOrdnBegr())) {
            if (!StringUtils.isEmpty(newData.getAufnehmOrdnBegr())) {
                originalData.setAufnehmOrdnBegr(newData.getAufnehmOrdnBegr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmOrdnBegr())
                    && !originalData.getAufnehmOrdnBegr().trim().equals(newData.getAufnehmOrdnBegr().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmOrdnBegr())
                            && !originalData.getAufnehmOrdnBegr().trim().equals(newData.getAufnehmOrdnBegr()))) {
                originalData.setAufnehmOrdnBegr(newData.getAufnehmOrdnBegr());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmOrt())) {
            if (!StringUtils.isEmpty(newData.getAufnehmOrt())) {
                originalData.setAufnehmOrt(newData.getAufnehmOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmOrt())
                    && !originalData.getAufnehmOrt().trim().equals(newData.getAufnehmOrt().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmOrt())
                            && !originalData.getAufnehmOrt().trim().equals(newData.getAufnehmOrt()))) {
                originalData.setAufnehmOrt(newData.getAufnehmOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmPfNr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_PF_NR tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAufnehmPfNr() == null) {
            if (newData.getAufnehmPfNr() != null) {
                originalData.setAufnehmPfNr(newData.getAufnehmPfNr());
                hydrated++;
            }
        } else {
            if (newData.getAufnehmPfNr() != null
                    && originalData.getAufnehmPfNr().compareTo(newData.getAufnehmPfNr()) != 0) {
                originalData.setAufnehmPfNr(newData.getAufnehmPfNr());
                hydrated++;
            } else if (newData.getAufnehmPfNr() == null) {
                originalData.setAufnehmPfNr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmPfOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_PF_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmPfOrt())) {
            if (!StringUtils.isEmpty(newData.getAufnehmPfOrt())) {
                originalData.setAufnehmPfOrt(newData.getAufnehmPfOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmPfOrt())
                    && !originalData.getAufnehmPfOrt().trim().equals(newData.getAufnehmPfOrt().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmPfOrt())
                            && !originalData.getAufnehmPfOrt().trim().equals(newData.getAufnehmPfOrt()))) {
                originalData.setAufnehmPfOrt(newData.getAufnehmPfOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmPfPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_PF_PLZ tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAufnehmPfPlz() == null) {
            if (newData.getAufnehmPfPlz() != null) {
                originalData.setAufnehmPfPlz(newData.getAufnehmPfPlz());
                hydrated++;
            }
        } else {
            if (newData.getAufnehmPfPlz() != null
                    && originalData.getAufnehmPfPlz().compareTo(newData.getAufnehmPfPlz()) != 0) {
                originalData.setAufnehmPfPlz(newData.getAufnehmPfPlz());
                hydrated++;
            } else if (newData.getAufnehmPfPlz() == null) {
                originalData.setAufnehmPfPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getAufnehmPlz() == null) {
            if (newData.getAufnehmPlz() != null) {
                originalData.setAufnehmPlz(newData.getAufnehmPlz());
                hydrated++;
            }
        } else {
            if (newData.getAufnehmPlz() != null
                    && originalData.getAufnehmPlz().compareTo(newData.getAufnehmPlz()) != 0) {
                originalData.setAufnehmPlz(newData.getAufnehmPlz());
                hydrated++;
            } else if (newData.getAufnehmPlz() == null) {
                originalData.setAufnehmPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmStaatSl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_STAAT_SL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmStaatSl())) {
            if (!StringUtils.isEmpty(newData.getAufnehmStaatSl())) {
                originalData.setAufnehmStaatSl(newData.getAufnehmStaatSl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmStaatSl())
                    && !originalData.getAufnehmStaatSl().trim().equals(newData.getAufnehmStaatSl().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmStaatSl())
                            && !originalData.getAufnehmStaatSl().trim().equals(newData.getAufnehmStaatSl()))) {
                originalData.setAufnehmStaatSl(newData.getAufnehmStaatSl());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmStr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_STR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmStr())) {
            if (!StringUtils.isEmpty(newData.getAufnehmStr())) {
                originalData.setAufnehmStr(newData.getAufnehmStr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmStr())
                    && !originalData.getAufnehmStr().trim().equals(newData.getAufnehmStr().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmStr())
                            && !originalData.getAufnehmStr().trim().equals(newData.getAufnehmStr()))) {
                originalData.setAufnehmStr(newData.getAufnehmStr());
                hydrated++;
            }
        }

        // =================================
        // metadata aufnehmTel isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUFNEHM_TEL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAufnehmTel())) {
            if (!StringUtils.isEmpty(newData.getAufnehmTel())) {
                originalData.setAufnehmTel(newData.getAufnehmTel());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAufnehmTel())
                    && !originalData.getAufnehmTel().trim().equals(newData.getAufnehmTel().trim())))
                    || (StringUtils.isEmpty(newData.getAufnehmTel())
                            && !originalData.getAufnehmTel().trim().equals(newData.getAufnehmTel()))) {
                originalData.setAufnehmTel(newData.getAufnehmTel());
                hydrated++;
            }
        }

        // =================================
        // metadata auslandsplz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSLANDSPLZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslandsplz())) {
            if (!StringUtils.isEmpty(newData.getAuslandsplz())) {
                originalData.setAuslandsplz(newData.getAuslandsplz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslandsplz())
                    && !originalData.getAuslandsplz().trim().equals(newData.getAuslandsplz().trim())))
                    || (StringUtils.isEmpty(newData.getAuslandsplz())
                            && !originalData.getAuslandsplz().trim().equals(newData.getAuslandsplz()))) {
                originalData.setAuslandsplz(newData.getAuslandsplz());
                hydrated++;
            }
        }

        // =================================
        // metadata ausstellungsdat isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSSTELLUNGSDAT tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAusstellungsdat() == null) {
            if (newData.getAusstellungsdat() != null) {
                originalData.setAusstellungsdat(newData.getAusstellungsdat());
                hydrated++;
            }
        } else {
            if (newData.getAusstellungsdat() != null
                    && originalData.getAusstellungsdat().compareTo(newData.getAusstellungsdat()) != 0) {
                originalData.setAusstellungsdat(newData.getAusstellungsdat());
                hydrated++;
            } else if (newData.getAusstellungsdat() == null) {
                originalData.setAusstellungsdat(null);
                hydrated++;
            }
        }

        // =================================
        // metadata dokuArt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName DOKU_ART tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getDokuArt())) {
            if (!StringUtils.isEmpty(newData.getDokuArt())) {
                originalData.setDokuArt(newData.getDokuArt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getDokuArt())
                    && !originalData.getDokuArt().trim().equals(newData.getDokuArt().trim())))
                    || (StringUtils.isEmpty(newData.getDokuArt())
                            && !originalData.getDokuArt().trim().equals(newData.getDokuArt()))) {
                originalData.setDokuArt(newData.getDokuArt());
                hydrated++;
            }
        }

        // =================================
        // metadata ehegLdnr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName EHEG_LDNR tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getEhegLdnr() == null) {
            if (newData.getEhegLdnr() != null) {
                originalData.setEhegLdnr(newData.getEhegLdnr());
                hydrated++;
            }
        } else {
            if (newData.getEhegLdnr() != null && originalData.getEhegLdnr().compareTo(newData.getEhegLdnr()) != 0) {
                originalData.setEhegLdnr(newData.getEhegLdnr());
                hydrated++;
            } else if (newData.getEhegLdnr() == null) {
                originalData.setEhegLdnr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ergaenzendeStb isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ERGAENZENDE_STB tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getErgaenzendeStb())) {
            if (!StringUtils.isEmpty(newData.getErgaenzendeStb())) {
                originalData.setErgaenzendeStb(newData.getErgaenzendeStb());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getErgaenzendeStb())
                    && !originalData.getErgaenzendeStb().trim().equals(newData.getErgaenzendeStb().trim())))
                    || (StringUtils.isEmpty(newData.getErgaenzendeStb())
                            && !originalData.getErgaenzendeStb().trim().equals(newData.getErgaenzendeStb()))) {
                originalData.setErgaenzendeStb(newData.getErgaenzendeStb());
                hydrated++;
            }
        }

        // =================================
        // metadata fAdrInfo isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_ADR_INFO tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFAdrInfo())) {
            if (!StringUtils.isEmpty(newData.getFAdrInfo())) {
                originalData.setFAdrInfo(newData.getFAdrInfo());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFAdrInfo())
                    && !originalData.getFAdrInfo().trim().equals(newData.getFAdrInfo().trim())))
                    || (StringUtils.isEmpty(newData.getFAdrInfo())
                            && !originalData.getFAdrInfo().trim().equals(newData.getFAdrInfo()))) {
                originalData.setFAdrInfo(newData.getFAdrInfo());
                hydrated++;
            }
        }

        // =================================
        // metadata fAdressergaenzung isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_ADRESSERGAENZUNG tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFAdressergaenzung())) {
            if (!StringUtils.isEmpty(newData.getFAdressergaenzung())) {
                originalData.setFAdressergaenzung(newData.getFAdressergaenzung());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFAdressergaenzung())
                    && !originalData.getFAdressergaenzung().trim().equals(newData.getFAdressergaenzung().trim())))
                    || (StringUtils.isEmpty(newData.getFAdressergaenzung())
                            && !originalData.getFAdressergaenzung().trim().equals(newData.getFAdressergaenzung()))) {
                originalData.setFAdressergaenzung(newData.getFAdressergaenzung());
                hydrated++;
            }
        }

        // =================================
        // metadata fAuslandsPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_AUSLANDS_PLZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFAuslandsPlz())) {
            if (!StringUtils.isEmpty(newData.getFAuslandsPlz())) {
                originalData.setFAuslandsPlz(newData.getFAuslandsPlz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFAuslandsPlz())
                    && !originalData.getFAuslandsPlz().trim().equals(newData.getFAuslandsPlz().trim())))
                    || (StringUtils.isEmpty(newData.getFAuslandsPlz())
                            && !originalData.getFAuslandsPlz().trim().equals(newData.getFAuslandsPlz()))) {
                originalData.setFAuslandsPlz(newData.getFAuslandsPlz());
                hydrated++;
            }
        }

        // =================================
        // metadata fGkPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_GK_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getFGkPlz() == null) {
            if (newData.getFGkPlz() != null) {
                originalData.setFGkPlz(newData.getFGkPlz());
                hydrated++;
            }
        } else {
            if (newData.getFGkPlz() != null && originalData.getFGkPlz().compareTo(newData.getFGkPlz()) != 0) {
                originalData.setFGkPlz(newData.getFGkPlz());
                hydrated++;
            } else if (newData.getFGkPlz() == null) {
                originalData.setFGkPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fGkWohnort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_GK_WOHNORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFGkWohnort())) {
            if (!StringUtils.isEmpty(newData.getFGkWohnort())) {
                originalData.setFGkWohnort(newData.getFGkWohnort());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFGkWohnort())
                    && !originalData.getFGkWohnort().trim().equals(newData.getFGkWohnort().trim())))
                    || (StringUtils.isEmpty(newData.getFGkWohnort())
                            && !originalData.getFGkWohnort().trim().equals(newData.getFGkWohnort()))) {
                originalData.setFGkWohnort(newData.getFGkWohnort());
                hydrated++;
            }
        }

        // =================================
        // metadata fHausnummer isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_HAUSNUMMER tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getFHausnummer() == null) {
            if (newData.getFHausnummer() != null) {
                originalData.setFHausnummer(newData.getFHausnummer());
                hydrated++;
            }
        } else {
            if (newData.getFHausnummer() != null
                    && originalData.getFHausnummer().compareTo(newData.getFHausnummer()) != 0) {
                originalData.setFHausnummer(newData.getFHausnummer());
                hydrated++;
            } else if (newData.getFHausnummer() == null) {
                originalData.setFHausnummer(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fHausnummerZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_HAUSNUMMER_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFHausnummerZus())) {
            if (!StringUtils.isEmpty(newData.getFHausnummerZus())) {
                originalData.setFHausnummerZus(newData.getFHausnummerZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFHausnummerZus())
                    && !originalData.getFHausnummerZus().trim().equals(newData.getFHausnummerZus().trim())))
                    || (StringUtils.isEmpty(newData.getFHausnummerZus())
                            && !originalData.getFHausnummerZus().trim().equals(newData.getFHausnummerZus()))) {
                originalData.setFHausnummerZus(newData.getFHausnummerZus());
                hydrated++;
            }
        }

        // =================================
        // metadata fLand isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_LAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFLand())) {
            if (!StringUtils.isEmpty(newData.getFLand())) {
                originalData.setFLand(newData.getFLand());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFLand())
                    && !originalData.getFLand().trim().equals(newData.getFLand().trim())))
                    || (StringUtils.isEmpty(newData.getFLand())
                            && !originalData.getFLand().trim().equals(newData.getFLand()))) {
                originalData.setFLand(newData.getFLand());
                hydrated++;
            }
        }

        // =================================
        // metadata fOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFOrt())) {
            if (!StringUtils.isEmpty(newData.getFOrt())) {
                originalData.setFOrt(newData.getFOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFOrt())
                    && !originalData.getFOrt().trim().equals(newData.getFOrt().trim())))
                    || (StringUtils.isEmpty(newData.getFOrt())
                            && !originalData.getFOrt().trim().equals(newData.getFOrt()))) {
                originalData.setFOrt(newData.getFOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata fPfOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_PF_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFPfOrt())) {
            if (!StringUtils.isEmpty(newData.getFPfOrt())) {
                originalData.setFPfOrt(newData.getFPfOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFPfOrt())
                    && !originalData.getFPfOrt().trim().equals(newData.getFPfOrt().trim())))
                    || (StringUtils.isEmpty(newData.getFPfOrt())
                            && !originalData.getFPfOrt().trim().equals(newData.getFPfOrt()))) {
                originalData.setFPfOrt(newData.getFPfOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata fPfPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_PF_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getFPfPlz() == null) {
            if (newData.getFPfPlz() != null) {
                originalData.setFPfPlz(newData.getFPfPlz());
                hydrated++;
            }
        } else {
            if (newData.getFPfPlz() != null && originalData.getFPfPlz().compareTo(newData.getFPfPlz()) != 0) {
                originalData.setFPfPlz(newData.getFPfPlz());
                hydrated++;
            } else if (newData.getFPfPlz() == null) {
                originalData.setFPfPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getFPlz() == null) {
            if (newData.getFPlz() != null) {
                originalData.setFPlz(newData.getFPlz());
                hydrated++;
            }
        } else {
            if (newData.getFPlz() != null && originalData.getFPlz().compareTo(newData.getFPlz()) != 0) {
                originalData.setFPlz(newData.getFPlz());
                hydrated++;
            } else if (newData.getFPlz() == null) {
                originalData.setFPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fPostfach isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_POSTFACH tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getFPostfach() == null) {
            if (newData.getFPostfach() != null) {
                originalData.setFPostfach(newData.getFPostfach());
                hydrated++;
            }
        } else {
            if (newData.getFPostfach() != null && originalData.getFPostfach().compareTo(newData.getFPostfach()) != 0) {
                originalData.setFPostfach(newData.getFPostfach());
                hydrated++;
            } else if (newData.getFPostfach() == null) {
                originalData.setFPostfach(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fStaatSchl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_STAAT_SCHL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFStaatSchl())) {
            if (!StringUtils.isEmpty(newData.getFStaatSchl())) {
                originalData.setFStaatSchl(newData.getFStaatSchl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFStaatSchl())
                    && !originalData.getFStaatSchl().trim().equals(newData.getFStaatSchl().trim())))
                    || (StringUtils.isEmpty(newData.getFStaatSchl())
                            && !originalData.getFStaatSchl().trim().equals(newData.getFStaatSchl()))) {
                originalData.setFStaatSchl(newData.getFStaatSchl());
                hydrated++;
            }
        }

        // =================================
        // metadata fStrasse isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_STRASSE tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFStrasse())) {
            if (!StringUtils.isEmpty(newData.getFStrasse())) {
                originalData.setFStrasse(newData.getFStrasse());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFStrasse())
                    && !originalData.getFStrasse().trim().equals(newData.getFStrasse().trim())))
                    || (StringUtils.isEmpty(newData.getFStrasse())
                            && !originalData.getFStrasse().trim().equals(newData.getFStrasse()))) {
                originalData.setFStrasse(newData.getFStrasse());
                hydrated++;
            }
        }

        // =================================
        // metadata fTyp isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName F_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFTyp())) {
            if (!StringUtils.isEmpty(newData.getFTyp())) {
                originalData.setFTyp(newData.getFTyp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFTyp())
                    && !originalData.getFTyp().trim().equals(newData.getFTyp().trim())))
                    || (StringUtils.isEmpty(newData.getFTyp())
                            && !originalData.getFTyp().trim().equals(newData.getFTyp()))) {
                originalData.setFTyp(newData.getFTyp());
                hydrated++;
            }
        }

        // =================================
        // metadata gkOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName GK_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getGkOrt())) {
            if (!StringUtils.isEmpty(newData.getGkOrt())) {
                originalData.setGkOrt(newData.getGkOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getGkOrt())
                    && !originalData.getGkOrt().trim().equals(newData.getGkOrt().trim())))
                    || (StringUtils.isEmpty(newData.getGkOrt())
                            && !originalData.getGkOrt().trim().equals(newData.getGkOrt()))) {
                originalData.setGkOrt(newData.getGkOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata gkPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName GK_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getGkPlz() == null) {
            if (newData.getGkPlz() != null) {
                originalData.setGkPlz(newData.getGkPlz());
                hydrated++;
            }
        } else {
            if (newData.getGkPlz() != null && originalData.getGkPlz().compareTo(newData.getGkPlz()) != 0) {
                originalData.setGkPlz(newData.getGkPlz());
                hydrated++;
            } else if (newData.getGkPlz() == null) {
                originalData.setGkPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata hausnummer isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName HAUSNUMMER tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getHausnummer() == null) {
            if (newData.getHausnummer() != null) {
                originalData.setHausnummer(newData.getHausnummer());
                hydrated++;
            }
        } else {
            if (newData.getHausnummer() != null
                    && originalData.getHausnummer().compareTo(newData.getHausnummer()) != 0) {
                originalData.setHausnummer(newData.getHausnummer());
                hydrated++;
            } else if (newData.getHausnummer() == null) {
                originalData.setHausnummer(null);
                hydrated++;
            }
        }

        // =================================
        // metadata hausnummerZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName HAUSNUMMER_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getHausnummerZus())) {
            if (!StringUtils.isEmpty(newData.getHausnummerZus())) {
                originalData.setHausnummerZus(newData.getHausnummerZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getHausnummerZus())
                    && !originalData.getHausnummerZus().trim().equals(newData.getHausnummerZus().trim())))
                    || (StringUtils.isEmpty(newData.getHausnummerZus())
                            && !originalData.getHausnummerZus().trim().equals(newData.getHausnummerZus()))) {
                originalData.setHausnummerZus(newData.getHausnummerZus());
                hydrated++;
            }
        }

        // =================================
        // metadata kmId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName KM_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKmId())) {
            if (!StringUtils.isEmpty(newData.getKmId())) {
                originalData.setKmId(newData.getKmId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKmId())
                    && !originalData.getKmId().trim().equals(newData.getKmId().trim())))
                    || (StringUtils.isEmpty(newData.getKmId())
                            && !originalData.getKmId().trim().equals(newData.getKmId()))) {
                originalData.setKmId(newData.getKmId());
                hydrated++;
            }
        }

        // =================================
        // metadata land isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName LAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getLand())) {
            if (!StringUtils.isEmpty(newData.getLand())) {
                originalData.setLand(newData.getLand());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getLand())
                    && !originalData.getLand().trim().equals(newData.getLand().trim())))
                    || (StringUtils.isEmpty(newData.getLand())
                            && !originalData.getLand().trim().equals(newData.getLand()))) {
                originalData.setLand(newData.getLand());
                hydrated++;
            }
        }

        // =================================
        // metadata meldeStatus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName MELDE_STATUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getMeldeStatus())) {
            if (!StringUtils.isEmpty(newData.getMeldeStatus())) {
                originalData.setMeldeStatus(newData.getMeldeStatus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getMeldeStatus())
                    && !originalData.getMeldeStatus().trim().equals(newData.getMeldeStatus().trim())))
                    || (StringUtils.isEmpty(newData.getMeldeStatus())
                            && !originalData.getMeldeStatus().trim().equals(newData.getMeldeStatus()))) {
                originalData.setMeldeStatus(newData.getMeldeStatus());
                hydrated++;
            }
        }

        // =================================
        // metadata meldejahr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName MELDEJAHR tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getMeldejahr() == null) {
            if (newData.getMeldejahr() != null) {
                originalData.setMeldejahr(newData.getMeldejahr());
                hydrated++;
            }
        } else {
            if (newData.getMeldejahr() != null && originalData.getMeldejahr().compareTo(newData.getMeldejahr()) != 0) {
                originalData.setMeldejahr(newData.getMeldejahr());
                hydrated++;
            } else if (newData.getMeldejahr() == null) {
                originalData.setMeldejahr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata meldungJjjjmmtt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName MELDUNG_JJJJMMTT tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getMeldungJjjjmmtt() == null) {
            if (newData.getMeldungJjjjmmtt() != null) {
                originalData.setMeldungJjjjmmtt(newData.getMeldungJjjjmmtt());
                hydrated++;
            }
        } else {
            if (newData.getMeldungJjjjmmtt() != null
                    && originalData.getMeldungJjjjmmtt().compareTo(newData.getMeldungJjjjmmtt()) != 0) {
                originalData.setMeldungJjjjmmtt(newData.getMeldungJjjjmmtt());
                hydrated++;
            } else if (newData.getMeldungJjjjmmtt() == null) {
                originalData.setMeldungJjjjmmtt(null);
                hydrated++;
            }
        }

        // =================================
        // metadata meldungUhrHms isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName MELDUNG_UHR_HMS tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getMeldungUhrHms() == null) {
            if (newData.getMeldungUhrHms() != null) {
                originalData.setMeldungUhrHms(newData.getMeldungUhrHms());
                hydrated++;
            }
        } else {
            if (newData.getMeldungUhrHms() != null
                    && originalData.getMeldungUhrHms().compareTo(newData.getMeldungUhrHms()) != 0) {
                originalData.setMeldungUhrHms(newData.getMeldungUhrHms());
                hydrated++;
            } else if (newData.getMeldungUhrHms() == null) {
                originalData.setMeldungUhrHms(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ndNrLfd isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ND_NR_LFD tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getNdNrLfd() == null) {
            if (newData.getNdNrLfd() != null) {
                originalData.setNdNrLfd(newData.getNdNrLfd());
                hydrated++;
            }
        } else {
            if (newData.getNdNrLfd() != null && originalData.getNdNrLfd().compareTo(newData.getNdNrLfd()) != 0) {
                originalData.setNdNrLfd(newData.getNdNrLfd());
                hydrated++;
            } else if (newData.getNdNrLfd() == null) {
                originalData.setNdNrLfd(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ndTicket isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ND_TICKET tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNdTicket())) {
            if (!StringUtils.isEmpty(newData.getNdTicket())) {
                originalData.setNdTicket(newData.getNdTicket());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNdTicket())
                    && !originalData.getNdTicket().trim().equals(newData.getNdTicket().trim())))
                    || (StringUtils.isEmpty(newData.getNdTicket())
                            && !originalData.getNdTicket().trim().equals(newData.getNdTicket()))) {
                originalData.setNdTicket(newData.getNdTicket());
                hydrated++;
            }
        }

        // =================================
        // metadata nnpAusPersstId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNP_AUS_PERSST_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnpAusPersstId())) {
            if (!StringUtils.isEmpty(newData.getNnpAusPersstId())) {
                originalData.setNnpAusPersstId(newData.getNnpAusPersstId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnpAusPersstId())
                    && !originalData.getNnpAusPersstId().trim().equals(newData.getNnpAusPersstId().trim())))
                    || (StringUtils.isEmpty(newData.getNnpAusPersstId())
                            && !originalData.getNnpAusPersstId().trim().equals(newData.getNnpAusPersstId()))) {
                originalData.setNnpAusPersstId(newData.getNnpAusPersstId());
                hydrated++;
            }
        }

        // =================================
        // metadata nnpFirmenname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNP_FIRMENNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnpFirmenname())) {
            if (!StringUtils.isEmpty(newData.getNnpFirmenname())) {
                originalData.setNnpFirmenname(newData.getNnpFirmenname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnpFirmenname())
                    && !originalData.getNnpFirmenname().trim().equals(newData.getNnpFirmenname().trim())))
                    || (StringUtils.isEmpty(newData.getNnpFirmenname())
                            && !originalData.getNnpFirmenname().trim().equals(newData.getNnpFirmenname()))) {
                originalData.setNnpFirmenname(newData.getNnpFirmenname());
                hydrated++;
            }
        }

        // =================================
        // metadata nnpSteuernummer isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNP_STEUERNUMMER tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnpSteuernummer())) {
            if (!StringUtils.isEmpty(newData.getNnpSteuernummer())) {
                originalData.setNnpSteuernummer(newData.getNnpSteuernummer());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnpSteuernummer())
                    && !originalData.getNnpSteuernummer().trim().equals(newData.getNnpSteuernummer().trim())))
                    || (StringUtils.isEmpty(newData.getNnpSteuernummer())
                            && !originalData.getNnpSteuernummer().trim().equals(newData.getNnpSteuernummer()))) {
                originalData.setNnpSteuernummer(newData.getNnpSteuernummer());
                hydrated++;
            }
        }

        // =================================
        // metadata nnpTyp isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNP_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnpTyp())) {
            if (!StringUtils.isEmpty(newData.getNnpTyp())) {
                originalData.setNnpTyp(newData.getNnpTyp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnpTyp())
                    && !originalData.getNnpTyp().trim().equals(newData.getNnpTyp().trim())))
                    || (StringUtils.isEmpty(newData.getNnpTyp())
                            && !originalData.getNnpTyp().trim().equals(newData.getNnpTyp()))) {
                originalData.setNnpTyp(newData.getNnpTyp());
                hydrated++;
            }
        }

        // =================================
        // metadata nnpWid isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNP_WID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnpWid())) {
            if (!StringUtils.isEmpty(newData.getNnpWid())) {
                originalData.setNnpWid(newData.getNnpWid());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnpWid())
                    && !originalData.getNnpWid().trim().equals(newData.getNnpWid().trim())))
                    || (StringUtils.isEmpty(newData.getNnpWid())
                            && !originalData.getNnpWid().trim().equals(newData.getNnpWid()))) {
                originalData.setNnpWid(newData.getNnpWid());
                hydrated++;
            }
        }

        // =================================
        // metadata nnwpAusPerstId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNWP_AUS_PERST_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnwpAusPerstId())) {
            if (!StringUtils.isEmpty(newData.getNnwpAusPerstId())) {
                originalData.setNnwpAusPerstId(newData.getNnwpAusPerstId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnwpAusPerstId())
                    && !originalData.getNnwpAusPerstId().trim().equals(newData.getNnwpAusPerstId().trim())))
                    || (StringUtils.isEmpty(newData.getNnwpAusPerstId())
                            && !originalData.getNnwpAusPerstId().trim().equals(newData.getNnwpAusPerstId()))) {
                originalData.setNnwpAusPerstId(newData.getNnwpAusPerstId());
                hydrated++;
            }
        }

        // =================================
        // metadata nnwpFirmenname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNWP_FIRMENNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnwpFirmenname())) {
            if (!StringUtils.isEmpty(newData.getNnwpFirmenname())) {
                originalData.setNnwpFirmenname(newData.getNnwpFirmenname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnwpFirmenname())
                    && !originalData.getNnwpFirmenname().trim().equals(newData.getNnwpFirmenname().trim())))
                    || (StringUtils.isEmpty(newData.getNnwpFirmenname())
                            && !originalData.getNnwpFirmenname().trim().equals(newData.getNnwpFirmenname()))) {
                originalData.setNnwpFirmenname(newData.getNnwpFirmenname());
                hydrated++;
            }
        }

        // =================================
        // metadata nnwpSteuernummer isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNWP_STEUERNUMMER tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNnwpSteuernummer() == null) {
            if (newData.getNnwpSteuernummer() != null) {
                originalData.setNnwpSteuernummer(newData.getNnwpSteuernummer());
                hydrated++;
            }
        } else {
            if (newData.getNnwpSteuernummer() != null
                    && originalData.getNnwpSteuernummer().compareTo(newData.getNnwpSteuernummer()) != 0) {
                originalData.setNnwpSteuernummer(newData.getNnwpSteuernummer());
                hydrated++;
            } else if (newData.getNnwpSteuernummer() == null) {
                originalData.setNnwpSteuernummer(null);
                hydrated++;
            }
        }

        // =================================
        // metadata nnwpTyp isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNWP_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnwpTyp())) {
            if (!StringUtils.isEmpty(newData.getNnwpTyp())) {
                originalData.setNnwpTyp(newData.getNnwpTyp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnwpTyp())
                    && !originalData.getNnwpTyp().trim().equals(newData.getNnwpTyp().trim())))
                    || (StringUtils.isEmpty(newData.getNnwpTyp())
                            && !originalData.getNnwpTyp().trim().equals(newData.getNnwpTyp()))) {
                originalData.setNnwpTyp(newData.getNnwpTyp());
                hydrated++;
            }
        }

        // =================================
        // metadata nnwpWid isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NNWP_WID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNnwpWid())) {
            if (!StringUtils.isEmpty(newData.getNnwpWid())) {
                originalData.setNnwpWid(newData.getNnwpWid());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNnwpWid())
                    && !originalData.getNnwpWid().trim().equals(newData.getNnwpWid().trim())))
                    || (StringUtils.isEmpty(newData.getNnwpWid())
                            && !originalData.getNnwpWid().trim().equals(newData.getNnwpWid()))) {
                originalData.setNnwpWid(newData.getNnwpWid());
                hydrated++;
            }
        }

        // =================================
        // metadata np1AuslPersstId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_AUSL_PERSST_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1AuslPersstId())) {
            if (!StringUtils.isEmpty(newData.getNp1AuslPersstId())) {
                originalData.setNp1AuslPersstId(newData.getNp1AuslPersstId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1AuslPersstId())
                    && !originalData.getNp1AuslPersstId().trim().equals(newData.getNp1AuslPersstId().trim())))
                    || (StringUtils.isEmpty(newData.getNp1AuslPersstId())
                            && !originalData.getNp1AuslPersstId().trim().equals(newData.getNp1AuslPersstId()))) {
                originalData.setNp1AuslPersstId(newData.getNp1AuslPersstId());
                hydrated++;
            }
        }

        // =================================
        // metadata np1AuswandDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_AUSWAND_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNp1AuswandDatum() == null) {
            if (newData.getNp1AuswandDatum() != null) {
                originalData.setNp1AuswandDatum(newData.getNp1AuswandDatum());
                hydrated++;
            }
        } else {
            if (newData.getNp1AuswandDatum() != null
                    && originalData.getNp1AuswandDatum().compareTo(newData.getNp1AuswandDatum()) != 0) {
                originalData.setNp1AuswandDatum(newData.getNp1AuswandDatum());
                hydrated++;
            } else if (newData.getNp1AuswandDatum() == null) {
                originalData.setNp1AuswandDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np1Gebdat isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBDAT tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getNp1Gebdat() == null) {
            if (newData.getNp1Gebdat() != null) {
                originalData.setNp1Gebdat(newData.getNp1Gebdat());
                hydrated++;
            }
        } else {
            if (newData.getNp1Gebdat() != null && originalData.getNp1Gebdat().compareTo(newData.getNp1Gebdat()) != 0) {
                originalData.setNp1Gebdat(newData.getNp1Gebdat());
                hydrated++;
            } else if (newData.getNp1Gebdat() == null) {
                originalData.setNp1Gebdat(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np1GebnameVors isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBNAME_VORS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1GebnameVors())) {
            if (!StringUtils.isEmpty(newData.getNp1GebnameVors())) {
                originalData.setNp1GebnameVors(newData.getNp1GebnameVors());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1GebnameVors())
                    && !originalData.getNp1GebnameVors().trim().equals(newData.getNp1GebnameVors().trim())))
                    || (StringUtils.isEmpty(newData.getNp1GebnameVors())
                            && !originalData.getNp1GebnameVors().trim().equals(newData.getNp1GebnameVors()))) {
                originalData.setNp1GebnameVors(newData.getNp1GebnameVors());
                hydrated++;
            }
        }

        // =================================
        // metadata np1GebnameZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBNAME_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1GebnameZus())) {
            if (!StringUtils.isEmpty(newData.getNp1GebnameZus())) {
                originalData.setNp1GebnameZus(newData.getNp1GebnameZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1GebnameZus())
                    && !originalData.getNp1GebnameZus().trim().equals(newData.getNp1GebnameZus().trim())))
                    || (StringUtils.isEmpty(newData.getNp1GebnameZus())
                            && !originalData.getNp1GebnameZus().trim().equals(newData.getNp1GebnameZus()))) {
                originalData.setNp1GebnameZus(newData.getNp1GebnameZus());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Geburtsland isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBURTSLAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Geburtsland())) {
            if (!StringUtils.isEmpty(newData.getNp1Geburtsland())) {
                originalData.setNp1Geburtsland(newData.getNp1Geburtsland());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Geburtsland())
                    && !originalData.getNp1Geburtsland().trim().equals(newData.getNp1Geburtsland().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Geburtsland())
                            && !originalData.getNp1Geburtsland().trim().equals(newData.getNp1Geburtsland()))) {
                originalData.setNp1Geburtsland(newData.getNp1Geburtsland());
                hydrated++;
            }
        }

        // =================================
        // metadata np1GeburtslandSl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBURTSLAND_SL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1GeburtslandSl())) {
            if (!StringUtils.isEmpty(newData.getNp1GeburtslandSl())) {
                originalData.setNp1GeburtslandSl(newData.getNp1GeburtslandSl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1GeburtslandSl())
                    && !originalData.getNp1GeburtslandSl().trim().equals(newData.getNp1GeburtslandSl().trim())))
                    || (StringUtils.isEmpty(newData.getNp1GeburtslandSl())
                            && !originalData.getNp1GeburtslandSl().trim().equals(newData.getNp1GeburtslandSl()))) {
                originalData.setNp1GeburtslandSl(newData.getNp1GeburtslandSl());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Geburtsname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBURTSNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Geburtsname())) {
            if (!StringUtils.isEmpty(newData.getNp1Geburtsname())) {
                originalData.setNp1Geburtsname(newData.getNp1Geburtsname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Geburtsname())
                    && !originalData.getNp1Geburtsname().trim().equals(newData.getNp1Geburtsname().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Geburtsname())
                            && !originalData.getNp1Geburtsname().trim().equals(newData.getNp1Geburtsname()))) {
                originalData.setNp1Geburtsname(newData.getNp1Geburtsname());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Geburtsort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GEBURTSORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Geburtsort())) {
            if (!StringUtils.isEmpty(newData.getNp1Geburtsort())) {
                originalData.setNp1Geburtsort(newData.getNp1Geburtsort());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Geburtsort())
                    && !originalData.getNp1Geburtsort().trim().equals(newData.getNp1Geburtsort().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Geburtsort())
                            && !originalData.getNp1Geburtsort().trim().equals(newData.getNp1Geburtsort()))) {
                originalData.setNp1Geburtsort(newData.getNp1Geburtsort());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Geschlecht isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_GESCHLECHT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Geschlecht())) {
            if (!StringUtils.isEmpty(newData.getNp1Geschlecht())) {
                originalData.setNp1Geschlecht(newData.getNp1Geschlecht());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Geschlecht())
                    && !originalData.getNp1Geschlecht().trim().equals(newData.getNp1Geschlecht().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Geschlecht())
                            && !originalData.getNp1Geschlecht().trim().equals(newData.getNp1Geschlecht()))) {
                originalData.setNp1Geschlecht(newData.getNp1Geschlecht());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Idnr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_IDNR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Idnr())) {
            if (!StringUtils.isEmpty(newData.getNp1Idnr())) {
                originalData.setNp1Idnr(newData.getNp1Idnr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Idnr())
                    && !originalData.getNp1Idnr().trim().equals(newData.getNp1Idnr().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Idnr())
                            && !originalData.getNp1Idnr().trim().equals(newData.getNp1Idnr()))) {
                originalData.setNp1Idnr(newData.getNp1Idnr());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Name isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_NAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Name())) {
            if (!StringUtils.isEmpty(newData.getNp1Name())) {
                originalData.setNp1Name(newData.getNp1Name());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Name())
                    && !originalData.getNp1Name().trim().equals(newData.getNp1Name().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Name())
                            && !originalData.getNp1Name().trim().equals(newData.getNp1Name()))) {
                originalData.setNp1Name(newData.getNp1Name());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Namensvorsatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_NAMENSVORSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Namensvorsatz())) {
            if (!StringUtils.isEmpty(newData.getNp1Namensvorsatz())) {
                originalData.setNp1Namensvorsatz(newData.getNp1Namensvorsatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Namensvorsatz())
                    && !originalData.getNp1Namensvorsatz().trim().equals(newData.getNp1Namensvorsatz().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Namensvorsatz())
                            && !originalData.getNp1Namensvorsatz().trim().equals(newData.getNp1Namensvorsatz()))) {
                originalData.setNp1Namensvorsatz(newData.getNp1Namensvorsatz());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Namenszusatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_NAMENSZUSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Namenszusatz())) {
            if (!StringUtils.isEmpty(newData.getNp1Namenszusatz())) {
                originalData.setNp1Namenszusatz(newData.getNp1Namenszusatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Namenszusatz())
                    && !originalData.getNp1Namenszusatz().trim().equals(newData.getNp1Namenszusatz().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Namenszusatz())
                            && !originalData.getNp1Namenszusatz().trim().equals(newData.getNp1Namenszusatz()))) {
                originalData.setNp1Namenszusatz(newData.getNp1Namenszusatz());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Nationalitaet isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_NATIONALITAET tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Nationalitaet())) {
            if (!StringUtils.isEmpty(newData.getNp1Nationalitaet())) {
                originalData.setNp1Nationalitaet(newData.getNp1Nationalitaet());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Nationalitaet())
                    && !originalData.getNp1Nationalitaet().trim().equals(newData.getNp1Nationalitaet().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Nationalitaet())
                            && !originalData.getNp1Nationalitaet().trim().equals(newData.getNp1Nationalitaet()))) {
                originalData.setNp1Nationalitaet(newData.getNp1Nationalitaet());
                hydrated++;
            }
        }

        // =================================
        // metadata np1SterbeDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_STERBE_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNp1SterbeDatum() == null) {
            if (newData.getNp1SterbeDatum() != null) {
                originalData.setNp1SterbeDatum(newData.getNp1SterbeDatum());
                hydrated++;
            }
        } else {
            if (newData.getNp1SterbeDatum() != null
                    && originalData.getNp1SterbeDatum().compareTo(newData.getNp1SterbeDatum()) != 0) {
                originalData.setNp1SterbeDatum(newData.getNp1SterbeDatum());
                hydrated++;
            } else if (newData.getNp1SterbeDatum() == null) {
                originalData.setNp1SterbeDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np1Titel isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_TITEL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Titel())) {
            if (!StringUtils.isEmpty(newData.getNp1Titel())) {
                originalData.setNp1Titel(newData.getNp1Titel());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Titel())
                    && !originalData.getNp1Titel().trim().equals(newData.getNp1Titel().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Titel())
                            && !originalData.getNp1Titel().trim().equals(newData.getNp1Titel()))) {
                originalData.setNp1Titel(newData.getNp1Titel());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Typ isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Typ())) {
            if (!StringUtils.isEmpty(newData.getNp1Typ())) {
                originalData.setNp1Typ(newData.getNp1Typ());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Typ())
                    && !originalData.getNp1Typ().trim().equals(newData.getNp1Typ().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Typ())
                            && !originalData.getNp1Typ().trim().equals(newData.getNp1Typ()))) {
                originalData.setNp1Typ(newData.getNp1Typ());
                hydrated++;
            }
        }

        // =================================
        // metadata np1Vorname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP1_VORNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp1Vorname())) {
            if (!StringUtils.isEmpty(newData.getNp1Vorname())) {
                originalData.setNp1Vorname(newData.getNp1Vorname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp1Vorname())
                    && !originalData.getNp1Vorname().trim().equals(newData.getNp1Vorname().trim())))
                    || (StringUtils.isEmpty(newData.getNp1Vorname())
                            && !originalData.getNp1Vorname().trim().equals(newData.getNp1Vorname()))) {
                originalData.setNp1Vorname(newData.getNp1Vorname());
                hydrated++;
            }
        }

        // =================================
        // metadata np2AuslPersstId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_AUSL_PERSST_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2AuslPersstId())) {
            if (!StringUtils.isEmpty(newData.getNp2AuslPersstId())) {
                originalData.setNp2AuslPersstId(newData.getNp2AuslPersstId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2AuslPersstId())
                    && !originalData.getNp2AuslPersstId().trim().equals(newData.getNp2AuslPersstId().trim())))
                    || (StringUtils.isEmpty(newData.getNp2AuslPersstId())
                            && !originalData.getNp2AuslPersstId().trim().equals(newData.getNp2AuslPersstId()))) {
                originalData.setNp2AuslPersstId(newData.getNp2AuslPersstId());
                hydrated++;
            }
        }

        // =================================
        // metadata np2AuswandDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_AUSWAND_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNp2AuswandDatum() == null) {
            if (newData.getNp2AuswandDatum() != null) {
                originalData.setNp2AuswandDatum(newData.getNp2AuswandDatum());
                hydrated++;
            }
        } else {
            if (newData.getNp2AuswandDatum() != null
                    && originalData.getNp2AuswandDatum().compareTo(newData.getNp2AuswandDatum()) != 0) {
                originalData.setNp2AuswandDatum(newData.getNp2AuswandDatum());
                hydrated++;
            } else if (newData.getNp2AuswandDatum() == null) {
                originalData.setNp2AuswandDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np2Gebdat isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBDAT tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getNp2Gebdat() == null) {
            if (newData.getNp2Gebdat() != null) {
                originalData.setNp2Gebdat(newData.getNp2Gebdat());
                hydrated++;
            }
        } else {
            if (newData.getNp2Gebdat() != null && originalData.getNp2Gebdat().compareTo(newData.getNp2Gebdat()) != 0) {
                originalData.setNp2Gebdat(newData.getNp2Gebdat());
                hydrated++;
            } else if (newData.getNp2Gebdat() == null) {
                originalData.setNp2Gebdat(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np2GebnameVors isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBNAME_VORS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2GebnameVors())) {
            if (!StringUtils.isEmpty(newData.getNp2GebnameVors())) {
                originalData.setNp2GebnameVors(newData.getNp2GebnameVors());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2GebnameVors())
                    && !originalData.getNp2GebnameVors().trim().equals(newData.getNp2GebnameVors().trim())))
                    || (StringUtils.isEmpty(newData.getNp2GebnameVors())
                            && !originalData.getNp2GebnameVors().trim().equals(newData.getNp2GebnameVors()))) {
                originalData.setNp2GebnameVors(newData.getNp2GebnameVors());
                hydrated++;
            }
        }

        // =================================
        // metadata np2GebnameZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBNAME_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2GebnameZus())) {
            if (!StringUtils.isEmpty(newData.getNp2GebnameZus())) {
                originalData.setNp2GebnameZus(newData.getNp2GebnameZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2GebnameZus())
                    && !originalData.getNp2GebnameZus().trim().equals(newData.getNp2GebnameZus().trim())))
                    || (StringUtils.isEmpty(newData.getNp2GebnameZus())
                            && !originalData.getNp2GebnameZus().trim().equals(newData.getNp2GebnameZus()))) {
                originalData.setNp2GebnameZus(newData.getNp2GebnameZus());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Geburtsland isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBURTSLAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Geburtsland())) {
            if (!StringUtils.isEmpty(newData.getNp2Geburtsland())) {
                originalData.setNp2Geburtsland(newData.getNp2Geburtsland());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Geburtsland())
                    && !originalData.getNp2Geburtsland().trim().equals(newData.getNp2Geburtsland().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Geburtsland())
                            && !originalData.getNp2Geburtsland().trim().equals(newData.getNp2Geburtsland()))) {
                originalData.setNp2Geburtsland(newData.getNp2Geburtsland());
                hydrated++;
            }
        }

        // =================================
        // metadata np2GeburtslandSl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBURTSLAND_SL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2GeburtslandSl())) {
            if (!StringUtils.isEmpty(newData.getNp2GeburtslandSl())) {
                originalData.setNp2GeburtslandSl(newData.getNp2GeburtslandSl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2GeburtslandSl())
                    && !originalData.getNp2GeburtslandSl().trim().equals(newData.getNp2GeburtslandSl().trim())))
                    || (StringUtils.isEmpty(newData.getNp2GeburtslandSl())
                            && !originalData.getNp2GeburtslandSl().trim().equals(newData.getNp2GeburtslandSl()))) {
                originalData.setNp2GeburtslandSl(newData.getNp2GeburtslandSl());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Geburtsname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBURTSNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Geburtsname())) {
            if (!StringUtils.isEmpty(newData.getNp2Geburtsname())) {
                originalData.setNp2Geburtsname(newData.getNp2Geburtsname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Geburtsname())
                    && !originalData.getNp2Geburtsname().trim().equals(newData.getNp2Geburtsname().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Geburtsname())
                            && !originalData.getNp2Geburtsname().trim().equals(newData.getNp2Geburtsname()))) {
                originalData.setNp2Geburtsname(newData.getNp2Geburtsname());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Geburtsort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GEBURTSORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Geburtsort())) {
            if (!StringUtils.isEmpty(newData.getNp2Geburtsort())) {
                originalData.setNp2Geburtsort(newData.getNp2Geburtsort());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Geburtsort())
                    && !originalData.getNp2Geburtsort().trim().equals(newData.getNp2Geburtsort().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Geburtsort())
                            && !originalData.getNp2Geburtsort().trim().equals(newData.getNp2Geburtsort()))) {
                originalData.setNp2Geburtsort(newData.getNp2Geburtsort());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Geschlecht isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_GESCHLECHT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Geschlecht())) {
            if (!StringUtils.isEmpty(newData.getNp2Geschlecht())) {
                originalData.setNp2Geschlecht(newData.getNp2Geschlecht());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Geschlecht())
                    && !originalData.getNp2Geschlecht().trim().equals(newData.getNp2Geschlecht().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Geschlecht())
                            && !originalData.getNp2Geschlecht().trim().equals(newData.getNp2Geschlecht()))) {
                originalData.setNp2Geschlecht(newData.getNp2Geschlecht());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Idnr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_IDNR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Idnr())) {
            if (!StringUtils.isEmpty(newData.getNp2Idnr())) {
                originalData.setNp2Idnr(newData.getNp2Idnr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Idnr())
                    && !originalData.getNp2Idnr().trim().equals(newData.getNp2Idnr().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Idnr())
                            && !originalData.getNp2Idnr().trim().equals(newData.getNp2Idnr()))) {
                originalData.setNp2Idnr(newData.getNp2Idnr());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Name isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_NAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Name())) {
            if (!StringUtils.isEmpty(newData.getNp2Name())) {
                originalData.setNp2Name(newData.getNp2Name());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Name())
                    && !originalData.getNp2Name().trim().equals(newData.getNp2Name().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Name())
                            && !originalData.getNp2Name().trim().equals(newData.getNp2Name()))) {
                originalData.setNp2Name(newData.getNp2Name());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Namensvorsatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_NAMENSVORSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Namensvorsatz())) {
            if (!StringUtils.isEmpty(newData.getNp2Namensvorsatz())) {
                originalData.setNp2Namensvorsatz(newData.getNp2Namensvorsatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Namensvorsatz())
                    && !originalData.getNp2Namensvorsatz().trim().equals(newData.getNp2Namensvorsatz().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Namensvorsatz())
                            && !originalData.getNp2Namensvorsatz().trim().equals(newData.getNp2Namensvorsatz()))) {
                originalData.setNp2Namensvorsatz(newData.getNp2Namensvorsatz());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Namenszusatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_NAMENSZUSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Namenszusatz())) {
            if (!StringUtils.isEmpty(newData.getNp2Namenszusatz())) {
                originalData.setNp2Namenszusatz(newData.getNp2Namenszusatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Namenszusatz())
                    && !originalData.getNp2Namenszusatz().trim().equals(newData.getNp2Namenszusatz().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Namenszusatz())
                            && !originalData.getNp2Namenszusatz().trim().equals(newData.getNp2Namenszusatz()))) {
                originalData.setNp2Namenszusatz(newData.getNp2Namenszusatz());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Nationalitaet isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_NATIONALITAET tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Nationalitaet())) {
            if (!StringUtils.isEmpty(newData.getNp2Nationalitaet())) {
                originalData.setNp2Nationalitaet(newData.getNp2Nationalitaet());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Nationalitaet())
                    && !originalData.getNp2Nationalitaet().trim().equals(newData.getNp2Nationalitaet().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Nationalitaet())
                            && !originalData.getNp2Nationalitaet().trim().equals(newData.getNp2Nationalitaet()))) {
                originalData.setNp2Nationalitaet(newData.getNp2Nationalitaet());
                hydrated++;
            }
        }

        // =================================
        // metadata np2SterbeDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_STERBE_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNp2SterbeDatum() == null) {
            if (newData.getNp2SterbeDatum() != null) {
                originalData.setNp2SterbeDatum(newData.getNp2SterbeDatum());
                hydrated++;
            }
        } else {
            if (newData.getNp2SterbeDatum() != null
                    && originalData.getNp2SterbeDatum().compareTo(newData.getNp2SterbeDatum()) != 0) {
                originalData.setNp2SterbeDatum(newData.getNp2SterbeDatum());
                hydrated++;
            } else if (newData.getNp2SterbeDatum() == null) {
                originalData.setNp2SterbeDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata np2Titel isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_TITEL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Titel())) {
            if (!StringUtils.isEmpty(newData.getNp2Titel())) {
                originalData.setNp2Titel(newData.getNp2Titel());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Titel())
                    && !originalData.getNp2Titel().trim().equals(newData.getNp2Titel().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Titel())
                            && !originalData.getNp2Titel().trim().equals(newData.getNp2Titel()))) {
                originalData.setNp2Titel(newData.getNp2Titel());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Typ isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Typ())) {
            if (!StringUtils.isEmpty(newData.getNp2Typ())) {
                originalData.setNp2Typ(newData.getNp2Typ());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Typ())
                    && !originalData.getNp2Typ().trim().equals(newData.getNp2Typ().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Typ())
                            && !originalData.getNp2Typ().trim().equals(newData.getNp2Typ()))) {
                originalData.setNp2Typ(newData.getNp2Typ());
                hydrated++;
            }
        }

        // =================================
        // metadata np2Vorname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NP2_VORNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNp2Vorname())) {
            if (!StringUtils.isEmpty(newData.getNp2Vorname())) {
                originalData.setNp2Vorname(newData.getNp2Vorname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNp2Vorname())
                    && !originalData.getNp2Vorname().trim().equals(newData.getNp2Vorname().trim())))
                    || (StringUtils.isEmpty(newData.getNp2Vorname())
                            && !originalData.getNp2Vorname().trim().equals(newData.getNp2Vorname()))) {
                originalData.setNp2Vorname(newData.getNp2Vorname());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpAuswandDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_AUSWAND_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNwpAuswandDatum() == null) {
            if (newData.getNwpAuswandDatum() != null) {
                originalData.setNwpAuswandDatum(newData.getNwpAuswandDatum());
                hydrated++;
            }
        } else {
            if (newData.getNwpAuswandDatum() != null
                    && originalData.getNwpAuswandDatum().compareTo(newData.getNwpAuswandDatum()) != 0) {
                originalData.setNwpAuswandDatum(newData.getNwpAuswandDatum());
                hydrated++;
            } else if (newData.getNwpAuswandDatum() == null) {
                originalData.setNwpAuswandDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGebLandSchl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEB_LAND_SCHL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGebLandSchl())) {
            if (!StringUtils.isEmpty(newData.getNwpGebLandSchl())) {
                originalData.setNwpGebLandSchl(newData.getNwpGebLandSchl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGebLandSchl())
                    && !originalData.getNwpGebLandSchl().trim().equals(newData.getNwpGebLandSchl().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGebLandSchl())
                            && !originalData.getNwpGebLandSchl().trim().equals(newData.getNwpGebLandSchl()))) {
                originalData.setNwpGebLandSchl(newData.getNwpGebLandSchl());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGebNameVors isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEB_NAME_VORS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGebNameVors())) {
            if (!StringUtils.isEmpty(newData.getNwpGebNameVors())) {
                originalData.setNwpGebNameVors(newData.getNwpGebNameVors());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGebNameVors())
                    && !originalData.getNwpGebNameVors().trim().equals(newData.getNwpGebNameVors().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGebNameVors())
                            && !originalData.getNwpGebNameVors().trim().equals(newData.getNwpGebNameVors()))) {
                originalData.setNwpGebNameVors(newData.getNwpGebNameVors());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGebNameZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEB_NAME_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGebNameZus())) {
            if (!StringUtils.isEmpty(newData.getNwpGebNameZus())) {
                originalData.setNwpGebNameZus(newData.getNwpGebNameZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGebNameZus())
                    && !originalData.getNwpGebNameZus().trim().equals(newData.getNwpGebNameZus().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGebNameZus())
                            && !originalData.getNwpGebNameZus().trim().equals(newData.getNwpGebNameZus()))) {
                originalData.setNwpGebNameZus(newData.getNwpGebNameZus());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGeburtsdatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEBURTSDATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNwpGeburtsdatum() == null) {
            if (newData.getNwpGeburtsdatum() != null) {
                originalData.setNwpGeburtsdatum(newData.getNwpGeburtsdatum());
                hydrated++;
            }
        } else {
            if (newData.getNwpGeburtsdatum() != null
                    && originalData.getNwpGeburtsdatum().compareTo(newData.getNwpGeburtsdatum()) != 0) {
                originalData.setNwpGeburtsdatum(newData.getNwpGeburtsdatum());
                hydrated++;
            } else if (newData.getNwpGeburtsdatum() == null) {
                originalData.setNwpGeburtsdatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGeburtsland isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEBURTSLAND tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGeburtsland())) {
            if (!StringUtils.isEmpty(newData.getNwpGeburtsland())) {
                originalData.setNwpGeburtsland(newData.getNwpGeburtsland());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGeburtsland())
                    && !originalData.getNwpGeburtsland().trim().equals(newData.getNwpGeburtsland().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGeburtsland())
                            && !originalData.getNwpGeburtsland().trim().equals(newData.getNwpGeburtsland()))) {
                originalData.setNwpGeburtsland(newData.getNwpGeburtsland());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGeburtsname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEBURTSNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGeburtsname())) {
            if (!StringUtils.isEmpty(newData.getNwpGeburtsname())) {
                originalData.setNwpGeburtsname(newData.getNwpGeburtsname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGeburtsname())
                    && !originalData.getNwpGeburtsname().trim().equals(newData.getNwpGeburtsname().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGeburtsname())
                            && !originalData.getNwpGeburtsname().trim().equals(newData.getNwpGeburtsname()))) {
                originalData.setNwpGeburtsname(newData.getNwpGeburtsname());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGeburtsort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GEBURTSORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGeburtsort())) {
            if (!StringUtils.isEmpty(newData.getNwpGeburtsort())) {
                originalData.setNwpGeburtsort(newData.getNwpGeburtsort());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGeburtsort())
                    && !originalData.getNwpGeburtsort().trim().equals(newData.getNwpGeburtsort().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGeburtsort())
                            && !originalData.getNwpGeburtsort().trim().equals(newData.getNwpGeburtsort()))) {
                originalData.setNwpGeburtsort(newData.getNwpGeburtsort());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpGeschlecht isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_GESCHLECHT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpGeschlecht())) {
            if (!StringUtils.isEmpty(newData.getNwpGeschlecht())) {
                originalData.setNwpGeschlecht(newData.getNwpGeschlecht());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpGeschlecht())
                    && !originalData.getNwpGeschlecht().trim().equals(newData.getNwpGeschlecht().trim())))
                    || (StringUtils.isEmpty(newData.getNwpGeschlecht())
                            && !originalData.getNwpGeschlecht().trim().equals(newData.getNwpGeschlecht()))) {
                originalData.setNwpGeschlecht(newData.getNwpGeschlecht());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpName isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_NAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpName())) {
            if (!StringUtils.isEmpty(newData.getNwpName())) {
                originalData.setNwpName(newData.getNwpName());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpName())
                    && !originalData.getNwpName().trim().equals(newData.getNwpName().trim())))
                    || (StringUtils.isEmpty(newData.getNwpName())
                            && !originalData.getNwpName().trim().equals(newData.getNwpName()))) {
                originalData.setNwpName(newData.getNwpName());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpNamensvorsatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_NAMENSVORSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpNamensvorsatz())) {
            if (!StringUtils.isEmpty(newData.getNwpNamensvorsatz())) {
                originalData.setNwpNamensvorsatz(newData.getNwpNamensvorsatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpNamensvorsatz())
                    && !originalData.getNwpNamensvorsatz().trim().equals(newData.getNwpNamensvorsatz().trim())))
                    || (StringUtils.isEmpty(newData.getNwpNamensvorsatz())
                            && !originalData.getNwpNamensvorsatz().trim().equals(newData.getNwpNamensvorsatz()))) {
                originalData.setNwpNamensvorsatz(newData.getNwpNamensvorsatz());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpNamenszusatz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_NAMENSZUSATZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpNamenszusatz())) {
            if (!StringUtils.isEmpty(newData.getNwpNamenszusatz())) {
                originalData.setNwpNamenszusatz(newData.getNwpNamenszusatz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpNamenszusatz())
                    && !originalData.getNwpNamenszusatz().trim().equals(newData.getNwpNamenszusatz().trim())))
                    || (StringUtils.isEmpty(newData.getNwpNamenszusatz())
                            && !originalData.getNwpNamenszusatz().trim().equals(newData.getNwpNamenszusatz()))) {
                originalData.setNwpNamenszusatz(newData.getNwpNamenszusatz());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpNationalitaet isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_NATIONALITAET tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpNationalitaet())) {
            if (!StringUtils.isEmpty(newData.getNwpNationalitaet())) {
                originalData.setNwpNationalitaet(newData.getNwpNationalitaet());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpNationalitaet())
                    && !originalData.getNwpNationalitaet().trim().equals(newData.getNwpNationalitaet().trim())))
                    || (StringUtils.isEmpty(newData.getNwpNationalitaet())
                            && !originalData.getNwpNationalitaet().trim().equals(newData.getNwpNationalitaet()))) {
                originalData.setNwpNationalitaet(newData.getNwpNationalitaet());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpPersoninfo isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_PERSONINFO tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpPersoninfo())) {
            if (!StringUtils.isEmpty(newData.getNwpPersoninfo())) {
                originalData.setNwpPersoninfo(newData.getNwpPersoninfo());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpPersoninfo())
                    && !originalData.getNwpPersoninfo().trim().equals(newData.getNwpPersoninfo().trim())))
                    || (StringUtils.isEmpty(newData.getNwpPersoninfo())
                            && !originalData.getNwpPersoninfo().trim().equals(newData.getNwpPersoninfo()))) {
                originalData.setNwpPersoninfo(newData.getNwpPersoninfo());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpSterbeDatum isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_STERBE_DATUM tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getNwpSterbeDatum() == null) {
            if (newData.getNwpSterbeDatum() != null) {
                originalData.setNwpSterbeDatum(newData.getNwpSterbeDatum());
                hydrated++;
            }
        } else {
            if (newData.getNwpSterbeDatum() != null
                    && originalData.getNwpSterbeDatum().compareTo(newData.getNwpSterbeDatum()) != 0) {
                originalData.setNwpSterbeDatum(newData.getNwpSterbeDatum());
                hydrated++;
            } else if (newData.getNwpSterbeDatum() == null) {
                originalData.setNwpSterbeDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata nwpTin isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_TIN tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getNwpTin() == null) {
            if (newData.getNwpTin() != null) {
                originalData.setNwpTin(newData.getNwpTin());
                hydrated++;
            }
        } else {
            if (newData.getNwpTin() != null && originalData.getNwpTin().compareTo(newData.getNwpTin()) != 0) {
                originalData.setNwpTin(newData.getNwpTin());
                hydrated++;
            } else if (newData.getNwpTin() == null) {
                originalData.setNwpTin(null);
                hydrated++;
            }
        }

        // =================================
        // metadata nwpTitel isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_TITEL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpTitel())) {
            if (!StringUtils.isEmpty(newData.getNwpTitel())) {
                originalData.setNwpTitel(newData.getNwpTitel());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpTitel())
                    && !originalData.getNwpTitel().trim().equals(newData.getNwpTitel().trim())))
                    || (StringUtils.isEmpty(newData.getNwpTitel())
                            && !originalData.getNwpTitel().trim().equals(newData.getNwpTitel()))) {
                originalData.setNwpTitel(newData.getNwpTitel());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpTyp isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpTyp())) {
            if (!StringUtils.isEmpty(newData.getNwpTyp())) {
                originalData.setNwpTyp(newData.getNwpTyp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpTyp())
                    && !originalData.getNwpTyp().trim().equals(newData.getNwpTyp().trim())))
                    || (StringUtils.isEmpty(newData.getNwpTyp())
                            && !originalData.getNwpTyp().trim().equals(newData.getNwpTyp()))) {
                originalData.setNwpTyp(newData.getNwpTyp());
                hydrated++;
            }
        }

        // =================================
        // metadata nwpVorname isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName NWP_VORNAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getNwpVorname())) {
            if (!StringUtils.isEmpty(newData.getNwpVorname())) {
                originalData.setNwpVorname(newData.getNwpVorname());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getNwpVorname())
                    && !originalData.getNwpVorname().trim().equals(newData.getNwpVorname().trim())))
                    || (StringUtils.isEmpty(newData.getNwpVorname())
                            && !originalData.getNwpVorname().trim().equals(newData.getNwpVorname()))) {
                originalData.setNwpVorname(newData.getNwpVorname());
                hydrated++;
            }
        }

        // =================================
        // metadata ordnungsbegriff isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ORDNUNGSBEGRIFF tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getOrdnungsbegriff())) {
            if (!StringUtils.isEmpty(newData.getOrdnungsbegriff())) {
                originalData.setOrdnungsbegriff(newData.getOrdnungsbegriff());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getOrdnungsbegriff())
                    && !originalData.getOrdnungsbegriff().trim().equals(newData.getOrdnungsbegriff().trim())))
                    || (StringUtils.isEmpty(newData.getOrdnungsbegriff())
                            && !originalData.getOrdnungsbegriff().trim().equals(newData.getOrdnungsbegriff()))) {
                originalData.setOrdnungsbegriff(newData.getOrdnungsbegriff());
                hydrated++;
            }
        }

        // =================================
        // metadata ort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getOrt())) {
            if (!StringUtils.isEmpty(newData.getOrt())) {
                originalData.setOrt(newData.getOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getOrt())
                    && !originalData.getOrt().trim().equals(newData.getOrt().trim())))
                    || (StringUtils.isEmpty(newData.getOrt())
                            && !originalData.getOrt().trim().equals(newData.getOrt()))) {
                originalData.setOrt(newData.getOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata partnerId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PARTNER_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPartnerId())) {
            if (!StringUtils.isEmpty(newData.getPartnerId())) {
                originalData.setPartnerId(newData.getPartnerId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPartnerId())
                    && !originalData.getPartnerId().trim().equals(newData.getPartnerId().trim())))
                    || (StringUtils.isEmpty(newData.getPartnerId())
                            && !originalData.getPartnerId().trim().equals(newData.getPartnerId()))) {
                originalData.setPartnerId(newData.getPartnerId());
                hydrated++;
            }
        }

        // =================================
        // metadata personId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PERSON_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPersonId())) {
            if (!StringUtils.isEmpty(newData.getPersonId())) {
                originalData.setPersonId(newData.getPersonId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPersonId())
                    && !originalData.getPersonId().trim().equals(newData.getPersonId().trim())))
                    || (StringUtils.isEmpty(newData.getPersonId())
                            && !originalData.getPersonId().trim().equals(newData.getPersonId()))) {
                originalData.setPersonId(newData.getPersonId());
                hydrated++;
            }
        }

        // =================================
        // metadata personRolle isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PERSON_ROLLE tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPersonRolle())) {
            if (!StringUtils.isEmpty(newData.getPersonRolle())) {
                originalData.setPersonRolle(newData.getPersonRolle());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPersonRolle())
                    && !originalData.getPersonRolle().trim().equals(newData.getPersonRolle().trim())))
                    || (StringUtils.isEmpty(newData.getPersonRolle())
                            && !originalData.getPersonRolle().trim().equals(newData.getPersonRolle()))) {
                originalData.setPersonRolle(newData.getPersonRolle());
                hydrated++;
            }
        }

        // =================================
        // metadata pfPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PF_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getPfPlz() == null) {
            if (newData.getPfPlz() != null) {
                originalData.setPfPlz(newData.getPfPlz());
                hydrated++;
            }
        } else {
            if (newData.getPfPlz() != null && originalData.getPfPlz().compareTo(newData.getPfPlz()) != 0) {
                originalData.setPfPlz(newData.getPfPlz());
                hydrated++;
            } else if (newData.getPfPlz() == null) {
                originalData.setPfPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pfWohnort isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PF_WOHNORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPfWohnort())) {
            if (!StringUtils.isEmpty(newData.getPfWohnort())) {
                originalData.setPfWohnort(newData.getPfWohnort());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPfWohnort())
                    && !originalData.getPfWohnort().trim().equals(newData.getPfWohnort().trim())))
                    || (StringUtils.isEmpty(newData.getPfWohnort())
                            && !originalData.getPfWohnort().trim().equals(newData.getPfWohnort()))) {
                originalData.setPfWohnort(newData.getPfWohnort());
                hydrated++;
            }
        }

        // =================================
        // metadata plz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getPlz() == null) {
            if (newData.getPlz() != null) {
                originalData.setPlz(newData.getPlz());
                hydrated++;
            }
        } else {
            if (newData.getPlz() != null && originalData.getPlz().compareTo(newData.getPlz()) != 0) {
                originalData.setPlz(newData.getPlz());
                hydrated++;
            } else if (newData.getPlz() == null) {
                originalData.setPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata postfach isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName POSTFACH tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getPostfach() == null) {
            if (newData.getPostfach() != null) {
                originalData.setPostfach(newData.getPostfach());
                hydrated++;
            }
        } else {
            if (newData.getPostfach() != null && originalData.getPostfach().compareTo(newData.getPostfach()) != 0) {
                originalData.setPostfach(newData.getPostfach());
                hydrated++;
            } else if (newData.getPostfach() == null) {
                originalData.setPostfach(null);
                hydrated++;
            }
        }

        // =================================
        // metadata refKmId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName REF_KM_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getRefKmId())) {
            if (!StringUtils.isEmpty(newData.getRefKmId())) {
                originalData.setRefKmId(newData.getRefKmId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getRefKmId())
                    && !originalData.getRefKmId().trim().equals(newData.getRefKmId().trim())))
                    || (StringUtils.isEmpty(newData.getRefKmId())
                            && !originalData.getRefKmId().trim().equals(newData.getRefKmId()))) {
                originalData.setRefKmId(newData.getRefKmId());
                hydrated++;
            }
        }

        // =================================
        // metadata rolleWeiterePers isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName ROLLE_WEITERE_PERS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getRolleWeiterePers())) {
            if (!StringUtils.isEmpty(newData.getRolleWeiterePers())) {
                originalData.setRolleWeiterePers(newData.getRolleWeiterePers());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getRolleWeiterePers())
                    && !originalData.getRolleWeiterePers().trim().equals(newData.getRolleWeiterePers().trim())))
                    || (StringUtils.isEmpty(newData.getRolleWeiterePers())
                            && !originalData.getRolleWeiterePers().trim().equals(newData.getRolleWeiterePers()))) {
                originalData.setRolleWeiterePers(newData.getRolleWeiterePers());
                hydrated++;
            }
        }

        // =================================
        // metadata staatSchl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STAAT_SCHL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStaatSchl())) {
            if (!StringUtils.isEmpty(newData.getStaatSchl())) {
                originalData.setStaatSchl(newData.getStaatSchl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStaatSchl())
                    && !originalData.getStaatSchl().trim().equals(newData.getStaatSchl().trim())))
                    || (StringUtils.isEmpty(newData.getStaatSchl())
                            && !originalData.getStaatSchl().trim().equals(newData.getStaatSchl()))) {
                originalData.setStaatSchl(newData.getStaatSchl());
                hydrated++;
            }
        }

        // =================================
        // metadata status isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STATUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStatus())) {
            if (!StringUtils.isEmpty(newData.getStatus())) {
                originalData.setStatus(newData.getStatus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStatus())
                    && !originalData.getStatus().trim().equals(newData.getStatus().trim())))
                    || (StringUtils.isEmpty(newData.getStatus())
                            && !originalData.getStatus().trim().equals(newData.getStatus()))) {
                originalData.setStatus(newData.getStatus());
                hydrated++;
            }
        }

        // =================================
        // metadata statusKommentar isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STATUS_KOMMENTAR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStatusKommentar())) {
            if (!StringUtils.isEmpty(newData.getStatusKommentar())) {
                originalData.setStatusKommentar(newData.getStatusKommentar());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStatusKommentar())
                    && !originalData.getStatusKommentar().trim().equals(newData.getStatusKommentar().trim())))
                    || (StringUtils.isEmpty(newData.getStatusKommentar())
                            && !originalData.getStatusKommentar().trim().equals(newData.getStatusKommentar()))) {
                originalData.setStatusKommentar(newData.getStatusKommentar());
                hydrated++;
            }
        }

        // =================================
        // metadata steuerZuord isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STEUER_ZUORD tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSteuerZuord())) {
            if (!StringUtils.isEmpty(newData.getSteuerZuord())) {
                originalData.setSteuerZuord(newData.getSteuerZuord());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSteuerZuord())
                    && !originalData.getSteuerZuord().trim().equals(newData.getSteuerZuord().trim())))
                    || (StringUtils.isEmpty(newData.getSteuerZuord())
                            && !originalData.getSteuerZuord().trim().equals(newData.getSteuerZuord()))) {
                originalData.setSteuerZuord(newData.getSteuerZuord());
                hydrated++;
            }
        }

        // =================================
        // metadata steuerauslaender isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STEUERAUSLAENDER tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSteuerauslaender())) {
            if (!StringUtils.isEmpty(newData.getSteuerauslaender())) {
                originalData.setSteuerauslaender(newData.getSteuerauslaender());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSteuerauslaender())
                    && !originalData.getSteuerauslaender().trim().equals(newData.getSteuerauslaender().trim())))
                    || (StringUtils.isEmpty(newData.getSteuerauslaender())
                            && !originalData.getSteuerauslaender().trim().equals(newData.getSteuerauslaender()))) {
                originalData.setSteuerauslaender(newData.getSteuerauslaender());
                hydrated++;
            }
        }

        // =================================
        // metadata stornoKz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STORNO_KZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStornoKz())) {
            if (!StringUtils.isEmpty(newData.getStornoKz())) {
                originalData.setStornoKz(newData.getStornoKz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStornoKz())
                    && !originalData.getStornoKz().trim().equals(newData.getStornoKz().trim())))
                    || (StringUtils.isEmpty(newData.getStornoKz())
                            && !originalData.getStornoKz().trim().equals(newData.getStornoKz()))) {
                originalData.setStornoKz(newData.getStornoKz());
                hydrated++;
            }
        }

        // =================================
        // metadata strasse isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName STRASSE tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStrasse())) {
            if (!StringUtils.isEmpty(newData.getStrasse())) {
                originalData.setStrasse(newData.getStrasse());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStrasse())
                    && !originalData.getStrasse().trim().equals(newData.getStrasse().trim())))
                    || (StringUtils.isEmpty(newData.getStrasse())
                            && !originalData.getStrasse().trim().equals(newData.getStrasse()))) {
                originalData.setStrasse(newData.getStrasse());
                hydrated++;
            }
        }

        // =================================
        // metadata updateCheck isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName UPDATE_CHECK tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getUpdateCheck() == null) {
            if (newData.getUpdateCheck() != null) {
                originalData.setUpdateCheck(newData.getUpdateCheck());
                hydrated++;
            }
        } else {
            if (newData.getUpdateCheck() != null
                    && originalData.getUpdateCheck().compareTo(newData.getUpdateCheck()) != 0) {
                originalData.setUpdateCheck(newData.getUpdateCheck());
                hydrated++;
            } else if (newData.getUpdateCheck() == null) {
                originalData.setUpdateCheck(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfAdrErg isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_ADR_ERG tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfAdrErg())) {
            if (!StringUtils.isEmpty(newData.getVerfAdrErg())) {
                originalData.setVerfAdrErg(newData.getVerfAdrErg());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfAdrErg())
                    && !originalData.getVerfAdrErg().trim().equals(newData.getVerfAdrErg().trim())))
                    || (StringUtils.isEmpty(newData.getVerfAdrErg())
                            && !originalData.getVerfAdrErg().trim().equals(newData.getVerfAdrErg()))) {
                originalData.setVerfAdrErg(newData.getVerfAdrErg());
                hydrated++;
            }
        }

        // =================================
        // metadata verfBic isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_BIC tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfBic())) {
            if (!StringUtils.isEmpty(newData.getVerfBic())) {
                originalData.setVerfBic(newData.getVerfBic());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfBic())
                    && !originalData.getVerfBic().trim().equals(newData.getVerfBic().trim())))
                    || (StringUtils.isEmpty(newData.getVerfBic())
                            && !originalData.getVerfBic().trim().equals(newData.getVerfBic()))) {
                originalData.setVerfBic(newData.getVerfBic());
                hydrated++;
            }
        }

        // =================================
        // metadata verfEmail isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_EMAIL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfEmail())) {
            if (!StringUtils.isEmpty(newData.getVerfEmail())) {
                originalData.setVerfEmail(newData.getVerfEmail());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfEmail())
                    && !originalData.getVerfEmail().trim().equals(newData.getVerfEmail().trim())))
                    || (StringUtils.isEmpty(newData.getVerfEmail())
                            && !originalData.getVerfEmail().trim().equals(newData.getVerfEmail()))) {
                originalData.setVerfEmail(newData.getVerfEmail());
                hydrated++;
            }
        }

        // =================================
        // metadata verfGkOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_GK_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfGkOrt())) {
            if (!StringUtils.isEmpty(newData.getVerfGkOrt())) {
                originalData.setVerfGkOrt(newData.getVerfGkOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfGkOrt())
                    && !originalData.getVerfGkOrt().trim().equals(newData.getVerfGkOrt().trim())))
                    || (StringUtils.isEmpty(newData.getVerfGkOrt())
                            && !originalData.getVerfGkOrt().trim().equals(newData.getVerfGkOrt()))) {
                originalData.setVerfGkOrt(newData.getVerfGkOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata verfGkPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_GK_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerfGkPlz() == null) {
            if (newData.getVerfGkPlz() != null) {
                originalData.setVerfGkPlz(newData.getVerfGkPlz());
                hydrated++;
            }
        } else {
            if (newData.getVerfGkPlz() != null && originalData.getVerfGkPlz().compareTo(newData.getVerfGkPlz()) != 0) {
                originalData.setVerfGkPlz(newData.getVerfGkPlz());
                hydrated++;
            } else if (newData.getVerfGkPlz() == null) {
                originalData.setVerfGkPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfHsnr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_HSNR tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerfHsnr() == null) {
            if (newData.getVerfHsnr() != null) {
                originalData.setVerfHsnr(newData.getVerfHsnr());
                hydrated++;
            }
        } else {
            if (newData.getVerfHsnr() != null && originalData.getVerfHsnr().compareTo(newData.getVerfHsnr()) != 0) {
                originalData.setVerfHsnr(newData.getVerfHsnr());
                hydrated++;
            } else if (newData.getVerfHsnr() == null) {
                originalData.setVerfHsnr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfHszu isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_HSZU tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfHszu())) {
            if (!StringUtils.isEmpty(newData.getVerfHszu())) {
                originalData.setVerfHszu(newData.getVerfHszu());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfHszu())
                    && !originalData.getVerfHszu().trim().equals(newData.getVerfHszu().trim())))
                    || (StringUtils.isEmpty(newData.getVerfHszu())
                            && !originalData.getVerfHszu().trim().equals(newData.getVerfHszu()))) {
                originalData.setVerfHszu(newData.getVerfHszu());
                hydrated++;
            }
        }

        // =================================
        // metadata verfName isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_NAME tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfName())) {
            if (!StringUtils.isEmpty(newData.getVerfName())) {
                originalData.setVerfName(newData.getVerfName());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfName())
                    && !originalData.getVerfName().trim().equals(newData.getVerfName().trim())))
                    || (StringUtils.isEmpty(newData.getVerfName())
                            && !originalData.getVerfName().trim().equals(newData.getVerfName()))) {
                originalData.setVerfName(newData.getVerfName());
                hydrated++;
            }
        }

        // =================================
        // metadata verfOrdBegriff isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_ORD_BEGRIFF tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfOrdBegriff())) {
            if (!StringUtils.isEmpty(newData.getVerfOrdBegriff())) {
                originalData.setVerfOrdBegriff(newData.getVerfOrdBegriff());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfOrdBegriff())
                    && !originalData.getVerfOrdBegriff().trim().equals(newData.getVerfOrdBegriff().trim())))
                    || (StringUtils.isEmpty(newData.getVerfOrdBegriff())
                            && !originalData.getVerfOrdBegriff().trim().equals(newData.getVerfOrdBegriff()))) {
                originalData.setVerfOrdBegriff(newData.getVerfOrdBegriff());
                hydrated++;
            }
        }

        // =================================
        // metadata verfOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfOrt())) {
            if (!StringUtils.isEmpty(newData.getVerfOrt())) {
                originalData.setVerfOrt(newData.getVerfOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfOrt())
                    && !originalData.getVerfOrt().trim().equals(newData.getVerfOrt().trim())))
                    || (StringUtils.isEmpty(newData.getVerfOrt())
                            && !originalData.getVerfOrt().trim().equals(newData.getVerfOrt()))) {
                originalData.setVerfOrt(newData.getVerfOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata verfPfNr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_PF_NR tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerfPfNr() == null) {
            if (newData.getVerfPfNr() != null) {
                originalData.setVerfPfNr(newData.getVerfPfNr());
                hydrated++;
            }
        } else {
            if (newData.getVerfPfNr() != null && originalData.getVerfPfNr().compareTo(newData.getVerfPfNr()) != 0) {
                originalData.setVerfPfNr(newData.getVerfPfNr());
                hydrated++;
            } else if (newData.getVerfPfNr() == null) {
                originalData.setVerfPfNr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfPfOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_PF_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfPfOrt())) {
            if (!StringUtils.isEmpty(newData.getVerfPfOrt())) {
                originalData.setVerfPfOrt(newData.getVerfPfOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfPfOrt())
                    && !originalData.getVerfPfOrt().trim().equals(newData.getVerfPfOrt().trim())))
                    || (StringUtils.isEmpty(newData.getVerfPfOrt())
                            && !originalData.getVerfPfOrt().trim().equals(newData.getVerfPfOrt()))) {
                originalData.setVerfPfOrt(newData.getVerfPfOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata verfPfPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_PF_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerfPfPlz() == null) {
            if (newData.getVerfPfPlz() != null) {
                originalData.setVerfPfPlz(newData.getVerfPfPlz());
                hydrated++;
            }
        } else {
            if (newData.getVerfPfPlz() != null && originalData.getVerfPfPlz().compareTo(newData.getVerfPfPlz()) != 0) {
                originalData.setVerfPfPlz(newData.getVerfPfPlz());
                hydrated++;
            } else if (newData.getVerfPfPlz() == null) {
                originalData.setVerfPfPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerfPlz() == null) {
            if (newData.getVerfPlz() != null) {
                originalData.setVerfPlz(newData.getVerfPlz());
                hydrated++;
            }
        } else {
            if (newData.getVerfPlz() != null && originalData.getVerfPlz().compareTo(newData.getVerfPlz()) != 0) {
                originalData.setVerfPlz(newData.getVerfPlz());
                hydrated++;
            } else if (newData.getVerfPlz() == null) {
                originalData.setVerfPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verfStr isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_STR tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfStr())) {
            if (!StringUtils.isEmpty(newData.getVerfStr())) {
                originalData.setVerfStr(newData.getVerfStr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfStr())
                    && !originalData.getVerfStr().trim().equals(newData.getVerfStr().trim())))
                    || (StringUtils.isEmpty(newData.getVerfStr())
                            && !originalData.getVerfStr().trim().equals(newData.getVerfStr()))) {
                originalData.setVerfStr(newData.getVerfStr());
                hydrated++;
            }
        }

        // =================================
        // metadata verfTel isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERF_TEL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerfTel())) {
            if (!StringUtils.isEmpty(newData.getVerfTel())) {
                originalData.setVerfTel(newData.getVerfTel());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerfTel())
                    && !originalData.getVerfTel().trim().equals(newData.getVerfTel().trim())))
                    || (StringUtils.isEmpty(newData.getVerfTel())
                            && !originalData.getVerfTel().trim().equals(newData.getVerfTel()))) {
                originalData.setVerfTel(newData.getVerfTel());
                hydrated++;
            }
        }

        // =================================
        // metadata version isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERSION tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVersion() == null) {
            if (newData.getVersion() != null) {
                originalData.setVersion(newData.getVersion());
                hydrated++;
            }
        } else {
            if (newData.getVersion() != null && originalData.getVersion().compareTo(newData.getVersion()) != 0) {
                originalData.setVersion(newData.getVersion());
                hydrated++;
            } else if (newData.getVersion() == null) {
                originalData.setVersion(null);
                hydrated++;
            }
        }

        // =================================
        // metadata versionDetail isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName VERSION_DETAIL tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getVersionDetail() == null) {
            if (newData.getVersionDetail() != null) {
                originalData.setVersionDetail(newData.getVersionDetail());
                hydrated++;
            }
        } else {
            if (newData.getVersionDetail() != null
                    && originalData.getVersionDetail().compareTo(newData.getVersionDetail()) != 0) {
                originalData.setVersionDetail(newData.getVersionDetail());
                hydrated++;
            } else if (newData.getVersionDetail() == null) {
                originalData.setVersionDetail(null);
                hydrated++;
            }
        }

        // =================================
        // metadata weitereErlaueterg isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WEITERE_ERLAUETERG tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWeitereErlaueterg())) {
            if (!StringUtils.isEmpty(newData.getWeitereErlaueterg())) {
                originalData.setWeitereErlaueterg(newData.getWeitereErlaueterg());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWeitereErlaueterg())
                    && !originalData.getWeitereErlaueterg().trim().equals(newData.getWeitereErlaueterg().trim())))
                    || (StringUtils.isEmpty(newData.getWeitereErlaueterg())
                            && !originalData.getWeitereErlaueterg().trim().equals(newData.getWeitereErlaueterg()))) {
                originalData.setWeitereErlaueterg(newData.getWeitereErlaueterg());
                hydrated++;
            }
        }

        // =================================
        // metadata wpAdressergaenz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_ADRESSERGAENZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpAdressergaenz())) {
            if (!StringUtils.isEmpty(newData.getWpAdressergaenz())) {
                originalData.setWpAdressergaenz(newData.getWpAdressergaenz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpAdressergaenz())
                    && !originalData.getWpAdressergaenz().trim().equals(newData.getWpAdressergaenz().trim())))
                    || (StringUtils.isEmpty(newData.getWpAdressergaenz())
                            && !originalData.getWpAdressergaenz().trim().equals(newData.getWpAdressergaenz()))) {
                originalData.setWpAdressergaenz(newData.getWpAdressergaenz());
                hydrated++;
            }
        }

        // =================================
        // metadata wpAuslandsPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_AUSLANDS_PLZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpAuslandsPlz())) {
            if (!StringUtils.isEmpty(newData.getWpAuslandsPlz())) {
                originalData.setWpAuslandsPlz(newData.getWpAuslandsPlz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpAuslandsPlz())
                    && !originalData.getWpAuslandsPlz().trim().equals(newData.getWpAuslandsPlz().trim())))
                    || (StringUtils.isEmpty(newData.getWpAuslandsPlz())
                            && !originalData.getWpAuslandsPlz().trim().equals(newData.getWpAuslandsPlz()))) {
                originalData.setWpAuslandsPlz(newData.getWpAuslandsPlz());
                hydrated++;
            }
        }

        // =================================
        // metadata wpHausnummer isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_HAUSNUMMER tblClassName TrTableBsb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getWpHausnummer() == null) {
            if (newData.getWpHausnummer() != null) {
                originalData.setWpHausnummer(newData.getWpHausnummer());
                hydrated++;
            }
        } else {
            if (newData.getWpHausnummer() != null
                    && originalData.getWpHausnummer().compareTo(newData.getWpHausnummer()) != 0) {
                originalData.setWpHausnummer(newData.getWpHausnummer());
                hydrated++;
            } else if (newData.getWpHausnummer() == null) {
                originalData.setWpHausnummer(null);
                hydrated++;
            }
        }

        // =================================
        // metadata wpHausnummerZus isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_HAUSNUMMER_ZUS tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpHausnummerZus())) {
            if (!StringUtils.isEmpty(newData.getWpHausnummerZus())) {
                originalData.setWpHausnummerZus(newData.getWpHausnummerZus());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpHausnummerZus())
                    && !originalData.getWpHausnummerZus().trim().equals(newData.getWpHausnummerZus().trim())))
                    || (StringUtils.isEmpty(newData.getWpHausnummerZus())
                            && !originalData.getWpHausnummerZus().trim().equals(newData.getWpHausnummerZus()))) {
                originalData.setWpHausnummerZus(newData.getWpHausnummerZus());
                hydrated++;
            }
        }

        // =================================
        // metadata wpInfo isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_INFO tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpInfo())) {
            if (!StringUtils.isEmpty(newData.getWpInfo())) {
                originalData.setWpInfo(newData.getWpInfo());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpInfo())
                    && !originalData.getWpInfo().trim().equals(newData.getWpInfo().trim())))
                    || (StringUtils.isEmpty(newData.getWpInfo())
                            && !originalData.getWpInfo().trim().equals(newData.getWpInfo()))) {
                originalData.setWpInfo(newData.getWpInfo());
                hydrated++;
            }
        }

        // =================================
        // metadata wpLandWP isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_LAND_W_P tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpLandWP())) {
            if (!StringUtils.isEmpty(newData.getWpLandWP())) {
                originalData.setWpLandWP(newData.getWpLandWP());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpLandWP())
                    && !originalData.getWpLandWP().trim().equals(newData.getWpLandWP().trim())))
                    || (StringUtils.isEmpty(newData.getWpLandWP())
                            && !originalData.getWpLandWP().trim().equals(newData.getWpLandWP()))) {
                originalData.setWpLandWP(newData.getWpLandWP());
                hydrated++;
            }
        }

        // =================================
        // metadata wpOrt isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_ORT tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpOrt())) {
            if (!StringUtils.isEmpty(newData.getWpOrt())) {
                originalData.setWpOrt(newData.getWpOrt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpOrt())
                    && !originalData.getWpOrt().trim().equals(newData.getWpOrt().trim())))
                    || (StringUtils.isEmpty(newData.getWpOrt())
                            && !originalData.getWpOrt().trim().equals(newData.getWpOrt()))) {
                originalData.setWpOrt(newData.getWpOrt());
                hydrated++;
            }
        }

        // =================================
        // metadata wpPlz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_PLZ tblClassName TrTableBsb colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getWpPlz() == null) {
            if (newData.getWpPlz() != null) {
                originalData.setWpPlz(newData.getWpPlz());
                hydrated++;
            }
        } else {
            if (newData.getWpPlz() != null && originalData.getWpPlz().compareTo(newData.getWpPlz()) != 0) {
                originalData.setWpPlz(newData.getWpPlz());
                hydrated++;
            } else if (newData.getWpPlz() == null) {
                originalData.setWpPlz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata wpStaatSchl isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_STAAT_SCHL tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpStaatSchl())) {
            if (!StringUtils.isEmpty(newData.getWpStaatSchl())) {
                originalData.setWpStaatSchl(newData.getWpStaatSchl());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpStaatSchl())
                    && !originalData.getWpStaatSchl().trim().equals(newData.getWpStaatSchl().trim())))
                    || (StringUtils.isEmpty(newData.getWpStaatSchl())
                            && !originalData.getWpStaatSchl().trim().equals(newData.getWpStaatSchl()))) {
                originalData.setWpStaatSchl(newData.getWpStaatSchl());
                hydrated++;
            }
        }

        // =================================
        // metadata wpStrasse isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_STRASSE tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpStrasse())) {
            if (!StringUtils.isEmpty(newData.getWpStrasse())) {
                originalData.setWpStrasse(newData.getWpStrasse());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpStrasse())
                    && !originalData.getWpStrasse().trim().equals(newData.getWpStrasse().trim())))
                    || (StringUtils.isEmpty(newData.getWpStrasse())
                            && !originalData.getWpStrasse().trim().equals(newData.getWpStrasse()))) {
                originalData.setWpStrasse(newData.getWpStrasse());
                hydrated++;
            }
        }

        // =================================
        // metadata wpTyp isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName WP_TYP tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getWpTyp())) {
            if (!StringUtils.isEmpty(newData.getWpTyp())) {
                originalData.setWpTyp(newData.getWpTyp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getWpTyp())
                    && !originalData.getWpTyp().trim().equals(newData.getWpTyp().trim())))
                    || (StringUtils.isEmpty(newData.getWpTyp())
                            && !originalData.getWpTyp().trim().equals(newData.getWpTyp()))) {
                originalData.setWpTyp(newData.getWpTyp());
                hydrated++;
            }
        }

        // =================================
        // metadata kontoId isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName KONTO_ID tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKontoId())) {
            if (!StringUtils.isEmpty(newData.getKontoId())) {
                originalData.setKontoId(newData.getKontoId());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKontoId())
                    && !originalData.getKontoId().trim().equals(newData.getKontoId().trim())))
                    || (StringUtils.isEmpty(newData.getKontoId())
                            && !originalData.getKontoId().trim().equals(newData.getKontoId()))) {
                originalData.setKontoId(newData.getKontoId());
                hydrated++;
            }
        }

        // =================================
        // metadata lebendKz isSubtable false parentTable null
        // isFlagOfSubset false isCountOfSubset false
        // colName LEBEND_KZ tblClassName TrTableBsb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getLebendKz())) {
            if (!StringUtils.isEmpty(newData.getLebendKz())) {
                originalData.setLebendKz(newData.getLebendKz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getLebendKz())
                    && !originalData.getLebendKz().trim().equals(newData.getLebendKz().trim())))
                    || (StringUtils.isEmpty(newData.getLebendKz())
                            && !originalData.getLebendKz().trim().equals(newData.getLebendKz()))) {
                originalData.setLebendKz(newData.getLebendKz());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1(TrTableBm1 originalData, TrTableBm1 newData, String action) {
        int hydrated = 0;

        // =================================
        // metadata aamVeraeussert isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName AAM_VERAEUSSERT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAamVeraeussert() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1AamSet())) {
                    originalData.setAamVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm1AamSet().size() < 1
                        && Integer.valueOf(originalData.getAamVeraeussert()) != 0) {
                    originalData.setAamVeraeussert(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm1AamSet().size() > 0
                        && Integer.valueOf(originalData.getAamVeraeussert()) != 1) {
                    originalData.setAamVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata akeVeraeussert isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName AKE_VERAEUSSERT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAkeVeraeussert() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1AkeSet())) {
                    originalData.setAkeVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm1AkeSet().size() < 1
                        && Integer.valueOf(originalData.getAkeVeraeussert()) != 0) {
                    originalData.setAkeVeraeussert(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm1AkeSet().size() > 0
                        && Integer.valueOf(originalData.getAkeVeraeussert()) != 1) {
                    originalData.setAkeVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata aktienErtrGew isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AKTIEN_ERTR_GEW tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAktienErtrGew() == null) {
            if (newData.getAktienErtrGew() != null) {
                originalData.setAktienErtrGew(newData.getAktienErtrGew());
                hydrated++;
            }
        } else {
            if (newData.getAktienErtrGew() != null
                    && originalData.getAktienErtrGew().compareTo(newData.getAktienErtrGew()) != 0) {
                originalData.setAktienErtrGew(newData.getAktienErtrGew());
                hydrated++;
            } else if (newData.getAktienErtrGew() == null) {
                originalData.setAktienErtrGew(null);
                hydrated++;
            }
        }

        // =================================
        // metadata angerechAuslSteu isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ANGERECH_AUSL_STEU tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAngerechAuslSteu() == null) {
            if (newData.getAngerechAuslSteu() != null) {
                originalData.setAngerechAuslSteu(newData.getAngerechAuslSteu());
                hydrated++;
            }
        } else {
            if (newData.getAngerechAuslSteu() != null
                    && originalData.getAngerechAuslSteu().compareTo(newData.getAngerechAuslSteu()) != 0) {
                originalData.setAngerechAuslSteu(newData.getAngerechAuslSteu());
                hydrated++;
            } else if (newData.getAngerechAuslSteu() == null) {
                originalData.setAngerechAuslSteu(null);
                hydrated++;
            }
        }

        // =================================
        // metadata anrechbAuslSteu isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ANRECHB_AUSL_STEU tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAnrechbAuslSteu() == null) {
            if (newData.getAnrechbAuslSteu() != null) {
                originalData.setAnrechbAuslSteu(newData.getAnrechbAuslSteu());
                hydrated++;
            }
        } else {
            if (newData.getAnrechbAuslSteu() != null
                    && originalData.getAnrechbAuslSteu().compareTo(newData.getAnrechbAuslSteu()) != 0) {
                originalData.setAnrechbAuslSteu(newData.getAnrechbAuslSteu());
                hydrated++;
            } else if (newData.getAnrechbAuslSteu() == null) {
                originalData.setAnrechbAuslSteu(null);
                hydrated++;
            }
        }

        // =================================
        // metadata anzahlAam isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_AAM tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlAam() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1AamSet())) {
                    originalData.setAnzahlAam(BigDecimal.valueOf(newData.getTrTableBm1AamSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlAam().intValue() == newData.getTrTableBm1AamSet().size())) {
                    originalData.setAnzahlAam(BigDecimal.valueOf(newData.getTrTableBm1AamSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlAke isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_AKE tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlAke() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1AkeSet())) {
                    originalData.setAnzahlAke(BigDecimal.valueOf(newData.getTrTableBm1AkeSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlAke().intValue() == newData.getTrTableBm1AkeSet().size())) {
                    originalData.setAnzahlAke(BigDecimal.valueOf(newData.getTrTableBm1AkeSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlEik isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_EIK tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlEik() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1EikSet())) {
                    originalData.setAnzahlEik(BigDecimal.valueOf(newData.getTrTableBm1EikSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlEik().intValue() == newData.getTrTableBm1EikSet().size())) {
                    originalData.setAnzahlEik(BigDecimal.valueOf(newData.getTrTableBm1EikSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlPiv isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_PIV tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlPiv() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1PivSet())) {
                    originalData.setAnzahlPiv(BigDecimal.valueOf(newData.getTrTableBm1PivSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlPiv().intValue() == newData.getTrTableBm1PivSet().size())) {
                    originalData.setAnzahlPiv(BigDecimal.valueOf(newData.getTrTableBm1PivSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata aulsKiGut isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AULS_KI_GUT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAulsKiGut())) {
            if (!StringUtils.isEmpty(newData.getAulsKiGut())) {
                originalData.setAulsKiGut(newData.getAulsKiGut());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAulsKiGut())
                    && !originalData.getAulsKiGut().trim().equals(newData.getAulsKiGut().trim())))
                    || (StringUtils.isEmpty(newData.getAulsKiGut())
                            && !originalData.getAulsKiGut().trim().equals(newData.getAulsKiGut()))) {
                originalData.setAulsKiGut(newData.getAulsKiGut());
                hydrated++;
            }
        }

        // =================================
        // metadata auslInvFonds isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_INV_FONDS tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAuslInvFonds() == null) {
            if (newData.getAuslInvFonds() != null) {
                originalData.setAuslInvFonds(newData.getAuslInvFonds());
                hydrated++;
            }
        } else {
            if (newData.getAuslInvFonds() != null
                    && originalData.getAuslInvFonds().compareTo(newData.getAuslInvFonds()) != 0) {
                originalData.setAuslInvFonds(newData.getAuslInvFonds());
                hydrated++;
            } else if (newData.getAuslInvFonds() == null) {
                originalData.setAuslInvFonds(null);
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiAntr isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_ANTR tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiAntr())) {
            if (!StringUtils.isEmpty(newData.getAuslKiAntr())) {
                originalData.setAuslKiAntr(newData.getAuslKiAntr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiAntr())
                    && !originalData.getAuslKiAntr().trim().equals(newData.getAuslKiAntr().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiAntr())
                            && !originalData.getAuslKiAntr().trim().equals(newData.getAuslKiAntr()))) {
                originalData.setAuslKiAntr(newData.getAuslKiAntr());
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiNamStadt isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_NAM_STADT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiNamStadt())) {
            if (!StringUtils.isEmpty(newData.getAuslKiNamStadt())) {
                originalData.setAuslKiNamStadt(newData.getAuslKiNamStadt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiNamStadt())
                    && !originalData.getAuslKiNamStadt().trim().equals(newData.getAuslKiNamStadt().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiNamStadt())
                            && !originalData.getAuslKiNamStadt().trim().equals(newData.getAuslKiNamStadt()))) {
                originalData.setAuslKiNamStadt(newData.getAuslKiNamStadt());
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiVerw isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_VERW tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiVerw())) {
            if (!StringUtils.isEmpty(newData.getAuslKiVerw())) {
                originalData.setAuslKiVerw(newData.getAuslKiVerw());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiVerw())
                    && !originalData.getAuslKiVerw().trim().equals(newData.getAuslKiVerw().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiVerw())
                            && !originalData.getAuslKiVerw().trim().equals(newData.getAuslKiVerw()))) {
                originalData.setAuslKiVerw(newData.getAuslKiVerw());
                hydrated++;
            }
        }

        // =================================
        // metadata depReAnzBesch isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_ANZ_BESCH tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getDepReAnzBesch() == null) {
            if (newData.getDepReAnzBesch() != null) {
                originalData.setDepReAnzBesch(newData.getDepReAnzBesch());
                hydrated++;
            }
        } else {
            if (newData.getDepReAnzBesch() != null
                    && originalData.getDepReAnzBesch().compareTo(newData.getDepReAnzBesch()) != 0) {
                originalData.setDepReAnzBesch(newData.getDepReAnzBesch());
                hydrated++;
            } else if (newData.getDepReAnzBesch() == null) {
                originalData.setDepReAnzBesch(null);
                hydrated++;
            }
        }

        // =================================
        // metadata depReGesamtzahl isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_GESAMTZAHL tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getDepReGesamtzahl() == null) {
            if (newData.getDepReGesamtzahl() != null) {
                originalData.setDepReGesamtzahl(newData.getDepReGesamtzahl());
                hydrated++;
            }
        } else {
            if (newData.getDepReGesamtzahl() != null
                    && originalData.getDepReGesamtzahl().compareTo(newData.getDepReGesamtzahl()) != 0) {
                originalData.setDepReGesamtzahl(newData.getDepReGesamtzahl());
                hydrated++;
            } else if (newData.getDepReGesamtzahl() == null) {
                originalData.setDepReGesamtzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata depReIsin isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_ISIN tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getDepReIsin())) {
            if (!StringUtils.isEmpty(newData.getDepReIsin())) {
                originalData.setDepReIsin(newData.getDepReIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getDepReIsin())
                    && !originalData.getDepReIsin().trim().equals(newData.getDepReIsin().trim())))
                    || (StringUtils.isEmpty(newData.getDepReIsin())
                            && !originalData.getDepReIsin().trim().equals(newData.getDepReIsin()))) {
                originalData.setDepReIsin(newData.getDepReIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata eikErstattet isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName EIK_ERSTATTET tblClassName TrTableBm1 colJavaType String
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getEikErstattet() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1EikSet())) {
                    originalData.setEikErstattet(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm1EikSet().size() < 1 && Integer.valueOf(originalData.getEikErstattet()) != 0) {
                    originalData.setEikErstattet(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm1EikSet().size() > 0 && Integer.valueOf(originalData.getEikErstattet()) != 1) {
                    originalData.setEikErstattet(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata entschaedigung isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ENTSCHAEDIGUNG tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEntschaedigung() == null) {
            if (newData.getEntschaedigung() != null) {
                originalData.setEntschaedigung(newData.getEntschaedigung());
                hydrated++;
            }
        } else {
            if (newData.getEntschaedigung() != null
                    && originalData.getEntschaedigung().compareTo(newData.getEntschaedigung()) != 0) {
                originalData.setEntschaedigung(newData.getEntschaedigung());
                hydrated++;
            } else if (newData.getEntschaedigung() == null) {
                originalData.setEntschaedigung(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ersatzBmg isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERSATZ_BMG tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErsatzBmg() == null) {
            if (newData.getErsatzBmg() != null) {
                originalData.setErsatzBmg(newData.getErsatzBmg());
                hydrated++;
            }
        } else {
            if (newData.getErsatzBmg() != null && originalData.getErsatzBmg().compareTo(newData.getErsatzBmg()) != 0) {
                originalData.setErsatzBmg(newData.getErsatzBmg());
                hydrated++;
            } else if (newData.getErsatzBmg() == null) {
                originalData.setErsatzBmg(null);
                hydrated++;
            }
        }

        // =================================
        // metadata fehlerKennz isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName FEHLER_KENNZ tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFehlerKennz())) {
            if (!StringUtils.isEmpty(newData.getFehlerKennz())) {
                originalData.setFehlerKennz(newData.getFehlerKennz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFehlerKennz())
                    && !originalData.getFehlerKennz().trim().equals(newData.getFehlerKennz().trim())))
                    || (StringUtils.isEmpty(newData.getFehlerKennz())
                            && !originalData.getFehlerKennz().trim().equals(newData.getFehlerKennz()))) {
                originalData.setFehlerKennz(newData.getFehlerKennz());
                hydrated++;
            }
        }

        // =================================
        // metadata formular isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName FORMULAR tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFormular())) {
            if (!StringUtils.isEmpty(newData.getFormular())) {
                originalData.setFormular(newData.getFormular());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFormular())
                    && !originalData.getFormular().trim().equals(newData.getFormular().trim())))
                    || (StringUtils.isEmpty(newData.getFormular())
                            && !originalData.getFormular().trim().equals(newData.getFormular()))) {
                originalData.setFormular(newData.getFormular());
                hydrated++;
            }
        }

        // =================================
        // metadata gewAktVorSon isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName GEW_AKT_VOR_SON tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getGewAktVorSon() == null) {
            if (newData.getGewAktVorSon() != null) {
                originalData.setGewAktVorSon(newData.getGewAktVorSon());
                hydrated++;
            }
        } else {
            if (newData.getGewAktVorSon() != null
                    && originalData.getGewAktVorSon().compareTo(newData.getGewAktVorSon()) != 0) {
                originalData.setGewAktVorSon(newData.getGewAktVorSon());
                hydrated++;
            } else if (newData.getGewAktVorSon() == null) {
                originalData.setGewAktVorSon(null);
                hydrated++;
            }
        }

        // =================================
        // metadata gewAltanteile isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName GEW_ALTANTEILE tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getGewAltanteile() == null) {
            if (newData.getGewAltanteile() != null) {
                originalData.setGewAltanteile(newData.getGewAltanteile());
                hydrated++;
            }
        } else {
            if (newData.getGewAltanteile() != null
                    && originalData.getGewAltanteile().compareTo(newData.getGewAltanteile()) != 0) {
                originalData.setGewAltanteile(newData.getGewAltanteile());
                hydrated++;
            } else if (newData.getGewAltanteile() == null) {
                originalData.setGewAltanteile(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inlKiVerw isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INL_KI_VERW tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getInlKiVerw())) {
            if (!StringUtils.isEmpty(newData.getInlKiVerw())) {
                originalData.setInlKiVerw(newData.getInlKiVerw());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getInlKiVerw())
                    && !originalData.getInlKiVerw().trim().equals(newData.getInlKiVerw().trim())))
                    || (StringUtils.isEmpty(newData.getInlKiVerw())
                            && !originalData.getInlKiVerw().trim().equals(newData.getInlKiVerw()))) {
                originalData.setInlKiVerw(newData.getInlKiVerw());
                hydrated++;
            }
        }

        // =================================
        // metadata kapitalertrag isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KAPITALERTRAG tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getKapitalertrag() == null) {
            if (newData.getKapitalertrag() != null) {
                originalData.setKapitalertrag(newData.getKapitalertrag());
                hydrated++;
            }
        } else {
            if (newData.getKapitalertrag() != null
                    && originalData.getKapitalertrag().compareTo(newData.getKapitalertrag()) != 0) {
                originalData.setKapitalertrag(newData.getKapitalertrag());
                hydrated++;
            } else if (newData.getKapitalertrag() == null) {
                originalData.setKapitalertrag(null);
                hydrated++;
            }
        }

        // =================================
        // metadata kapstAbzug isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KAPST_ABZUG tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getKapstAbzug() == null) {
            if (newData.getKapstAbzug() != null) {
                originalData.setKapstAbzug(newData.getKapstAbzug());
                hydrated++;
            }
        } else {
            if (newData.getKapstAbzug() != null
                    && originalData.getKapstAbzug().compareTo(newData.getKapstAbzug()) != 0) {
                originalData.setKapstAbzug(newData.getKapstAbzug());
                hydrated++;
            } else if (newData.getKapstAbzug() == null) {
                originalData.setKapstAbzug(null);
                hydrated++;
            }
        }

        // =================================
        // metadata keinOriginalM1 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KEIN_ORIGINAL_M1 tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKeinOriginalM1())) {
            if (!StringUtils.isEmpty(newData.getKeinOriginalM1())) {
                originalData.setKeinOriginalM1(newData.getKeinOriginalM1());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKeinOriginalM1())
                    && !originalData.getKeinOriginalM1().trim().equals(newData.getKeinOriginalM1().trim())))
                    || (StringUtils.isEmpty(newData.getKeinOriginalM1())
                            && !originalData.getKeinOriginalM1().trim().equals(newData.getKeinOriginalM1()))) {
                originalData.setKeinOriginalM1(newData.getKeinOriginalM1());
                hydrated++;
            }
        }

        // =================================
        // metadata kistAbzug isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KIST_ABZUG tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getKistAbzug() == null) {
            if (newData.getKistAbzug() != null) {
                originalData.setKistAbzug(newData.getKistAbzug());
                hydrated++;
            }
        } else {
            if (newData.getKistAbzug() != null && originalData.getKistAbzug().compareTo(newData.getKistAbzug()) != 0) {
                originalData.setKistAbzug(newData.getKistAbzug());
                hydrated++;
            } else if (newData.getKistAbzug() == null) {
                originalData.setKistAbzug(null);
                hydrated++;
            }
        }

        // =================================
        // metadata kistKonf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KIST_KONF tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getKistKonf() == null) {
            if (newData.getKistKonf() != null) {
                originalData.setKistKonf(newData.getKistKonf());
                hydrated++;
            }
        } else {
            if (newData.getKistKonf() != null && originalData.getKistKonf().compareTo(newData.getKistKonf()) != 0) {
                originalData.setKistKonf(newData.getKistKonf());
                hydrated++;
            } else if (newData.getKistKonf() == null) {
                originalData.setKistKonf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata liefertiefe isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName LIEFERTIEFE tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getLiefertiefe())) {
            if (!StringUtils.isEmpty(newData.getLiefertiefe())) {
                originalData.setLiefertiefe(newData.getLiefertiefe());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getLiefertiefe())
                    && !originalData.getLiefertiefe().trim().equals(newData.getLiefertiefe().trim())))
                    || (StringUtils.isEmpty(newData.getLiefertiefe())
                            && !originalData.getLiefertiefe().trim().equals(newData.getLiefertiefe()))) {
                originalData.setLiefertiefe(newData.getLiefertiefe());
                hydrated++;
            }
        }

        // =================================
        // metadata ordnungsnummer isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ORDNUNGSNUMMER tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getOrdnungsnummer())) {
            if (!StringUtils.isEmpty(newData.getOrdnungsnummer())) {
                originalData.setOrdnungsnummer(newData.getOrdnungsnummer());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getOrdnungsnummer())
                    && !originalData.getOrdnungsnummer().trim().equals(newData.getOrdnungsnummer().trim())))
                    || (StringUtils.isEmpty(newData.getOrdnungsnummer())
                            && !originalData.getOrdnungsnummer().trim().equals(newData.getOrdnungsnummer()))) {
                originalData.setOrdnungsnummer(newData.getOrdnungsnummer());
                hydrated++;
            }
        }

        // =================================
        // metadata p2KistAbzug isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName P2_KIST_ABZUG tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getP2KistAbzug() == null) {
            if (newData.getP2KistAbzug() != null) {
                originalData.setP2KistAbzug(newData.getP2KistAbzug());
                hydrated++;
            }
        } else {
            if (newData.getP2KistAbzug() != null
                    && originalData.getP2KistAbzug().compareTo(newData.getP2KistAbzug()) != 0) {
                originalData.setP2KistAbzug(newData.getP2KistAbzug());
                hydrated++;
            } else if (newData.getP2KistAbzug() == null) {
                originalData.setP2KistAbzug(null);
                hydrated++;
            }
        }

        // =================================
        // metadata p2KistKonf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName P2_KIST_KONF tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getP2KistKonf() == null) {
            if (newData.getP2KistKonf() != null) {
                originalData.setP2KistKonf(newData.getP2KistKonf());
                hydrated++;
            }
        } else {
            if (newData.getP2KistKonf() != null
                    && originalData.getP2KistKonf().compareTo(newData.getP2KistKonf()) != 0) {
                originalData.setP2KistKonf(newData.getP2KistKonf());
                hydrated++;
            } else if (newData.getP2KistKonf() == null) {
                originalData.setP2KistKonf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pivVerwahrt isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName PIV_VERWAHRT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (!newData.getTrTableBm1PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm1PK().getKeySatzart().equals("H"))) {
            if (originalData.getPivVerwahrt() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm1PivSet())) {
                    originalData.setPivVerwahrt(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm1PivSet().size() < 1 && Integer.valueOf(originalData.getPivVerwahrt()) != 0) {
                    originalData.setPivVerwahrt(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm1PivSet().size() > 0 && Integer.valueOf(originalData.getPivVerwahrt()) != 1) {
                    originalData.setPivVerwahrt(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata privat isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName PRIVAT tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPrivat())) {
            if (!StringUtils.isEmpty(newData.getPrivat())) {
                originalData.setPrivat(newData.getPrivat());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPrivat())
                    && !originalData.getPrivat().trim().equals(newData.getPrivat().trim())))
                    || (StringUtils.isEmpty(newData.getPrivat())
                            && !originalData.getPrivat().trim().equals(newData.getPrivat()))) {
                originalData.setPrivat(newData.getPrivat());
                hydrated++;
            }
        }

        // =================================
        // metadata solzAbzug isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SOLZ_ABZUG tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSolzAbzug() == null) {
            if (newData.getSolzAbzug() != null) {
                originalData.setSolzAbzug(newData.getSolzAbzug());
                hydrated++;
            }
        } else {
            if (newData.getSolzAbzug() != null && originalData.getSolzAbzug().compareTo(newData.getSolzAbzug()) != 0) {
                originalData.setSolzAbzug(newData.getSolzAbzug());
                hydrated++;
            } else if (newData.getSolzAbzug() == null) {
                originalData.setSolzAbzug(null);
                hydrated++;
            }
        }

        // =================================
        // metadata stbJahr isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STB_JAHR tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getStbJahr() == null) {
            if (newData.getStbJahr() != null) {
                originalData.setStbJahr(newData.getStbJahr());
                hydrated++;
            }
        } else {
            if (newData.getStbJahr() != null && originalData.getStbJahr().compareTo(newData.getStbJahr()) != 0) {
                originalData.setStbJahr(newData.getStbJahr());
                hydrated++;
            } else if (newData.getStbJahr() == null) {
                originalData.setStbJahr(null);
                hydrated++;
            }
        }

        // =================================
        // metadata steuerbeschDv isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STEUERBESCH_DV tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSteuerbeschDv())) {
            if (!StringUtils.isEmpty(newData.getSteuerbeschDv())) {
                originalData.setSteuerbeschDv(newData.getSteuerbeschDv());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSteuerbeschDv())
                    && !originalData.getSteuerbeschDv().trim().equals(newData.getSteuerbeschDv().trim())))
                    || (StringUtils.isEmpty(newData.getSteuerbeschDv())
                            && !originalData.getSteuerbeschDv().trim().equals(newData.getSteuerbeschDv()))) {
                originalData.setSteuerbeschDv(newData.getSteuerbeschDv());
                hydrated++;
            }
        }

        // =================================
        // metadata steuerlEinlagekto isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STEUERL_EINLAGEKTO tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSteuerlEinlagekto())) {
            if (!StringUtils.isEmpty(newData.getSteuerlEinlagekto())) {
                originalData.setSteuerlEinlagekto(newData.getSteuerlEinlagekto());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSteuerlEinlagekto())
                    && !originalData.getSteuerlEinlagekto().trim().equals(newData.getSteuerlEinlagekto().trim())))
                    || (StringUtils.isEmpty(newData.getSteuerlEinlagekto())
                            && !originalData.getSteuerlEinlagekto().trim().equals(newData.getSteuerlEinlagekto()))) {
                originalData.setSteuerlEinlagekto(newData.getSteuerlEinlagekto());
                hydrated++;
            }
        }

        // =================================
        // metadata stillhVorSon isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STILLH_VOR_SON tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getStillhVorSon() == null) {
            if (newData.getStillhVorSon() != null) {
                originalData.setStillhVorSon(newData.getStillhVorSon());
                hydrated++;
            }
        } else {
            if (newData.getStillhVorSon() != null
                    && originalData.getStillhVorSon().compareTo(newData.getStillhVorSon()) != 0) {
                originalData.setStillhVorSon(newData.getStillhVorSon());
                hydrated++;
            } else if (newData.getStillhVorSon() == null) {
                originalData.setStillhVorSon(null);
                hydrated++;
            }
        }

        // =================================
        // metadata stillhalterTermin isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STILLHALTER_TERMIN tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getStillhalterTermin() == null) {
            if (newData.getStillhalterTermin() != null) {
                originalData.setStillhalterTermin(newData.getStillhalterTermin());
                hydrated++;
            }
        } else {
            if (newData.getStillhalterTermin() != null
                    && originalData.getStillhalterTermin().compareTo(newData.getStillhalterTermin()) != 0) {
                originalData.setStillhalterTermin(newData.getStillhalterTermin());
                hydrated++;
            } else if (newData.getStillhalterTermin() == null) {
                originalData.setStillhalterTermin(null);
                hydrated++;
            }
        }

        // =================================
        // metadata stornierung isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STORNIERUNG tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStornierung())) {
            if (!StringUtils.isEmpty(newData.getStornierung())) {
                originalData.setStornierung(newData.getStornierung());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStornierung())
                    && !originalData.getStornierung().trim().equals(newData.getStornierung().trim())))
                    || (StringUtils.isEmpty(newData.getStornierung())
                            && !originalData.getStornierung().trim().equals(newData.getStornierung()))) {
                originalData.setStornierung(newData.getStornierung());
                hydrated++;
            }
        }

        // =================================
        // metadata verbrSparerpausch isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERBR_SPARERPAUSCH tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getVerbrSparerpausch() == null) {
            if (newData.getVerbrSparerpausch() != null) {
                originalData.setVerbrSparerpausch(newData.getVerbrSparerpausch());
                hydrated++;
            }
        } else {
            if (newData.getVerbrSparerpausch() != null
                    && originalData.getVerbrSparerpausch().compareTo(newData.getVerbrSparerpausch()) != 0) {
                originalData.setVerbrSparerpausch(newData.getVerbrSparerpausch());
                hydrated++;
            } else if (newData.getVerbrSparerpausch() == null) {
                originalData.setVerbrSparerpausch(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verlTermingesch isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERL_TERMINGESCH tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getVerlTermingesch() == null) {
            if (newData.getVerlTermingesch() != null) {
                originalData.setVerlTermingesch(newData.getVerlTermingesch());
                hydrated++;
            }
        } else {
            if (newData.getVerlTermingesch() != null
                    && originalData.getVerlTermingesch().compareTo(newData.getVerlTermingesch()) != 0) {
                originalData.setVerlTermingesch(newData.getVerlTermingesch());
                hydrated++;
            } else if (newData.getVerlTermingesch() == null) {
                originalData.setVerlTermingesch(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verlWertloUneinb isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERL_WERTLO_UNEINB tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getVerlWertloUneinb() == null) {
            if (newData.getVerlWertloUneinb() != null) {
                originalData.setVerlWertloUneinb(newData.getVerlWertloUneinb());
                hydrated++;
            }
        } else {
            if (newData.getVerlWertloUneinb() != null
                    && originalData.getVerlWertloUneinb().compareTo(newData.getVerlWertloUneinb()) != 0) {
                originalData.setVerlWertloUneinb(newData.getVerlWertloUneinb());
                hydrated++;
            } else if (newData.getVerlWertloUneinb() == null) {
                originalData.setVerlWertloUneinb(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verlustAkt isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERLUST_AKT tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getVerlustAkt() == null) {
            if (newData.getVerlustAkt() != null) {
                originalData.setVerlustAkt(newData.getVerlustAkt());
                hydrated++;
            }
        } else {
            if (newData.getVerlustAkt() != null
                    && originalData.getVerlustAkt().compareTo(newData.getVerlustAkt()) != 0) {
                originalData.setVerlustAkt(newData.getVerlustAkt());
                hydrated++;
            } else if (newData.getVerlustAkt() == null) {
                originalData.setVerlustAkt(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verlustOhneAkt isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERLUST_OHNE_AKT tblClassName TrTableBm1 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getVerlustOhneAkt() == null) {
            if (newData.getVerlustOhneAkt() != null) {
                originalData.setVerlustOhneAkt(newData.getVerlustOhneAkt());
                hydrated++;
            }
        } else {
            if (newData.getVerlustOhneAkt() != null
                    && originalData.getVerlustOhneAkt().compareTo(newData.getVerlustOhneAkt()) != 0) {
                originalData.setVerlustOhneAkt(newData.getVerlustOhneAkt());
                hydrated++;
            } else if (newData.getVerlustOhneAkt() == null) {
                originalData.setVerlustOhneAkt(null);
                hydrated++;
            }
        }

        // =================================
        // metadata verlustbesch isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName VERLUSTBESCH tblClassName TrTableBm1 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getVerlustbesch())) {
            if (!StringUtils.isEmpty(newData.getVerlustbesch())) {
                originalData.setVerlustbesch(newData.getVerlustbesch());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getVerlustbesch())
                    && !originalData.getVerlustbesch().trim().equals(newData.getVerlustbesch().trim())))
                    || (StringUtils.isEmpty(newData.getVerlustbesch())
                            && !originalData.getVerlustbesch().trim().equals(newData.getVerlustbesch()))) {
                originalData.setVerlustbesch(newData.getVerlustbesch());
                hydrated++;
            }
        }

        // =================================
        // metadata zahlungstag isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZAHLUNGSTAG tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getZahlungstag() == null) {
            if (newData.getZahlungstag() != null) {
                originalData.setZahlungstag(newData.getZahlungstag());
                hydrated++;
            }
        } else {
            if (newData.getZahlungstag() != null
                    && originalData.getZahlungstag().compareTo(newData.getZahlungstag()) != 0) {
                originalData.setZahlungstag(newData.getZahlungstag());
                hydrated++;
            } else if (newData.getZahlungstag() == null) {
                originalData.setZahlungstag(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zeitraumBis isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZEITRAUM_BIS tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getZeitraumBis() == null) {
            if (newData.getZeitraumBis() != null) {
                originalData.setZeitraumBis(newData.getZeitraumBis());
                hydrated++;
            }
        } else {
            if (newData.getZeitraumBis() != null
                    && originalData.getZeitraumBis().compareTo(newData.getZeitraumBis()) != 0) {
                originalData.setZeitraumBis(newData.getZeitraumBis());
                hydrated++;
            } else if (newData.getZeitraumBis() == null) {
                originalData.setZeitraumBis(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zeitraumVon isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZEITRAUM_VON tblClassName TrTableBm1 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getZeitraumVon() == null) {
            if (newData.getZeitraumVon() != null) {
                originalData.setZeitraumVon(newData.getZeitraumVon());
                hydrated++;
            }
        } else {
            if (newData.getZeitraumVon() != null
                    && originalData.getZeitraumVon().compareTo(newData.getZeitraumVon()) != 0) {
                originalData.setZeitraumVon(newData.getZeitraumVon());
                hydrated++;
            } else if (newData.getZeitraumVon() == null) {
                originalData.setZeitraumVon(null);
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3(TrTableBm3 originalData, TrTableBm3 newData, String action) {
        int hydrated = 0;

        // =================================
        // metadata abstandSteuerabz isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ABSTAND_STEUERABZ tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAbstandSteuerabz())) {
            if (!StringUtils.isEmpty(newData.getAbstandSteuerabz())) {
                originalData.setAbstandSteuerabz(newData.getAbstandSteuerabz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAbstandSteuerabz())
                    && !originalData.getAbstandSteuerabz().trim().equals(newData.getAbstandSteuerabz().trim())))
                    || (StringUtils.isEmpty(newData.getAbstandSteuerabz())
                            && !originalData.getAbstandSteuerabz().trim().equals(newData.getAbstandSteuerabz()))) {
                originalData.setAbstandSteuerabz(newData.getAbstandSteuerabz());
                hydrated++;
            }
        }

        // =================================
        // metadata abzDreiFueP433 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ABZ_DREI_FUE_P43_3 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAbzDreiFueP433() == null) {
            if (newData.getAbzDreiFueP433() != null) {
                originalData.setAbzDreiFueP433(newData.getAbzDreiFueP433());
                hydrated++;
            }
        } else {
            if (newData.getAbzDreiFueP433() != null
                    && originalData.getAbzDreiFueP433().compareTo(newData.getAbzDreiFueP433()) != 0) {
                originalData.setAbzDreiFueP433(newData.getAbzDreiFueP433());
                hydrated++;
            } else if (newData.getAbzDreiFueP433() == null) {
                originalData.setAbzDreiFueP433(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akbVeraeussert isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName AKB_VERAEUSSERT tblClassName TrTableBm3 colJavaType String
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAkbVeraeussert() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3AkbSet())) {
                    originalData.setAkbVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm3AkbSet().size() < 1
                        && Integer.valueOf(originalData.getAkbVeraeussert()) != 0) {
                    originalData.setAkbVeraeussert(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm3AkbSet().size() > 0
                        && Integer.valueOf(originalData.getAkbVeraeussert()) != 1) {
                    originalData.setAkbVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata akeVeraeussert isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName AKE_VERAEUSSERT tblClassName TrTableBm3 colJavaType String
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAkeVeraeussert() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3AkeSet())) {
                    originalData.setAkeVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm3AkeSet().size() < 1
                        && Integer.valueOf(originalData.getAkeVeraeussert()) != 0) {
                    originalData.setAkeVeraeussert(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm3AkeSet().size() > 0
                        && Integer.valueOf(originalData.getAkeVeraeussert()) != 1) {
                    originalData.setAkeVeraeussert(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlAkb isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_AKB tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlAkb() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3AkbSet())) {
                    originalData.setAnzahlAkb(BigDecimal.valueOf(newData.getTrTableBm3AkbSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlAkb().intValue() == newData.getTrTableBm3AkbSet().size())) {
                    originalData.setAnzahlAkb(BigDecimal.valueOf(newData.getTrTableBm3AkbSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlAke isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_AKE tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlAke() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3AkeSet())) {
                    originalData.setAnzahlAke(BigDecimal.valueOf(newData.getTrTableBm3AkeSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlAke().intValue() == newData.getTrTableBm3AkeSet().size())) {
                    originalData.setAnzahlAke(BigDecimal.valueOf(newData.getTrTableBm3AkeSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlEik isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_EIK tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlEik() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3EikSet())) {
                    originalData.setAnzahlEik(BigDecimal.valueOf(newData.getTrTableBm3EikSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlEik().intValue() == newData.getTrTableBm3EikSet().size())) {
                    originalData.setAnzahlEik(BigDecimal.valueOf(newData.getTrTableBm3EikSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata anzahlPiv isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset true
        // colName ANZAHL_PIV tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getAnzahlPiv() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3PivSet())) {
                    originalData.setAnzahlPiv(BigDecimal.valueOf(newData.getTrTableBm3PivSet().size()));
                    hydrated++;
                }
            } else {
                if (!(originalData.getAnzahlPiv().intValue() == newData.getTrTableBm3PivSet().size())) {
                    originalData.setAnzahlPiv(BigDecimal.valueOf(newData.getTrTableBm3PivSet().size()));
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata aulsKiGut isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AULS_KI_GUT tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAulsKiGut())) {
            if (!StringUtils.isEmpty(newData.getAulsKiGut())) {
                originalData.setAulsKiGut(newData.getAulsKiGut());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAulsKiGut())
                    && !originalData.getAulsKiGut().trim().equals(newData.getAulsKiGut().trim())))
                    || (StringUtils.isEmpty(newData.getAulsKiGut())
                            && !originalData.getAulsKiGut().trim().equals(newData.getAulsKiGut()))) {
                originalData.setAulsKiGut(newData.getAulsKiGut());
                hydrated++;
            }
        }

        // =================================
        // metadata auslInvKStabzug isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_INV_K_STABZUG tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAuslInvKStabzug() == null) {
            if (newData.getAuslInvKStabzug() != null) {
                originalData.setAuslInvKStabzug(newData.getAuslInvKStabzug());
                hydrated++;
            }
        } else {
            if (newData.getAuslInvKStabzug() != null
                    && originalData.getAuslInvKStabzug().compareTo(newData.getAuslInvKStabzug()) != 0) {
                originalData.setAuslInvKStabzug(newData.getAuslInvKStabzug());
                hydrated++;
            } else if (newData.getAuslInvKStabzug() == null) {
                originalData.setAuslInvKStabzug(null);
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiAntr isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_ANTR tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiAntr())) {
            if (!StringUtils.isEmpty(newData.getAuslKiAntr())) {
                originalData.setAuslKiAntr(newData.getAuslKiAntr());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiAntr())
                    && !originalData.getAuslKiAntr().trim().equals(newData.getAuslKiAntr().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiAntr())
                            && !originalData.getAuslKiAntr().trim().equals(newData.getAuslKiAntr()))) {
                originalData.setAuslKiAntr(newData.getAuslKiAntr());
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiNamStadt isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_NAM_STADT tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiNamStadt())) {
            if (!StringUtils.isEmpty(newData.getAuslKiNamStadt())) {
                originalData.setAuslKiNamStadt(newData.getAuslKiNamStadt());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiNamStadt())
                    && !originalData.getAuslKiNamStadt().trim().equals(newData.getAuslKiNamStadt().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiNamStadt())
                            && !originalData.getAuslKiNamStadt().trim().equals(newData.getAuslKiNamStadt()))) {
                originalData.setAuslKiNamStadt(newData.getAuslKiNamStadt());
                hydrated++;
            }
        }

        // =================================
        // metadata auslKiVerw isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_KI_VERW tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslKiVerw())) {
            if (!StringUtils.isEmpty(newData.getAuslKiVerw())) {
                originalData.setAuslKiVerw(newData.getAuslKiVerw());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslKiVerw())
                    && !originalData.getAuslKiVerw().trim().equals(newData.getAuslKiVerw().trim())))
                    || (StringUtils.isEmpty(newData.getAuslKiVerw())
                            && !originalData.getAuslKiVerw().trim().equals(newData.getAuslKiVerw()))) {
                originalData.setAuslKiVerw(newData.getAuslKiVerw());
                hydrated++;
            }
        }

        // =================================
        // metadata auslSpezInv isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName AUSL_SPEZ_INV tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAuslSpezInv())) {
            if (!StringUtils.isEmpty(newData.getAuslSpezInv())) {
                originalData.setAuslSpezInv(newData.getAuslSpezInv());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAuslSpezInv())
                    && !originalData.getAuslSpezInv().trim().equals(newData.getAuslSpezInv().trim())))
                    || (StringUtils.isEmpty(newData.getAuslSpezInv())
                            && !originalData.getAuslSpezInv().trim().equals(newData.getAuslSpezInv()))) {
                originalData.setAuslSpezInv(newData.getAuslSpezInv());
                hydrated++;
            }
        }

        // =================================
        // metadata depReAnzBesch isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_ANZ_BESCH tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getDepReAnzBesch() == null) {
            if (newData.getDepReAnzBesch() != null) {
                originalData.setDepReAnzBesch(newData.getDepReAnzBesch());
                hydrated++;
            }
        } else {
            if (newData.getDepReAnzBesch() != null
                    && originalData.getDepReAnzBesch().compareTo(newData.getDepReAnzBesch()) != 0) {
                originalData.setDepReAnzBesch(newData.getDepReAnzBesch());
                hydrated++;
            } else if (newData.getDepReAnzBesch() == null) {
                originalData.setDepReAnzBesch(null);
                hydrated++;
            }
        }

        // =================================
        // metadata depReGesamtzahl isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_GESAMTZAHL tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getDepReGesamtzahl() == null) {
            if (newData.getDepReGesamtzahl() != null) {
                originalData.setDepReGesamtzahl(newData.getDepReGesamtzahl());
                hydrated++;
            }
        } else {
            if (newData.getDepReGesamtzahl() != null
                    && originalData.getDepReGesamtzahl().compareTo(newData.getDepReGesamtzahl()) != 0) {
                originalData.setDepReGesamtzahl(newData.getDepReGesamtzahl());
                hydrated++;
            } else if (newData.getDepReGesamtzahl() == null) {
                originalData.setDepReGesamtzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata depReIsin isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName DEP_RE_ISIN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getDepReIsin())) {
            if (!StringUtils.isEmpty(newData.getDepReIsin())) {
                originalData.setDepReIsin(newData.getDepReIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getDepReIsin())
                    && !originalData.getDepReIsin().trim().equals(newData.getDepReIsin().trim())))
                    || (StringUtils.isEmpty(newData.getDepReIsin())
                            && !originalData.getDepReIsin().trim().equals(newData.getDepReIsin()))) {
                originalData.setDepReIsin(newData.getDepReIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata eikErstattet isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName EIK_ERSTATTET tblClassName TrTableBm3 colJavaType String
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getEikErstattet() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3EikSet())) {
                    originalData.setEikErstattet(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm3EikSet().size() < 1 && Integer.valueOf(originalData.getEikErstattet()) != 0) {
                    originalData.setEikErstattet(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm3EikSet().size() > 0 && Integer.valueOf(originalData.getEikErstattet()) != 1) {
                    originalData.setEikErstattet(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata einlagekontoSum isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName EINLAGEKONTO_SUM tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEinlagekontoSum() == null) {
            if (newData.getEinlagekontoSum() != null) {
                originalData.setEinlagekontoSum(newData.getEinlagekontoSum());
                hydrated++;
            }
        } else {
            if (newData.getEinlagekontoSum() != null
                    && originalData.getEinlagekontoSum().compareTo(newData.getEinlagekontoSum()) != 0) {
                originalData.setEinlagekontoSum(newData.getEinlagekontoSum());
                hydrated++;
            } else if (newData.getEinlagekontoSum() == null) {
                originalData.setEinlagekontoSum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ersatzBmg isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERSATZ_BMG tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErsatzBmg() == null) {
            if (newData.getErsatzBmg() != null) {
                originalData.setErsatzBmg(newData.getErsatzBmg());
                hydrated++;
            }
        } else {
            if (newData.getErsatzBmg() != null && originalData.getErsatzBmg().compareTo(newData.getErsatzBmg()) != 0) {
                originalData.setErsatzBmg(newData.getErsatzBmg());
                hydrated++;
            } else if (newData.getErsatzBmg() == null) {
                originalData.setErsatzBmg(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertBeschrStpfl1 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERT_BESCHR_STPFL_1 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtBeschrStpfl1() == null) {
            if (newData.getErtBeschrStpfl1() != null) {
                originalData.setErtBeschrStpfl1(newData.getErtBeschrStpfl1());
                hydrated++;
            }
        } else {
            if (newData.getErtBeschrStpfl1() != null
                    && originalData.getErtBeschrStpfl1().compareTo(newData.getErtBeschrStpfl1()) != 0) {
                originalData.setErtBeschrStpfl1(newData.getErtBeschrStpfl1());
                hydrated++;
            } else if (newData.getErtBeschrStpfl1() == null) {
                originalData.setErtBeschrStpfl1(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertBeschrStpfl2 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERT_BESCHR_STPFL_2 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtBeschrStpfl2() == null) {
            if (newData.getErtBeschrStpfl2() != null) {
                originalData.setErtBeschrStpfl2(newData.getErtBeschrStpfl2());
                hydrated++;
            }
        } else {
            if (newData.getErtBeschrStpfl2() != null
                    && originalData.getErtBeschrStpfl2().compareTo(newData.getErtBeschrStpfl2()) != 0) {
                originalData.setErtBeschrStpfl2(newData.getErtBeschrStpfl2());
                hydrated++;
            } else if (newData.getErtBeschrStpfl2() == null) {
                originalData.setErtBeschrStpfl2(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP19Reit1 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P19_REIT_1 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtragP19Reit1() == null) {
            if (newData.getErtragP19Reit1() != null) {
                originalData.setErtragP19Reit1(newData.getErtragP19Reit1());
                hydrated++;
            }
        } else {
            if (newData.getErtragP19Reit1() != null
                    && originalData.getErtragP19Reit1().compareTo(newData.getErtragP19Reit1()) != 0) {
                originalData.setErtragP19Reit1(newData.getErtragP19Reit1());
                hydrated++;
            } else if (newData.getErtragP19Reit1() == null) {
                originalData.setErtragP19Reit1(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP19Reit6 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P19_REIT_6 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtragP19Reit6() == null) {
            if (newData.getErtragP19Reit6() != null) {
                originalData.setErtragP19Reit6(newData.getErtragP19Reit6());
                hydrated++;
            }
        } else {
            if (newData.getErtragP19Reit6() != null
                    && originalData.getErtragP19Reit6().compareTo(newData.getErtragP19Reit6()) != 0) {
                originalData.setErtragP19Reit6(newData.getErtragP19Reit6());
                hydrated++;
            } else if (newData.getErtragP19Reit6() == null) {
                originalData.setErtragP19Reit6(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP431 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_1 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP431() == null) {
            if (newData.getErtragP431() != null) {
                originalData.setErtragP431(newData.getErtragP431());
                hydrated++;
            }
        } else {
            if (newData.getErtragP431() != null
                    && originalData.getErtragP431().compareTo(newData.getErtragP431()) != 0) {
                originalData.setErtragP431(newData.getErtragP431());
                hydrated++;
            } else if (newData.getErtragP431() == null) {
                originalData.setErtragP431(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP432 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_2 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP432() == null) {
            if (newData.getErtragP432() != null) {
                originalData.setErtragP432(newData.getErtragP432());
                hydrated++;
            }
        } else {
            if (newData.getErtragP432() != null
                    && originalData.getErtragP432().compareTo(newData.getErtragP432()) != 0) {
                originalData.setErtragP432(newData.getErtragP432());
                hydrated++;
            } else if (newData.getErtragP432() == null) {
                originalData.setErtragP432(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP433 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_3 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP433() == null) {
            if (newData.getErtragP433() != null) {
                originalData.setErtragP433(newData.getErtragP433());
                hydrated++;
            }
        } else {
            if (newData.getErtragP433() != null
                    && originalData.getErtragP433().compareTo(newData.getErtragP433()) != 0) {
                originalData.setErtragP433(newData.getErtragP433());
                hydrated++;
            } else if (newData.getErtragP433() == null) {
                originalData.setErtragP433(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP434 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_4 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP434() == null) {
            if (newData.getErtragP434() != null) {
                originalData.setErtragP434(newData.getErtragP434());
                hydrated++;
            }
        } else {
            if (newData.getErtragP434() != null
                    && originalData.getErtragP434().compareTo(newData.getErtragP434()) != 0) {
                originalData.setErtragP434(newData.getErtragP434());
                hydrated++;
            } else if (newData.getErtragP434() == null) {
                originalData.setErtragP434(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP435 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_5 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP435() == null) {
            if (newData.getErtragP435() != null) {
                originalData.setErtragP435(newData.getErtragP435());
                hydrated++;
            }
        } else {
            if (newData.getErtragP435() != null
                    && originalData.getErtragP435().compareTo(newData.getErtragP435()) != 0) {
                originalData.setErtragP435(newData.getErtragP435());
                hydrated++;
            } else if (newData.getErtragP435() == null) {
                originalData.setErtragP435(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP436 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_6 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP436() == null) {
            if (newData.getErtragP436() != null) {
                originalData.setErtragP436(newData.getErtragP436());
                hydrated++;
            }
        } else {
            if (newData.getErtragP436() != null
                    && originalData.getErtragP436().compareTo(newData.getErtragP436()) != 0) {
                originalData.setErtragP436(newData.getErtragP436());
                hydrated++;
            } else if (newData.getErtragP436() == null) {
                originalData.setErtragP436(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP437 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_7 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP437() == null) {
            if (newData.getErtragP437() != null) {
                originalData.setErtragP437(newData.getErtragP437());
                hydrated++;
            }
        } else {
            if (newData.getErtragP437() != null
                    && originalData.getErtragP437().compareTo(newData.getErtragP437()) != 0) {
                originalData.setErtragP437(newData.getErtragP437());
                hydrated++;
            } else if (newData.getErtragP437() == null) {
                originalData.setErtragP437(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP438 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_8 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP438() == null) {
            if (newData.getErtragP438() != null) {
                originalData.setErtragP438(newData.getErtragP438());
                hydrated++;
            }
        } else {
            if (newData.getErtragP438() != null
                    && originalData.getErtragP438().compareTo(newData.getErtragP438()) != 0) {
                originalData.setErtragP438(newData.getErtragP438());
                hydrated++;
            } else if (newData.getErtragP438() == null) {
                originalData.setErtragP438(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragP439 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_P43_9 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getErtragP439() == null) {
            if (newData.getErtragP439() != null) {
                originalData.setErtragP439(newData.getErtragP439());
                hydrated++;
            }
        } else {
            if (newData.getErtragP439() != null
                    && originalData.getErtragP439().compareTo(newData.getErtragP439()) != 0) {
                originalData.setErtragP439(newData.getErtragP439());
                hydrated++;
            } else if (newData.getErtragP439() == null) {
                originalData.setErtragP439(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragTevP431 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_TEV_P43_1 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtragTevP431() == null) {
            if (newData.getErtragTevP431() != null) {
                originalData.setErtragTevP431(newData.getErtragTevP431());
                hydrated++;
            }
        } else {
            if (newData.getErtragTevP431() != null
                    && originalData.getErtragTevP431().compareTo(newData.getErtragTevP431()) != 0) {
                originalData.setErtragTevP431(newData.getErtragTevP431());
                hydrated++;
            } else if (newData.getErtragTevP431() == null) {
                originalData.setErtragTevP431(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragTevP436 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_TEV_P43_6 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtragTevP436() == null) {
            if (newData.getErtragTevP436() != null) {
                originalData.setErtragTevP436(newData.getErtragTevP436());
                hydrated++;
            }
        } else {
            if (newData.getErtragTevP436() != null
                    && originalData.getErtragTevP436().compareTo(newData.getErtragTevP436()) != 0) {
                originalData.setErtragTevP436(newData.getErtragTevP436());
                hydrated++;
            } else if (newData.getErtragTevP436() == null) {
                originalData.setErtragTevP436(null);
                hydrated++;
            }
        }

        // =================================
        // metadata ertragTevP439 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ERTRAG_TEV_P43_9 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getErtragTevP439() == null) {
            if (newData.getErtragTevP439() != null) {
                originalData.setErtragTevP439(newData.getErtragTevP439());
                hydrated++;
            }
        } else {
            if (newData.getErtragTevP439() != null
                    && originalData.getErtragTevP439().compareTo(newData.getErtragTevP439()) != 0) {
                originalData.setErtragTevP439(newData.getErtragTevP439());
                hydrated++;
            } else if (newData.getErtragTevP439() == null) {
                originalData.setErtragTevP439(null);
                hydrated++;
            }
        }

        // =================================
        // metadata estb isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ESTB tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getEstb())) {
            if (!StringUtils.isEmpty(newData.getEstb())) {
                originalData.setEstb(newData.getEstb());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getEstb())
                    && !originalData.getEstb().trim().equals(newData.getEstb().trim())))
                    || (StringUtils.isEmpty(newData.getEstb())
                            && !originalData.getEstb().trim().equals(newData.getEstb()))) {
                originalData.setEstb(newData.getEstb());
                hydrated++;
            }
        }

        // =================================
        // metadata fehlerKennz isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName FEHLER_KENNZ tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFehlerKennz())) {
            if (!StringUtils.isEmpty(newData.getFehlerKennz())) {
                originalData.setFehlerKennz(newData.getFehlerKennz());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFehlerKennz())
                    && !originalData.getFehlerKennz().trim().equals(newData.getFehlerKennz().trim())))
                    || (StringUtils.isEmpty(newData.getFehlerKennz())
                            && !originalData.getFehlerKennz().trim().equals(newData.getFehlerKennz()))) {
                originalData.setFehlerKennz(newData.getFehlerKennz());
                hydrated++;
            }
        }

        // =================================
        // metadata formular isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName FORMULAR tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getFormular())) {
            if (!StringUtils.isEmpty(newData.getFormular())) {
                originalData.setFormular(newData.getFormular());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getFormular())
                    && !originalData.getFormular().trim().equals(newData.getFormular().trim())))
                    || (StringUtils.isEmpty(newData.getFormular())
                            && !originalData.getFormular().trim().equals(newData.getFormular()))) {
                originalData.setFormular(newData.getFormular());
                hydrated++;
            }
        }

        // =================================
        // metadata gewinnVerInv isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName GEWINN_VER_INV tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getGewinnVerInv() == null) {
            if (newData.getGewinnVerInv() != null) {
                originalData.setGewinnVerInv(newData.getGewinnVerInv());
                hydrated++;
            }
        } else {
            if (newData.getGewinnVerInv() != null
                    && originalData.getGewinnVerInv().compareTo(newData.getGewinnVerInv()) != 0) {
                originalData.setGewinnVerInv(newData.getGewinnVerInv());
                hydrated++;
            } else if (newData.getGewinnVerInv() == null) {
                originalData.setGewinnVerInv(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inlKiVerw isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INL_KI_VERW tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getInlKiVerw())) {
            if (!StringUtils.isEmpty(newData.getInlKiVerw())) {
                originalData.setInlKiVerw(newData.getInlKiVerw());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getInlKiVerw())
                    && !originalData.getInlKiVerw().trim().equals(newData.getInlKiVerw().trim())))
                    || (StringUtils.isEmpty(newData.getInlKiVerw())
                            && !originalData.getInlKiVerw().trim().equals(newData.getInlKiVerw()))) {
                originalData.setInlKiVerw(newData.getInlKiVerw());
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612Aimmf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_AIMMF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612Aimmf() == null) {
            if (newData.getInv1612Aimmf() != null) {
                originalData.setInv1612Aimmf(newData.getInv1612Aimmf());
                hydrated++;
            }
        } else {
            if (newData.getInv1612Aimmf() != null
                    && originalData.getInv1612Aimmf().compareTo(newData.getInv1612Aimmf()) != 0) {
                originalData.setInv1612Aimmf(newData.getInv1612Aimmf());
                hydrated++;
            } else if (newData.getInv1612Aimmf() == null) {
                originalData.setInv1612Aimmf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612AimmfVp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_AIMMF_VP tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612AimmfVp() == null) {
            if (newData.getInv1612AimmfVp() != null) {
                originalData.setInv1612AimmfVp(newData.getInv1612AimmfVp());
                hydrated++;
            }
        } else {
            if (newData.getInv1612AimmfVp() != null
                    && originalData.getInv1612AimmfVp().compareTo(newData.getInv1612AimmfVp()) != 0) {
                originalData.setInv1612AimmfVp(newData.getInv1612AimmfVp());
                hydrated++;
            } else if (newData.getInv1612AimmfVp() == null) {
                originalData.setInv1612AimmfVp(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612Aktf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_AKTF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612Aktf() == null) {
            if (newData.getInv1612Aktf() != null) {
                originalData.setInv1612Aktf(newData.getInv1612Aktf());
                hydrated++;
            }
        } else {
            if (newData.getInv1612Aktf() != null
                    && originalData.getInv1612Aktf().compareTo(newData.getInv1612Aktf()) != 0) {
                originalData.setInv1612Aktf(newData.getInv1612Aktf());
                hydrated++;
            } else if (newData.getInv1612Aktf() == null) {
                originalData.setInv1612Aktf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612AktfVp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_AKTF_VP tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612AktfVp() == null) {
            if (newData.getInv1612AktfVp() != null) {
                originalData.setInv1612AktfVp(newData.getInv1612AktfVp());
                hydrated++;
            }
        } else {
            if (newData.getInv1612AktfVp() != null
                    && originalData.getInv1612AktfVp().compareTo(newData.getInv1612AktfVp()) != 0) {
                originalData.setInv1612AktfVp(newData.getInv1612AktfVp());
                hydrated++;
            } else if (newData.getInv1612AktfVp() == null) {
                originalData.setInv1612AktfVp(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612Immf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_IMMF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612Immf() == null) {
            if (newData.getInv1612Immf() != null) {
                originalData.setInv1612Immf(newData.getInv1612Immf());
                hydrated++;
            }
        } else {
            if (newData.getInv1612Immf() != null
                    && originalData.getInv1612Immf().compareTo(newData.getInv1612Immf()) != 0) {
                originalData.setInv1612Immf(newData.getInv1612Immf());
                hydrated++;
            } else if (newData.getInv1612Immf() == null) {
                originalData.setInv1612Immf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612ImmfVp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_IMMF_VP tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612ImmfVp() == null) {
            if (newData.getInv1612ImmfVp() != null) {
                originalData.setInv1612ImmfVp(newData.getInv1612ImmfVp());
                hydrated++;
            }
        } else {
            if (newData.getInv1612ImmfVp() != null
                    && originalData.getInv1612ImmfVp().compareTo(newData.getInv1612ImmfVp()) != 0) {
                originalData.setInv1612ImmfVp(newData.getInv1612ImmfVp());
                hydrated++;
            } else if (newData.getInv1612ImmfVp() == null) {
                originalData.setInv1612ImmfVp(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612Misf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_MISF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612Misf() == null) {
            if (newData.getInv1612Misf() != null) {
                originalData.setInv1612Misf(newData.getInv1612Misf());
                hydrated++;
            }
        } else {
            if (newData.getInv1612Misf() != null
                    && originalData.getInv1612Misf().compareTo(newData.getInv1612Misf()) != 0) {
                originalData.setInv1612Misf(newData.getInv1612Misf());
                hydrated++;
            } else if (newData.getInv1612Misf() == null) {
                originalData.setInv1612Misf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612MisfVp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_MISF_VP tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612MisfVp() == null) {
            if (newData.getInv1612MisfVp() != null) {
                originalData.setInv1612MisfVp(newData.getInv1612MisfVp());
                hydrated++;
            }
        } else {
            if (newData.getInv1612MisfVp() != null
                    && originalData.getInv1612MisfVp().compareTo(newData.getInv1612MisfVp()) != 0) {
                originalData.setInv1612MisfVp(newData.getInv1612MisfVp());
                hydrated++;
            } else if (newData.getInv1612MisfVp() == null) {
                originalData.setInv1612MisfVp(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612Sonf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_SONF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612Sonf() == null) {
            if (newData.getInv1612Sonf() != null) {
                originalData.setInv1612Sonf(newData.getInv1612Sonf());
                hydrated++;
            }
        } else {
            if (newData.getInv1612Sonf() != null
                    && originalData.getInv1612Sonf().compareTo(newData.getInv1612Sonf()) != 0) {
                originalData.setInv1612Sonf(newData.getInv1612Sonf());
                hydrated++;
            } else if (newData.getInv1612Sonf() == null) {
                originalData.setInv1612Sonf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv1612SonfVp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_12_SONF_VP tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv1612SonfVp() == null) {
            if (newData.getInv1612SonfVp() != null) {
                originalData.setInv1612SonfVp(newData.getInv1612SonfVp());
                hydrated++;
            }
        } else {
            if (newData.getInv1612SonfVp() != null
                    && originalData.getInv1612SonfVp().compareTo(newData.getInv1612SonfVp()) != 0) {
                originalData.setInv1612SonfVp(newData.getInv1612SonfVp());
                hydrated++;
            } else if (newData.getInv1612SonfVp() == null) {
                originalData.setInv1612SonfVp(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv163Aimmf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_3_AIMMF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv163Aimmf() == null) {
            if (newData.getInv163Aimmf() != null) {
                originalData.setInv163Aimmf(newData.getInv163Aimmf());
                hydrated++;
            }
        } else {
            if (newData.getInv163Aimmf() != null
                    && originalData.getInv163Aimmf().compareTo(newData.getInv163Aimmf()) != 0) {
                originalData.setInv163Aimmf(newData.getInv163Aimmf());
                hydrated++;
            } else if (newData.getInv163Aimmf() == null) {
                originalData.setInv163Aimmf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv163Aktf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_3_AKTF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv163Aktf() == null) {
            if (newData.getInv163Aktf() != null) {
                originalData.setInv163Aktf(newData.getInv163Aktf());
                hydrated++;
            }
        } else {
            if (newData.getInv163Aktf() != null
                    && originalData.getInv163Aktf().compareTo(newData.getInv163Aktf()) != 0) {
                originalData.setInv163Aktf(newData.getInv163Aktf());
                hydrated++;
            } else if (newData.getInv163Aktf() == null) {
                originalData.setInv163Aktf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv163Immf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_3_IMMF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv163Immf() == null) {
            if (newData.getInv163Immf() != null) {
                originalData.setInv163Immf(newData.getInv163Immf());
                hydrated++;
            }
        } else {
            if (newData.getInv163Immf() != null
                    && originalData.getInv163Immf().compareTo(newData.getInv163Immf()) != 0) {
                originalData.setInv163Immf(newData.getInv163Immf());
                hydrated++;
            } else if (newData.getInv163Immf() == null) {
                originalData.setInv163Immf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv163Misf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_3_MISF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv163Misf() == null) {
            if (newData.getInv163Misf() != null) {
                originalData.setInv163Misf(newData.getInv163Misf());
                hydrated++;
            }
        } else {
            if (newData.getInv163Misf() != null
                    && originalData.getInv163Misf().compareTo(newData.getInv163Misf()) != 0) {
                originalData.setInv163Misf(newData.getInv163Misf());
                hydrated++;
            } else if (newData.getInv163Misf() == null) {
                originalData.setInv163Misf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata inv163Sonf isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_16_3_SONF tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInv163Sonf() == null) {
            if (newData.getInv163Sonf() != null) {
                originalData.setInv163Sonf(newData.getInv163Sonf());
                hydrated++;
            }
        } else {
            if (newData.getInv163Sonf() != null
                    && originalData.getInv163Sonf().compareTo(newData.getInv163Sonf()) != 0) {
                originalData.setInv163Sonf(newData.getInv163Sonf());
                hydrated++;
            } else if (newData.getInv163Sonf() == null) {
                originalData.setInv163Sonf(null);
                hydrated++;
            }
        }

        // =================================
        // metadata invErt1612 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_ERT_16_12 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getInvErt1612() == null) {
            if (newData.getInvErt1612() != null) {
                originalData.setInvErt1612(newData.getInvErt1612());
                hydrated++;
            }
        } else {
            if (newData.getInvErt1612() != null
                    && originalData.getInvErt1612().compareTo(newData.getInvErt1612()) != 0) {
                originalData.setInvErt1612(newData.getInvErt1612());
                hydrated++;
            } else if (newData.getInvErt1612() == null) {
                originalData.setInvErt1612(null);
                hydrated++;
            }
        }

        // =================================
        // metadata invErt163 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_ERT_16_3 tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getInvErt163() == null) {
            if (newData.getInvErt163() != null) {
                originalData.setInvErt163(newData.getInvErt163());
                hydrated++;
            }
        } else {
            if (newData.getInvErt163() != null && originalData.getInvErt163().compareTo(newData.getInvErt163()) != 0) {
                originalData.setInvErt163(newData.getInvErt163());
                hydrated++;
            } else if (newData.getInvErt163() == null) {
                originalData.setInvErt163(null);
                hydrated++;
            }
        }

        // =================================
        // metadata invVorhanden isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName INV_VORHANDEN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getInvVorhanden())) {
            if (!StringUtils.isEmpty(newData.getInvVorhanden())) {
                originalData.setInvVorhanden(newData.getInvVorhanden());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getInvVorhanden())
                    && !originalData.getInvVorhanden().trim().equals(newData.getInvVorhanden().trim())))
                    || (StringUtils.isEmpty(newData.getInvVorhanden())
                            && !originalData.getInvVorhanden().trim().equals(newData.getInvVorhanden()))) {
                originalData.setInvVorhanden(newData.getInvVorhanden());
                hydrated++;
            }
        }

        // =================================
        // metadata kStabzugP43Nr1 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName K_STABZUG_P43_NR1 tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKStabzugP43Nr1())) {
            if (!StringUtils.isEmpty(newData.getKStabzugP43Nr1())) {
                originalData.setKStabzugP43Nr1(newData.getKStabzugP43Nr1());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKStabzugP43Nr1())
                    && !originalData.getKStabzugP43Nr1().trim().equals(newData.getKStabzugP43Nr1().trim())))
                    || (StringUtils.isEmpty(newData.getKStabzugP43Nr1())
                            && !originalData.getKStabzugP43Nr1().trim().equals(newData.getKStabzugP43Nr1()))) {
                originalData.setKStabzugP43Nr1(newData.getKStabzugP43Nr1());
                hydrated++;
            }
        }

        // =================================
        // metadata kStabzugP43Nr5 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName K_STABZUG_P43_NR5 tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKStabzugP43Nr5())) {
            if (!StringUtils.isEmpty(newData.getKStabzugP43Nr5())) {
                originalData.setKStabzugP43Nr5(newData.getKStabzugP43Nr5());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKStabzugP43Nr5())
                    && !originalData.getKStabzugP43Nr5().trim().equals(newData.getKStabzugP43Nr5().trim())))
                    || (StringUtils.isEmpty(newData.getKStabzugP43Nr5())
                            && !originalData.getKStabzugP43Nr5().trim().equals(newData.getKStabzugP43Nr5()))) {
                originalData.setKStabzugP43Nr5(newData.getKStabzugP43Nr5());
                hydrated++;
            }
        }

        // =================================
        // metadata kapDreiFueP433 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KAP_DREI_FUE_P43_3 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getKapDreiFueP433() == null) {
            if (newData.getKapDreiFueP433() != null) {
                originalData.setKapDreiFueP433(newData.getKapDreiFueP433());
                hydrated++;
            }
        } else {
            if (newData.getKapDreiFueP433() != null
                    && originalData.getKapDreiFueP433().compareTo(newData.getKapDreiFueP433()) != 0) {
                originalData.setKapDreiFueP433(newData.getKapDreiFueP433());
                hydrated++;
            } else if (newData.getKapDreiFueP433() == null) {
                originalData.setKapDreiFueP433(null);
                hydrated++;
            }
        }

        // =================================
        // metadata kapest isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KAPEST tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getKapest() == null) {
            if (newData.getKapest() != null) {
                originalData.setKapest(newData.getKapest());
                hydrated++;
            }
        } else {
            if (newData.getKapest() != null && originalData.getKapest().compareTo(newData.getKapest()) != 0) {
                originalData.setKapest(newData.getKapest());
                hydrated++;
            } else if (newData.getKapest() == null) {
                originalData.setKapest(null);
                hydrated++;
            }
        }

        // =================================
        // metadata keinOriginalM3 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KEIN_ORIGINAL_M3 tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getKeinOriginalM3())) {
            if (!StringUtils.isEmpty(newData.getKeinOriginalM3())) {
                originalData.setKeinOriginalM3(newData.getKeinOriginalM3());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getKeinOriginalM3())
                    && !originalData.getKeinOriginalM3().trim().equals(newData.getKeinOriginalM3().trim())))
                    || (StringUtils.isEmpty(newData.getKeinOriginalM3())
                            && !originalData.getKeinOriginalM3().trim().equals(newData.getKeinOriginalM3()))) {
                originalData.setKeinOriginalM3(newData.getKeinOriginalM3());
                hydrated++;
            }
        }

        // =================================
        // metadata liefertiefe isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName LIEFERTIEFE tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getLiefertiefe())) {
            if (!StringUtils.isEmpty(newData.getLiefertiefe())) {
                originalData.setLiefertiefe(newData.getLiefertiefe());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getLiefertiefe())
                    && !originalData.getLiefertiefe().trim().equals(newData.getLiefertiefe().trim())))
                    || (StringUtils.isEmpty(newData.getLiefertiefe())
                            && !originalData.getLiefertiefe().trim().equals(newData.getLiefertiefe()))) {
                originalData.setLiefertiefe(newData.getLiefertiefe());
                hydrated++;
            }
        }

        // =================================
        // metadata ordnungsnummer isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ORDNUNGSNUMMER tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getOrdnungsnummer())) {
            if (!StringUtils.isEmpty(newData.getOrdnungsnummer())) {
                originalData.setOrdnungsnummer(newData.getOrdnungsnummer());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getOrdnungsnummer())
                    && !originalData.getOrdnungsnummer().trim().equals(newData.getOrdnungsnummer().trim())))
                    || (StringUtils.isEmpty(newData.getOrdnungsnummer())
                            && !originalData.getOrdnungsnummer().trim().equals(newData.getOrdnungsnummer()))) {
                originalData.setOrdnungsnummer(newData.getOrdnungsnummer());
                hydrated++;
            }
        }

        // =================================
        // metadata pivVerwahrt isSubtable false parentTable Bsb
        // isFlagOfSubset true isCountOfSubset false
        // colName PIV_VERWAHRT tblClassName TrTableBm3 colJavaType String
        // =================================

        if (!newData.getTrTableBm3PK().getKeySatzart().equals("H")
                || (CommonConstants.HISTORICAL_UPDATE_ACTION.equals(action)
                        && newData.getTrTableBm3PK().getKeySatzart().equals("H"))) {
            if (originalData.getPivVerwahrt() == null) {
                if (!ObjectUtils.isEmpty(newData.getTrTableBm3PivSet())) {
                    originalData.setPivVerwahrt(BigDecimal.ONE.toString());
                    hydrated++;
                }
            } else {
                if (newData.getTrTableBm3PivSet().size() < 1 && Integer.valueOf(originalData.getPivVerwahrt()) != 0) {
                    originalData.setPivVerwahrt(BigDecimal.ZERO.toString());
                    hydrated++;
                }
                if (newData.getTrTableBm3PivSet().size() > 0 && Integer.valueOf(originalData.getPivVerwahrt()) != 1) {
                    originalData.setPivVerwahrt(BigDecimal.ONE.toString());
                    hydrated++;
                }
            }
        }

        // =================================
        // metadata sdBezugWP isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SD_BEZUG_W_P tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSdBezugWP())) {
            if (!StringUtils.isEmpty(newData.getSdBezugWP())) {
                originalData.setSdBezugWP(newData.getSdBezugWP());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSdBezugWP())
                    && !originalData.getSdBezugWP().trim().equals(newData.getSdBezugWP().trim())))
                    || (StringUtils.isEmpty(newData.getSdBezugWP())
                            && !originalData.getSdBezugWP().trim().equals(newData.getSdBezugWP()))) {
                originalData.setSdBezugWP(newData.getSdBezugWP());
                hydrated++;
            }
        }

        // =================================
        // metadata sdIsin isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SD_ISIN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSdIsin())) {
            if (!StringUtils.isEmpty(newData.getSdIsin())) {
                originalData.setSdIsin(newData.getSdIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSdIsin())
                    && !originalData.getSdIsin().trim().equals(newData.getSdIsin().trim())))
                    || (StringUtils.isEmpty(newData.getSdIsin())
                            && !originalData.getSdIsin().trim().equals(newData.getSdIsin()))) {
                originalData.setSdIsin(newData.getSdIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata sdWkn isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SD_WKN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSdWkn())) {
            if (!StringUtils.isEmpty(newData.getSdWkn())) {
                originalData.setSdWkn(newData.getSdWkn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSdWkn())
                    && !originalData.getSdWkn().trim().equals(newData.getSdWkn().trim())))
                    || (StringUtils.isEmpty(newData.getSdWkn())
                            && !originalData.getSdWkn().trim().equals(newData.getSdWkn()))) {
                originalData.setSdWkn(newData.getSdWkn());
                hydrated++;
            }
        }

        // =================================
        // metadata sfAnlAnzahl isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ANL_ANZAHL tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getSfAnlAnzahl() == null) {
            if (newData.getSfAnlAnzahl() != null) {
                originalData.setSfAnlAnzahl(newData.getSfAnlAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getSfAnlAnzahl() != null
                    && originalData.getSfAnlAnzahl().compareTo(newData.getSfAnlAnzahl()) != 0) {
                originalData.setSfAnlAnzahl(newData.getSfAnlAnzahl());
                hydrated++;
            } else if (newData.getSfAnlAnzahl() == null) {
                originalData.setSfAnlAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfAnlBezugWP isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ANL_BEZUG_W_P tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfAnlBezugWP())) {
            if (!StringUtils.isEmpty(newData.getSfAnlBezugWP())) {
                originalData.setSfAnlBezugWP(newData.getSfAnlBezugWP());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfAnlBezugWP())
                    && !originalData.getSfAnlBezugWP().trim().equals(newData.getSfAnlBezugWP().trim())))
                    || (StringUtils.isEmpty(newData.getSfAnlBezugWP())
                            && !originalData.getSfAnlBezugWP().trim().equals(newData.getSfAnlBezugWP()))) {
                originalData.setSfAnlBezugWP(newData.getSfAnlBezugWP());
                hydrated++;
            }
        }

        // =================================
        // metadata sfAnlKapest isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ANL_KAPEST tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getSfAnlKapest() == null) {
            if (newData.getSfAnlKapest() != null) {
                originalData.setSfAnlKapest(newData.getSfAnlKapest());
                hydrated++;
            }
        } else {
            if (newData.getSfAnlKapest() != null
                    && originalData.getSfAnlKapest().compareTo(newData.getSfAnlKapest()) != 0) {
                originalData.setSfAnlKapest(newData.getSfAnlKapest());
                hydrated++;
            } else if (newData.getSfAnlKapest() == null) {
                originalData.setSfAnlKapest(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfAnlSolz isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ANL_SOLZ tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSfAnlSolz() == null) {
            if (newData.getSfAnlSolz() != null) {
                originalData.setSfAnlSolz(newData.getSfAnlSolz());
                hydrated++;
            }
        } else {
            if (newData.getSfAnlSolz() != null && originalData.getSfAnlSolz().compareTo(newData.getSfAnlSolz()) != 0) {
                originalData.setSfAnlSolz(newData.getSfAnlSolz());
                hydrated++;
            } else if (newData.getSfAnlSolz() == null) {
                originalData.setSfAnlSolz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfAnteile isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ANTEILE tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSfAnteile() == null) {
            if (newData.getSfAnteile() != null) {
                originalData.setSfAnteile(newData.getSfAnteile());
                hydrated++;
            }
        } else {
            if (newData.getSfAnteile() != null && originalData.getSfAnteile().compareTo(newData.getSfAnteile()) != 0) {
                originalData.setSfAnteile(newData.getSfAnteile());
                hydrated++;
            } else if (newData.getSfAnteile() == null) {
                originalData.setSfAnteile(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfBetEinnahm isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_BET_EINNAHM tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfBetEinnahm())) {
            if (!StringUtils.isEmpty(newData.getSfBetEinnahm())) {
                originalData.setSfBetEinnahm(newData.getSfBetEinnahm());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfBetEinnahm())
                    && !originalData.getSfBetEinnahm().trim().equals(newData.getSfBetEinnahm().trim())))
                    || (StringUtils.isEmpty(newData.getSfBetEinnahm())
                            && !originalData.getSfBetEinnahm().trim().equals(newData.getSfBetEinnahm()))) {
                originalData.setSfBetEinnahm(newData.getSfBetEinnahm());
                hydrated++;
            }
        }

        // =================================
        // metadata sfBetrag isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_BETRAG tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSfBetrag() == null) {
            if (newData.getSfBetrag() != null) {
                originalData.setSfBetrag(newData.getSfBetrag());
                hydrated++;
            }
        } else {
            if (newData.getSfBetrag() != null && originalData.getSfBetrag().compareTo(newData.getSfBetrag()) != 0) {
                originalData.setSfBetrag(newData.getSfBetrag());
                hydrated++;
            } else if (newData.getSfBetrag() == null) {
                originalData.setSfBetrag(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfBezeichn isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_BEZEICHN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfBezeichn())) {
            if (!StringUtils.isEmpty(newData.getSfBezeichn())) {
                originalData.setSfBezeichn(newData.getSfBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfBezeichn())
                    && !originalData.getSfBezeichn().trim().equals(newData.getSfBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getSfBezeichn())
                            && !originalData.getSfBezeichn().trim().equals(newData.getSfBezeichn()))) {
                originalData.setSfBezeichn(newData.getSfBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata sfDatum isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_DATUM tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSfDatum() == null) {
            if (newData.getSfDatum() != null) {
                originalData.setSfDatum(newData.getSfDatum());
                hydrated++;
            }
        } else {
            if (newData.getSfDatum() != null && originalData.getSfDatum().compareTo(newData.getSfDatum()) != 0) {
                originalData.setSfDatum(newData.getSfDatum());
                hydrated++;
            } else if (newData.getSfDatum() == null) {
                originalData.setSfDatum(null);
                hydrated++;
            }
        }

        // =================================
        // metadata sfIsin isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_ISIN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfIsin())) {
            if (!StringUtils.isEmpty(newData.getSfIsin())) {
                originalData.setSfIsin(newData.getSfIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfIsin())
                    && !originalData.getSfIsin().trim().equals(newData.getSfIsin().trim())))
                    || (StringUtils.isEmpty(newData.getSfIsin())
                            && !originalData.getSfIsin().trim().equals(newData.getSfIsin()))) {
                originalData.setSfIsin(newData.getSfIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata sfSchuldBezWp isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_SCHULD_BEZ_WP tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfSchuldBezWp())) {
            if (!StringUtils.isEmpty(newData.getSfSchuldBezWp())) {
                originalData.setSfSchuldBezWp(newData.getSfSchuldBezWp());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfSchuldBezWp())
                    && !originalData.getSfSchuldBezWp().trim().equals(newData.getSfSchuldBezWp().trim())))
                    || (StringUtils.isEmpty(newData.getSfSchuldBezWp())
                            && !originalData.getSfSchuldBezWp().trim().equals(newData.getSfSchuldBezWp()))) {
                originalData.setSfSchuldBezWp(newData.getSfSchuldBezWp());
                hydrated++;
            }
        }

        // =================================
        // metadata sfSonsEink isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_SONS_EINK tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfSonsEink())) {
            if (!StringUtils.isEmpty(newData.getSfSonsEink())) {
                originalData.setSfSonsEink(newData.getSfSonsEink());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfSonsEink())
                    && !originalData.getSfSonsEink().trim().equals(newData.getSfSonsEink().trim())))
                    || (StringUtils.isEmpty(newData.getSfSonsEink())
                            && !originalData.getSfSonsEink().trim().equals(newData.getSfSonsEink()))) {
                originalData.setSfSonsEink(newData.getSfSonsEink());
                hydrated++;
            }
        }

        // =================================
        // metadata sfWkn isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SF_WKN tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getSfWkn())) {
            if (!StringUtils.isEmpty(newData.getSfWkn())) {
                originalData.setSfWkn(newData.getSfWkn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getSfWkn())
                    && !originalData.getSfWkn().trim().equals(newData.getSfWkn().trim())))
                    || (StringUtils.isEmpty(newData.getSfWkn())
                            && !originalData.getSfWkn().trim().equals(newData.getSfWkn()))) {
                originalData.setSfWkn(newData.getSfWkn());
                hydrated++;
            }
        }

        // =================================
        // metadata solz isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName SOLZ tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getSolz() == null) {
            if (newData.getSolz() != null) {
                originalData.setSolz(newData.getSolz());
                hydrated++;
            }
        } else {
            if (newData.getSolz() != null && originalData.getSolz().compareTo(newData.getSolz()) != 0) {
                originalData.setSolz(newData.getSolz());
                hydrated++;
            } else if (newData.getSolz() == null) {
                originalData.setSolz(null);
                hydrated++;
            }
        }

        // =================================
        // metadata stornierung isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName STORNIERUNG tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getStornierung())) {
            if (!StringUtils.isEmpty(newData.getStornierung())) {
                originalData.setStornierung(newData.getStornierung());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getStornierung())
                    && !originalData.getStornierung().trim().equals(newData.getStornierung().trim())))
                    || (StringUtils.isEmpty(newData.getStornierung())
                            && !originalData.getStornierung().trim().equals(newData.getStornierung()))) {
                originalData.setStornierung(newData.getStornierung());
                hydrated++;
            }
        }

        // =================================
        // metadata zahlungstag isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZAHLUNGSTAG tblClassName TrTableBm3 colJavaType java.math.BigDecimal
        // =================================

        if (originalData.getZahlungstag() == null) {
            if (newData.getZahlungstag() != null) {
                originalData.setZahlungstag(newData.getZahlungstag());
                hydrated++;
            }
        } else {
            if (newData.getZahlungstag() != null
                    && originalData.getZahlungstag().compareTo(newData.getZahlungstag()) != 0) {
                originalData.setZahlungstag(newData.getZahlungstag());
                hydrated++;
            } else if (newData.getZahlungstag() == null) {
                originalData.setZahlungstag(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zahlungszeitrBis isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZAHLUNGSZEITR_BIS tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getZahlungszeitrBis() == null) {
            if (newData.getZahlungszeitrBis() != null) {
                originalData.setZahlungszeitrBis(newData.getZahlungszeitrBis());
                hydrated++;
            }
        } else {
            if (newData.getZahlungszeitrBis() != null
                    && originalData.getZahlungszeitrBis().compareTo(newData.getZahlungszeitrBis()) != 0) {
                originalData.setZahlungszeitrBis(newData.getZahlungszeitrBis());
                hydrated++;
            } else if (newData.getZahlungszeitrBis() == null) {
                originalData.setZahlungszeitrBis(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zahlungszeitrVon isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZAHLUNGSZEITR_VON tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getZahlungszeitrVon() == null) {
            if (newData.getZahlungszeitrVon() != null) {
                originalData.setZahlungszeitrVon(newData.getZahlungszeitrVon());
                hydrated++;
            }
        } else {
            if (newData.getZahlungszeitrVon() != null
                    && originalData.getZahlungszeitrVon().compareTo(newData.getZahlungszeitrVon()) != 0) {
                originalData.setZahlungszeitrVon(newData.getZahlungszeitrVon());
                hydrated++;
            } else if (newData.getZahlungszeitrVon() == null) {
                originalData.setZahlungszeitrVon(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zusGefassteStb isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZUS_GEFASSTE_STB tblClassName TrTableBm3 colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getZusGefassteStb())) {
            if (!StringUtils.isEmpty(newData.getZusGefassteStb())) {
                originalData.setZusGefassteStb(newData.getZusGefassteStb());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getZusGefassteStb())
                    && !originalData.getZusGefassteStb().trim().equals(newData.getZusGefassteStb().trim())))
                    || (StringUtils.isEmpty(newData.getZusGefassteStb())
                            && !originalData.getZusGefassteStb().trim().equals(newData.getZusGefassteStb()))) {
                originalData.setZusGefassteStb(newData.getZusGefassteStb());
                hydrated++;
            }
        }

        // =================================
        // metadata zusZeitraumBis isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZUS_ZEITRAUM_BIS tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getZusZeitraumBis() == null) {
            if (newData.getZusZeitraumBis() != null) {
                originalData.setZusZeitraumBis(newData.getZusZeitraumBis());
                hydrated++;
            }
        } else {
            if (newData.getZusZeitraumBis() != null
                    && originalData.getZusZeitraumBis().compareTo(newData.getZusZeitraumBis()) != 0) {
                originalData.setZusZeitraumBis(newData.getZusZeitraumBis());
                hydrated++;
            } else if (newData.getZusZeitraumBis() == null) {
                originalData.setZusZeitraumBis(null);
                hydrated++;
            }
        }

        // =================================
        // metadata zusZeitraumVon isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ZUS_ZEITRAUM_VON tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getZusZeitraumVon() == null) {
            if (newData.getZusZeitraumVon() != null) {
                originalData.setZusZeitraumVon(newData.getZusZeitraumVon());
                hydrated++;
            }
        } else {
            if (newData.getZusZeitraumVon() != null
                    && originalData.getZusZeitraumVon().compareTo(newData.getZusZeitraumVon()) != 0) {
                originalData.setZusZeitraumVon(newData.getZusZeitraumVon());
                hydrated++;
            } else if (newData.getZusZeitraumVon() == null) {
                originalData.setZusZeitraumVon(null);
                hydrated++;
            }
        }

        // =================================
        // metadata abzDreiFueP431 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName ABZ_DREI_FUE_P43_1 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAbzDreiFueP431() == null) {
            if (newData.getAbzDreiFueP431() != null) {
                originalData.setAbzDreiFueP431(newData.getAbzDreiFueP431());
                hydrated++;
            }
        } else {
            if (newData.getAbzDreiFueP431() != null
                    && originalData.getAbzDreiFueP431().compareTo(newData.getAbzDreiFueP431()) != 0) {
                originalData.setAbzDreiFueP431(newData.getAbzDreiFueP431());
                hydrated++;
            } else if (newData.getAbzDreiFueP431() == null) {
                originalData.setAbzDreiFueP431(null);
                hydrated++;
            }
        }

        // =================================
        // metadata kapDreiFueP431 isSubtable false parentTable Bsb
        // isFlagOfSubset false isCountOfSubset false
        // colName KAP_DREI_FUE_P43_1 tblClassName TrTableBm3 colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getKapDreiFueP431() == null) {
            if (newData.getKapDreiFueP431() != null) {
                originalData.setKapDreiFueP431(newData.getKapDreiFueP431());
                hydrated++;
            }
        } else {
            if (newData.getKapDreiFueP431() != null
                    && originalData.getKapDreiFueP431().compareTo(newData.getKapDreiFueP431()) != 0) {
                originalData.setKapDreiFueP431(newData.getKapDreiFueP431());
                hydrated++;
            } else if (newData.getKapDreiFueP431() == null) {
                originalData.setKapDreiFueP431(null);
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1Aam(TrTableBm1Aam originalData, TrTableBm1Aam newData) {
        int hydrated = 0;

        // =================================
        // metadata aamAntAnzahl isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_ANZAHL tblClassName TrTableBm1Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamAntAnzahl() == null) {
            if (newData.getAamAntAnzahl() != null) {
                originalData.setAamAntAnzahl(newData.getAamAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAamAntAnzahl() != null
                    && originalData.getAamAntAnzahl().compareTo(newData.getAamAntAnzahl()) != 0) {
                originalData.setAamAntAnzahl(newData.getAamAntAnzahl());
                hydrated++;
            } else if (newData.getAamAntAnzahl() == null) {
                originalData.setAamAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntBezeichn isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_BEZEICHN tblClassName TrTableBm1Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAamAntBezeichn())) {
                originalData.setAamAntBezeichn(newData.getAamAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntBezeichn())
                    && !originalData.getAamAntBezeichn().trim().equals(newData.getAamAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntBezeichn())
                            && !originalData.getAamAntBezeichn().trim().equals(newData.getAamAntBezeichn()))) {
                originalData.setAamAntBezeichn(newData.getAamAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntGewFiktiv isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_GEW_FIKTIV tblClassName TrTableBm1Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamAntGewFiktiv() == null) {
            if (newData.getAamAntGewFiktiv() != null) {
                originalData.setAamAntGewFiktiv(newData.getAamAntGewFiktiv());
                hydrated++;
            }
        } else {
            if (newData.getAamAntGewFiktiv() != null
                    && originalData.getAamAntGewFiktiv().compareTo(newData.getAamAntGewFiktiv()) != 0) {
                originalData.setAamAntGewFiktiv(newData.getAamAntGewFiktiv());
                hydrated++;
            } else if (newData.getAamAntGewFiktiv() == null) {
                originalData.setAamAntGewFiktiv(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntGewNiErm isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_GEW_NI_ERM tblClassName TrTableBm1Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntGewNiErm())) {
            if (!StringUtils.isEmpty(newData.getAamAntGewNiErm())) {
                originalData.setAamAntGewNiErm(newData.getAamAntGewNiErm());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntGewNiErm())
                    && !originalData.getAamAntGewNiErm().trim().equals(newData.getAamAntGewNiErm().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntGewNiErm())
                            && !originalData.getAamAntGewNiErm().trim().equals(newData.getAamAntGewNiErm()))) {
                originalData.setAamAntGewNiErm(newData.getAamAntGewNiErm());
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntIsin isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_ISIN tblClassName TrTableBm1Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAamAntIsin())) {
                originalData.setAamAntIsin(newData.getAamAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntIsin())
                    && !originalData.getAamAntIsin().trim().equals(newData.getAamAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntIsin())
                            && !originalData.getAamAntIsin().trim().equals(newData.getAamAntIsin()))) {
                originalData.setAamAntIsin(newData.getAamAntIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata aamGewVerMillfo isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_GEW_VER_MILLFO tblClassName TrTableBm1Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamGewVerMillfo() == null) {
            if (newData.getAamGewVerMillfo() != null) {
                originalData.setAamGewVerMillfo(newData.getAamGewVerMillfo());
                hydrated++;
            }
        } else {
            if (newData.getAamGewVerMillfo() != null
                    && originalData.getAamGewVerMillfo().compareTo(newData.getAamGewVerMillfo()) != 0) {
                originalData.setAamGewVerMillfo(newData.getAamGewVerMillfo());
                hydrated++;
            } else if (newData.getAamGewVerMillfo() == null) {
                originalData.setAamGewVerMillfo(null);
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3Aam(TrTableBm3Aam originalData, TrTableBm3Aam newData) {
        int hydrated = 0;

        // =================================
        // metadata aamAntAnzahl isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_ANZAHL tblClassName TrTableBm3Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamAntAnzahl() == null) {
            if (newData.getAamAntAnzahl() != null) {
                originalData.setAamAntAnzahl(newData.getAamAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAamAntAnzahl() != null
                    && originalData.getAamAntAnzahl().compareTo(newData.getAamAntAnzahl()) != 0) {
                originalData.setAamAntAnzahl(newData.getAamAntAnzahl());
                hydrated++;
            } else if (newData.getAamAntAnzahl() == null) {
                originalData.setAamAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntBezeichn isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_BEZEICHN tblClassName TrTableBm3Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAamAntBezeichn())) {
                originalData.setAamAntBezeichn(newData.getAamAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntBezeichn())
                    && !originalData.getAamAntBezeichn().trim().equals(newData.getAamAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntBezeichn())
                            && !originalData.getAamAntBezeichn().trim().equals(newData.getAamAntBezeichn()))) {
                originalData.setAamAntBezeichn(newData.getAamAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntGewFiktiv isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_GEW_FIKTIV tblClassName TrTableBm3Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamAntGewFiktiv() == null) {
            if (newData.getAamAntGewFiktiv() != null) {
                originalData.setAamAntGewFiktiv(newData.getAamAntGewFiktiv());
                hydrated++;
            }
        } else {
            if (newData.getAamAntGewFiktiv() != null
                    && originalData.getAamAntGewFiktiv().compareTo(newData.getAamAntGewFiktiv()) != 0) {
                originalData.setAamAntGewFiktiv(newData.getAamAntGewFiktiv());
                hydrated++;
            } else if (newData.getAamAntGewFiktiv() == null) {
                originalData.setAamAntGewFiktiv(null);
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntGewNiErm isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_GEW_NI_ERM tblClassName TrTableBm3Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntGewNiErm())) {
            if (!StringUtils.isEmpty(newData.getAamAntGewNiErm())) {
                originalData.setAamAntGewNiErm(newData.getAamAntGewNiErm());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntGewNiErm())
                    && !originalData.getAamAntGewNiErm().trim().equals(newData.getAamAntGewNiErm().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntGewNiErm())
                            && !originalData.getAamAntGewNiErm().trim().equals(newData.getAamAntGewNiErm()))) {
                originalData.setAamAntGewNiErm(newData.getAamAntGewNiErm());
                hydrated++;
            }
        }

        // =================================
        // metadata aamAntIsin isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_ANT_ISIN tblClassName TrTableBm3Aam colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAamAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAamAntIsin())) {
                originalData.setAamAntIsin(newData.getAamAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAamAntIsin())
                    && !originalData.getAamAntIsin().trim().equals(newData.getAamAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAamAntIsin())
                            && !originalData.getAamAntIsin().trim().equals(newData.getAamAntIsin()))) {
                originalData.setAamAntIsin(newData.getAamAntIsin());
                hydrated++;
            }
        }

        // =================================
        // metadata aamGewVerMillfo isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AAM_GEW_VER_MILLFO tblClassName TrTableBm3Aam colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAamGewVerMillfo() == null) {
            if (newData.getAamGewVerMillfo() != null) {
                originalData.setAamGewVerMillfo(newData.getAamGewVerMillfo());
                hydrated++;
            }
        } else {
            if (newData.getAamGewVerMillfo() != null
                    && originalData.getAamGewVerMillfo().compareTo(newData.getAamGewVerMillfo()) != 0) {
                originalData.setAamGewVerMillfo(newData.getAamGewVerMillfo());
                hydrated++;
            } else if (newData.getAamGewVerMillfo() == null) {
                originalData.setAamGewVerMillfo(null);
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3Akb(TrTableBm3Akb originalData, TrTableBm3Akb newData) {
        int hydrated = 0;

        // =================================
        // metadata akbAntAnzahl isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_ANZAHL tblClassName TrTableBm3Akb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkbAntAnzahl() == null) {
            if (newData.getAkbAntAnzahl() != null) {
                originalData.setAkbAntAnzahl(newData.getAkbAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAkbAntAnzahl() != null
                    && originalData.getAkbAntAnzahl().compareTo(newData.getAkbAntAnzahl()) != 0) {
                originalData.setAkbAntAnzahl(newData.getAkbAntAnzahl());
                hydrated++;
            } else if (newData.getAkbAntAnzahl() == null) {
                originalData.setAkbAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntBezeichn isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_BEZEICHN tblClassName TrTableBm3Akb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkbAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAkbAntBezeichn())) {
                originalData.setAkbAntBezeichn(newData.getAkbAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkbAntBezeichn())
                    && !originalData.getAkbAntBezeichn().trim().equals(newData.getAkbAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAkbAntBezeichn())
                            && !originalData.getAkbAntBezeichn().trim().equals(newData.getAkbAntBezeichn()))) {
                originalData.setAkbAntBezeichn(newData.getAkbAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntGewinn isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_GEWINN tblClassName TrTableBm3Akb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkbAntGewinn() == null) {
            if (newData.getAkbAntGewinn() != null) {
                originalData.setAkbAntGewinn(newData.getAkbAntGewinn());
                hydrated++;
            }
        } else {
            if (newData.getAkbAntGewinn() != null
                    && originalData.getAkbAntGewinn().compareTo(newData.getAkbAntGewinn()) != 0) {
                originalData.setAkbAntGewinn(newData.getAkbAntGewinn());
                hydrated++;
            } else if (newData.getAkbAntGewinn() == null) {
                originalData.setAkbAntGewinn(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntIsin isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_ISIN tblClassName TrTableBm3Akb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkbAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAkbAntIsin())) {
                originalData.setAkbAntIsin(newData.getAkbAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkbAntIsin())
                    && !originalData.getAkbAntIsin().trim().equals(newData.getAkbAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAkbAntIsin())
                            && !originalData.getAkbAntIsin().trim().equals(newData.getAkbAntIsin()))) {
                originalData.setAkbAntIsin(newData.getAkbAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1Akb(TrTableBm1Akb originalData, TrTableBm1Akb newData) {
        int hydrated = 0;

        // =================================
        // metadata akbAntAnzahl isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_ANZAHL tblClassName TrTableBm1Akb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkbAntAnzahl() == null) {
            if (newData.getAkbAntAnzahl() != null) {
                originalData.setAkbAntAnzahl(newData.getAkbAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAkbAntAnzahl() != null
                    && originalData.getAkbAntAnzahl().compareTo(newData.getAkbAntAnzahl()) != 0) {
                originalData.setAkbAntAnzahl(newData.getAkbAntAnzahl());
                hydrated++;
            } else if (newData.getAkbAntAnzahl() == null) {
                originalData.setAkbAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntBezeichn isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_BEZEICHN tblClassName TrTableBm1Akb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkbAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAkbAntBezeichn())) {
                originalData.setAkbAntBezeichn(newData.getAkbAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkbAntBezeichn())
                    && !originalData.getAkbAntBezeichn().trim().equals(newData.getAkbAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAkbAntBezeichn())
                            && !originalData.getAkbAntBezeichn().trim().equals(newData.getAkbAntBezeichn()))) {
                originalData.setAkbAntBezeichn(newData.getAkbAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntGewinn isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_GEWINN tblClassName TrTableBm1Akb colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkbAntGewinn() == null) {
            if (newData.getAkbAntGewinn() != null) {
                originalData.setAkbAntGewinn(newData.getAkbAntGewinn());
                hydrated++;
            }
        } else {
            if (newData.getAkbAntGewinn() != null
                    && originalData.getAkbAntGewinn().compareTo(newData.getAkbAntGewinn()) != 0) {
                originalData.setAkbAntGewinn(newData.getAkbAntGewinn());
                hydrated++;
            } else if (newData.getAkbAntGewinn() == null) {
                originalData.setAkbAntGewinn(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akbAntIsin isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKB_ANT_ISIN tblClassName TrTableBm1Akb colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkbAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAkbAntIsin())) {
                originalData.setAkbAntIsin(newData.getAkbAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkbAntIsin())
                    && !originalData.getAkbAntIsin().trim().equals(newData.getAkbAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAkbAntIsin())
                            && !originalData.getAkbAntIsin().trim().equals(newData.getAkbAntIsin()))) {
                originalData.setAkbAntIsin(newData.getAkbAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3Ake(TrTableBm3Ake originalData, TrTableBm3Ake newData) {
        int hydrated = 0;

        // =================================
        // metadata akeAntAnzahl isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ANZAHL tblClassName TrTableBm3Ake colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkeAntAnzahl() == null) {
            if (newData.getAkeAntAnzahl() != null) {
                originalData.setAkeAntAnzahl(newData.getAkeAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAkeAntAnzahl() != null
                    && originalData.getAkeAntAnzahl().compareTo(newData.getAkeAntAnzahl()) != 0) {
                originalData.setAkeAntAnzahl(newData.getAkeAntAnzahl());
                hydrated++;
            } else if (newData.getAkeAntAnzahl() == null) {
                originalData.setAkeAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntBezeichn isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_BEZEICHN tblClassName TrTableBm3Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAkeAntBezeichn())) {
                originalData.setAkeAntBezeichn(newData.getAkeAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntBezeichn())
                    && !originalData.getAkeAntBezeichn().trim().equals(newData.getAkeAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntBezeichn())
                            && !originalData.getAkeAntBezeichn().trim().equals(newData.getAkeAntBezeichn()))) {
                originalData.setAkeAntBezeichn(newData.getAkeAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntEbmgNErm isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_EBMG_N_ERM tblClassName TrTableBm3Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntEbmgNErm())) {
            if (!StringUtils.isEmpty(newData.getAkeAntEbmgNErm())) {
                originalData.setAkeAntEbmgNErm(newData.getAkeAntEbmgNErm());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntEbmgNErm())
                    && !originalData.getAkeAntEbmgNErm().trim().equals(newData.getAkeAntEbmgNErm().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntEbmgNErm())
                            && !originalData.getAkeAntEbmgNErm().trim().equals(newData.getAkeAntEbmgNErm()))) {
                originalData.setAkeAntEbmgNErm(newData.getAkeAntEbmgNErm());
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntErsBmg isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ERS_BMG tblClassName TrTableBm3Ake colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkeAntErsBmg() == null) {
            if (newData.getAkeAntErsBmg() != null) {
                originalData.setAkeAntErsBmg(newData.getAkeAntErsBmg());
                hydrated++;
            }
        } else {
            if (newData.getAkeAntErsBmg() != null
                    && originalData.getAkeAntErsBmg().compareTo(newData.getAkeAntErsBmg()) != 0) {
                originalData.setAkeAntErsBmg(newData.getAkeAntErsBmg());
                hydrated++;
            } else if (newData.getAkeAntErsBmg() == null) {
                originalData.setAkeAntErsBmg(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntIsin isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ISIN tblClassName TrTableBm3Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAkeAntIsin())) {
                originalData.setAkeAntIsin(newData.getAkeAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntIsin())
                    && !originalData.getAkeAntIsin().trim().equals(newData.getAkeAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntIsin())
                            && !originalData.getAkeAntIsin().trim().equals(newData.getAkeAntIsin()))) {
                originalData.setAkeAntIsin(newData.getAkeAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1Ake(TrTableBm1Ake originalData, TrTableBm1Ake newData) {
        int hydrated = 0;

        // =================================
        // metadata akeAntAnzahl isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ANZAHL tblClassName TrTableBm1Ake colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkeAntAnzahl() == null) {
            if (newData.getAkeAntAnzahl() != null) {
                originalData.setAkeAntAnzahl(newData.getAkeAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getAkeAntAnzahl() != null
                    && originalData.getAkeAntAnzahl().compareTo(newData.getAkeAntAnzahl()) != 0) {
                originalData.setAkeAntAnzahl(newData.getAkeAntAnzahl());
                hydrated++;
            } else if (newData.getAkeAntAnzahl() == null) {
                originalData.setAkeAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntBezeichn isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_BEZEICHN tblClassName TrTableBm1Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getAkeAntBezeichn())) {
                originalData.setAkeAntBezeichn(newData.getAkeAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntBezeichn())
                    && !originalData.getAkeAntBezeichn().trim().equals(newData.getAkeAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntBezeichn())
                            && !originalData.getAkeAntBezeichn().trim().equals(newData.getAkeAntBezeichn()))) {
                originalData.setAkeAntBezeichn(newData.getAkeAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntEbmgNErm isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_EBMG_N_ERM tblClassName TrTableBm1Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntEbmgNErm())) {
            if (!StringUtils.isEmpty(newData.getAkeAntEbmgNErm())) {
                originalData.setAkeAntEbmgNErm(newData.getAkeAntEbmgNErm());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntEbmgNErm())
                    && !originalData.getAkeAntEbmgNErm().trim().equals(newData.getAkeAntEbmgNErm().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntEbmgNErm())
                            && !originalData.getAkeAntEbmgNErm().trim().equals(newData.getAkeAntEbmgNErm()))) {
                originalData.setAkeAntEbmgNErm(newData.getAkeAntEbmgNErm());
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntErsBmg isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ERS_BMG tblClassName TrTableBm1Ake colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getAkeAntErsBmg() == null) {
            if (newData.getAkeAntErsBmg() != null) {
                originalData.setAkeAntErsBmg(newData.getAkeAntErsBmg());
                hydrated++;
            }
        } else {
            if (newData.getAkeAntErsBmg() != null
                    && originalData.getAkeAntErsBmg().compareTo(newData.getAkeAntErsBmg()) != 0) {
                originalData.setAkeAntErsBmg(newData.getAkeAntErsBmg());
                hydrated++;
            } else if (newData.getAkeAntErsBmg() == null) {
                originalData.setAkeAntErsBmg(null);
                hydrated++;
            }
        }

        // =================================
        // metadata akeAntIsin isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName AKE_ANT_ISIN tblClassName TrTableBm1Ake colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getAkeAntIsin())) {
            if (!StringUtils.isEmpty(newData.getAkeAntIsin())) {
                originalData.setAkeAntIsin(newData.getAkeAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getAkeAntIsin())
                    && !originalData.getAkeAntIsin().trim().equals(newData.getAkeAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getAkeAntIsin())
                            && !originalData.getAkeAntIsin().trim().equals(newData.getAkeAntIsin()))) {
                originalData.setAkeAntIsin(newData.getAkeAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1Eik(TrTableBm1Eik originalData, TrTableBm1Eik newData) {
        int hydrated = 0;

        // =================================
        // metadata eikAntAnzahl isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_ANZAHL tblClassName TrTableBm1Eik colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEikAntAnzahl() == null) {
            if (newData.getEikAntAnzahl() != null) {
                originalData.setEikAntAnzahl(newData.getEikAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getEikAntAnzahl() != null
                    && originalData.getEikAntAnzahl().compareTo(newData.getEikAntAnzahl()) != 0) {
                originalData.setEikAntAnzahl(newData.getEikAntAnzahl());
                hydrated++;
            } else if (newData.getEikAntAnzahl() == null) {
                originalData.setEikAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntAusschuett isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_AUSSCHUETT tblClassName TrTableBm1Eik colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEikAntAusschuett() == null) {
            if (newData.getEikAntAusschuett() != null) {
                originalData.setEikAntAusschuett(newData.getEikAntAusschuett());
                hydrated++;
            }
        } else {
            if (newData.getEikAntAusschuett() != null
                    && originalData.getEikAntAusschuett().compareTo(newData.getEikAntAusschuett()) != 0) {
                originalData.setEikAntAusschuett(newData.getEikAntAusschuett());
                hydrated++;
            } else if (newData.getEikAntAusschuett() == null) {
                originalData.setEikAntAusschuett(null);
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntBezeichn isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_BEZEICHN tblClassName TrTableBm1Eik colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getEikAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getEikAntBezeichn())) {
                originalData.setEikAntBezeichn(newData.getEikAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getEikAntBezeichn())
                    && !originalData.getEikAntBezeichn().trim().equals(newData.getEikAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getEikAntBezeichn())
                            && !originalData.getEikAntBezeichn().trim().equals(newData.getEikAntBezeichn()))) {
                originalData.setEikAntBezeichn(newData.getEikAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntIsin isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_ISIN tblClassName TrTableBm1Eik colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getEikAntIsin())) {
            if (!StringUtils.isEmpty(newData.getEikAntIsin())) {
                originalData.setEikAntIsin(newData.getEikAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getEikAntIsin())
                    && !originalData.getEikAntIsin().trim().equals(newData.getEikAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getEikAntIsin())
                            && !originalData.getEikAntIsin().trim().equals(newData.getEikAntIsin()))) {
                originalData.setEikAntIsin(newData.getEikAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3Eik(TrTableBm3Eik originalData, TrTableBm3Eik newData) {
        int hydrated = 0;

        // =================================
        // metadata eikAntAnzahl isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_ANZAHL tblClassName TrTableBm3Eik colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEikAntAnzahl() == null) {
            if (newData.getEikAntAnzahl() != null) {
                originalData.setEikAntAnzahl(newData.getEikAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getEikAntAnzahl() != null
                    && originalData.getEikAntAnzahl().compareTo(newData.getEikAntAnzahl()) != 0) {
                originalData.setEikAntAnzahl(newData.getEikAntAnzahl());
                hydrated++;
            } else if (newData.getEikAntAnzahl() == null) {
                originalData.setEikAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntAusschuett isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_AUSSCHUETT tblClassName TrTableBm3Eik colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getEikAntAusschuett() == null) {
            if (newData.getEikAntAusschuett() != null) {
                originalData.setEikAntAusschuett(newData.getEikAntAusschuett());
                hydrated++;
            }
        } else {
            if (newData.getEikAntAusschuett() != null
                    && originalData.getEikAntAusschuett().compareTo(newData.getEikAntAusschuett()) != 0) {
                originalData.setEikAntAusschuett(newData.getEikAntAusschuett());
                hydrated++;
            } else if (newData.getEikAntAusschuett() == null) {
                originalData.setEikAntAusschuett(null);
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntBezeichn isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_BEZEICHN tblClassName TrTableBm3Eik colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getEikAntBezeichn())) {
            if (!StringUtils.isEmpty(newData.getEikAntBezeichn())) {
                originalData.setEikAntBezeichn(newData.getEikAntBezeichn());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getEikAntBezeichn())
                    && !originalData.getEikAntBezeichn().trim().equals(newData.getEikAntBezeichn().trim())))
                    || (StringUtils.isEmpty(newData.getEikAntBezeichn())
                            && !originalData.getEikAntBezeichn().trim().equals(newData.getEikAntBezeichn()))) {
                originalData.setEikAntBezeichn(newData.getEikAntBezeichn());
                hydrated++;
            }
        }

        // =================================
        // metadata eikAntIsin isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName EIK_ANT_ISIN tblClassName TrTableBm3Eik colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getEikAntIsin())) {
            if (!StringUtils.isEmpty(newData.getEikAntIsin())) {
                originalData.setEikAntIsin(newData.getEikAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getEikAntIsin())
                    && !originalData.getEikAntIsin().trim().equals(newData.getEikAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getEikAntIsin())
                            && !originalData.getEikAntIsin().trim().equals(newData.getEikAntIsin()))) {
                originalData.setEikAntIsin(newData.getEikAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm3Piv(TrTableBm3Piv originalData, TrTableBm3Piv newData) {
        int hydrated = 0;

        // =================================
        // metadata pivAntAnzahl isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_ANZAHL tblClassName TrTableBm3Piv colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getPivAntAnzahl() == null) {
            if (newData.getPivAntAnzahl() != null) {
                originalData.setPivAntAnzahl(newData.getPivAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getPivAntAnzahl() != null
                    && originalData.getPivAntAnzahl().compareTo(newData.getPivAntAnzahl()) != 0) {
                originalData.setPivAntAnzahl(newData.getPivAntAnzahl());
                hydrated++;
            } else if (newData.getPivAntAnzahl() == null) {
                originalData.setPivAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntAusErloes isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_AUS_ERLOES tblClassName TrTableBm3Piv colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getPivAntAusErloes() == null) {
            if (newData.getPivAntAusErloes() != null) {
                originalData.setPivAntAusErloes(newData.getPivAntAusErloes());
                hydrated++;
            }
        } else {
            if (newData.getPivAntAusErloes() != null
                    && originalData.getPivAntAusErloes().compareTo(newData.getPivAntAusErloes()) != 0) {
                originalData.setPivAntAusErloes(newData.getPivAntAusErloes());
                hydrated++;
            } else if (newData.getPivAntAusErloes() == null) {
                originalData.setPivAntAusErloes(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntBezeichng isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_BEZEICHNG tblClassName TrTableBm3Piv colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPivAntBezeichng())) {
            if (!StringUtils.isEmpty(newData.getPivAntBezeichng())) {
                originalData.setPivAntBezeichng(newData.getPivAntBezeichng());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPivAntBezeichng())
                    && !originalData.getPivAntBezeichng().trim().equals(newData.getPivAntBezeichng().trim())))
                    || (StringUtils.isEmpty(newData.getPivAntBezeichng())
                            && !originalData.getPivAntBezeichng().trim().equals(newData.getPivAntBezeichng()))) {
                originalData.setPivAntBezeichng(newData.getPivAntBezeichng());
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntIsin isSubtable true parentTable Bm3
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_ISIN tblClassName TrTableBm3Piv colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPivAntIsin())) {
            if (!StringUtils.isEmpty(newData.getPivAntIsin())) {
                originalData.setPivAntIsin(newData.getPivAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPivAntIsin())
                    && !originalData.getPivAntIsin().trim().equals(newData.getPivAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getPivAntIsin())
                            && !originalData.getPivAntIsin().trim().equals(newData.getPivAntIsin()))) {
                originalData.setPivAntIsin(newData.getPivAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }

    private int getChangesTrTableBm1Piv(TrTableBm1Piv originalData, TrTableBm1Piv newData) {
        int hydrated = 0;

        // =================================
        // metadata pivAntAnzahl isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_ANZAHL tblClassName TrTableBm1Piv colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getPivAntAnzahl() == null) {
            if (newData.getPivAntAnzahl() != null) {
                originalData.setPivAntAnzahl(newData.getPivAntAnzahl());
                hydrated++;
            }
        } else {
            if (newData.getPivAntAnzahl() != null
                    && originalData.getPivAntAnzahl().compareTo(newData.getPivAntAnzahl()) != 0) {
                originalData.setPivAntAnzahl(newData.getPivAntAnzahl());
                hydrated++;
            } else if (newData.getPivAntAnzahl() == null) {
                originalData.setPivAntAnzahl(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntAusErloes isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_AUS_ERLOES tblClassName TrTableBm1Piv colJavaType
        // java.math.BigDecimal
        // =================================

        if (originalData.getPivAntAusErloes() == null) {
            if (newData.getPivAntAusErloes() != null) {
                originalData.setPivAntAusErloes(newData.getPivAntAusErloes());
                hydrated++;
            }
        } else {
            if (newData.getPivAntAusErloes() != null
                    && originalData.getPivAntAusErloes().compareTo(newData.getPivAntAusErloes()) != 0) {
                originalData.setPivAntAusErloes(newData.getPivAntAusErloes());
                hydrated++;
            } else if (newData.getPivAntAusErloes() == null) {
                originalData.setPivAntAusErloes(null);
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntBezeichng isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_BEZEICHNG tblClassName TrTableBm1Piv colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPivAntBezeichng())) {
            if (!StringUtils.isEmpty(newData.getPivAntBezeichng())) {
                originalData.setPivAntBezeichng(newData.getPivAntBezeichng());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPivAntBezeichng())
                    && !originalData.getPivAntBezeichng().trim().equals(newData.getPivAntBezeichng().trim())))
                    || (StringUtils.isEmpty(newData.getPivAntBezeichng())
                            && !originalData.getPivAntBezeichng().trim().equals(newData.getPivAntBezeichng()))) {
                originalData.setPivAntBezeichng(newData.getPivAntBezeichng());
                hydrated++;
            }
        }

        // =================================
        // metadata pivAntIsin isSubtable true parentTable Bm1
        // isFlagOfSubset false isCountOfSubset false
        // colName PIV_ANT_ISIN tblClassName TrTableBm1Piv colJavaType String
        // =================================

        if (StringUtils.isEmpty(originalData.getPivAntIsin())) {
            if (!StringUtils.isEmpty(newData.getPivAntIsin())) {
                originalData.setPivAntIsin(newData.getPivAntIsin());
                hydrated++;
            }
        } else {
            if (((!StringUtils.isEmpty(newData.getPivAntIsin())
                    && !originalData.getPivAntIsin().trim().equals(newData.getPivAntIsin().trim())))
                    || (StringUtils.isEmpty(newData.getPivAntIsin())
                            && !originalData.getPivAntIsin().trim().equals(newData.getPivAntIsin()))) {
                originalData.setPivAntIsin(newData.getPivAntIsin());
                hydrated++;
            }
        }

        return hydrated;
    }
}
