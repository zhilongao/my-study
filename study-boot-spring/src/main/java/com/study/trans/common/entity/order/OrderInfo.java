package com.study.trans.common.entity.order;

import lombok.Data;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:27
 * @since v1.0.0001
 */
@Data
public class OrderInfo {

    private Long id;

    private int count;

    private Long money;

    private byte status;

}
