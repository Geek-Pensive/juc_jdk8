package com.qjw.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask也可以用做闭锁，get方法会阻塞
 * @author : qjw
 * @data : 2019/6/5
 */
public class Test06Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*Callable<Integer> work= () -> {
            int sum = 0;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sum += i;
            }
            return sum;
        };*/

        Work work = new Work();
        FutureTask<Integer> workFutureTask = new FutureTask<>(work);
        new Thread(workFutureTask).start();

        System.out.println(workFutureTask.get());
        System.out.println("-----------------");

    }


}

class Work implements Callable<Integer>{

    @Override
    public Integer call()  {
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }
}