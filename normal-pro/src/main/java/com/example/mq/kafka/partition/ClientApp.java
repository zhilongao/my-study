package com.example.mq.kafka.partition;

import com.example.mq.kafka.CommonConstant;

public class ClientApp {
    public static void main(String[] args) {
        String bootTopic = CommonConstant.SPRING_BOOT_TOPIC_NAME;
        String cloudTopic = CommonConstant.SPRING_CLOUD_TOPIC_NAME;
        PartitionInformation.printTopicInfo(bootTopic);
        System.err.println("--------------------------------");
        PartitionInformation.printTopicInfo(cloudTopic);
    }
}
