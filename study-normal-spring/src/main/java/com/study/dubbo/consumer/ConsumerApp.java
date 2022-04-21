package com.study.dubbo.consumer;

import com.study.dubbo.api.PayService;
import org.apache.dubbo.registry.client.ServiceDiscovery;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/9 16:51
 * @since v1.0.0001
 */
public class ConsumerApp {
    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer-service.xml"});
                PayService payService = (PayService) context.getBean("payService");
                System.err.println("hello,world");
                String rs = payService.pay("Test");
                System.out.println(rs);
//            }
//        }).start();
//        try {
//            TimeUnit.SECONDS.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
