package com.qjw.juc;

import java.lang.reflect.Field;

/**
 * @author : qjw
 * @data : 2019/6/11
 */
public class Test00Swap2Integer {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a = 2;
        Integer b = 4;
        swap(a, b);
        System.out.println("a=" + a + ",b=" + b);
    }


    public static void swap(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {
        // 这里必须用new Integer ???
        Integer c = new Integer(a);
        Field field = a.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(a, b);
        field.set(b, c);
    }

}
