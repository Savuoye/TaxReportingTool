package com.fisglobal.taxreporting.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * This utility class will return the list by passing iterable object
 */
public class IterableListConversion {
    /**
     * This function to convert Iterable into Collection
     *
     * @param <T>
     *                Compatible to any data type
     * @param itr
     *                Iterable object
     * 
     * @return List of object
     */
    public static <T> List<T> getCollectionFromIteralbe(Iterable<T> itr) {
        if (itr == null)
            return Collections.emptyList();

        return StreamSupport.stream(itr.spliterator(), false).collect(Collectors.toList());
    }
}
