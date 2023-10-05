package com.study.search.debug.service.impl;

import com.study.search.debug.entity.TableEntity;
import com.study.search.debug.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceA {

    @Autowired
    private TableService tableService;

    @Autowired
    private TransactionServiceB transactionServiceB;


    public void methodA(){
        System.out.println("methodA");
        tableService.insertTableA(new TableEntity());
        transactionServiceB.methodB();
    }

}
