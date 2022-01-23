package com.study.search.aop.search.skywalking.service.impl;

import com.study.search.aop.search.common.log.Log;
import com.study.search.aop.search.skywalking.service.OrderService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Trace
    @Tag(key = "list", value = "returnedObj")
    @Override
    @Log
    public List<String> findList(String customerId) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList("111", "222", "333");
    }
}
