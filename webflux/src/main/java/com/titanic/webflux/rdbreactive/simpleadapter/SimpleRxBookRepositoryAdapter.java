package com.titanic.webflux.rdbreactive.simpleadapter;

import com.titanic.webflux.rdbreactive.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Component
public class SimpleRxBookRepositoryAdapter {

    private final SimpleRxBookRepository simpleRxBookRepository;
    private final Scheduler scheduler;

    public SimpleRxBookRepositoryAdapter(
            SimpleRxBookRepository simpleRxBookRepository,
            @Qualifier("jdbcScheduler") Scheduler scheduler) {
        this.simpleRxBookRepository = simpleRxBookRepository;
        this.scheduler = scheduler;
    }

    public Mono<Book> save(Book entity) {
        return Mono.fromCallable(() -> simpleRxBookRepository.save(entity))
                .subscribeOn(scheduler);
    }

    public Mono<Book> read(Integer id) {
        return Mono.fromCallable(() -> simpleRxBookRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("[id] - " + id + ", no matching entity")))
                .subscribeOn(scheduler);
    }
}
