package com.springboot.springbootinternals;

import com.springboot.springbootinternals.kafka.JackProducer;
import com.springboot.springbootinternals.kafka.JackProducerConfig;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * 쓰레드가 많이 만들어지면?
 * - OOME가 발생할 수 있다. 쓰레드가 가져야 하는 기본적인 프로퍼티가 있기 때문에
 * - 컨텍스트 스위칭이 많이 발생할 수 있다. 놀고 있는 쓰레드를 보면 waiting으로 바꾸고
 *   다시 일 할때는 running으로 하기 때문에
 */
@ComponentScan(
                excludeFilters = {
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JackProducer.class, JackProducerConfig.class}),
                @Filter(type = FilterType.REGEX, pattern ={"com.springboot.springbootinternals.rabbitmq.*"})}
)
@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@EnableAsync
public class SpringbootInternalsApplication {

    /**
     * AsyncRestTemplate만으로는 쓰레드 하나만 사용되진 않는다. 뒤에서 worker 쓰레드가 만들어진다.
     * Netty를 추가하면 쓰레드가 1개만 만들어진다.
     */
    AsyncRestTemplate rt = new AsyncRestTemplate(
            new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

    /** config 셋팅. // thread 하나로 어떻게 동작하는지 알기 위함
     * server:
     *   tomcat:
     *     threads:
     *       max: 1
     */
    @RestController
    public class MyController {

        @Autowired
        MyService myService;

        static final String URL1 = "http://localhost:8081/service?req={req}";
        static final String URL2 = "http://localhost:8081/service2?req={req}";

        // success callback을 스프링에서 자동으로 등록해주기 때문에 사용자가 직접 등록하지 않는다.
        @GetMapping("/rest")
        public DeferredResult<String> rest(@RequestParam("idx") int idx) {
            DeferredResult<String> dr = new DeferredResult<>();

            ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(URL1, String.class, "hello " + idx);
            f1.addCallback(s->{
                ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(URL2, String.class, "hello " + s.getBody());
                f2.addCallback(s2->{
                    ListenableFuture<String> f3 = myService.work(s2.getBody());
                    f3.addCallback(s3-> {
                        dr.setResult(s3);
                    }, e->{
                        dr.setErrorResult(e.getMessage());
                    });
                }, e->{
                    dr.setErrorResult(e.getMessage());
                });
            }, e-> {
                // 비동기 방식에서는 에러를 던져도 어느 스택트레이스에 타고 있는지 파악이 어렵다.
                dr.setErrorResult(e.getMessage());
            });
            return dr;
        }
    }

    @Service
    public static class MyService {
        @Async
        public ListenableFuture<String> work(String req) {
            return new AsyncResult<>(req + "/asyncwork");
        }
    }

    @Bean
    public ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
        te.setCorePoolSize(1);
        te.setMaxPoolSize(1);
        te.initialize();
        return te;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInternalsApplication.class, args);
    }
}
