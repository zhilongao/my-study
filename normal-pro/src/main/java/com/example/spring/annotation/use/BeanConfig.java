package com.example.spring.annotation.use;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(name = "jack")
    public Person p1() {
        return new Person("jack", 50);
    }

    @Bean(name = "tom")
    public Person p2() {
        return new Person("tom",55);
    }

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

    @ConditionalSystem(type = ConditionalSystem.Type.WINDOWS)
    @Bean(name = "condition-person-windows")
    public Person p5() {
        return new Person("condition-person-windows", 88);
    }


    @ConditionalSystem(type = ConditionalSystem.Type.LINUX)
    @Bean(name = "condition-person-linux")
    public Person p6() {
        return new Person("condition-person-linux", 89);
    }
}
