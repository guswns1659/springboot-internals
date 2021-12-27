package com.titanic.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mono")
@Slf4j
public class MonoController {

    /**
     * Mono.just : subscribe 시점이 아닌 미리 데이터를 만들어 놓는다.
     * Mono.fromSupplier : subscribe 시점에 데이터가 만들어진다.
     */
    @GetMapping("/")
    Mono<String> hello() {
        log.info("pos1");
        // 이 시점이 아니라 return할 때 실행된다. 이유는 구독이 return할 때 발생하기 때문이다.
        Mono<String> m = Mono.fromSupplier(() -> generateHello()).log();
        /**
         * Mono, Flux는 하나 이상의 subscriber를 가질 수 있다.
         * return 전에 subscribe하면 리액티브 스트림이 바로 실행된다.
         *
         * Cold source, DB 호출처럼 미리 정해진 코드는 구독할 때마다 처음부터 replay된다.
         * Hot source, 사용자 입력처럼 미리 정해지지 않는 코드는 구독할 때마다 reploy가 아닌 다음 요청부터 구독한다.
         */
        m.subscribe();
        log.info("pos2");
        return m;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Mono";
    }
}
