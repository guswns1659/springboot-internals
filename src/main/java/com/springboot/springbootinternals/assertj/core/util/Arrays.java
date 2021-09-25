package com.springboot.springbootinternals.assertj.core.util;

/**
 * Utility methods related to arrays
 */
public class Arrays {

    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || isEmpty(array);
    }

    public static <T> boolean isEmpty(T[] array) {
        return array.length == 0;
    }

    @SafeVarargs
    public static <T> T[] array(T... values) {
        return values;
    }
}
