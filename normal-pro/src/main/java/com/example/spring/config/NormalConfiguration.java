package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class NormalConfiguration {

    @Bean
    public Date currentDate() {
        return new Date();
    }

}
