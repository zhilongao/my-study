package com.study.search.aop.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Controller
@RequestMapping("/test")
public class IndexController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @GetMapping("/get")
    public String test(HttpServletRequest request, @RequestParam("name") String name) {
        System.err.println("--------------start header----------------");
        printHeaders(request);
        System.err.println("---------------start param---------------");
        System.err.println("name:" + name);
        orderService.findList("1001");
        System.err.println("--------------- end ---------------");
        return "get ok";
    }

    @ResponseBody
    @PostMapping("/post")
    public String post(HttpServletRequest request, @RequestBody JSONObject param) {
        System.err.println("--------------start header----------------");
        printHeaders(request);
        System.err.println("---------------start param---------------");
        System.err.println(param);
        System.err.println("--------------- end ---------------");
        return "post ok";
    }

    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.err.println(headerName + "-" + headerValue);
        }
    }

}
