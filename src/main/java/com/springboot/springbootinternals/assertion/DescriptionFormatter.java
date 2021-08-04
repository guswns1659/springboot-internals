package com.springboot.springbootinternals.assertion;

import static com.springboot.springbootinternals.assertion.Strings.isNullOfEmpty;

public class DescriptionFormatter {

    public static final DescriptionFormatter INSTANCE = new DescriptionFormatter();

    public static DescriptionFormatter instance() {
        return INSTANCE;
    }

    @VisibleForTesting
    DescriptionFormatter() {}

    public String format(Description d) {
        String s = (d != null) ? d.value() : null;
        if (isNullOfEmpty(s)) return "";
        return String.format("[%s] ", s);
    }
}
