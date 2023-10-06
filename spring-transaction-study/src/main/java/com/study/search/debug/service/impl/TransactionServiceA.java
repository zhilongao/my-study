package com.study.search.debug.service.impl;

import com.study.search.debug.entity.TableEntity;
import com.study.search.debug.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceA {

    @Autowired
    private TableService tableService;

    @Autowired
    private TransactionServiceB transactionServiceB;

    @Transactional()
    public void methodA() throws Exception {
        tableService.insertTableA(new TableEntity());
        transactionServiceB.methodB();
        throw new Exception();
    }

}
