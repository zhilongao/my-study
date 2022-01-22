package com.study.search.aop.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    private Logger logger = LoggerFactory.getLogger(NotifyController.class);

    /**
     * 接收SkyWalking的告警信息
     * @param obj obj
     * @return result
     */
    @RequestMapping("/sendMess")
    public String notify(@RequestBody Object obj) {
        logger.info("receive alarm message:{}", obj.toString());
        return "notify successfully";
    }
}
