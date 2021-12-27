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
        log.info("pos2");
        return m;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Mono";
    }
}
