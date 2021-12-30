package com.titanic.webflux.tobylecturerepeat;

import java.util.concurrent.Flow;

public class DelegateSub implements Flow.Subscriber<Integer> {

    private final Flow.Subscriber<Integer> sub;

    public DelegateSub(Flow.Subscriber sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        sub.onSubscribe(subscription);
    }

    @Override
    public void onNext(Integer item) {
        sub.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
