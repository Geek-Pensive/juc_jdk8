package com.qjw.jdk8;

import org.junit.Test;

import java.util.Optional;

/**
 * Optional 容器类：避免NPE
 * @author : qjw
 * @data : 2019/7/1
 */
public class TestOptional {


    /**
     * 3种创建方式
     * 1.Optional.of(a):a不能为null
     * 2.Optional.ofNullable(b):b可以为null:为空时：Optional.empty();
     * 3.Optional.empty():get():NoSuchElementException: No value present
     */
    @Test
    public void test1(){
        String a = "aa";
        Optional<String> a1 = Optional.of(a);
        String b = null;
        Optional<Object> o = Optional.ofNullable(b);
        Optional<Object> empty = Optional.empty();
        System.out.println(o);
        System.out.println(empty.get());
    }

    /**
     * 2.获取
     * isPresent():是否有值
     * 1.get():获取容器中的值
     * 2.orElse(T other):获取容器中的值，如果为null，则返回other
     * 3.orElseGet(Supplier<? extends T> other):获取容器中的值，如果为null,则生产一个返回
     * 4.orElseThrow(Supplier<? extends X> exceptionSupplier):获取容器中的值，如果为null,则生产异常
     */
    @Test
    public void test2(){
        String a  = null;
        Optional<String> a1 = Optional.ofNullable(a);
        System.out.println(a1.isPresent());
//        System.out.println(a1.get());
        System.out.println(a1.orElse("bbb"));
        System.out.println(a1.orElseGet(() -> "ccc"));
        a1.orElseThrow(()->new RuntimeException("no value"));
    }

    /**
     * map(Function f)
     * flatMap(Function f) f需要返回一个Optional，进一步防止空指针异常
     */
    @Test
    public void test3(){
        String a = null;
        Optional<String> a1 = Optional.ofNullable(a);
        Optional<String> s = a1.map((item) -> item + "---");
        System.out.println(s);

        System.out.println(a1.flatMap(item -> Optional.ofNullable(item.trim())).orElse("bbb"));
    }


}
