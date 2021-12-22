package com.titanic.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class WebfluxApplication {

    public static final String URL1 = "http://localhost:8081/service?req={req}";
    public static final String URL2 = "http://localhost:8081/service2?req={req}";

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Service
    public static class MyService {
        @Async
        public CompletableFuture<String> work(String req) {
            return CompletableFuture.completedFuture(req + "/asyncwork");
        }
    }

    @RestController
    class WebfluxController {

        @Autowired
        MyService myService;

        /**
         * Mono는 List와 같은 컨테이너라 생각하면된다. 컨테이너에 담아두면 컨테이너의 기능을 사용할 수 있다.
         */
        @GetMapping("/rest")
        public Mono<String> rest(@RequestParam("idx") int idx) {
            WebClient webClient = WebClient.create();
            // webClient는 pub이기 때문에 구독하지 않으면 실행되지 않는다.
            // 스프링이 리턴타입을 보고 Mono, Flux면 자동으로 구독하고 pub에 request도 보낸다.
            // --------
            // bodyToMono는 리턴이 Mono<String>이기 때문에 기존부터 Mono로 감싸져있기 때문에 map을 쓰면 Mono<Mono<>>가 된다.
            // flatMap으로 평평하게 만들어줘야한다.
            // --------
            // return은 main쓰레드가 바로 종료되기때문에 바로되지만 deferredResult처럼 결과를 담고 있는 형태가 된다.
            return webClient
                    .get()
                    .uri(URL1, idx)
                    .retrieve().bodyToMono(String.class)
                    .flatMap(res -> webClient.get().uri(URL2, res).retrieve().bodyToMono(String.class))
                    // fromCompletionStage의 응답이 Mono<?> 이기에 flatMap 사용
                    .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)));
        }
    }

}
