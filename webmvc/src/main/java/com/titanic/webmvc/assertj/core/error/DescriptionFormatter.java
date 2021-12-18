package com.titanic.webmvc.assertj.core.error;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.util.VisibleForTesting;

import static com.titanic.webmvc.assertj.core.util.Strings.isNullOrEmpty;

public class DescriptionFormatter {

    private static final DescriptionFormatter INSTANCE = new DescriptionFormatter();

    public static DescriptionFormatter instance() {
        return INSTANCE;
    }

    @VisibleForTesting
    DescriptionFormatter() {}

    public String format(Description d) {
        String s = (d != null) ? d.value() : null;
        if (isNullOrEmpty(s)) return "";
        return String.format("[%s] ", s);
    }
}
