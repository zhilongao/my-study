package com.example.mq.kafka.admin;

public class ClientApp {
    public static void main(String[] args) {
        try {
            PrintTopicMessage.listTopicsWithOptions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
