package com.springboot.springbootinternals.webflux;

import java.util.Arrays;

import static java.util.concurrent.Flow.*;

public class PubSub {
    public static void main(String[] args) {
        // Publisher == Observable.
        // Subscriber == Observer.
        /**
         * 1. pub.subscribe(sub)
         * 2. sub.onSubscribe(Subscription)
         */
        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

        Publisher<Integer> p = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {

                    }

                    @Override
                    public void cancel() {

                    }
                });

            }
        };

        Subscriber<Integer> s = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscription");
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext " +item);
            }

            // try - catch don't need.
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        p.subscribe(s);

    }
}
