package com.example.mq.kafka.topic;

import java.util.Set;

public class TopicOperateTest {
    public static void main(String[] args) {
        try {
            Set<String> beforeTopics = TopicOperate.listTopicsWithOptions(false);
            beforeTopics.forEach(System.out::println);

            for (String topic : beforeTopics) {
                TopicOperate.deleteTopic(topic);
            }

            System.out.println("------------------------------------");
            Set<String> afterTopics = TopicOperate.listTopicsWithOptions(false);
            afterTopics.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
