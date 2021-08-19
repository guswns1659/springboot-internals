package com.springboot.springbootinternals.assertj.core.error;

import com.springboot.springbootinternals.assertj.core.description.Description;
import com.springboot.springbootinternals.assertj.core.internal.ComparisonStrategy;
import com.springboot.springbootinternals.assertj.core.internal.Failures;
import com.springboot.springbootinternals.assertj.core.presentation.Representation;

public class ShouldBeEqual implements AssertionErrorFactory {

    protected final Object actual;
    protected final Object expected;
    protected final MessageFormatter messageFormatter = MessageFormatter.instance();
    protected final ComparisonStrategy comparisonStrategy;
    private Representation representation;

    public static AssertionErrorFactory shouldBeEqual(Object actual, Object expected,
        ComparisonStrategy comparisonStrategy,
        Representation representation) {
        return new ShouldBeEqual(actual, expected, comparisonStrategy, representation);
    }

    protected ShouldBeEqual(Object actual, Object expected, ComparisonStrategy comparisonStrategy, Representation representation) {
        this.actual = actual;
        this.expected = expected;
        this.comparisonStrategy = comparisonStrategy;
        this.representation = representation;
    }

    // TODO
    @Override
    public AssertionError newAssertionError(Description description, Representation representation) {
        return null;
//        String message = smartErrorMessage(description, representation);
//        // only use JUnit error message if the comparison strategy used was standard, otherwise we need to mention
//        // comparison strategy in the assertion error message to make it clear to the user it was used.
//        if (comparisonStrategy.isStandard() && !actualAndExpectedHaveSameStringRepresentation()) {
//            // comparison strategy is standard -> try to build an AssertionFailedError used in JUnit 5 that is nicely displayed in IDEs
//            AssertionError assertionFailedError = assertionFailedError(message, representation);
//            // assertionFailedError != null means that JUnit 5 and opentest4j are in the classpath
//            if (assertionFailedError != null) return assertionFailedError;
//            // Junit5 was not used, try to build a JUnit 4 ComparisonFailure that is nicely displayed in IDEs
//            AssertionError error = comparisonFailure(description);
//            // error != null means that JUnit 4 was in the classpath and we build a ComparisonFailure.
//            if (error != null) return error;
//        }
//        AssertionError assertionFailedError = assertionFailedError(message, representation);
//        // assertionFailedError != null means that JUnit 5 and opentest4j was in the classpath
//        if (assertionFailedError != null) return assertionFailedError;
//        // No JUnit in the classpath => fall back to default error message
//        return Failures.instance().failure(message)
    }
}
