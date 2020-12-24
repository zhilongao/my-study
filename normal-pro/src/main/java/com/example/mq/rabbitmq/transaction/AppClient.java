package com.example.mq.rabbitmq.transaction;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 11:12
 * @since v1.0.0001
 */
public class AppClient {
    public static void main(String[] args) {
        TransactionProducer p1 = new TransactionProducer();
        Thread t1 = new Thread(p1);
        t1.start();
    }
}
