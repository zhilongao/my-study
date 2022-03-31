package com.study.search.dubbo.reference;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ReferenceApp {

    public static void main(String[] args) {
        SpringApplication.run(ReferenceApp.class, args);
    }

}
