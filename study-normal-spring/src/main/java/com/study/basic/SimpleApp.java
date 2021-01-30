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
public class SimpleApp {
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        String[] definitionNames = context.getBeanDefinitionNames();
        System.err.println("==========");
        for (String definitionName : definitionNames) {
            System.err.println(definitionName);
        }
        System.err.println("==========");
        context.getBean("searchService");
    }
}
