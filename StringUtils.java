package com.fisglobal.taxreporting.util;

import java.util.Optional;


/**
 * String utils class used validation and customize string functions
 */
public class StringUtils {
    /**
     * Mapping to escape the Java control characters. Namely: \b \n \t \f \r
     * Returns: the mapping table
     *
     * @param str
     *                Input
     * 
     * @return Result string value
     */
    public static String validate(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str))
            return "";

        str = str.replaceAll("\\\\", "").replaceAll("\b", "").replaceAll("\n", "").replaceAll("\t", "")
                .replaceAll("\f", "").replaceAll("\n", "");
        return str;
    }

    /*
     * This method converts Object to String value. When using native query to get
     * records from database the column value we will get as Char type not as String
     * so we need this conversion to change to String type
     */
    public static String getStringValue(Object value) {
        if (value == null)
            return null;
        return String.valueOf(value);
    }

    /*
     * Return true if: - str is null - str is empty string - str contains just
     * white/blank spaces (space, tab \t, new line \n, \r,) in any amount Otherwise
     * return false
     */
    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        if (str.isEmpty())
            return true;
        return false;
    }

    /*
     * If isEmpty (the method above), then return the str as-is with no
     * modifications Otherwise: return String.trim()
     */
    public static String strip(String str) {
        if (StringUtils.isEmpty(str))
            return str;
        return str.trim();

    }

    public static <T> Optional<T> ofNullable(T value) {
        if (value == null) {
            return Optional.empty();
        }
        if (!(value instanceof String)) {
            return Optional.empty();
        }
        if (((String) value).isBlank()) {
            return Optional.empty();
        }
        return value == null ? Optional.empty() : Optional.of(value);
    }

    /**
     * Trim two strings and then it will compare the trimmed values
     * 
     * @param s1
     *               First value
     * @param s2
     *               Second value
     * 
     * @return
     *             Boolean result
     */
    public static boolean compareAfterTrim(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }

        if (s1 != null && s2 == null) {
            return false;
        }

        if (s1 == null && s2 != null) {
            return false;
        }

        if (s1.trim().equals(s2.trim())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method check null and empty and also check all the character in the parameter is empty
     * or not
     * 
     * @param input
     *                  incoming request string
     * 
     * @return
     *             Return true or false
     */
    public static boolean isBlank(String input) {
        return org.apache.commons.lang3.StringUtils.isBlank(input);
    }
}
