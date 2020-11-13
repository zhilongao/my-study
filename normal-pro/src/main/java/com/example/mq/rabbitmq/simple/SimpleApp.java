package com.example.mq.rabbitmq.simple;

public class SimpleApp {
    public static void main(String[] args) throws Exception{
        // SimpleMessageService.startSimpleConsumer();
        // SimpleMessageService.originalProduceMessage();
        // SimpleMessageService.deleteQueueAndExchange();
        SimpleMessageService.createDefaultExchangeConsumer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleMessageService.createDefaultExchangeProducer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
