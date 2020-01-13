package com.mosl.threadpool;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    @Override
    public void execute(Job job) {

    }

    @Override
    public void shutDown() {

    }

    @Override
    public void addWorkers(int num) {

    }

    @Override
    public void removeWorkers(int num) {

    }

    @Override
    public int getJobSize() {
        return 0;
    }
}
