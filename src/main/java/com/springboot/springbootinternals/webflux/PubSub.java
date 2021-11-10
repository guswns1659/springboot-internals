package com.springboot.springbootinternals.webflux;

import java.util.Arrays;
import java.util.Iterator;

import static java.util.concurrent.Flow.*;

public class PubSub {
    public static void main(String[] args) {
        // Publisher == Observable.
        // Subscriber == Observer.
        /**
         * 1. pub.subscribe(sub)
         * 2. sub.onSubscribe(Subscription)
         * 3. Subscription.request(n)
         * 4. sub.onNext()
         */
        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

        Publisher<Integer> p = new Publisher() {
            @Override
            public void subscribe(Subscriber subscriber) {
                Iterator<Integer> it = itr.iterator();
                subscriber.onSubscribe(new Subscription() {
                    public void request(long n) {
                        try {
                            while (n-- > 0) {
                                if (it.hasNext()) {
                                    subscriber.onNext(it.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        } catch (RuntimeException e) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });

            }
        };

        Subscriber<Integer> s = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscription");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext " + item);
                this.subscription.request(1);
            }

            // try - catch don't need.
            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        p.subscribe(s);
    }
}
