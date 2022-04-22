package com.zl.study.seatabusinessapp.service;

import com.zl.study.seatabusinessapp.model.Order;

public interface OrderService {

    Order findOrderById(Integer id);

    int insert(Order record);

    int insertV2(Order record);

    int insertV3(Order record);

    int insertV4(Order record);

    int insertV5(Order record);

    int insertV6(Order record);

    int successInsert(Order record);

    int failInsert(Order record);

    int delete(Integer id);

}
