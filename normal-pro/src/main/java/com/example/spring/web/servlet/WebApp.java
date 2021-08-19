package com.example.spring.web.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.http.HttpServlet;
import java.util.stream.Stream;

@SpringBootApplication
public class WebApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApp.class, args);
        String[] beanNames = context.getBeanNamesForType(HttpServlet.class);
        Stream.of(beanNames).forEach(System.err::println);
    }
}
