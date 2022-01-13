package com.study.search.aop.search.im;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/im")
public class ImController {

    // 如何交互
    @GetMapping("/connect")
    public String connect(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        // 登录 ->

        return "ok";
    }


}
