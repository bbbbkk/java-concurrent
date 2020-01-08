package com.mosl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockFooBarTest {

    public static void main(String[] args) {

    }
}

class FooBar {

    private int n;

    private Lock lock = new ReentrantLock();

    private Condition a = lock.newCondition();

    private int flag = 0;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            //获取锁
            lock.lock();
            //获取到了锁
            if (flag != 0) {
                a.await(); //不等于0
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            flag = 1;
            a.signalAll();
            lock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            //获取锁
            lock.lock();
            //获取到了锁
            if (flag != 1) {
                a.await(); //不等于0
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printBar.run();
            flag = 0;
            a.signalAll();
            lock.unlock();
        }
    }
}
