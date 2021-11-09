package com.springboot.springbootinternals;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationYamlTestService {

    @Value("${jack.name:name}")
    private String name;

    public String ymlOverrideTest() {
        return name;
    }
}
