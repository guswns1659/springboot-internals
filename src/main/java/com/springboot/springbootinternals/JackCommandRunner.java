package com.springboot.springbootinternals;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JackCommandRunner implements CommandLineRunner {

    private final ApplicationYamlTestService applicationYamlTestService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("<<<<<" + applicationYamlTestService.ymlOverrideTest());
    }
}
