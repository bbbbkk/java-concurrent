package com.mosl;

public class JoinCountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        Thread parser1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser1");
            }
        });

        Thread parser2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("parser2");
            }
        });

        parser1.start();
        parser2.start();

        parser1.join();
        parser2.join();
    }
}
