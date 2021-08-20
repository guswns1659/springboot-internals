package com.springboot.springbootinternals.assertj.core.presentation;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/**
 * Standard java object representation
 */
public class StandardRepresentation implements Representation {

    // can share this as StandardRepresentation has no state
    public static final StandardRepresentation STANDARD_REPRESENTATION = new StandardRepresentation();

    private static final Map<Class<?>, Function<?, String>> customFormatterByType = new HashMap<>();


    protected boolean hasCustomFormatterFor(Object object) {
        if (object == null) return false;
        return customFormatterByType.containsKey(object.getClass());
    }

    @SuppressWarnings("unchecked")
    protected <T> String customFormat(T object) {
        if (object == null) return null;
        return ((Function<T, String>) customFormatterByType.get(object.getClass())).apply(object);
    }

    // TODO
    /**
     * Returns standard the toString representation of the given object. it may or not the object's own
     * implementation of toString
     * @param object
     * @return
     */
    @Override
    public String toStringOf(Object object) {
//        if (object == null) return null;
//        if (hasCustomFormatterFor(object)) return customFormat(object);
//        if (object instanceof ComparatorBasedComparisonStrategy) return toStringOf((ComparatorBasedComparisonStrategy) object);
//        if (object instanceof Calendar) return toStringOf((Calendar) object);
//        if (object instanceof Class<?>) return toStringOf((Class<?>) object);
//        if (object instanceof Date) return toStringOf((Date) object);
//        if (object instanceof Duration) return toStringOf((Duration) object);
//        if (object instanceof LocalDate) return toStringOf((LocalDate) object);
//        if (object instanceof LocalDateTime) return toStringOf((LocalDateTime) object);
//        if (object instanceof OffsetDateTime) return toStringOf((OffsetDateTime) object);
//        if (object instanceof ZonedDateTime) return toStringOf((ZonedDateTime) object);
//        if (object instanceof LongAdder) return toStringOf((LongAdder) object);
//        if (object instanceof AtomicReference) return toStringOf((AtomicReference<?>) object);
//        if (object instanceof AtomicMarkableReference) return toStringOf((AtomicMarkableReference<?>) object);
//        if (object instanceof AtomicStampedReference) return toStringOf((AtomicStampedReference<?>) object);
//        if (object instanceof AtomicIntegerFieldUpdater) return AtomicIntegerFieldUpdater.class.getSimpleName();
//        if (object instanceof AtomicLongFieldUpdater) return AtomicLongFieldUpdater.class.getSimpleName();
//        if (object instanceof AtomicReferenceFieldUpdater) return AtomicReferenceFieldUpdater.class.getSimpleName();
//        if (object instanceof File) return toStringOf((File) object);
//        if (object instanceof Path) return fallbackToStringOf(object);
//        if (object instanceof String) return toStringOf((String) object);
//        if (object instanceof Character) return toStringOf((Character) object);
//        if (object instanceof Comparator) return toStringOf((Comparator<?>) object);
//        if (object instanceof SimpleDateFormat) return toStringOf((SimpleDateFormat) object);
//        if (object instanceof PredicateDescription) return toStringOf((PredicateDescription) object);
//        if (object instanceof Future) return toStringOf((Future<?>) object);
//        if (isArray(object)) return formatArray(object);
//        if (object instanceof Collection<?>) return smartFormat((Collection<?>) object);
//        if (object instanceof Map<?, ?>) return toStringOf((Map<?, ?>) object);
//        if (object instanceof Tuple) return toStringOf((Tuple) object);
//        if (object instanceof MapEntry) return toStringOf((MapEntry<?, ?>) object);
//        if (object instanceof Method) return ((Method) object).toGenericString();
//        if (object instanceof InsertDelta<?>) return toStringOf((InsertDelta<?>) object);
//        if (object instanceof ChangeDelta<?>) return toStringOf((ChangeDelta<?>) object);
//        if (object instanceof DeleteDelta<?>) return toStringOf((DeleteDelta<?>) object);
//        // Only format Iterables that are not collections and have not overridden toString
//        // ex: JsonNode is an Iterable that is best formatted with its own String
//        // Path is another example but we can deal with it specifically as it is part of the JDK.
//        if (object instanceof Iterable<?> && !hasOverriddenToString((Iterable<?>) object)) return smartFormat((Iterable<?>) object);
//        if (object instanceof AtomicInteger) return toStringOf((AtomicInteger) object);
//        if (object instanceof AtomicBoolean) return toStringOf((AtomicBoolean) object);
//        if (object instanceof AtomicLong) return toStringOf((AtomicLong) object);
//        if (object instanceof Number) return toStringOf((Number) object);
//        if (object instanceof Throwable) return toStringOf((Throwable) object);
//        return fallbackToStringOf(object);
        return null;
    }
}
