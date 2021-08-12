package com.example.spring;

import com.example.spring.config.NormalConfiguration;
import com.example.spring.enable.ColorAutoConfiguration;
import com.example.spring.spi.SpiDemoInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        // test1();
        // test2();
        test3();
    }

    /**
     * 测试普通的@Configuration注解和@bean注解
     */
    private static void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(NormalConfiguration.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.err::println);
    }

    /**
     * 测试@Configuration注解配合@Enable注解
     */
    private static void test2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ColorAutoConfiguration.class);
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String beanName : definitionNames) {
            System.err.println(beanName);
        }
    }

    /**
     * 测试spi机制
     * 需要在resources/META-INF/services目录下创建指定名称文件,并在文件内部提供接口实现类
     */
    private static void test3() {
        ServiceLoader<SpiDemoInterface> loaders = ServiceLoader.load(SpiDemoInterface.class);
        loaders.forEach(SpiDemoInterface::test);
    }
}
