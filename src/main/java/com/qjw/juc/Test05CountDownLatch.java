package com.qjw.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author : qjw
 * @data : 2019/6/5
 */
public class Test05CountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        HouseWork houseWork = new HouseWork(latch);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(houseWork).start();
        }

        latch.await();
        long end = System.currentTimeMillis();

        System.out.println("耗时：" + (end - start));
    }

}

class HouseWork implements Runnable {

    private CountDownLatch latch;

    public HouseWork(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 10000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + "---" + String.valueOf(i));
                    }
                }
            } finally {
                latch.countDown();
            }
        }
    }
}
