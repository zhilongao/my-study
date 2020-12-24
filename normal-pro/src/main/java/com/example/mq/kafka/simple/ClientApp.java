package com.example.mq.kafka.simple;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 16:20
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) {
        SimpleProducer simpleProducer = new SimpleProducer();
        SimpleConsumer simpleConsumer = new SimpleConsumer();
        Thread t1 = new Thread(simpleProducer);
        Thread t2 = new Thread(simpleConsumer);
        t1.start();
        t2.start();
    }
}
