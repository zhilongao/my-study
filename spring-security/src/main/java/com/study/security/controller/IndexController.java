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

    @GetMapping("/loginDefaultSuccessUrl")
    public String loginDefaultSuccessUrl() {
        return "login defaultSuccessUrl ";
    }

    @GetMapping("/loginSuccessForwardUrl")
    public String loginSuccessForwardUrl() {
        return "login successForwardUrl ";
    }
}
