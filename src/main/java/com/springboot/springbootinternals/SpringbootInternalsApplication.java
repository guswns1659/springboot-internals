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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
        /** AOP 기반으로 @Async 비동기 작업을 수행한다.
         *  Executor를 지정하지 않으면 SimpleAsyncTaskExecutor가 동작하는데, 매 요청마다 쓰레드를 새로 만든다.
         *  운영에서는 Executor를 지정해야 한다.
         */
        @Async(value = "tp")
        public ListenableFuture<String> hello() throws InterruptedException {
            log.info("##### hello()");
            Thread.sleep(2000);
            return new AsyncResult<>("Hello");
        }
    }

    @Bean
    ThreadPoolTaskExecutor tp() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        // 미리 만들어주는 쓰레드 개수. 첫 쓰레드 요청이 오면 만든다.
        te.setCorePoolSize(10);
        // Core 개수가 차면 대기거는 개수
        te.setQueueCapacity(200);
        // 주의) 큐가 다 찼을 때 max까지 만드는 것이다. Core가 넘어간다고 Max까지 만들지 않는다.
        te.setMaxPoolSize(100);
        te.setThreadNamePrefix("mythread");
        te.initialize();
        return te;
    };

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
