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

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/flux")
public class FluxController {

    /**
     *  Diff between Mono and Flux
     * Impossible to operate on each elements of list when Lists exists in Mono.
     * Possible to operate on each elements of list when Lists exists in Flux.
     */

    @GetMapping("/event/{id}")
    public Mono<List<Event>> event(@PathVariable long id) {
        List<Event> events = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Mono.just(events);
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> events() {
        Flux<String> es = Flux.generate(sink -> sink.next("Value"));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(es, interval).map(tu -> new Event(tu.getT2(), tu.getT1())).take(10);
    }

    @Data @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
