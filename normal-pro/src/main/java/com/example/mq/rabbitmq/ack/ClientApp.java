package com.example.mq.rabbitmq.ack;

public class ClientApp {

    public static void main(String[] args) {
        AckComsumer ackComsumer = new AckComsumer();
        AckProducer ackProducer = new AckProducer();
        Thread t1 = new Thread(ackComsumer);
        Thread t2 = new Thread(ackProducer);
        t1.start();
        t2.start();
    }
}
