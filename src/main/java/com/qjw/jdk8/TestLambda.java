package com.qjw.jdk8;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java的四大函数式接口
 * 1.Consumer<T> :消费型接口
 * void accept(T t)
 * 2.Supplier<T>：供给型接口
 * T get()
 * 3.Function<T,R>：函数型接口
 * R apply(T t)
 * 4 Predicate<T>：断言型接口
 * boolean test(T t)
 *
 * @author : qjw
 * @data : 2019/6/26
 */
public class TestLambda<S> {

    // 1.
    @Test
    public void testConsumer() {
        consumer(100, (m) -> System.out.println(m));
    }

    private void consumer(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    // 2.
    @Test
    public void testSupplier() {
        Supplier<String> supplier = () -> "HelloWorld";
        String s = get(supplier);
        System.out.println(s);
    }

    private <T> T get(Supplier<T> supplier) {
        return supplier.get();
    }

    // 3.
    @Test
    public void testFunction() {
        Function<Object, Integer> f = (o)->Integer.parseInt(String.valueOf(o));
        Integer apply = apply(f, "123");
        System.out.println(apply);
    }

    private <B> B apply(Function<Object, B> function, Object origin) {
        return function.apply(origin);
    }

    // 4.
    @Test
    public void testPredicate(){
        System.out.println(sexPre((i) -> i.contains("man"), "freshmen"));
    }

    private Boolean sexPre(Predicate<String> predicate,String sex){
        return predicate.test(sex);
    }

}
