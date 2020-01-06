package com.mosl;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser1");
                c.countDown();
            }
        });

        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2");
                c.countDown();
            }
        });

        parser1.start();
        parser2.start();

        c.await();
    }
}
