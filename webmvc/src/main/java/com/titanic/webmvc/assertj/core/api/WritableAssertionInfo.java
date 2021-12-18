package com.titanic.webmvc.assertj.core.api;


import com.titanic.webmvc.assertj.core.description.Description;
import com.titanic.webmvc.assertj.core.presentation.Representation;

import java.util.function.Supplier;

import static com.titanic.webmvc.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static com.titanic.webmvc.assertj.core.util.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

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
        return overridingErrorMessageSupplier != null ? overridingErrorMessageSupplier.get()
            : overridingErrorMessage;
    }

    @Override
    public Description description() {
        return description;
    }

    @Override
    public Representation representation() {
        return representation;
    }

    /**
     * Sets the lay fail message that will replace the default message of an assertion failure by using a supplier.
     */
    public void overridingErrorMessage(Supplier<String> supplier) {
        checkState(overridingErrorMessage == null,
            "An error message has already been set with overridingErrorMessage(String newErrorMessage)");
        overridingErrorMessageSupplier = supplier;
    }
}
