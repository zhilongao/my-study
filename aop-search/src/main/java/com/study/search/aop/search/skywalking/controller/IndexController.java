package com.study.search.aop.search.skywalking.controller;


import com.study.search.aop.search.log.LogUtils;
import com.study.search.aop.search.skywalking.service.OrderService;
import com.study.search.aop.search.skywalking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        testLog();
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

    private void testLog() {
        String basePath = "E:\\files\\";
        String logName = "test-log-1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger logger = LogUtils.getInstance().getLogger(basePath, logName);
                while (true) {
                    logger.info("hello,world,0912381028312038213111111111111111111111111111111");
                    try {
                        TimeUnit.MICROSECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @RequestMapping("/sent")
    public String sent(@RequestParam String param) {
        return "ok";
    }
}
