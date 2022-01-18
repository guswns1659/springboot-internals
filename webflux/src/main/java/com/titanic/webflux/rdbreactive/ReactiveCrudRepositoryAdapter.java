package com.titanic.webflux.rdbreactive;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

public abstract class ReactiveCrudRepositoryAdapter<T, ID, I extends CrudRepository<T, ID>>
        implements ReactiveCrudRepository<T, ID> {

    protected final I delegate;
    protected final Scheduler scheduler;

    public ReactiveCrudRepositoryAdapter(
            I delegate,
            Scheduler scheduler) {
        this.delegate = delegate;
        this.scheduler = scheduler;
    }

    @Override
    public <S extends T> Mono<S> save(S entity) {
        return Mono
                .fromCallable(() -> delegate.save(entity))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(Publisher<ID> id) {
        return Mono.from(id)
                .flatMap(actualId -> delegate.findById(actualId)
                        .map(Mono::just)
                        .orElseGet(Mono::empty))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> entities) {
        return Flux.from(entities)
                .flatMap(entity -> Mono
                        .fromRunnable(() -> delegate.delete(entity))
                        .subscribeOn(scheduler))
                .then();
    }
}
