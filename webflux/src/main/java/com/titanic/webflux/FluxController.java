package com.titanic.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class FluxController {

    @GetMapping("/event/{id}")
    public Mono<Event> hello(@PathVariable long id) {
        return Mono.just(new Event(id, "event " + id));
    }

    @Data @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
