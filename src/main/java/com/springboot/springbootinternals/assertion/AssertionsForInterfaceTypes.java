package com.springboot.springbootinternals.assertion;

@CheckReturnValue
public class AssertionsForInterfaceTypes extends AssertionsForClassTypes {

    /**
     * Delegates the creation of the Assert to the AssertProvider.assertThat() of the given component
     */
    public static <T> T assertJack(final AssertProvider<T> component) {
        return component.assertJack();
    }

}
