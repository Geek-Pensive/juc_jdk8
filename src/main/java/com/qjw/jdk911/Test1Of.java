package com.qjw.jdk911;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * of:创建对象,一般都是不可变的
 * @author : qjw
 * @data : 2019/6/24
 */
public class Test1Of {

    public static void main(String[] args) {
        // 创建不变集合，
//        List<String> list = List.of("aaa","bb","eee");
        // 不能修改 : UnsupportedOperationException
//        list.add("deet");

    }

    @Test
    public void testList(){
        String [] arr = {"asd","fsd","asdasfwqe"};
        // 这个list不是java.util的list，是内部类
        List<String> list = Arrays.asList(arr);
        System.out.println(list);
        // 不能修改 : UnsupportedOperationException
        list.add("add");
        System.out.println(list);
    }

    @Test
    public void testSet(){
        LocalDate of = LocalDate.of(2019, 2, 23);
        System.out.println(of);  // 2019-02-23

        // 不能修改 : UnsupportedOperationException
//        Set set = Set.of(12,23,4,1);
        Stream<String> aaa = Stream.of("aaa", "bbb", "ddd");
    }

}
