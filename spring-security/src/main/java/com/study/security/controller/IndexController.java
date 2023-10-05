package com.study.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/index")
@RestController
public class IndexController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello,world";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "admin";
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "user";
    }
}
