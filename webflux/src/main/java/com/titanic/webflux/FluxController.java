package com.titanic.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/flux")
public class FluxController {

    @GetMapping("/event/{id}")
    public Mono<List<Event>> event(@PathVariable long id) {
        List<Event> events = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        // Impossible to operate on each elements of list when Lists exists in Mono.
        return Mono.just(events);
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> events() {
        List<Event> events = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        // Possible to operate on each elements of list when Lists exists in Flux.
        return Flux.fromIterable(events);
    }

    @Data @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
