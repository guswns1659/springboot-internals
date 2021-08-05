package com.springboot.springbootinternals.assertj.core.configuration;

import static com.springboot.springbootinternals.assertj.core.configuration.Configuration.DEFAULT_CONFIGURATION;
import static com.springboot.springbootinternals.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import static java.lang.String.format;
import com.springboot.springbootinternals.assertj.core.Services;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

/**
 * Provider for all the configuration settings / parameters within AssertJ
 */
public final class ConfigurationProvider {

    public static final ConfigurationProvider CONFIGURATION_PROVIDER = new ConfigurationProvider();
    private final Representation representation;
    private final Configuration configuration;

    private ConfigurationProvider() {
        representation = Services.get(Representation.class, STANDARD_REPRESENTATION);
        if (representation != STANDARD_REPRESENTATION) {
            System.err.println(format("Although it still works, registering a Representation through a file named 'org.assertj.core.presentation.Representation' in the META-INF/services directory is deprecated.%n"
                + "The proper way of providing a Representation is to register a Configuration as described in the documentation (a Configuration allowing to provide a Representation and other AssertJ configuration elements)"));
        }
        configuration = Services.get(Configuration.class, DEFAULT_CONFIGURATION);
        if (configuration != DEFAULT_CONFIGURATION) {
            configuration.applyAndDisplay();
        }
    }

    public Representation representation() {
        return configuration.hasCustomRepresentation() ? configuration.representation() : representation;
    }
}
