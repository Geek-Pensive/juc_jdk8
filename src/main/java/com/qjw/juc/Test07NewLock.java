package com.qjw.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程访问公共数据，
 * volatile + AtomicInteger 不可以。为什么? Test1Volatile?
 * 1. synchronized 方法
 * 2. synchronized 代码块
 * 3. Lock ReentrantLock (finally释放)
 *
 * @author : qjw
 * @data : 2019/6/6
 */
public class Test07NewLock {

    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow();

        new Thread(ticketWindow).start();
        new Thread(ticketWindow).start();
        new Thread(ticketWindow).start();

    }


}


class TicketWindow implements Runnable {

    //    private AtomicInteger ticket = new AtomicInteger(100);
    private int ticket = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        try {

            while (ticket > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第" + Thread.currentThread().getName() + "窗口出售一张票，余票为：" + --ticket);
            }
        }finally {
            lock.unlock();
        }
    }
}