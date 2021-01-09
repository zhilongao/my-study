package com.study.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/9 16:33
 * @since v1.0.0001
 */
public class ProviderApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider-service.xml");
        context.start();
    }

}
