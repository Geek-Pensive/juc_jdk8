package com.qjw.juc;

/**
 * @author : qjw
 * @data : 2019/6/6
 */
public class Test08ProductorAndConsumer {

    public static void main(String[] args) {
        Package aPackage = new Package(1);

        ProductTask productTask = new ProductTask(aPackage, 5, 100);
        ConsumeTask consumeTask = new ConsumeTask(aPackage, 5, 50);
        new Thread(productTask, "生成者A").start();
        new Thread(consumeTask, "消费者B").start();

        /**
         * 多生产者多消费者，生产和消费速率不一直的情况下，会出现虚假唤醒
         */
        new Thread(productTask, "生成者AA").start();
        new Thread(consumeTask, "消费者BB").start();
    }


}

// 背包
class Package {

    private volatile Integer maxSize;

    Package(Integer maxSize) {
        this.maxSize = maxSize;
    }

    // 共享数据
    private int productNum = 0;

    // 消费
    public synchronized void consume() throws InterruptedException {
        /**
         * 使用if ：虚假唤醒 ,
         *  当消费者比较快时，productNum = 0，两个线程都会wait，
         *      当生产了1个物品时，两个线程同时被唤醒。-- -- ，出现负数
         * 解决方案。使用while：无限轮训productNum（数量）
         */
        while (productNum <= 0) {
            System.out.println(Thread.currentThread().getName() + "：无法消费，背包里已经没有产品了！");
            this.wait();
        }
        /**
         * 注意：这里不能用else,因为wait被唤醒后会继续执行，else不会执行，notifyAll不会执行
         *      在消费者较快的情况下，最后2个物品用else就不会被消费
         */
        System.out.println(Thread.currentThread().getName() + "：消费1个产品，剩余产品个数:" + --productNum);
        this.notifyAll();


    }

    // 生成
    public synchronized void product() throws InterruptedException {
        /**
         * 虚假唤醒，同上
         */
        while (productNum >= maxSize) {
            System.out.println(Thread.currentThread().getName() + "：无法生成，背包已满！");
            this.wait();
        }
        /**
         * 注意：这里不能用else,因为wait被唤醒后会继续执行，else不会执行，notifyAll不会执行
         *      在生产者较快的情况下，最后2个物品用else就不会生成
         */
        System.out.println(Thread.currentThread().getName() + "：生产一个产品，剩余产品个数:" + ++productNum);
        this.notifyAll();

    }

}

// 生成任务
class ProductTask implements Runnable {
    // 生产后放入的背包
    private Package aPackage;
    // 生产的个数
    private int proNum;
    private int productivity;

    ProductTask(Package aPackage, int proNum, int productivity) {
        this.aPackage = aPackage;
        this.proNum = proNum;
        this.productivity = productivity;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < proNum; i++) {
                Thread.sleep(productivity);
                aPackage.product();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 消费任务
class ConsumeTask implements Runnable {
    // 生产后放入的背包
    private Package aPackage;
    // 消费的个数
    private int consumeNum;
    private int consumption;

    ConsumeTask(Package aPackage, int consumeNum, int consumption) {
        this.aPackage = aPackage;
        this.consumeNum = consumeNum;
        this.consumption = consumption;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < consumeNum; i++) {
                Thread.sleep(consumption);
                aPackage.consume();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


