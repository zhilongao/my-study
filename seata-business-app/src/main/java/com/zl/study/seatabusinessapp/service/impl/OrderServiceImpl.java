package com.zl.study.seatabusinessapp.service.impl;

import com.zl.study.seatabusinessapp.mapper.OrderMapper;
import com.zl.study.seatabusinessapp.model.Order;
import com.zl.study.seatabusinessapp.service.AbstractService;
import com.zl.study.seatabusinessapp.service.OrderService;
import com.zl.study.seatabusinessapp.service.OrderServiceV2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderServiceV2 orderServiceV2;


    public Order findOrderById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    // spring事物的几种传播类型
    // Propagation.REQUIRED(必须有  事物如果有则加入,如果没有则新建)
    // Propagation.SUPPORTS(可有可无  事物如果有则加入,如果没有就不管)
    // Propagation.MANDATORY(使用当前的事物,如果没有则抛出异常)
    // Propagation.REQUIRES_NEW(新建事物,如果当前有事物,则将当前的事物挂起)
        // 外层事物不影响内层事物的提交和回滚
        // 内层事物的异常,会影响外部事物的回滚
    // Propagation.NOT_SUPPORTED(以非事物的方式运行,如果当前有事物,将当前事物挂起)
    // Propagation.NEVER(以非事物方式运行,如果当前存在事物,排除异常)
    // TransactionAspectSupport

    // 验证问题:外层事物不影响内层事物的提交和回滚
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)// 新建事物
    public int insert(Order record) {
        int num = orderMapper.insert(record);
        Order o = new Order();
        BeanUtils.copyProperties(record, o);
        o.setId(record.getId());
        orderServiceV2.save(o);
        buildException();
        return num;
    }

    // 验证问题:内层事物的异常,会影响外部事物的回滚
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)// 新建事物
    public int insertV2(Order record) {
        int num = orderMapper.insert(record);
        Order o = new Order();
        BeanUtils.copyProperties(record, o);
        o.setId(record.getId());
        orderServiceV2.saveV2(o);
        return num;
    }

    // 解决问题:内层事物的异常,会影响外部事物的回滚
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)// 新建事物
    public int insertV3(Order record) {
        int num = orderMapper.insert(record);
        Order o = new Order();
        BeanUtils.copyProperties(record, o);
        o.setId(record.getId());
        try {
            orderServiceV2.saveV2(o);
        } catch (Exception e) {

        }
        return num;
    }


    @Override
    public int delete(Integer id) {
        return orderMapper.delete(id);
    }
}
