package com.example.spring.annotation.use;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    /******************************** 普通的bean装配 ***********************/
    @Bean(name = "jack")
    public Person p1() {
        return new Person("jack", 50);
    }

    @Bean(name = "tom")
    public Person p2() {
        return new Person("tom",55);
    }

    /********************************** 自定义不同的@Conditional ********************/
    @Conditional(LinuxCondition.class)
    @Bean(name = "person-linux")
    public Person p3() {
        return new Person("person-linux", 90);
    }

    @Conditional(WindowsCondition.class)
    @Bean(name = "person-windows")
    public Person p4() {
        return new Person("person-window", 91);
    }

    /***************************** 添加条件判断的@Conditional ********************/
    @ConditionalOnSystemProperty(name = "language", value = "Chinese")
    @Bean(name = "condition-person-chinese")
    public Person p5() {
        return new Person("condition-person-chinese", 88);
    }

    @ConditionalOnSystemProperty(name = "language", value = "English")
    @Bean(name = "condition-person-english")
    public Person p6() {
        return new Person("condition-person-english", 89);
    }
}
