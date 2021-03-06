package com.titanic.webmvc.ymloveride;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Component
@RequiredArgsConstructor
public class JackCommandRunner implements CommandLineRunner {

    private final ApplicationYamlTestService applicationYamlTestService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("<<<<<" + applicationYamlTestService.ymlOverrideTest());
    }
}
