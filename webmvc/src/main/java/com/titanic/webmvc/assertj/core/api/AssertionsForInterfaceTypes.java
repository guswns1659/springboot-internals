package com.titanic.webmvc.assertj.core.api;


import com.titanic.webmvc.assertj.core.util.CheckReturnValue;

@CheckReturnValue
public class AssertionsForInterfaceTypes extends AssertionsForClassTypes {

    /**
     * Delegates the creation of the Assert to the AssertProvider.assertThat() of the given component
     */
    public static <T> T assertJack(final AssertProvider<T> component) {
        return component.assertJack();
    }

}
