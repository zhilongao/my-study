package com.study.trans.distribute.controller;

import com.study.trans.distribute.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 16:13
 * @since v1.0.0001
 */
@RestController
@RequestMapping("/test")
public class SimpleRestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String message() {
        testService.addUser();
        return "hello,world";
    }
}
