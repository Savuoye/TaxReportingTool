package com.fisglobal.taxreporting.util;

import java.math.BigDecimal;


/**
 * Utility class to validate big decimal data type
 */
public class BigDecimalUtils {
    /**
     * This method will convert the value to big decimal
     *
     * @param value
     *                  Pass object in big decimal
     * 
     * @return Big decimal object
     */
    public static BigDecimal getBigDecimalValue(Object value) {
        if (value == null)
            return null;
        return ((BigDecimal) value);
    }
}
