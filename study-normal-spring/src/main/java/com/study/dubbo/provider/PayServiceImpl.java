package com.study.dubbo.provider;

import com.study.dubbo.api.PayService;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/9 16:32
 * @since v1.0.0001
 */
public class PayServiceImpl implements PayService {

    public String pay(String info) {
        System.out.println("execute pay："+info);
        return "Hello Dubbo :"+info;
    }
}
