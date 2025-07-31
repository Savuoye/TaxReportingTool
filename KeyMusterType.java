package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * This enum will hold all the values of antwort status
 */
public enum KeyMusterType {
    JSTB_I("JSTB-I"), JSTB_III("JSTB-III");

    private String value;

    KeyMusterType(String value) {
        this.value = value;
    }

    public static boolean isMatching(String meldeStatus) {
        try {
            KeyMusterType.valueOf(meldeStatus);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @JsonCreator
    public static KeyMusterType fromValue(String text) {
        for (KeyMusterType b : KeyMusterType.values()) {
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
