package com.springboot.springbootinternals.webflux;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Ob {

    /**
     * 리액티브란?
     * 발생한 이벤트에 대해서 대응하는 방식을 의미한다.
     * duality
     * observer pattern
     * Reactive Stream : 자바를 사용하는 회사들이 만든 리액티브 표준을 의미한다. java9 API에 들어감.
     */
    public static void main(String[] args) throws InterruptedException {
        // pull - iterator
        Iterable<Integer> iterable = () ->
                new Iterator<>() {
                    static final int MAX = 10;
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };

        for (int i : iterable) {
            log.info("Iterable : {}", i);
        }

        log.info("-------------------");
        // push - observable
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                log.info("Observable : {}", arg);
            }
        };

        final IntObservable observable = new IntObservable();
        observable.addObserver(observer);

        final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.execute(observable);

        log.info("main Exit");

        es.shutdown();
        es.awaitTermination(5, TimeUnit.SECONDS);

    }

    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i);
            }
        }
    }
}
