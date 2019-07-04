package com.qjw.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : qjw
 * @data : 2019/6/12
 */
public class Test09ABCAlternateO {

    public static void main(String[] args) {
        ABCAlternateController abcAlternateController = new ABCAlternateController();
        Runnable pinrtATask = () -> {
            for (int i = 0; i < 10; i++) {
                abcAlternateController.loopPrintA(i);
            }
        };
        Runnable pinrtBTask = () -> {
            for (int i = 0; i < 10; i++) {
                abcAlternateController.loopPrintB(i);
            }
        };
        Runnable pinrtCTask = () -> {
            for (int i = 0; i < 10; i++) {
                abcAlternateController.loopPrintC(i);
                System.out.println("-----------------------------------");
            }
        };

        new Thread(pinrtATask, "A").start();
        new Thread(pinrtBTask, "B").start();
        new Thread(pinrtCTask, "C").start();
    }


}

class ABCAlternateController {

    // 保存当前正在执行线程
    private int num = 1;

    // 锁
    private Lock lock = new ReentrantLock();
    /**
     * Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，以便通过将这些对象与任意 Lock 实现组合使用
     * 注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
     * Condition和传统的线程通信没什么区别，Condition的强大之处在于它可以为多个线程间建立不同的Condition
     */
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    // 循环打印A， 加锁
    public void loopPrintA(int totolLoop) {
        lock.lock();

        try {
            if (num != 1) {
                // condition1等待
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("线程名：" + Thread.currentThread().getName() + "\t" + "循环次数：" + (i + 1) + "\t" + "第几轮：" + (totolLoop + 1));
            }

            // 只唤醒condition2
            num = 2;
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 循环打印B， 加锁
    public void loopPrintB(int totolLoop) {
        lock.lock();

        try {
            if (num != 2) {
                // condition2等待
                condition2.await();
            }
            for (int i = 0; i < 1; i++) {
                System.out.println("线程名：" + Thread.currentThread().getName() + "\t" + "循环次数：" + (i + 1) + "\t" + "第几轮：" + (totolLoop + 1));
            }

            // 只唤醒condition3
            num = 3;
            condition3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 循环打印C， 加锁
    public void loopPrintC(int totolLoop) {
        lock.lock();

        try {
            if (num != 3) {
                // condition3等待
                condition3.await();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("线程名：" + Thread.currentThread().getName() + "\t" + "循环次数：" + (i + 1) + "\t" + "第几轮：" + (totolLoop + 1));
            }

            // 只唤醒condition1
            num = 1;
            condition1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}