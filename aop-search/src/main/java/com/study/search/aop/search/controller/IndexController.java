package com.study.search.aop.search.controller;

import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/index")
    public String index() {
        String s = orderService.findList("11").toString();
        System.err.println("controller return before");
        return s;
    }
}
