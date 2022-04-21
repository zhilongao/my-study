package com.charles.dubbo.test;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboTestApp {

    public static void main(String[] args) {
        SpringApplication.run(DubboTestApp.class, args);
    }
}
