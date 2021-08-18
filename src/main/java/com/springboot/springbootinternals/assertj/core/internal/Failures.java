package com.springboot.springbootinternals.assertj.core.internal;

import static com.springboot.springbootinternals.assertj.core.util.Strings.isNullOrEmpty;
import static java.lang.String.format;

import com.springboot.springbootinternals.assertj.core.api.AssertionInfo;
import com.springboot.springbootinternals.assertj.core.configuration.Configuration;
import com.springboot.springbootinternals.assertj.core.error.AssertionErrorCreator;
import com.springboot.springbootinternals.assertj.core.error.AssertionErrorFactory;
import com.springboot.springbootinternals.assertj.core.error.ErrorMessageFactory;
import com.springboot.springbootinternals.assertj.core.error.MessageFormatter;
import com.springboot.springbootinternals.assertj.core.util.Throwables;
import com.springboot.springbootinternals.assertj.core.util.VisibleForTesting;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Failure actions
 */
public class Failures {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static final Failures INSTANCE = new Failures();
    private AssertionErrorCreator assertionErrorCreator = new AssertionErrorCreator();

    /**
     * flag indicating that in case of a failure a threaddump is printed out.
     */
    private boolean printThreadDump = false;

    /**
     * flag indicating whether or not we remove elements related to AssertJ from assertion error stack trace.
     */
    private boolean removeAssertJRelatedElementsFromStackTrace = Configuration.REMOVE_ASSERTJ_RELATED_ELEMENTS_FROM_STACK_TRACE;


    @VisibleForTesting
    Failures() {
    }

    public static Failures instance() {
        return INSTANCE;
    }

    /**
     * Creates a AssertionError following this pattern: 1. ~~ 2. ~~
     *
     * @param info           - contains information about the failed assertion
     * @param messageFactory - knows how to create detail messages for AssertionErrors.
     * @return : the created AssertionError
     */
    public AssertionError failure(AssertionInfo info, ErrorMessageFactory messageFactory) {
        AssertionError error = failureIfErrorMessageIsOverridden(info);
        if (error != null) return error;
        String assertionErrorMessage = assertionErrorMessage(info, messageFactory);
        AssertionError assertionError = assertionErrorCreator.assertionError(assertionErrorMessage);
        removeAssertJRelatedElementsFromStackTraceIfNeeded(assertionError);
        printThreadDumpIfNeeded();
        return assertionError;
    }

    public AssertionError failure(AssertionInfo info, AssertionErrorFactory factory) {
        AssertionError error = failureIfErrorMessageIsOverridden(info);
        if (error != null) return error;
        printThreadDumpIfNeeded();
        return factory.newAssertionError(info.description(), info.representation());
    }

    protected String assertionErrorMessage(AssertionInfo info, ErrorMessageFactory messageFactory) {
        String overridingErrorMessage = info.overridingErrorMessage();
        return isNullOrEmpty(overridingErrorMessage)
            ? messageFactory.create(info.description(), info.representation())
            : MessageFormatter.instance().format(info.description(), info.representation(), overridingErrorMessage);
    }

    public AssertionError failureIfErrorMessageIsOverridden(AssertionInfo info) {
        String overridingErrorMessage = info.overridingErrorMessage();
        return isNullOrEmpty(overridingErrorMessage) ? null
            : failure(MessageFormatter.instance().format(info.description(), info.representation(),
                overridingErrorMessage));
    }

    /**
     * Creates a AssertionError using the given String as message. It filters the AssertionError stack trace by
     * default, th have full stack trace use set ~~
     *
     * @param message - the message of the AssertionsError to create
     * @return: the created AssertionError.
     */
    public AssertionError failure(String message) {
        AssertionError assertionError = assertionErrorCreator.assertionError(message);
        removeAssertJRelatedElementsFromStackTraceIfNeeded(assertionError);
        printThreadDumpIfNeeded();
        return assertionError;
    }

    public void removeAssertJRelatedElementsFromStackTraceIfNeeded(AssertionError assertionError) {
        if (removeAssertJRelatedElementsFromStackTrace) {
            Throwables.removeAssertJRelatedElementsFromStackTrace(assertionError);
        }
    }

    public void printThreadDumpIfNeeded() {
        if (printThreadDump) {
            System.err.println(threadDumpDescription());
        }
    }

    public static String threadDumpDescription() {
        StringBuilder threadDumpDescription = new StringBuilder();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        for (ThreadInfo threadInfo : threadInfos) {
            threadDumpDescription.append(format("\"%s\"%n\tjava.lang.Thread.State: %s",
                threadInfo.getThreadName(), threadInfo.getThreadState()));
            for (StackTraceElement stackTraceElement : threadInfo.getStackTrace()) {
                threadDumpDescription.append(LINE_SEPARATOR).append("\t\tat ").append(stackTraceElement);
            }
            threadDumpDescription.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        }
        return threadDumpDescription.toString();
    }
}
