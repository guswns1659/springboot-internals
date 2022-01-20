package com.titanic.webflux.rdbreactive;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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

    // Rest of method... (Do I have to implement all?.. )


    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> entities) {
        return Mono.fromCallable(() -> delegate.saveAll(entities))
                .subscribeOn(scheduler) // subOn : Using scheduler when publisher creates data, DB request in here.
                .publishOn(Schedulers.boundedElastic()) // TODO(jack.comeback) : Need to remove
                .log() // TODO(jack.comeback) : Need to remove
                .flatMapMany(savedEntities -> Flux.fromIterable(savedEntities));
    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<T> findById(ID id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Flux<T> findAll() {
        return null;
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(T entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends ID> ids) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
