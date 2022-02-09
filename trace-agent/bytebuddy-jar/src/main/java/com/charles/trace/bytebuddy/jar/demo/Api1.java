package com.charles.trace.bytebuddy.jar.demo;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.function.Function;

public class Api1 {

    public static void main(String[] args) {
        test1();
        // test2();
    }

    public static void test1() {
        Class<?> cls = new ByteBuddy()
                // 指定父类
                .subclass(Object.class)
                // 根据名称匹配需要拦截的方法
                // .method(ElementMatchers.named("toString"))
                .method(ElementMatchers.isToString())

                // 拦截方法调用,返回固定值
                .intercept(FixedValue.value("hello,world"))
                // 产生字节码
                .make()
                // 加载类
                .load(Api1.class.getClassLoader())
                // 获取class对象
                .getLoaded();
        try {
            String s = cls.newInstance().toString();
            System.err.println(s);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        Class<? extends Function> cls = new ByteBuddy()
                // 指定父类Function
                .subclass(Function.class)
                // 根据名称匹配需要拦截的方法
                .method(ElementMatchers.named("apply"))
                // 拦截function.apply方法调用,委托给GreetingInterceptor处理
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                // 产生字节码
                .make()
                // 加载类
                .load(Api1.class.getClassLoader())
                // 获取class对象
                .getLoaded();

        try {
            Object apply = cls.newInstance().apply("hello,jack");
            System.err.println(apply);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 定义的拦截器类
    public static class GreetingInterceptor {
        // 方法签名随意,但是只能有一个方法
        /*
        public Object greet(Object argument) {
            return "Hello from " + argument;
        }
         */

        // 可以指定注解
        public Object greet(@AllArguments Object[] allArguments, @Origin Method method) {
            String clsName = method.getDeclaringClass().getName();
            String methodName = method.getName();
            System.err.println("clsName:" + clsName);
            System.err.println("methodName:" + methodName);
            int length = allArguments.length;
            if (length > 0) {
                for (Object allArgument : allArguments) {
                    System.err.println(allArgument);
                }
            }
            return "hi";
        }

    }


}
