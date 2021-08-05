package com.springboot.springbootinternals.assertj.core.configuration;

import static java.lang.String.format;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A simple locator for SPI implementations
 */
class Services {

    private Services() {}

    public static <SERVICE> SERVICE get(Class<SERVICE> serviceType, SERVICE defaultValue) {

        Iterator<SERVICE> services = ServiceLoader.load(serviceType, Services.class.getClassLoader()).iterator();

        SERVICE result = services.hasNext() ? services.next() : defaultValue;
        if (services.hasNext()) {
            result = defaultValue;
            System.err.println(format("Found multiple implementations for the service provider %s. Using the default: %s",
                serviceType, result.getClass()));
        }
        return result;
    }

}
