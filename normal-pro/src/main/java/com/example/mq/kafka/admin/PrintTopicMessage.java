package com.example.mq.kafka.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;

import java.util.Collection;
import java.util.Set;

public class PrintTopicMessage {

    public static void listTopicsWithOptions() throws Exception {
        // 创建AdminClient客户端对象
        AdminClient adminClient = AdminClientFactory.createAdminClientByProperties();
        ListTopicsOptions options = new ListTopicsOptions();
        // 列出内部的Topic
        options.listInternal(true);
        // 列出所有的topic
        ListTopicsResult result = adminClient.listTopics(options);
        // 获取所有topic的名字，返回的是一个Set集合
        Set<String> topicNames = result.names().get();
        // 打印所有topic的名字
        topicNames.forEach(System.err::println);
        // 获取所有topic的信息，返回的是一个Collection集合
        // (name=hello-kafka, internal=false),internal代表是否为内部的topic
        Collection<TopicListing> topicListings = result.listings().get();
        // 打印所有topic的信息
        topicListings.forEach(System.err::println);
        // 关闭资源
        adminClient.close();
    }
}
