package com.mosl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2OTest {

    static ExecutorService executor = new ThreadPoolExecutor(50, 50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

    static H2O h2O = new H2O();

    public static void main(String[] args) throws InterruptedException {
        String test = "OOHHHH";
        char[] array = test.toCharArray();
        CountDownLatch countDownLatch = new CountDownLatch(array.length);
        for (char a: array) {
            if (a == 'O') {
                submitOx(countDownLatch); //O
            } else {
                submitHy(countDownLatch); //H
            }
        }
        countDownLatch.await();
        executor.shutdown();
    }

    private static void submitOx(CountDownLatch countDownLatch) {
        executor.execute(() -> {
            try {
                h2O.oxygen(() -> {
                    System.out.print("O");
                });
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void submitHy(CountDownLatch countDownLatch) {
        executor.execute(() -> {
            try {
                h2O.hydrogen(() -> {
                    System.out.print("H");
                });
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

/**
 *
 * 分析：
 * 如果先产生O：等待产生2个H
 * 如果先产生H: 产生O和H
 *
 * B拿到锁
 * O
 * 1
 * B拿到锁
 * bCount == 1
 * A拿到锁
 * H
 * 1
 * A拿到锁
 * H
 * 2
 * A拿到锁
 * aCount == 2
 * A拿到锁
 * H
 * 1
 * O
 * 1
 *
 */
class H2O {

    private int aCount = 0; //计数

    private int bCount = 0;

    private Lock lock = new ReentrantLock();

    private Condition a = lock.newCondition();

    private Condition b = lock.newCondition();

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        lock.lock();
        if (aCount == 2) {
            //唤醒B
            b.signalAll();
            a.await();//等待 17被阻塞在这里 aCount = 0;
            aCount = 0;
        }
        releaseHydrogen.run(); //产生H
        aCount++;
        b.signalAll(); //唤醒B
        lock.unlock();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        lock.lock();
        if (bCount == 1) {
            a.signalAll();
            b.await();//14被阻塞在这里
            bCount = 0;
        }
        releaseOxygen.run(); //产生O
        bCount++;
        a.signalAll(); //唤醒A
        lock.unlock();
    }
}