package com.itboyst.facedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan({"com.itboyst.facedemo.dao.mapper","com.itboyst.facedemo.mapper"})
@EnableTransactionManagement
public class FaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceApplication.class, args);
    }
}

