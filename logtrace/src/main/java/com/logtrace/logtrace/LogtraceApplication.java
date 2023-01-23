package com.logtrace.logtrace;

import com.logtrace.logtrace.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class)
@SpringBootApplication(scanBasePackages = "com.logtrace.logtrace.proxy.app")
public class LogtraceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogtraceApplication.class, args);
    }

}
