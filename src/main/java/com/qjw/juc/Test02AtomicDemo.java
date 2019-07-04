package com.qjw.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * jdk5后java.util.concurrent.atomic提供了原子变量
 * CAS:Compare and swap 并发情况下，保证数据的原子性，
 *      CAS包含三个操作数：
 *          1.内存值 V
 *          2.预估值 A
 *          3.更新值 B
 *          当且仅当(where) V == A 时，V == B，否则不操作
 * @author : qjw
 * @data : 2019/6/4
 */
public class Test02AtomicDemo {

    public static void main(String[] args) {
        Task2 task2 = new Task2();
        for (int i = 0; i < 10; i++) {
            new Thread(task2).start();
        }
    }

}
class Task2 implements Runnable{

    private AtomicInteger id = new AtomicInteger();

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getId());
    }

    public int getId() {
        return id.getAndIncrement();
    }
}

