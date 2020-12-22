package com.example.mq.rabbitmq.simple;

import java.util.concurrent.TimeUnit;

public class ClientApp {

    public static void main(String[] args) throws Exception{
        consumeMessage();
        produceMessage();
    }

    private static void produceMessage() {
        int limit = 1000000;
        int suffix = 0;
        while (suffix != limit) {
            String message = "测试消息" + suffix++;
            try {
                // SimpleMessageService.simpleProduceMessage(message);
                SimpleMessageService.defaultProduceMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumeMessage() {
        try {
            // SimpleMessageService.simpleConsumerMessage();
            SimpleMessageService.defaultConsumeMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
