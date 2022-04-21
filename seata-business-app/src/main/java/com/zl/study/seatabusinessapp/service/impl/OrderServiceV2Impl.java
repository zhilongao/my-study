package com.zl.study.seatabusinessapp.service.impl;

import com.zl.study.seatabusinessapp.mapper.OrderMapper;
import com.zl.study.seatabusinessapp.model.Order;
import com.zl.study.seatabusinessapp.service.AbstractService;
import com.zl.study.seatabusinessapp.service.OrderServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceV2Impl extends AbstractService implements OrderServiceV2 {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(Order record) {
        record.setId(record.getId() + 1);
        orderMapper.insert(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveV2(Order record) {
        record.setId(record.getId() + 1);
        orderMapper.insert(record);
        buildException();
    }
}
