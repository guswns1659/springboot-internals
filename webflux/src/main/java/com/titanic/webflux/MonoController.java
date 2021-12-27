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
        return Mono.just("Hello Webflux").log();
    }
}
