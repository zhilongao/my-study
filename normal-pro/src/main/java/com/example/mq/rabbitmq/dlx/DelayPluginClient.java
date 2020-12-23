package com.example.mq.rabbitmq.dlx;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/23 18:44
 * @since v1.0.0001
 */
public class DelayPluginClient {
    public static void main(String[] args) {
        DelayPluginConsumer delayPluginConsumer = new DelayPluginConsumer();
        DelayPluginProducer delayPluginProducer = new DelayPluginProducer();
        Thread t1 = new Thread(delayPluginConsumer);
        Thread t2 = new Thread(delayPluginProducer);
        t1.start();
        t2.start();
    }
}
