package com.study.search.debug.service;

import com.study.search.debug.entity.TableEntity;

public interface TableService {
    /**
     * 插入到A表
     * @param tableEntity
     */
    void insertTableA(TableEntity tableEntity);

    /**
     * 插入到B表
     * @param tableEntity
     */
    void insertTableB(TableEntity tableEntity);

}
