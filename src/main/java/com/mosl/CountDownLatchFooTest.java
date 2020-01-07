package com.mosl;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchFooTest {

    private CountDownLatch b = new CountDownLatch(1);
    private CountDownLatch c = new CountDownLatch(1);

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        b.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        b.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        c.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        c.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
