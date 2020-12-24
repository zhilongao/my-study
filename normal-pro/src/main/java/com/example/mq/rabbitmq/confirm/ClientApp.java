package com.example.mq.rabbitmq.confirm;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 10:04
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) {
        AsyncConfirmProducer asyncConfirmProducer = new AsyncConfirmProducer();
        BatchConfirmProducer batchConfirmProducer = new BatchConfirmProducer();
        NormalConfirmProducer normalConfirmProducer = new NormalConfirmProducer();
        Thread t1 = new Thread(asyncConfirmProducer);
        Thread t2 = new Thread(batchConfirmProducer);
        Thread t3 = new Thread(normalConfirmProducer);
        t1.start();
        t2.start();
        t3.start();
    }
}
