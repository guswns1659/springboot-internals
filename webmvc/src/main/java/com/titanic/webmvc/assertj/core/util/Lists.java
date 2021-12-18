package com.titanic.webmvc.assertj.core.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Utility methods related to java.util.Lists.
 */
public final class Lists {

    /**
     * Creates a mutable ArrayList containing the given elements.
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... elements) {
        if (elements == null) {
            return null;
        }
        ArrayList<T> list = newArrayList();
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * Creates a mutable ArrayList
     * @param <T> - the generic type of the ArrayList to create.
     * @return
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

}
