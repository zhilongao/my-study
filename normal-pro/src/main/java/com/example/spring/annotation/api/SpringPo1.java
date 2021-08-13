package com.example.spring.annotation.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
@EnableAutoConfiguration
public class SpringPo1 {

    static {
        System.err.println("SpringPo1 is load");
    }

}
