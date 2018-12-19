package com.web.java.executor;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xieyuhui on 2018/12/19.
 * ExecutorService示例
 */
public class SimpleThreadPool {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executorService.execute(worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
