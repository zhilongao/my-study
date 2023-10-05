package com.study.search.debug.service.impl;

import com.study.search.debug.dao.TableMapper;
import com.study.search.debug.entity.TableEntity;
import com.study.search.debug.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Override
    public void insertTableA(TableEntity tableEntity) {
        tableMapper.insertTableA(tableEntity);
    }

    @Override
    public void insertTableB(TableEntity tableEntity) {
        tableMapper.insertTableB(tableEntity);
    }
}
