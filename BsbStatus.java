package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * This enum will hold all the status in BSB table
 */
public enum BsbStatus {
    H("H"), // DE:Steuerbescheinigung (Historisiert), EN:Tax certificate (historicized)
    B("B"), // DE:Steuerbescheinigung (Berichtigung), EN:Tax certificate (correction)
    K("K"), // DE:Steuerbescheinigung (Korrektur), EN:Tax certificate (correction)
    S("S"), // DE:Steuerbescheinigung (Storno), EN:Tax certificate (reversal/cancellation)
    ;

    private String value;

    BsbStatus(String value) {
        this.value = value;
    }

    public static boolean isMatching(String meldeStatus) {
        try {
            BsbStatus.valueOf(meldeStatus);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @JsonCreator
    public static BsbStatus fromValue(String text) {
        for (BsbStatus b : BsbStatus.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
