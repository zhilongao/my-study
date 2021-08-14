package com.example.spring.annotation.use;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        // test1();

        // test2();

        test3();
    }

    // 普通装配
    private static void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        Map<String, Person> map = context.getBeansOfType(Person.class);
        System.err.println(map);
    }

    // 条件装配1
    private static void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        String osName = context.getEnvironment().getProperty("os.name");
        System.out.println("当前系统为：" + osName);
        Map<String, Person> map = context.getBeansOfType(Person.class);
        System.err.println(map);
    }

    // 条件装配2
    private static void test3() {
        System.setProperty("language", "Chinese");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(BeanConfig.class);
        context.refresh();
        Map<String, Person> map = context.getBeansOfType(Person.class);
        System.err.println(map);
    }
}
