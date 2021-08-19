package com.example.spring.annotation.use;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        // test1();

        // test2();

        // test3();

        test4();
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

    private static void test4() {
        // OnClassCondition
        // ClassMetadata
        // MethodMetadata

        ConditionMessage message = ConditionMessage.empty();
        List<String> list = new ArrayList<String>();
        message = message.andCondition(Service.class)
                .found("required class", "required classes")
                .items(ConditionMessage.Style.QUOTE, list);

        message = message.andCondition(Component.class)
                .found("not required classed", "111")
                .items();

        ConditionOutcome match = ConditionOutcome.match(message);
        System.err.println(message);
        System.err.println(match);

        // AutoConfigurationImportFilter
        // FilteringSpringBootCondition
        // @SpringBootApplication

        // 如何配置一个spring web mvc的自动装配
        //
    }
}
