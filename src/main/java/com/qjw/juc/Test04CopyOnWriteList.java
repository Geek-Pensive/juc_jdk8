package com.qjw.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : qjw
 * @data : 2019/6/5
 */
public class Test04CopyOnWriteList {

    public static void main(String[] args) throws InterruptedException {

        //test1
        /*ArrayList<String> aaa = new ArrayList<String>() {{
            this.add("aaa");
            this.add("bbb");
        }};

        for (String s : aaa) {
            System.out.println(s);
            aaa.add("xxx");
        }*/

        // test2
        AddTask addTask = new AddTask();
        for (int i = 0; i < 10; i++) {
            new Thread(addTask).start();
        }
        Thread.sleep(1000);
        System.out.println(addTask.list.size());

    }

}

class AddTask implements Runnable {

    /*private List<String> list = Collections.synchronizedList(new ArrayList<String>() {{
        this.add("aaa");
        this.add("bbb");
        this.add("ddd");
    }});*/
    public List<String> list = new CopyOnWriteArrayList<String>() {{
        this.add("aaa");
    }};

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("xxx");
        }
    }
}