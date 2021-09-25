package com.springboot.springbootinternals.assertj.core.error;

import static com.springboot.springbootinternals.assertj.core.util.Arrays.array;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.internal.ComparatorBasedComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.internal.ComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.internal.Failures;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;
import java.util.Objects;

public class ShouldBeEqual implements AssertionErrorFactory {

    private static final String EXPECTED_BUT_WAS_MESSAGE = "%nexpected: %s%nbut was : %s";
    private static final String EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR = EXPECTED_BUT_WAS_MESSAGE + "%n%s";
    private static final Class<?>[] MSG_ARG_TYPES = array(String.class, String.class, String.class);
    private static final Class<?>[] MSG_ARG_TYPES_FOR_ASSERTION_FAILED_ERROR = array(String.class, Object.class,
        Object.class);

    protected final Object actual;
    protected final Object expected;
    protected final MessageFormatter messageFormatter = MessageFormatter.instance();
    protected final ComparisonStrategy comparisonStrategy;
    @VisibleForTesting
    ConstructorInvoker constructorInvoker = new ConstructorInvoker();
    @VisibleForTesting
    DescriptionFormatter descriptionFormatter = DescriptionFormatter.instance();
    private Representation representation;

    protected ShouldBeEqual(Object actual, Object expected, ComparisonStrategy comparisonStrategy,
        Representation representation) {
        this.actual = actual;
        this.expected = expected;
        this.comparisonStrategy = comparisonStrategy;
        this.representation = representation;
    }

    public static AssertionErrorFactory shouldBeEqual(Object actual, Object expected,
        ComparisonStrategy comparisonStrategy,
        Representation representation) {
        return new ShouldBeEqual(actual, expected, comparisonStrategy, representation);
    }

    /**
     * Creates an AssertionError indicating that an assertion that verifies that two objects are equal failed. The
     * AssertionError message is built so that it differentiates actual and expected description in case their
     * string representation are the same (like 42 float and 42 double)
     *
     * @param description
     * @param representation
     * @return
     */
    @Override
    public AssertionError newAssertionError(Description description, Representation representation) {
        String message = smartErrorMessage(description, representation);
        // only use JUnit error message if the comparison strategy used was standard, otherwise we need to mention
        // comparison strategy in the assertion error message to make it clear to the user it was used.
        if (comparisonStrategy.isStandard() && !actualAndExpectedHaveSameStringRepresentation()) {
            // comparison strategy is standard -> try to build an AssertionFailedError used in JUnit 5 that is nicely displayed in IDEs
            AssertionError assertionFailedError = assertionFailedError(message, representation);
            // assertionFailedError != null means that JUnit 5 and opentest4j are in the classpath
            if (assertionFailedError != null) {
                return assertionFailedError;
            }
            // Junit5 was not used, try to build a JUnit 4 ComparisonFailure that is nicely displayed in IDEs
            AssertionError error = comparisonFailure(description);
            // error != null means that JUnit 4 was in the classpath and we build a ComparisonFailure.
            if (error != null) {
                return error;
            }
        }
        AssertionError assertionFailedError = assertionFailedError(message, representation);
        // assertionFailedError != null means that JUnit 5 and opentest4j was in the classpath
        if (assertionFailedError != null) {
            return assertionFailedError;
        }
        // No JUnit in the classpath => fall back to default error message
        return Failures.instance().failure(message);
    }

    private AssertionError comparisonFailure(Description description) {
        try {
            AssertionError comparisonFailure = newComparisonFailure(
                descriptionFormatter.format(description).trim());
            Failures.instance().removeAssertJRelatedElementsFromStackTraceIfNeeded(comparisonFailure);
            return comparisonFailure;
        } catch (Throwable e) {
            return null;
        }
    }

    private AssertionError newComparisonFailure(String description) throws Exception {
        Object o = constructorInvoker.newInstance("org.junit.ComparisonFailure",
            MSG_ARG_TYPES,
            description,
            representation.toStringOf(expected),
            representation.toStringOf(actual));
        if (o instanceof AssertionError) {
            return (AssertionError) o;
        }
        return null;
    }

    private AssertionError assertionFailedError(String message, Representation representation) {
        try {
            Object o = constructorInvoker.newInstance("org.opentest4j.AssertionFailedError",
                MSG_ARG_TYPES_FOR_ASSERTION_FAILED_ERROR,
                message,
                representation.toStringOf(expected),
                representation.toStringOf(actual));

            if (o instanceof AssertionError) {
                AssertionError assertionError = (AssertionError) o;
                Failures.instance().removeAssertJRelatedElementsFromStackTraceIfNeeded(assertionError);
                return assertionError;
            }
            return null;
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Builds and returns an error message from the given description using expected and actual basic
     * representation if their description differ otherwise use defaultDetailedErrorMessage to represent them
     * differently.
     */
    protected String smartErrorMessage(Description description, Representation representation) {
        if (actualAndExpectedHaveSameStringRepresentation()) {
            // This happens for example when actual = 42f and expected = 42d, which will give this error:
            // actual : "42" and expected : "42".
            // JUnit 4 manages this case even worst, it will output something like :
            // "java.lang.String expected:java.lang.String<42.0> but was: java.lang.String<42.0>"
            // which makes things even more confusing since we lost the fact that 42 was a float or a double.
            // It is therefore better to built our own description without using ComparisonFailure, the
            // only drawback is that it won't look nice in IDEs.
            return defaultDetailedErrorMessage(description, representation);
        }
        return comparisonStrategy.isStandard()
            ? messageFormatter.format(description, representation, EXPECTED_BUT_WAS_MESSAGE, expected, actual)
            : messageFormatter.format(description, representation, EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR,
                expected, actual, comparisonStrategy);
    }

    protected boolean actualAndExpectedHaveSameStringRepresentation() {
        return Objects.equals(representation.toStringOf(actual), representation.toStringOf(expected));
    }

    /**
     * Builds and returns an error message from description using detailedExpected() and detailed representation.
     *
     * @param description
     * @param representation
     * @return
     */
    protected String defaultDetailedErrorMessage(Description description, Representation representation) {
        if (comparisonStrategy instanceof ComparatorBasedComparisonStrategy) {
            return messageFormatter.format(description, representation, EXPECTED_BUT_WAS_MESSAGE_USING_COMPARATOR,
                detailedExpected(), detailedActual(), comparisonStrategy);
        }
        return messageFormatter
            .format(description, representation, EXPECTED_BUT_WAS_MESSAGE, detailedExpected(), detailedActual());
    }

    protected String detailedExpected() {
        return representation.unambiguousToStringOf(expected);
    }

    protected String detailedActual() {
        return representation.unambiguousToStringOf(actual);
    }
}
