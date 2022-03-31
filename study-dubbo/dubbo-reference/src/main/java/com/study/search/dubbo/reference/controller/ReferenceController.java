package com.study.search.dubbo.reference.controller;

import com.study.search.dubbo.reference.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @GetMapping("/reference")
    public String reference() {
        return referenceService.reference("hello,world");
    }
}
