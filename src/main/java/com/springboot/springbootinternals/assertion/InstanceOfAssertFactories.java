package com.springboot.springbootinternals.assertion;

import java.util.function.Predicate;

/**
 * Static InstanceOfAssertFactories for Assert.asInstanceOf(InstanceOfAssertFactory)
 */
public interface InstanceOfAssertFactories {

    /**
     * InstanceOfAssertFactory for a Predicate, assuming Object as input type
     */
    @SuppressWarnings("rawtypes")
    InstanceOfAssertFactory<Predicate, PredicateAssert<Object>> PREDICATE = predicate(Object.class);

    /**
     * InstanceOfAssertFactory for a Predicate
     * @param type
     * @param <T>
     * @return
     */
    static <T> InstanceOfAssertFactory<Predicate, PredicateAssert<T>> predicate(Class<T> type) {
        return new InstanceOfAssertFactory<>(Predicate.class, Assertions::<T> assertJack);
    }

}
