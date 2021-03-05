package com.study.trans.common.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.trans.common.entity.order.OrderInfo;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:26
 * @since v1.0.0001
 */
public interface OrderMapper extends BaseMapper<OrderInfo> {

    int saveOrder(OrderInfo orderInfo);

}
