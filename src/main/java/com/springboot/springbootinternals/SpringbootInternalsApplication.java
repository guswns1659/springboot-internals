package com.springboot.springbootinternals;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.Executors;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * 쓰레드가 많이 만들어지면?
 * - OOME가 발생할 수 있다. 쓰레드가 가져야 하는 기본적인 프로퍼티가 있기 때문에
 * - 컨텍스트 스위칭이 많이 발생할 수 있다. 놀고 있는 쓰레드를 보면 waiting으로 바꾸고
 *   다시 일 할때는 running으로 하기 때문에
 */
@ComponentScan(
                excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JackProducer.class, JackProducerConfig.class}))
@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@EnableAsync
public class SpringbootInternalsApplication {

    @RestController
    public static class MyController {
        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() throws InterruptedException {
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();

            Executors.newSingleThreadExecutor().submit(() -> {
                for (int i = 1; i <= 50; i++) {
                    try {
                        emitter.send("<p>Stream " + i + "</p>");
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return emitter;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInternalsApplication.class, args);
    }
}
