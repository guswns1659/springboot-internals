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

    @GetMapping("/")
    Mono<String> hello() {
        log.info("pos1");
        String msg = generateHello();
        // 이 시점이 아니라 return할 때 실행된다. 이유는 구독이 return할 때 발생하기 때문이다.
        Mono<String> m = Mono.just(msg).log();
        log.info("pos2");
        return m;
    }

    private String generateHello() {
        log.info("method generateHello()");
        return "Hello Mono";
    }
}
