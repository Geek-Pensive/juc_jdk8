package com.qjw.jdk8;

/**
 * 接口的默认方法：类优先
 *
 * @author : qjw
 * @data : 2019/7/1
 */
public class TestInterface {

    public static void main(String[] args) {
        TestClass1 testClass1 = new TestClass1();
        TestClass2 testClass2 = new TestClass2();
        TestClass3 testClass3 = new TestClass3();
        testClass1.testSayName(); /*类优先*/
        testClass2.testSayName();
        testClass3.testSayName();


        ITest1.show();
    }

}

class TestClass1 extends Par implements ITest1 {
}

class TestClass2 implements ITest1 {
}

//必须重写
class TestClass3 implements ITest1, ITest2 {
    @Override
    public void testSayName() {
        ITest2.super.testSayName();
    }
}

interface ITest1 {
    default void testSayName() {
        System.out.println("qjw1");
    }


    static void show() {
        System.out.println("static method");
    }
}

interface ITest2 {
    default void testSayName() {
        System.out.println("qjw2");
    }
}

class Par {
    public void testSayName() {
        System.out.println("qjw_method");
    }
}