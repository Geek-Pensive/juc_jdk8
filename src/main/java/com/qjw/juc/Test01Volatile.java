package com.qjw.juc;

import static java.lang.Thread.*;

/**
 * 1.isFalg加同步锁synchronized,锁task1对象
 * 2.falg用volatile修饰
 *
 * @author : qjw
 * @data : 2019/6/4
 */
public class Test01Volatile {

    public static void main(String[] args) {
        Task1 task1 = new Task1();
        new Thread(task1, "t1").start();
        while (true) {
            if (task1.isFalg()) {
                System.out.println(Thread.currentThread().getName()+"收到修改的变量，flag:"+task1.isFalg());
                break;
            }
        }
    }

}

class Task1 implements Runnable {

    private volatile boolean falg = false;

    public boolean isFalg() {
        return falg;
    }

    public void run() {
        try {
            sleep(2000);
            falg = true;
            System.out.println(Thread.currentThread().getName()+"修改flag，flag=" + falg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
