package com.study.micro.service.mall.order.service.impl;

import com.study.micro.service.mall.order.fegin.PayFeignClient;
import com.study.micro.service.mall.order.service.OrderService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author gaozhilong
 * @date 2022/3/3 11:28
 */
@Service
public class OrderServiceImpl implements OrderService {


    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private PayFeignClient payFeignClient;

    @Override
    @Trace
    @Tag(key = "list", value = "returnedObj")
    public boolean closeOrder(String id) {
        logger.info("execute query order, id:{}", id);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean pay = payFeignClient.checkPay(id);
        logger.info("end get order, get pay result:{}", pay);
        return pay;
    }
}
