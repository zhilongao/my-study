package com.study.search.debug.service.impl;

import com.study.search.debug.entity.TableEntity;
import com.study.search.debug.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceB{

    @Autowired
    private TableService tableService;

    @Transactional()
    public void methodB(){
        System.out.println("methodB");
        tableService.insertTableB(new TableEntity());
        // throw new RuntimeException();
    }
}
