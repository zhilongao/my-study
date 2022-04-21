package com.study.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/9 16:33
 * @since v1.0.0001
 */
public class ProviderApp {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider-service.xml");
                context.start();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
