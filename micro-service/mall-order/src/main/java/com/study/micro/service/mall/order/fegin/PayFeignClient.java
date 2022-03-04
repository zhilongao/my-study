package com.study.micro.service.mall.order.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gaozhilong
 * @date 2022/3/3 11:10
 */
@FeignClient(name = "mall-pay", path = "/pay/")
public interface PayFeignClient {

    @RequestMapping(value = "pay")
    boolean checkPay(@RequestParam("orderId") String orderId);
}
