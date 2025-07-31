package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * This enum will hold all the values of antwort status
 */
public enum AntwortStatus {
    _01("01"), // DE:Eingang fertig, EN:Input ready
    _02("02") // DE:Eingang Fehler, EN:Input error
    ;

    private String value;

    AntwortStatus(String value) {
        this.value = value;
    }

    public static boolean isMatching(String meldeStatus) {
        try {
            AntwortStatus.valueOf(meldeStatus);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @JsonCreator
    public static AntwortStatus fromValue(String text) {
        for (AntwortStatus b : AntwortStatus.values()) {
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
