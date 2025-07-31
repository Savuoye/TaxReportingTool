package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * This enum will hold all the melde status values of workflow status in BSB
 * table
 */
public enum MeldeStatus {
    _00("00"), // DE: Keine Aktion, EN: No action
    _01("01"), // DE: Zum Ausgang, EN: To exit
    _02("02"), // DE: Zum Ausgang Storno, EN: To output Cancellation
    _03("03"), // DE: Zum Ausgang Korrektur, EN: To output Correction
    _04("04"), // DE: Zum Ausgang freigegeben, EN: Enabled for output
    _05("05"), // DE: Zum Ausgang Storno freigegeben, EN: Enabled for output Cancellation
    _06("06"), // DE: Zum Ausgang Korrektur freigegeben, EN: To output Correction enabled
    _11("11"), // DE: Ausgang gemeldet, EN: Output reported
    _12("12"), // DE: Ausgang Storno gemeldet, EN: Output Cancellation reported
    _13("13") // DE: Ausgang Korrektur gemeldet, EN: Output correction reported
    ;

    private String value;

    MeldeStatus(String value) {
        this.value = value;
    }

    public static boolean isMatching(String meldeStatus) {
        try {
            MeldeStatus.valueOf(meldeStatus);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @JsonCreator
    public static MeldeStatus fromValue(String text) {
        for (MeldeStatus b : MeldeStatus.values()) {
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
