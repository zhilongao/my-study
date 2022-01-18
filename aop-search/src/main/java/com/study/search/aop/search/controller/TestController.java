package com.study.search.aop.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/message")
    public String getMessage(@RequestParam("name") String name) {
        logger.info("params name:{}", name);
        return "hello" + name;
    }

}
