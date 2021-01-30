package com.example.spring.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 13:42
 * @since v1.0.0001
 */
@Configuration
@ComponentScan("com.example.spring.resource.config")
public class BeanScopeConfiguration {

    @Bean
    public Person p1() {
        return new Person();
    }

    @Bean
    public Person p2() {
        return new Person();
    }
}
