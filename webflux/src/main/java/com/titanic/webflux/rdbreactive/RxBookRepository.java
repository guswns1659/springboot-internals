package com.titanic.webflux.rdbreactive;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Repository
public class RxBookRepository extends ReactiveCrudRepositoryAdapter<Book, Integer, BookJpaRepository> {

    public RxBookRepository(
            BookJpaRepository delegate,
            @Qualifier("jdbcScheduler") Scheduler scheduler) {
        super(delegate, scheduler);
    }

    public Flux<Book> findByBetween(
            Publisher<Integer> lowerPublisher,
            Publisher<Integer> upperPublisher
    ) {
        return Mono.zip(
                Mono.from(lowerPublisher),
                Mono.from(upperPublisher)
        ).flatMapMany(
                tuple ->
                        Flux.fromIterable(delegate.findByIdBetween(tuple.getT1(), tuple.getT2()))
                                .subscribeOn(scheduler));
    }

    public Flux<Book> findShortestTitle() {
        return Mono.fromCallable(() -> delegate.findShortestTitle())
                .subscribeOn(scheduler)
                .flatMapMany(books -> Flux.fromIterable(books));
    }
}
