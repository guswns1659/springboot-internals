package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;

@Slf4j
public class Operator {
    public static void main(String[] args) {
        // Operator multiplies 10 on data published by upstream.
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        final Flow.Publisher<Integer> mainPub = new Flow.Publisher<>() {

            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        for (int i : list) {
                            subscriber.onNext(i);
                        }
                        subscriber.onComplete();
                    }

                    @Override
                    public void cancel() {

                    }
                });

            }
        };

        final Flow.Publisher<Integer> operator = new Flow.Publisher<>() {

            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                mainPub.subscribe(new Flow.Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {
                        subscriber.onSubscribe(subscription);
                    }

                    @Override
                    public void onNext(Integer item) {
                        subscriber.onNext(item * 10);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }
                });

            }
        };

        final Flow.Subscriber<Integer> sub = new Flow.Subscriber<>() {

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                log.info("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer item) {
                log.info("onNext : {}", item);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };

        operator.subscribe(sub);
    }
}
