package com.zl.study.seatabusinessapp.service.impl;

import com.zl.study.seatabusinessapp.mapper.OrderMapper;
import com.zl.study.seatabusinessapp.model.Order;
import com.zl.study.seatabusinessapp.service.AbstractService;
import com.zl.study.seatabusinessapp.service.OrderService;
import com.zl.study.seatabusinessapp.service.OrderServiceV2;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.PostConstruct;

@Service
public class OrderServiceImpl extends AbstractService implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderServiceV2 orderServiceV2;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationContext context;

    //@Autowired
    //private PlatformTransactionManager manager;


    public Order findOrderById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    //@PostConstruct
    public void run(){
        orderMapper.delete(1);
        orderMapper.delete(2);
        Order order = new Order();
        order.setId(1);
        order.setOrderNum(1001);
        orderService.insert(order);
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

    // 验证问题:类内部this方法调用事物失效
    @Override
    public int insertV4(Order record) {
        // 类内部this方法调用事物失效
        // return this.insetV5(record);

        // 类内部事物失效的解决方法
        // 方法1:直接注入
        // return this.orderService.insetV5(record);

        // 方法2:spring提供的静态方法获取代理对象
        // OrderService orderService = (OrderService) AopContext.currentProxy();
        // return orderService.insetV5(record);

        // 方法3:从上下文获取代理对象
        OrderService orderService = context.getBean(OrderService.class);
        return orderService.failInsert(record);
    }

    // 验证问题:多个线程事物不生效的问题
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertV5(Order record) {
        int num = successInsert(record);
        new Thread(this::buildException).start();
        return num;
    }

    // 验证问题:rollbackFor
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
    public int insertV6(Order record) {
        int num = successInsert(record);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int successInsert(Order record) {
        return orderMapper.insert(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int failInsert(Order record) {
        int num = orderMapper.insert(record);
        buildException();
        return num;
    }

    @Override
    public int delete(Integer id) {
        return orderMapper.delete(id);
    }

    public static class BusinessException extends Exception {

    }
}
