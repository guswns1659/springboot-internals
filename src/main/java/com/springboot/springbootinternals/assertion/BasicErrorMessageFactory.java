package com.springboot.springbootinternals.assertion;

import org.slf4j.helpers.MessageFormatter;

public class BasicErrorMessageFactory implements ErrorMessageFactory {

//    @VisibleForTesting
//    MessageFormatter formatter = Mes

    @Override
    public String create(Description d, Represetation represetation) {
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
