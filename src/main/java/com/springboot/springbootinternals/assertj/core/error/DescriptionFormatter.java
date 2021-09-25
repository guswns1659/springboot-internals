package com.springboot.springbootinternals.assertj.core.error;

import static com.springboot.springbootinternals.assertj.core.util.Strings.isNullOrEmpty;
import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

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
