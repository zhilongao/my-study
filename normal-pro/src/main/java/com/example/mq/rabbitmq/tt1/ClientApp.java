package com.example.mq.rabbitmq.tt1;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 11:26
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) {
        TtlProducer ttlProducer = new TtlProducer();
        Thread t1 = new Thread(ttlProducer);
        t1.start();
    }
}
