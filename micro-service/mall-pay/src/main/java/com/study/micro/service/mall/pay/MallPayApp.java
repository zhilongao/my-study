package com.study.micro.service.mall.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MallPayApp {

    public static void main(String[] args) {
        SpringApplication.run(MallPayApp.class, args);
    }
}
