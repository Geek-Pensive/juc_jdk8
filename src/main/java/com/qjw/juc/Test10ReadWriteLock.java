package com.qjw.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：读读共享、读写/写写互斥
 * @author : qjw
 * @data : 2019/6/12
 */
public class Test10ReadWriteLock {

    public static void main(String[] args) {
        Bean bean = new Bean();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    bean.setAge((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        for (int i = 0; i < 100; i++) {
            new Thread(()-> {
                try {
                    bean.getAge();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

}

class Bean {
    private int age;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void getAge() throws InterruptedException {
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + "---" + age);
        }finally {
            readLock.unlock();
        }

    }

    public void setAge(int age) throws InterruptedException {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            Thread.sleep(80);
            System.out.println(Thread.currentThread().getName()+"~~~写入");
            this.age = age;
        }finally {
            writeLock.unlock();
        }

    }
}
