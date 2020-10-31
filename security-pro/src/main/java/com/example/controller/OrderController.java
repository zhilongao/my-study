package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/28 11:19
 * @since v1.0.0001
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @RequestMapping("/order1")
    public String order1() {
        return "order1 .......";
    }

    @RequestMapping("/order2")
    public String order2() {
        return "order2 .......";
    }

    @RequestMapping("/order3")
    public String order3() {
        return "order3 .......";
    }

    @PreAuthorize(value = "hasRole('CREATE')")
    @RequestMapping("/order4")
    public String order4() {
        return "order4 .......";
    }

}
