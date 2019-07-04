package com.qjw.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 调度线程池
 * @author : qjw
 * @data : 2019/6/13
 */
public class TestScheduledThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> scheduledFuture = scheduledThreadPool.schedule(() -> {
                int num = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + "---" + num);
                return num;
            }, 1, TimeUnit.SECONDS);
            System.out.println(scheduledFuture.get());
        }

        scheduledThreadPool.shutdown();

    }
}
