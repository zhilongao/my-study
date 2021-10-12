package com.study.search.aop.search.controller;

import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/test")
public class IndexController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        orderService.findList("1001");
        return "ok";
    }

}
