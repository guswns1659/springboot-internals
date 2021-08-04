package com.springboot.springbootinternals.assertion;

import static java.util.Objects.requireNonNull;

public class MessageFormatter {

    public static final MessageFormatter INSTANCE = new MessageFormatter();

    @VisibleForTesting
    DescriptionFormatter descriptionFormatter = DescriptionFormatter.instance();

    // TODO :
//    public String format(Description d, Represetation p, String format, Object... args) {
//        requireNonNull(format);
//        requireNonNull(args);
//        return descriptionFormatter.format(d) + formatIfArgs(format, format(p, args));
//    }

    // TODO :
//    private Object[]  format(Represetation p, Object[] args) {
//        int argCount = args.length;
//        String[] formatted = new String[argCount];
//        for (int i = 0; i < argCount; i++) {
//            formatted[i] = asText(p, args[i]);
//        }
//        return formatted;
//    }

    // TODO:
//    private String asText(Represetation p, Object arg) {
//        if (arg instanceof AbstractComparisonStrategy) {
//            return
//        }
//    }

}
