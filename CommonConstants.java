package com.fisglobal.taxreporting.util;

import java.util.Arrays;
import java.util.List;

import com.fisglobal.taxreporting.enums.MeldeStatus;


/**
 * This class holds commonly used constant value in tax reporting backend
 * application
 */
public class CommonConstants {
    public static final List<String> validStatusForReview = Arrays.asList("B", "K", "S");
    public static final List<String> invalidMeldeStatusForApprove = Arrays.asList(MeldeStatus._04.toString(),
            MeldeStatus._05.toString(), MeldeStatus._06.toString(), MeldeStatus._11.toString(),
            MeldeStatus._12.toString(), MeldeStatus._13.toString());

    public static final String TR_TABLE_BSB_MELDE_STATUS = "tr_table_bsb.melde_status";
    public static final String TR_TABLE_BSB_AENDER_ERFASSER = "tr_table_bsb.aender_erfasser";
    public static final String TR_TABLE_BSB_STATUS = "tr_table_bsb.status";

    public static final String TR_TABLE_BSB_PERSON_ID = "tr_table_bsb.person_id";
    public static final String TR_TABLE_BSB_KONTO_ID = "tr_table_bsb.konto_id";
    public static final String TR_TABLE_BSB_STEUER_ZUORD = "tr_table_bsb.steuer_zuord";
    public static final String TR_TABLE_BSB_KEY_ID_NR = "tr_table_bsb.key_id_nr";
    public static final String TR_TABLE_BSB_KEY_MELDEJAHR = "tr_table_bsb.key_meldejahr";
    public static final String TR_TABLE_BSB_KEY_MUSTER = "tr_table_bsb.key_muster";
    public static final String TR_TABLE_BSB_NP1_ID_NR = "tr_table_bsb.np1_idnr";
    public static final String TR_TABLE_BSB_NP2_ID_NR = "tr_table_bsb.np2_idnr";
    public static final String TR_TABLE_BSB_NWP_TIN = "tr_table_bsb.nwp_tin";

    public static final String TR_TABLE_BSB_BED_TIMESTAMP_FORMAT = "yyyyMMddHHmmssSS";
    public static final String TR_TABLE_BSB_DATE_FORMAT = "yyyyMMdd";
    public static final String TR_TABLE_BSB_TIME_FORMAT = "HHmmss";
    public static final String PARAMETER_ACTION = "action";

    public static final String BM1_TR_TABLE_AAM_SET = "bm1TrTableAamSet";
    public static final String BM1_TR_TABLE_AKE_SET = "bm1TrTableAkeSet";
    public static final String BM1_TR_TABLE_EIK_SET = "bm1TrTableEikSet";
    public static final String BM1_TR_TABLE_PIV_SET = "bm1TrTablePivSet";

    public static final String BM3_TR_TABLE_AKB_SET = "bm3TrTableAkbSet";
    public static final String BM3_TR_TABLE_AKE_SET = "bm3TrTableAkeSet";
    public static final String BM3_TR_TABLE_EIK_SET = "bm3TrTableEikSet";
    public static final String BM3_TR_TABLE_PIV_SET = "bm3TrTablePivSet";

    public static final String KEY_STAZART = "key_satzart";

    public static final String BSB_ANWEISUNGSART_NEU = "Neu";
    public static final String BSB_ANWEISUNGSART_KORRECKTUR = "Korrektur";

    public static final List<String> bm1ExcludeList = Arrays.asList("trTableBm1PK", "trTableBsbFK", "trTableBm1AamSet",
            "trTableBm1AkbSet", "trTableBm1AkeSet", "trTableBm1EikSet", "trTableBm1PivSet");
    public static final List<String> bm3ExcludeList = Arrays.asList("trTableBm3PK", "trTableBsbFK", "trTableBm3AamSet",
            "trTableBm3AkbSet", "trTableBm3AkeSet", "trTableBm3EikSet", "trTableBm3PivSet");
    public static final List<String> subTableExcludeList = Arrays.asList("trTableBm3FK", "trTableBm1FK", "trTableAamPK",
            "trTableAkbPK", "trTableAkePK", "trTableEikPK", "trTablePivPK");

    public static final String UPDATE_ACTION = "update";
    public static final String REVIEW_ACTION = "review";
    public static final String APPROVE_ACTION = "approve";
    public static final String DELETE_ACTION = "delete";
    public static final String ADJUST_ACTION = "adjust";
    public static final String CANCEL_ACTION = "cancel";
    public static final String HISTORICAL_UPDATE_ACTION = "historyUpdate";
    public static final String HISTORICAL_DELETE_ACTION = "historyDelete";

    public static final String CORRECTION_ACTION = "correction";

    public static final String HISTORY_ACTION = "history";

    public static final String UPDATE_ACTION_USECASE_ID = "UC04";
    public static final String REVIEW_ACTION_USECASE_ID = "UC05";
    public static final String APPROVE_ACTION_USECASE_ID = "UC06";
    public static final String DELETE_ACTION_USECASE_ID = "UC07";
    public static final String ADJUST_ACTION_USECASE_ID = "UC10";
    public static final String CANCEL_ACTION_USECASE_ID = "UC11";
    public static final String CORRECTION_ACTION_USECASE_ID = "UC03";
    public static final String HISTORY_ACTION_USECASE_ID = "UC12";
    public static final String HISTORICAL_UPDATE_ACTION_USECASE_ID = "UC13";
    public static final String HISTORICAL_DELETE_ACTION_USECASE_ID = "UC14";

    public static final String UPDATE_ACTION_USECASE_SUMMARY = "Journaling for UC04 - Update Tax Certificate";
    public static final String REVIEW_ACTION_USECASE_SUMMARY = "Journaling for UC05 - Mark For Review";
    public static final String APPROVE_ACTION_USECASE_SUMMARY = "Journaling for UC06 - Approve Tax Certificate";
    public static final String DELETE_ACTION_USECASE_SUMMARY = "Journaling for UC07 - Delete Tax Certificate";
    public static final String ADJUST_ACTION_USECASE_SUMMARY = "Journaling for UC10 - Adjustment for Tax Certificate";
    public static final String CANCEL_ACTION_USECASE_SUMMARY = "Journaling for UC11 - Cancel Tax Certificate";
    public static final String CORRECTION_ACTION_USECASE_SUMMARY = "Journaling for UC03 - Correction Tax Certificate";

    public static final String HISTORY_ACTION_USECASE_SUMMARY = "Journaling for UC12 - Create Historicized Tax Certificate";
    public static final String HISTORICAL_UPDATE_ACTION_USECASE_SUMMARY = "Journaling for UC13 - Historical update Tax Certificate";
    public static final String HISTORICAL_DELETE_ACTION_USECASE_SUMMARY = "Journaling for UC14 - Historical delete Tax Certificate";

    public static final String EMPTY_VALUE = "";
    public static final String HYPEN_VALUE = "-";

    public static final String SINGLE_SPACE = " ";

    public static final List<String> validStatusForCreateAdjustment = Arrays.asList("B", "K");
    public static final List<String> previousStatusCheckForCorrection = Arrays.asList("B");
    public static final List<String> invalidStatusForHistorydeletion = Arrays.asList("B", "K", "S");

    public static final String BSB_ANLASS_DESCRIPTION = "Fachliche Berichtigung einer Steuerbescheinigung";
    public static final String BSB = "BSB";
    public static final String LAUFENDE_NUMMER = "00001";
    public static final String BSB_DOKU_ART = "5";
    public static final String IS_LATEST_HISTORICAL = "isLatestHistorical";

    public static final String HISTORICIZED_CREATION_STATUS = "H";
    public static final long HISTORICIZED_CREATION_KEY_LAUFNUMMER = 999;
    public static final String HISTORICIZED_CREATION_ANWEISUNGSART = "Neu";
    public static final String HISTORICIZED_CREATION_DOKU_ART = "2";
    public static final String CURRENT_YEAR_FORMAT = "yyyy";
    public static final long YEAR_2024 = 2024;
    public static final String LOGIN = "login";
    public static final String LOGIN_TYPE_JWT = "JWT";
    public static final int IDNR_NUMBER_LENGTH = 10;
    public static final int IDNR_CHECKSUM_LENGTH = 1;

    public static final int KEY_ID_NR_LENGTH = 13;
    public static final int KEY_MUSTER_LENGTH = 8;
    public static final int MAND_SL_LENGTH = 3;

    public static final int JOURNALING_VERSION = 2;
}
