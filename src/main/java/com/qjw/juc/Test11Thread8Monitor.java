package com.qjw.juc;

/**
 * 8种锁的方式：
 * 1.one、two都加synchronized，且睡眠：                        1000ms打印One，500ms打印Two，!!!因为synchronized锁了this!!!
 * 2.在1的基础上，添加sayThree方法，不睡眠，不加锁：            先打印three和1的情况并行
 * 3.加person2，person调sayOne，改为person2调sayTwo：          先500ms打印two后500ms打印one，
 * 4.修改sayOne为静态方法，还是用统一person调用sayOne、sayTwo： 先500ms打印two后500ms打印one，!!!因为synchronized锁了类!!!
 * 5.再修改sayTwo为静态方法，还是用统一person调用sayOne、sayTwo：1000ms打印One，500ms打印Two
 * 6.sayOne静态，sayTwo不静态，两个对象分别调sayOne、sayTwo：   先500ms打印two后500ms打印one：!!!两个对象的锁不冲突!!!
 * 7.将sayTwo改为静态的，两个对象分别调sayOne、sayTwo：         1000ms打印One，500ms打印Two  !!!两个对象都会锁类!!!
 */
public class Test11Thread8Monitor {

    public static void main(String[] args) {

        Person person = new Person();
        Person person2 = new Person();

        new Thread(() -> {
            try {
                person.sayOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
//                person.sayTwo();
                person2.sayTwo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

        /*new Thread(()-> {
            try {
                person.sayThree();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();*/

    }


}

class Person {
    private String name;

    public static synchronized void sayOne() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("one");
    }

    public static synchronized void sayTwo() throws InterruptedException {
        Thread.sleep(500);
        System.out.println("two");
    }

    public void sayThree() throws InterruptedException {

        System.out.println("three");
    }
}