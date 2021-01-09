package com.study.dubbo.consumer;

import com.study.dubbo.api.PayService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/9 16:51
 * @since v1.0.0001
 */
public class ConsumerApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer-service.xml"});
        PayService payService = (PayService) context.getBean("payService");
        String rs = payService.pay("Test");
        System.out.println(rs);
    }
}
