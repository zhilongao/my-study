package com.study.search.aop.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AopSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopSearchApplication.class, args);
    }

}
