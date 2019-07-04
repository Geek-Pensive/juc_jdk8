package com.qjw.juc;

/**
 * @author : qjw
 * @data : 2019/6/11
 */
public class Test09ABCAlternate {

    public static void main(String[] args) {

        PrintController printController = new PrintController();
        PrintATask printATask = new PrintATask(printController, 100);
        PrintBTask printBTask = new PrintBTask(printController, 100);
        PrintCTask printCTask = new PrintCTask(printController, 100);

        new Thread(printATask,"TA").start();
        new Thread(printBTask,"TB").start();
        new Thread(printCTask,"TC").start();
    }

}

class PrintCTask implements Runnable {
    private PrintController printController;
    private int printCnt;

    public PrintCTask(PrintController printController, int printCnt) {
        this.printController = printController;
        this.printCnt = printCnt;
    }

    @Override
    public void run() {
        for (int i = 0; i < printCnt; i++) {
            printController.printC();
        }
    }
}

class PrintBTask implements Runnable {
    private PrintController printController;
    private int printCnt;

    public PrintBTask(PrintController printController, int printCnt) {
        this.printController = printController;
        this.printCnt = printCnt;
    }

    @Override
    public void run() {
        for (int i = 0; i < printCnt; i++) {
            printController.printB();
        }
    }
}

class PrintATask implements Runnable {
    private PrintController printController;
    private int printCnt;

    public PrintATask(PrintController printController, int printCnt) {
        this.printController = printController;
        this.printCnt = printCnt;
    }

    @Override
    public void run() {
        for (int i = 0; i < printCnt; i++) {
            printController.printA();
        }
    }
}

class PrintController {
    private volatile int  singal = 0;

    public synchronized void printA() {
        if (singal != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + "--A--");
        singal = 1;
        notifyAll();
    }

    public synchronized void printB() {
        if (singal != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName() + "--B--");
        }
        singal = 2;
        notifyAll();
    }

    public synchronized void printC() {
        while (singal != 2) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + "--C--");

        }
        singal = 0;
        notifyAll();

    }
}
