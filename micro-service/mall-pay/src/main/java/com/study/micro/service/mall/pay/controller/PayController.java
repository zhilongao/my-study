package com.study.micro.service.mall.pay.controller;

import com.study.micro.service.mall.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;


    @GetMapping("/pay")
    public boolean pay(@RequestParam String orderId) {
        return payService.checkPay(orderId);
    }

}
