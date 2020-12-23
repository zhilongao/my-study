package com.example.mq.rabbitmq.dlx;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/23 11:03
 * @since v1.0.0001
 */
public class DlxClient {

    /*
        原理:
            1.首先是声明一个队列(queue1)，在声明该队列的时候可以指定一个属性(x-dead-letter-exchange)，该属性指向了某个交换机(exchange2)。
            2.声明交换机(exchange2)和一个队列(queue2)，并将交换机(exchange2)和队列(queue2)绑定，routingKey为#，表示exchange2接收到的所有消息都将发送到队列queue2。
            3.当发送端将消息发送到某个交换机(exchange1),交换机(exchange1)通过某种路由规则(routingKey)将消息路由到队列(queue1)。
            4.由于队列(queue1)中的消息没有消费端消息，到了指定的过期时间，会将该消息发送到(x-dead-letter-exchange)，该exchange在将消息分发到某种规则绑定的队列。
    */

    public static void main(String[] args) {
        DlxProducerTask producerTask = new DlxProducerTask();
        DlxConsumerTask consumerTask = new DlxConsumerTask();
        Thread t1 = new Thread(producerTask);
        Thread t2 = new Thread(consumerTask);
        t1.start();
        t2.start();
    }
}
