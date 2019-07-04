package com.qjw.jdk8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author : qjw
 * @data : 2019/6/27
 */
public class TestStream {

    /**
     * stream的创建：4种方式
     */
    @Test
    public void testCreateStream() {
        List<String> list = Arrays.asList("aaa", "bbbb", "ccc");
        Stream<String> stream1 = list.stream();

        Stream<String> stream2 = Arrays.stream(new String[]{"aaa", "bbbb", "ccccc"});

        Stream<String> stream3 = Stream.of("aaa", "bbb", "ccc");

        UnaryOperator<Integer> op = new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 1;
            }
        };
        Stream<Integer> stream4 = Stream.iterate(2, op);
    }

    /**
     * 中间操作:必须得有终止操作（）
     * 1.filter
     * 2.limit
     * 3.skip
     * 4.distinct
     * 5.map
     * 6.flatMap 把多个流汇聚到一个流中：要求函数返回一个流
     * 7.sorted
     */
    @Test
    public void testIntermediateOperation() {
        Stream<String> stream3 = Stream.of("1", "00", "aaa", "bbbb", "ccc", "aaa", "ddd");
        stream3.filter(item -> item.length() >= 3)
                .skip(1)
                .distinct()
                .limit(2)
                .forEach(System.out::println);

        Stream<String> stream4 = Stream.of("1", "00", "aaa", "bbbb", "ccc", "aaa", "ddd");
        stream4.map(item -> item.concat("---")).forEach(System.out::println);

        Stream<String> stream5 = Stream.of("1", "00", "aaa", "bbbb", "ccc", "aaa", "ddd");
        Function<String, Stream<Character>> mapper = s -> {
            List<Character> list = new ArrayList<>();
            char[] chars = s.toCharArray();
            // 基本数据类型的数组不能使用Arrays.asList()
//                List<char[]> chars1 = Arrays.asList(chars);
            for (Character aChar : chars) {
                list.add(aChar);
            }
            return list.stream();
        };
        stream5.flatMap(mapper).forEach(System.out::println);
    }

    /**
     * 终止操作：
     * 1.allMatch   -- boolean
     * 2.anyMatch   -- boolean
     * 3.noneMatch  -- boolean
     * 4.findFirst  -- Optional
     * 5.findAny    -- Optional
     * 6.count      -- long
     * 7.min        -- Object
     * 8.max        -- Object
     */
    @Test
    public void testFinalOperation1() {
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 6, 7);
        Stream<String> stream2 = Stream.of("a", "b");
//        System.out.println(stream1.anyMatch((i) -> i > 3));
//        System.out.println(stream1.findFirst().get());
//        System.out.println(stream1.findAny().get());
        System.out.println(stream1.count());

        System.out.println(stream2.min(String::compareTo).get());
    }

    /**
     * 9.reduce: 可将流中的元素反复结合起来，得到一个值]
     * 10.collect: 收集:总数、总和、平均值、分组（返回map）、分区，joiner。。。。
     */
    @Test
    public void testFinalOperation2() {
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 6, 7);
        // 第一轮：1是a，stream1中的第1个元素：1是b，加起来，得到2
        // 第二轮：2是a，stream1中的第2个元素：2是b，加起来
        System.out.println(stream1.reduce(1, (a, b) -> a + b));

        Stream<String> stream2 = Stream.of("aaa", "bbb");
        HashSet<String> collect = stream2.collect(Collectors.toCollection(HashSet::new));
        System.out.println(collect);

        Stream<Integer> stream3 = Stream.of(1, 2, 3, 5, 6, 7, 8);
        Double collect1 = stream3.collect(Collectors.averagingInt(i -> i));
        System.out.println(collect1);
    }


    /**
     * 并行流
     */
    @Test
    public void testParStream(){
        Instant now = Instant.now();
//        System.out.println(now);// 2019-07-01T06:14:32.963Z
        LongStream longStream = LongStream.rangeClosed(0, 100000000L);
        long res = longStream.parallel().reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println(Duration.between(now,end).toMillis());



    }


}
