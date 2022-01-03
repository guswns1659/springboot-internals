package com.titanic.webflux.tobylecture;

import java.util.Iterator;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class Ob {

    /**
     * 리액티브란? 발생한 이벤트에 대해서 대응하는 방식을 의미한다.
     * duality. : 기능이 동일한데 반대 방향으로 표현하는 것.
     * observer pattern.
     * Reactive Stream : 자바를 사용하는 회사들이 만든 리액티브 표준을 의미한다. java9 API에 들어감.
     * <p>
     * duality
     * Iterable <--> Observable
     * Pull <--> Push
     */
    public static void main(String[] args) {

        IntIterable iterable = new IntIterable();
        for(int num : iterable) {
            System.out.println(num);
        }

        System.out.println("------------------");

        IntObservable observable = new IntObservable();
        observable.addObserver((o, arg) -> System.out.println(Thread.currentThread().getName() + " " +arg));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(observable);

        System.out.println(Thread.currentThread().getName() + " EXIT");
        executorService.shutdown();
    }

    static class IntIterable implements Iterable<Integer> {

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                int i = 0;
                static final int MAX = 10;

                @Override
                public boolean hasNext() {
                    return i < MAX;
                }

                @Override
                public Integer next() {
                    return ++i;
                }
            };
        }
    }

    // Observable
    // Source(Observable) -> Event -> Observer
    static class IntObservable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i); // Using update method for notifying observers. observer has only a method, update.
            }
        }
    }
}
