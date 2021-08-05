package com.springboot.springbootinternals.assertj.core.error;

import static com.springboot.springbootinternals.assertj.core.util.Strings.formatIfArgs;
import static java.util.Objects.requireNonNull;
import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.internal.AbstractComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;

public class MessageFormatter {

    public static final MessageFormatter INSTANCE = new MessageFormatter();

    @VisibleForTesting
    DescriptionFormatter descriptionFormatter = DescriptionFormatter.instance();

    public String format(Description d, Representation p, String format, Object... args) {
        requireNonNull(format);
        requireNonNull(args);
        return descriptionFormatter.format(d) + formatIfArgs(format, format(p, args));
    }

    private Object[] format(Representation p, Object[] args) {
        int argCount = args.length;
        String[] formatted = new String[argCount];
        for (int i = 0; i < argCount; i++) {
            formatted[i] = asText(p, args[i]);
        }
        return formatted;
    }

    private String asText(Representation p, Object o) {
        if (o instanceof AbstractComparisonStrategy) {
            return ((AbstractComparisonStrategy) o).asText();
        }
        return p.toStringOf(o);
    }

}
