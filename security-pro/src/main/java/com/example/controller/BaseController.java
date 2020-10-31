package com.example.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/27 19:10
 * @since v1.0.0001
 */
@Controller
public class BaseController {

    @GetMapping("/login.html")
    public String loginPage(){
        return "/login.html";
    }

    @GetMapping("/home.html")
    public String home(){
        return "/home.html";
    }

    @GetMapping("/")
    public String basePage(){
        return "/home.html";
    }

    @GetMapping("/error.html")
    public String error(){
        return "/error.html";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test:" + SecurityContextHolder.getContext().getAuthentication();
    }

    // 认证和授权
    // 在认证的时候，保存用户所具有的所有权限
    // 针对特定的url做授权操作

}
