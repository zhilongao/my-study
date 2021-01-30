package com.study.basic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/18 19:58
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfiguration.class);
        context.getBean(SimpleCls.class);
    }

    @Configuration
    public static class SimpleConfiguration {

        @Bean
        public SimpleCls simpleCls() {
            return new SimpleCls();
        }
    }


    public static class SimpleCls{

        public SimpleCls() {
            System.err.println("SimpleCls Constructor");
        }

        private String name;

        private int age;

        @Override
        public String toString() {
            return "SimpleCls{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
