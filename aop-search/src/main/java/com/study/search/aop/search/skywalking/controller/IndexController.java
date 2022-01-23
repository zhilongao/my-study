package com.study.search.aop.search.skywalking.controller;


import com.study.search.aop.search.skywalking.service.OrderService;
import com.study.search.aop.search.skywalking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * SkyWalking测试接口1
     * @param customerId
     * @return
     */
    @GetMapping("/getOrder")
    @ResponseBody
    public List<String> getOrder(@RequestParam("customerId") String customerId) {
        logger.info("get order customerId:{}", customerId);
        boolean exists = userService.checkUser(customerId);
        if (exists) {
            return orderService.findList(customerId);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * SkyWalking测试接口2
     * @param customerId
     * @return
     */
    @GetMapping("/searchOrder")
    @ResponseBody
    public List<String> searchOrder(@RequestParam("customerId") String customerId) {
        return orderService.findList(customerId);
    }
}
