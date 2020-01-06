package com.mosl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierABTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    System.out.print("A");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.print("B");
                }
            }
        });

        aThread.start();
        bThread.start();

    }
}
