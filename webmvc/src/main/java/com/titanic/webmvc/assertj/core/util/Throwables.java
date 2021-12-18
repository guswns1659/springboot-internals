package com.titanic.webmvc.assertj.core.util;

import java.util.List;

import static com.titanic.webmvc.assertj.core.util.Lists.newArrayList;


/**
 * Utility methods related to Throwables.
 */
public class Throwables {

    public static final String ORG_ASSERTJ = "org.assert";

    // Notes : how to remove messageElements from stackTrace

    /**
     * Removes the AssertJ-related elements from the Throwable stack trace that have little value for end user.
     *
     * @param throwable - the Throwable to filter stack trace.
     */
    public static void removeAssertJRelatedElementsFromStackTrace(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        List<StackTraceElement> filtered = newArrayList(throwable.getStackTrace());
        StackTraceElement previous = null;
        for (StackTraceElement element : throwable.getStackTrace()) {
            if (element.getClassName().contains(ORG_ASSERTJ)) {
                filtered.remove(element);
            }
            previous = element;
        }
        StackTraceElement[] newStackTrace = filtered.toArray(new StackTraceElement[0]);
        throwable.setStackTrace(newStackTrace);
    }
}
