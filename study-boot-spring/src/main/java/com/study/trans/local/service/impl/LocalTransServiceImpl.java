package com.study.trans.local.service.impl;

import com.study.trans.common.entity.order.OrderInfo;
import com.study.trans.common.entity.user.UserInfo;
import com.study.trans.common.mapper.order.OrderMapper;
import com.study.trans.common.mapper.user.UserMapper;
import com.study.trans.local.service.LocalTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 16:17
 * @since v1.0.0001
 */
@Service
public class LocalTransServiceImpl implements LocalTransService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 当项目中有集成多个数据源的时候，spring的@Transactional注解需要明确的指出transactionManager
     */
    @Transactional(transactionManager = "userDataSourceTransactionManager")
    @Override
    public void localTransactionExecute() {
        try {
            UserInfo u1 = new UserInfo();
            u1.setId(101L);
            u1.setName("test1");
            userMapper.saveUser(u1);
            //int a = 10 / 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        // 不获取异常之后，抛出了异常，数据库事务回滚，后面的任务也不会再执行了
        System.err.println("send message to user!");
    }

    // 在一个方法中调用两个数据源中mapper的方法，会产生问题
    // 1. 首先是userMapper的插入会成功
    // 2. 然后是orderMapper的插入会失败
    @Transactional
    @Override
    public void distributeTransactionExecute() {
        UserInfo u1 = new UserInfo();
        u1.setId(104L);
        u1.setName("jack1");
        int i = userMapper.saveUser(u1);
        // 操作订单库
        OrderInfo o1 = new OrderInfo();
        o1.setId(104L);
        o1.setCount(1000);
        o1.setMoney(2000L);
        o1.setStatus((byte)1);
        int j = orderMapper.saveOrder(o1);
        int m = 5 / 0;
    }
}
