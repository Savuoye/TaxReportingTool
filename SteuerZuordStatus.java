package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * This enum will hold all the status in Steuer Zuord in BSB table
 */
public enum SteuerZuordStatus {
    P("P"), // Private asset
    G("G"), // Business asset
    D("D"), // Third party
    ;

    private String value;

    SteuerZuordStatus(String value) {
        this.value = value;
    }

    public static boolean isMatching(String meldeStatus) {
        try {
            SteuerZuordStatus.valueOf(meldeStatus);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @JsonCreator
    public static SteuerZuordStatus fromValue(String text) {
        for (SteuerZuordStatus b : SteuerZuordStatus.values()) {
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
