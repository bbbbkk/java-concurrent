package com.mosl.threadpool;

public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutDown();

    void addWorkers(int num);

    void removeWorkers(int num);

    int getJobSize();
}
