package com.mosl;

public class SynchronizedFooTest {

    private int flag = 0;

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (this) {
            //如果flag == 0
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            flag = 1;
            this.notify();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (this) {
            if (flag != 1) {
                this.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            flag = 2;
            this.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (this) {
            if (flag != 2) {
                this.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            flag = 0;
            this.notifyAll();
        }
    }


    public static void main(String[] args) {
        SynchronizedFooTest fooTest = new SynchronizedFooTest();
        new Thread(() -> {
            try {
                fooTest.first(() -> { System.out.print("one");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fooTest.second(() -> { System.out.print("two");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fooTest.third(() -> { System.out.print("three");});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
