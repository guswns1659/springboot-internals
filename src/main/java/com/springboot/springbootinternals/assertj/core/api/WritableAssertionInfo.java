package com.springboot.springbootinternals.assertj.core.api;

import static com.springboot.springbootinternals.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static java.util.Objects.requireNonNull;
import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import java.util.function.Supplier;

/**
 * Writable information about an assertion
 */
public class WritableAssertionInfo implements AssertionInfo {

    private Supplier<String> overridingErrorMessageSupplier;
    private String overridingErrorMessage;
    private Description description;
    private Representation representation;

    public WritableAssertionInfo(Representation customRepresentation) {
        useRepresentation(
            customRepresentation == null ? CONFIGURATION_PROVIDER.representation() : customRepresentation);
    }

    public void useRepresentation(Representation newRepresentation) {
        requireNonNull(newRepresentation, "The representation to use should not be null.");
        representation = newRepresentation;
    }

    @Override
    public String overridingErrorMessage() {
        return overridingErrorMessageSupplier != null ? overridingErrorMessageSupplier.get() : overridingErrorMessage;
    }

    @Override
    public Description description() {
        return description;
    }

    @Override
    public Representation representation() {
        return representation;
    }
}
