package com.qjw.jdk911;

import org.junit.Test;

/**
 * @author : qjw
 * @data : 2019/6/24
 */
public class Test3String {

    @Test
    public void testName(){
        String s = "\t \r   \n";
        System.out.println(s.isEmpty());
        // 判断字符串都是空白：true
//        System.out.println(s.isBlank());
        System.out.println("----------------");

        String s2 = "   \t a  \r\n　   ";
        // 1.trim去重unicode码<32（空格），不能去除中文空格
        String trim = s2.trim();
        System.out.println(trim);
        System.out.println(trim.length());

        // 可以去除所有的空白字符：中文空格
//        s2.strip()

    }

    @Test
    public void test2(){
        String a = "java";
        // 重复5次：javajavajavajavajava
//        a.repeat(5)

        // lines()产生以行为单位的流
//        a.lines().count();
    }


}
