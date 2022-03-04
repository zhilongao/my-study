package com.study.micro.service.mall.pay.service.impl;

import com.study.micro.service.mall.pay.service.PayService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author gaozhilong
 * @date 2022/3/3 15:01
 */
@Service
public class PayServiceImpl implements PayService {

    Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    @Trace
    @Tag(key = "list", value = "returnedObj")
    public boolean checkPay(String orderId) {
        logger.info("check order pay orderId:{}", orderId);
        return true;
    }
}
