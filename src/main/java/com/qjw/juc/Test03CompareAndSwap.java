package com.qjw.juc;

/**
 * @author : qjw
 * @data : 2019/6/5
 */
public class Test03CompareAndSwap {

    public static void main(String[] args) {
        System.out.println((int) Math.random() * 101);
        System.out.println((int) (Math.random() * 101));

        CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                int value = cas.getValue();
                boolean b = cas.compareAndSet(value, (int) (Math.random() * 101));
                System.out.println(b);
            }).start();
        }
    }
}

class CompareAndSwap {
    // 内存值
    private int value;

    /**
     * 获取内存值
     *
     * @return
     */
    public synchronized int getValue() {
        return value;
    }

    /**
     * 比较和交换
     *
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        if (oldValue == expectedValue) {
            this.value = newValue;
        }

        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }

}