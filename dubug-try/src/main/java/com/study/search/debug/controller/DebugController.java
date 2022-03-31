package com.study.search.debug.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class DebugController {

    @GetMapping("/debug/info")
    public String debug(HttpServletRequest request) {
        String a = "a";
        String b = "b";
        String c = a + b;
        return c;
    }

}
