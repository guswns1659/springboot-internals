package com.springboot.springbootinternals.assertion;

import static java.lang.String.format;
import java.util.Arrays;

public final class Strings {

    public static boolean isNullOfEmpty(String s) {
        return s == null || s.length() == 0;
    }

    // TODO :
    /*public static String formatIfArgs(String message, Object... args) {
        return Arrays.isNullOrEmpty(args) ? format(escapePercentExceptWhenFollowedBy_n(message))
            : format(message, args);
    }*/

    private static String escapePercentExceptWhenFollowedBy_n(String message) {
        return revertEscapingPercent_n(escapePercent(message));
    }

    private static String escapePercent(String value) {
        return value == null ? null : value.replace("%", "%%");
    }

    private static String revertEscapingPercent_n(String value) {
        return value == null ? null : value.replace("%%n", "%n");
    }

}
