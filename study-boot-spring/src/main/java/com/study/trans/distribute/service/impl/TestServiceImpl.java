package com.study.trans.distribute.service.impl;

import com.study.trans.distribute.entity.order.OrderInfo;
import com.study.trans.distribute.entity.user.UserInfo;
import com.study.trans.distribute.mapper.order.OrderMapper;
import com.study.trans.distribute.mapper.user.UserMapper;
import com.study.trans.distribute.service.TestService;
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
public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;


    // 全局事务处理器
    // 事务底层原理采用aop技术做增强
    // 无需再指定某个事务管理器，全交给 Atomikos 全局事务
    @Transactional
    @Override
    public Boolean addUser() {
        UserInfo u1 = new UserInfo();
        u1.setId(101L);
        u1.setName("jack1");
        int i = userMapper.saveUser(u1);
        // 操作订单库
        OrderInfo o1 = new OrderInfo();
        o1.setId(100L);
        o1.setCount(1000);
        o1.setMoney(2000L);
        o1.setStatus((byte)1);
        int j = orderMapper.saveOrder(o1);
        // 测试事务回滚(age = 0：回滚；age > 0：事务提交)
        // int flag = 1 / 0;
        return i > 0 && j > 0;
    }
}
