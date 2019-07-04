package com.qjw.jdk911;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author : qjw
 * @data : 2019/6/24
 */
public class Test4Optional {

    @Test
    public void test1() throws Throwable {
        // of(null)：NEP
//        Optional<Object> o = Optional.of(null);

        // 通过:会创建Optional.empty
        Optional<String> o = Optional.ofNullable(null);
        System.out.println(o);
//        System.out.println(o.get()); // get方法会出问题
//        System.out.println(o.or());

        // 如果optional中的为null，则返回参数
        System.out.println(o.orElse("aaa"));

        // 如果optional中的为null，则返回生产的对象
        Supplier<? extends String> a = ()->"bbb";
        System.out.println(o.orElseGet(a));
        Supplier<? extends Throwable> b = ()->new RuntimeException("bbb");
        System.out.println(o.orElseThrow(b));

        // jdk11，o.orElseThrow(),不用传参数
    }


}
