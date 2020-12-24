package com.example.mq.rabbitmq.limit;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/24 9:51
 * @since v1.0.0001
 */
public class LimitApp {

    public static void main(String[] args) {
        LimitProducer p = new LimitProducer();
        LimitConsumerV1 c1 = new LimitConsumerV1();
        LimitConsumerV2 c2 = new LimitConsumerV2();
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(c2);
        t1.start();
        t2.start();
        t3.start();
    }
}
