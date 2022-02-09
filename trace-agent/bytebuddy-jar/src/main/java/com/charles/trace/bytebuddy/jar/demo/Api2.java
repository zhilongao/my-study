package com.charles.trace.bytebuddy.jar.demo;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Api2 {

    public static void main(String[] args) throws Exception {
        test1();
        // test2();
        // test3();
    }


    public static void test1() throws Exception {
        File file = new File("D:\\work\\data\\files");
        new ByteBuddy()
                // 重新定义类
                // .redefine(Foo.class)
                .rebase(Foo.class)
                // 类名
                .name(Foo.class.getName())
                .method(ElementMatchers.named("sayHelloFoo"))
                .intercept(MethodDelegation.to(Bar.class))
                // 创建
                .make()
                .saveIn(file);
    }

    // 将Foo.class的sayHelloFoo方法调用委托给了Bar.class的sayHelloBar方法
    public static void test2() throws IllegalAccessException, InstantiationException {
        String result = new ByteBuddy()
                .subclass(Foo.class)
                .method(
                        ElementMatchers.named("sayHelloFoo")
                                .and(ElementMatchers.isDeclaredBy(Foo.class))
                                .and(ElementMatchers.returns(String.class)))
                .intercept(MethodDelegation.to(Bar.class))
                .make()
                .load(Api2.class.getClassLoader())
                .getLoaded()
                .newInstance()
                .sayHelloFoo();
        System.err.println(result);
    }

    // 给Foo.class动态生成方法sayHelloFoo,调用时委托给Bar.class的sayHelloBar方法
    // 给Foo.class动态生成字段x
    public static void test3() throws Exception {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .name("MyClassName")
                .defineMethod("custom", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(Bar.class))
                .defineField("x", String.class, Modifier.PUBLIC)
                .make()
                .load(Api2.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
        Method method = type.getDeclaredMethod("custom", null);
        Object invoke = method.invoke(type.newInstance());
        System.err.println(invoke);
        Field x = type.getDeclaredField("x");
        System.err.println(x);
    }

    public static class Foo {
        public String sayHelloFoo() {
            return "Hello in Foo!";
        }

        public String bar() {
            return "bar";
        }
    }

    public static class Bar extends Foo {
        @BindingPriority(1)
        public static String sayHelloBarV1() {
            return "Hello in Bar V1!";
        }
        @BindingPriority(2)
        public static String sayHelloBarV2() {
            return "Hello in Bar V2!";
        }
    }
}
