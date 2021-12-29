package com.titanic.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class FluxController {

    @GetMapping("/event/{id}")
    public Mono<Event> event(@PathVariable long id) {
        return Mono.just(new Event(id, "event " + id));
    }

    @GetMapping("/events")
    public Flux<Event> events() {
        return Flux.just(new Event(1L, "event1"), new Event(2L, "event2"));
    }

    @Data @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
