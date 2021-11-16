package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class SchedulerEx {
    public static void main(String[] args) {

        Publisher<Integer> pub = s -> {
            s.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    s.onNext(1);
                    s.onNext(2);
                    s.onNext(3);
                    s.onNext(4);
                    s.onNext(5);
                    s.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        pub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("onNext : {}  ", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.info("onError : {}  ", t);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });
    }
}
