package com.titanic.webmvc;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableAsync
@EnableBatchProcessing
@Slf4j
public class WebmvcApplication {

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

            toCF(rt.getForEntity(URL1, String.class, "hello " + idx))
                    .thenCompose(s -> toCF(rt.getForEntity(URL2, String.class,  s.getBody())))
                    .thenApplyAsync(s2 -> myService.work(s2.getBody()))
                    .thenAccept(s3 -> dr.setResult(s3))
                    // function 타입으로 받아야해서 명시적으로 null을 리턴해야함.
                    .exceptionally(e -> {
                        dr.setErrorResult(e.getMessage());
                        return (Void) null; });

            return dr;
        }

        // lf -> cf
        <T> CompletableFuture<T> toCF(ListenableFuture<T> lf) {
            CompletableFuture<T> cf = new CompletableFuture<>();
            lf.addCallback(s-> cf.complete(s), e-> cf.completeExceptionally(e));

            return cf;
        }
    }

    // Consumer acceptCompletion
    // value를 받아서 소비하기 때문에 S. 사실 어렵당..
    public static class AcceptCompletion<S> extends Completion<S, Void> {
        Consumer<S> con;
        public AcceptCompletion(Consumer<S> con) {
            this.con = con;
        }

        @Override
        void run(S value) {
            con.accept(value);
        }
    }

    // Function applyCompletion
    public static class ApplyCompletion<S, T> extends Completion<S, T> {
        Function<S, ListenableFuture<T>> fn;
        public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
            this.fn = fn;
        }

        @Override
        void run(S value) {
            ListenableFuture<T> lf = fn.apply(value);
            lf.addCallback(s->complete(s), e->error(e));
        }
    }

    // Error consumer Completion
    public static class ErrorCompletion<T> extends Completion<T,T> {
        Consumer<Throwable> econ;
        public ErrorCompletion(Consumer<Throwable> econ) {
            this.econ = econ;
        }

        @Override
        void run(T value) {
            if (next != null) next.run(value);
        }

        @Override
        void error(Throwable e) {
            econ.accept(e);
        }
    }

    // Basic Completion
    public static class Completion<S, T> {
        Completion next;

        public void andAccept(Consumer<T> con) {
            log.info("andAccept start");
            Completion<T, Void> c = new AcceptCompletion<T>(con);
            this.next = c;
        }

        // retureType을 안넘기면 다음에 수행하는 객체가 들어오는 타입을 알 수 없다. 컴파일에러는 Object 타입이라고 알려줌
        public Completion<T, T> andError(Consumer<Throwable> econ) {
            log.info("andError start");
            Completion<T, T> c = new ErrorCompletion<>(econ);
            this.next = c;
            return c;
        }

        // V의 이유는 어떤 리스너블퓨처가 들어올지 모르니까 새로운 타입을 설정하고, 메세드 레벨에 적어놓는다.
        public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
            log.info("andApply start");
            Completion<T, V> c = new ApplyCompletion<>(fn);
            this.next = c;
            return c;
        }

        // from은 static 메서드라서 타입파라미터 대신 메서드 파라미터를 사용한다.
        // return type의 타입을 알려줘야 다음 Completion이 타입을 알 수 있다.
        public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
            log.info("from start");
            Completion<S, T> c = new Completion<>();
            lf.addCallback(s-> {
                c.complete(s);
            }, e-> {
                c.error(e);
            });
            return c;
        }

        void error(Throwable e) { if (next != null) next.error(e); }

        // ListenableFuture가 실행된 결과가 들어오기 때문에 T
        void complete(T s) {
            log.info("complete start");
            if (next != null) next.run(s);
        }

        // value를 가지고 어떤 로직을 수행하기 때문에 S
        void run(S value) { }
    }

    @Service
    public static class MyService {
        public String work(String req) {
            return req + "/asyncwork";
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
        SpringApplication.run(WebmvcApplication.class, args);
    }

}
