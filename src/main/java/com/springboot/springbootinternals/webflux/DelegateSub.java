package com.springboot.springbootinternals.webflux;

import java.util.concurrent.Flow;

/**
 * DelegateSub equals operators that change data published by upstream.
 */
public class DelegateSub<T> implements Flow.Subscriber<T> {

    Flow.Subscriber subscriber;

    public DelegateSub(Flow.Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // only delegate
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T item) {
        subscriber.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
