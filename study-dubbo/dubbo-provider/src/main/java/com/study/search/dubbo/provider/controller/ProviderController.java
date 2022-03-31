package com.study.search.dubbo.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProviderController {


    @GetMapping("/provider")
    public String provider() {
        return "provider controller";
    }

}
