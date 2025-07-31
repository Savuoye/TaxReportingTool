package com.fisglobal.taxreporting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Error class enum hold all the values of exception types
 */
public enum ValidationClassEnum {
    INFO("info"),

    WARNING("warning"),

    ERROR("error");

    private String value;

    ValidationClassEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ValidationClassEnum fromValue(String text) {
        for (ValidationClassEnum b : ValidationClassEnum.values()) {
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
