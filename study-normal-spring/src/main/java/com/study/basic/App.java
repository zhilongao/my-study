package com.study.basic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/30 10:22
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        // lazyTest1();
        lazyTest2();
    }

    /**
     * spring bean的懒加载测试
     */
    private static void lazyTest1() {
        System.err.println("------------------------start-------------------------");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        // 此时,searchService还未初始化
        String[] definitionNames = context.getBeanDefinitionNames();
        System.err.println("*****************************************");
        for (String definitionName : definitionNames) {
            System.err.println(definitionName);
        }
        System.err.println("*****************************************");
        context.getBean("searchService");
        System.err.println("------------------------end-------------------------");
    }

    /**
     * 测试getBeanNamesForType方法是否会创建懒加载bean
     */
    private static void lazyTest2() {
        System.err.println("------------------------start-------------------------");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        String[] beanNames = context.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            System.err.println(beanName);
        }
        System.err.println("------------------------end-------------------------");
    }
}
