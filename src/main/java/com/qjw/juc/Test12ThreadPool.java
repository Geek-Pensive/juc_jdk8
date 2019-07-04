package com.qjw.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : qjw
 * @data : 2019/6/13
 */
public class Test12ThreadPool {

    public static void main(String[] args) {
        TestTask testTask = new TestTask();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            pool.submit(testTask);
        }
        pool.shutdown();
    }

}

class TestTask implements Runnable {

    private  AtomicInteger num =  new AtomicInteger();

    @Override
    public void run() {
        while (num.get() < 50) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---" + num.getAndIncrement());
        }
    }
}
