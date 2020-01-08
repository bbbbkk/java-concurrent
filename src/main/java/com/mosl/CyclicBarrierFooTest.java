package com.mosl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierFooTest {

    private CyclicBarrier b = new CyclicBarrier(2);
    private CyclicBarrier c = new CyclicBarrier(2);

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        try {
            b.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        try {
            b.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        try {
            c.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        try {
            c.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
