package com.springboot.springbootinternals.webflux;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

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
        Iterable<Integer> iterable = () ->
                new Iterator<>() {
                    final static int MAX = 10;
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

        for (Integer i : iterable) {
            System.out.println(i);
        }

        for (Iterator<Integer> it = iterable.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        // Observable
        // Soruce(Observable) -> Event -> Observer
        Observer ob = (Observable o, Object arg) -> System.out.println(arg);
    }

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
