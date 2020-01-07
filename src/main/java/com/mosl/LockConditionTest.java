package com.mosl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {

    private static volatile int flag = 1;

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition a = lock.newCondition(); //线程A的条件变量
        Condition b = lock.newCondition(); //线程B的条件变量

        //线程A
        new Thread(() -> {
            while(true) {
                lock.lock(); //获取锁
//                System.out.println("A 获取到锁");
                try {
                    if (flag != 0) {
                        a.await();//等待a条件释放,这里会阻塞
                    }
                    Thread.sleep(1000); //睡眠1秒
                    System.out.print("A"); //输出A
                    flag = 1; //标记弄成1
                    b.signal();//唤醒B
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        //线程B
        new Thread(() -> {
            while(true) {
                lock.lock(); //获取锁
//                System.out.println("B 获取到锁");
                try {
                    if (flag != 1) {
                        b.await();//等待
                    }
                    Thread.sleep(1000); //睡眠1秒
                    System.out.print("B"); //输出B
                    flag = 0;
                    a.signal();//唤醒A
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}
