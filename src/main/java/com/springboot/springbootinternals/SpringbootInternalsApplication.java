package com.springboot.springbootinternals;

import com.springboot.springbootinternals.kafka.JackProducer;
import com.springboot.springbootinternals.kafka.JackProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import static org.springframework.context.annotation.ComponentScan.Filter;

@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JackProducer.class, JackProducerConfig.class}))
@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@EnableAsync
public class SpringbootInternalsApplication {

    @Component
    public static class MyService {
        // AOP 기반으로 @Async 비동기 작업을 수행한다.
        @Async
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("##### hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello");
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInternalsApplication.class, args);
    }

    @Autowired
    MyService myService;

    @Bean
    ApplicationRunner run() {
        return args -> {
            log.info("##### run()");
            ListenableFuture<String> f = myService.hello();
            f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
            log.info("##### exit");
        };
    }

}
