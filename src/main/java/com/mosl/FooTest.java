package com.mosl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooTest {

    private Lock lock = new ReentrantLock();

    private Condition a = lock.newCondition();

    private Condition b = lock.newCondition();

    private Condition c = lock.newCondition();

    private volatile int flag = 0;

    public FooTest() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        lock.lock();//获取到锁
        if (flag != 0) {
            a.await();
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        flag = 1;
        b.signal();
        lock.unlock();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        lock.lock();//获取到锁
        if (flag != 1) {
            b.await();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        flag = 2;
        c.signal(); //唤醒C
        lock.unlock();
    }

    public void third(Runnable printThird) throws InterruptedException {
        lock.lock();
        if (flag != 2) {
            c.await();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        flag = 0;
        a.signal(); //唤醒A
        lock.unlock();
    }
}
