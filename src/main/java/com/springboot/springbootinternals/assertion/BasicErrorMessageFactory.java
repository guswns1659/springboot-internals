package com.springboot.springbootinternals.assertion;

public class BasicErrorMessageFactory implements ErrorMessageFactory {

//    @VisibleForTesting
//    MessageFormatter formatter = Mes

    @Override
    public String create(Description d, Representation representation) {
        return "";
    }

    @Override
    public String create(Description d) {
        return null;
    }

    @Override
    public String create() {
        return null;
    }
}
