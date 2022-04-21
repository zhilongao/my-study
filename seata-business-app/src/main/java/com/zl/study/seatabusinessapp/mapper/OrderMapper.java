package com.zl.study.seatabusinessapp.mapper;

import com.zl.study.seatabusinessapp.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Order selectByPrimaryKey(Integer id);

    int insert(Order record);

    int delete(Integer id);
}
