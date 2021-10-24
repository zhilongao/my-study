package com.study.search.aop.search.service.impl;

import com.study.search.aop.search.annotation.Log;
import com.study.search.aop.search.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    // @Log
    public List<String> findList(String customerId) {
        return Arrays.asList("111", "222", "333");
    }
}
