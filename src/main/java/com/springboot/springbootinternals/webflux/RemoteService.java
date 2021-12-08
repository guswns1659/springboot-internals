package com.springboot.springbootinternals.webflux;

import com.springboot.springbootinternals.kafka.JackProducer;
import com.springboot.springbootinternals.kafka.JackProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JackProducer.class, JackProducerConfig.class}),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern ={"com.springboot.springbootinternals.rabbitmq.*"})}
)
@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@EnableAsync
public class RemoteService {
    @RestController
    public static class MyController {
        @GetMapping("/service")
        public String rest(@RequestParam("req") String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service" ;
        }
    }

    public static void main(String[] args) {
        // Set system properties for two tomcat.
        System.setProperty("SERVER.PORT","8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
