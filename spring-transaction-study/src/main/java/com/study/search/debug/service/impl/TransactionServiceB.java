package com.study.search.debug.service.impl;

import com.study.search.debug.dao.TableMapper;
import com.study.search.debug.entity.TableEntity;
import com.study.search.debug.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceB{

    @Autowired
    private TableService tableService;

    public void methodB(){
        System.out.println("methodB");
        tableService.insertTableB(new TableEntity());
    }
}
