package com.example.mq.kafka;

import com.example.mq.kafka.simple.SimpleConsumer;
import com.example.mq.kafka.simple.SimpleProducer;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/10 14:31
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) {
        testSimple();
    }

    public static void testSimple() {
        SimpleProducer simpleProducer = new SimpleProducer();
        SimpleConsumer simpleConsumer = new SimpleConsumer();
        Thread t1 = new Thread(simpleProducer);
        Thread t2 = new Thread(simpleConsumer);
        t1.start();
        t2.start();
    }
}
