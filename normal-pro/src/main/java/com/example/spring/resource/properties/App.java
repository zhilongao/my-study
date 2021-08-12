package com.example.spring.resource.properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/29 17:34
 * @since v1.0.0001
 */
public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcPropertiesConfiguration.class);
        String message1 = context.getBean(JdbcProperties.class).toString();
        String message2 = context.getBean(JdbcXmlProperty.class).toString();
        String message3 = context.getBean(JdbcYmlProperty.class).toString();
        System.err.println(message1);
        System.err.println(message2);
        System.err.println(message3);
    }
}
