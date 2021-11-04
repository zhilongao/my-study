package com.study.search.aop.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class IndexController {

    @Autowired
    private OrderService orderService;


    /**
     * 接收来自各个客户端的请求(云之家 飞书 企微)
     * @param request request
     * @return
     */
    @ResponseBody
    @GetMapping("/index1")
    public String index1(HttpServletRequest request) {
        String params = request.getParameter("params");
        String[] split = params.split(",");
        Map<String, String> map = new HashMap<>();
        for (String s : split) {
            String[] item = s.split(":");
            map.put(item[0], item[1]);
        }
        if (map.get("type").equals("Feishu")) {
            String code = request.getParameter("code");
            map.put("code", code);
            System.err.println("飞书");
        }
        return JSONObject.toJSONString(map);
    }

    @ResponseBody
    @GetMapping("/get")
    public String test(HttpServletRequest request, @RequestParam("name") String name) {
        orderService.findList("1001");
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
