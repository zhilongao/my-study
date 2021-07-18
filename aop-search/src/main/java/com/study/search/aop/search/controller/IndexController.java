package com.study.search.aop.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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


    @RequestMapping(value = "/index1", consumes = {"application/json", "application/x-www-form-urlencoded"})
    public String testM1(@RequestParam Map<String, Object> body) {
        return "hello,world";
    }
}
