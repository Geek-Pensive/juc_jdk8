package com.qjw.jdk911;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * 流的处理：
 * 1.创建流
 * 2.中间操作
 * 3.终止操作
 *
 * @author : qjw
 * @data : 2019/6/24
 */
public class Test2Stream {

    @Test
    public void testJDK8() {
        Stream<String> stream1 = Stream.of("aaa", "bbb", "eee");
        // 终止操作
        stream1.forEach(System.out::println);
        System.out.println("-------------");

        // 流中没有数据
        Stream<Object> stream2 = Stream.of();
        stream2.forEach(System.out::println);
        System.out.println("-------------");

        // 传入null会被解析为一个数组对象，访问.length报NPE
//        Stream<Object> stream3 = Stream.of(null);
//        stream3.forEach(System.out::println);
    }

    @Test
    public void testJDK11() {
        // 为null是创建空的流
//        Stream<Object> stream3 = Stream.ofNullable(null);

        Stream<String> stream1 = Stream.of("aaa", "bbb", "eee");

        // 传入Predicate：从流中一直获取判定器为真的元素，一旦遇到为假的元素，则终止
//        Stream<String> stream2 = stream1.takeWhile();
//        stream1.dropWhile();

        // jdk8 无限迭代
        Stream<Integer> iterate = Stream.iterate(1, i -> i * 2 + 1);
        iterate.limit(10).forEach(System.out::println);

        // jdk11 有限流:判定器
//        Stream.iterate(1, predicate, t -> t * 2 + 1);
    }

}
