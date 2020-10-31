package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/31 11:13
 * @since v1.0.0001
 */
@RestController
public class OrderController {

    @PreAuthorize(value = "hasAnyAuthority('ROOT')")
    @RequestMapping("/gp/query1")
    public String query1(){
        return "query1 ...";
    }
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @RequestMapping("/gp/query2")
    public String query2(){
        return "query2 ...";
    }

}
