package com.example.mq.rabbitmq.send.confirm;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 10:04
 * @since v1.0.0001
 */
public class ClientApp {
    public static void main(String[] args) {
        NormalConfirmProducer normalConfirmProducer = new NormalConfirmProducer();
        BatchConfirmProducer batchConfirmProducer = new BatchConfirmProducer();
        AsyncConfirmProducer asyncConfirmProducer = new AsyncConfirmProducer();
        Thread t1 = new Thread(normalConfirmProducer);
        Thread t2 = new Thread(batchConfirmProducer);
        Thread t3 = new Thread(asyncConfirmProducer);
        // t1.start();
        // t2.start();
        t3.start();
    }
}
