package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Operators
 *
 * pub -> data -> sub
 */
@Slf4j
public class Operators {
    public static void main(String[] args) {
        // publisher that publish 1 to 10 sequentially.
        Flow.Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
        pub.subscribe(logSub());
    }


    private static Flow.Subscriber<Integer> logSub() {
        return new Flow.Subscriber<Integer>() {
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
                log.info("onError:{}", throwable);
            }

            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        };
    }

    private static Flow.Publisher<Integer> iterPub(Iterable<Integer> iter) {
        return new Flow.Publisher<Integer>() {
            @Override
            public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
                subscriber.onSubscribe(new Flow.Subscription() {
                    @Override
                    public void request(long n) {
                        try {
                            iter.forEach(subscriber::onNext);
                            subscriber.onComplete();
                        } catch (Throwable t) {
                            subscriber.onError(t);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };
    }
}
