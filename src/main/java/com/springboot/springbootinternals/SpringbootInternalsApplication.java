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

import java.util.function.Consumer;
import java.util.function.Function;

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

        @GetMapping("/rest")
        public DeferredResult<String> rest(@RequestParam("idx") int idx) {
            DeferredResult<String> dr = new DeferredResult<>();

            Completion
                    .from(rt.getForEntity(URL1, String.class, "hello " + idx))
                    .andApply(s-> rt.getForEntity(URL2, String.class, s.getBody()))
                    .andError(e -> dr.setErrorResult(e.toString()))
                    .andAccept(s-> dr.setResult(s.getBody()));
            
//            ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(URL1, String.class, "hello " + idx);
//            f1.addCallback(s->{
//                ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(URL2, String.class, "hello " + s.getBody());
//                f2.addCallback(s2->{
//                    ListenableFuture<String> f3 = myService.work(s2.getBody());
//                    f3.addCallback(s3-> {
//                        dr.setResult(s3);
//                    }, e->{
//                        dr.setErrorResult(e.getMessage());
//                    });
//                }, e->{
//                    dr.setErrorResult(e.getMessage());
//                });
//            }, e-> {
//                // 비동기 방식에서는 에러를 던져도 어느 스택트레이스에 타고 있는지 파악이 어렵다.
//                dr.setErrorResult(e.getMessage());
//            });
            return dr;
        }
    }

    public static class AcceptCompletion extends Completion {
        Consumer<ResponseEntity<String>> con;
        public AcceptCompletion(Consumer<ResponseEntity<String>> con) {
            this.con = con;
        }

        @Override
        void run(ResponseEntity<String> value) {
            con.accept(value);
        }
    }

    public static class ApplyCompletion extends Completion {
        Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;
        public ApplyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            this.fn = fn;
        }

        @Override
        void run(ResponseEntity<String> value) {
            final ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
            lf.addCallback(s->complete(s), e->error(e));
        }
    }


    public static class ErrorCompletion extends Completion {
        Consumer<Throwable> econ;
        public ErrorCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(ResponseEntity<String> value) {
            if (next != null) next.run(value);
        }

        @Override
        void error(Throwable e) {
            econ.accept(e);
        }
    }

    public static class Completion {
        Completion next;

        public void andAccept(Consumer<ResponseEntity<String>> con) {
            Completion c = new AcceptCompletion(con);
            this.next = c;
        }

        public Completion andError(Consumer<Throwable> econ) {
            Completion c = new ErrorCompletion(econ);
            this.next = c;
            return c;
        }

        public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
            Completion c = new ApplyCompletion(fn);
            this.next = c;
            return c;
        }

        public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
            Completion c = new Completion();
            lf.addCallback(s-> {
                c.complete(s);
            }, e-> {
                c.error(e);
            });
            return c;
        }

        void error(Throwable e) {
            if (next != null) next.error(e);

        }

        void complete(ResponseEntity<String> s) {
            if (next != null) next.run(s);
        }

        void run(ResponseEntity<String> value) {
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
