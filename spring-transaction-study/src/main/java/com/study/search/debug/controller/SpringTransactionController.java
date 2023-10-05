package com.study.search.debug.controller;


import com.study.search.debug.service.impl.TransactionServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpringTransactionController {

    @Autowired
    private TransactionServiceA transactionServiceA;

    @RequestMapping("/spring-transaction")
    public String testTransaction() {
        transactionServiceA.methodA();
        return "SUCCESS";
    }

}
