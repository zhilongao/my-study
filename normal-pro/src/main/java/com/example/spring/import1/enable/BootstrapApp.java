package com.example.spring.import1.enable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 11:41
 * @since v1.0.0001
 */
public class BootstrapApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ColorAutoConfiguration.class);
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String beanName : definitionNames) {
            System.err.println(beanName);
        }
    }
}
