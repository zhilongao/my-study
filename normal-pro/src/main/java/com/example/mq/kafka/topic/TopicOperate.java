package com.example.mq.kafka.topic;

import com.example.mq.kafka.util.AdminClientFactory;
import org.apache.kafka.clients.admin.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author gaozhilong
 */
public class TopicOperate {

    /**
     * 打印所有topic的名称
     * @throws Exception
     */
    public static Set<String> listTopicsWithOptions(boolean print) throws Exception {
        // 创建AdminClient客户端对象
        AdminClient adminClient = AdminClientFactory.createAdminClientByProperties();
        ListTopicsOptions options = new ListTopicsOptions();
        // 列出内部的Topic
        options.listInternal(true);
        // 列出所有的topic
        ListTopicsResult result = adminClient.listTopics(options);
        // 获取所有topic的名字，返回的是一个Set集合
        Set<String> topicNames = result.names().get();
        // 获取所有topic的信息，返回的是一个Collection集合
        // (name=hello-kafka, internal=false),internal代表是否为内部的topic
        Collection<TopicListing> topicListings = result.listings().get();
        if (!print) {
            return topicNames;
        }
        // 打印所有topic的名字
        topicNames.forEach(System.err::println);
        // 打印所有topic的信息
        topicListings.forEach(System.err::println);
        // 关闭资源
        adminClient.close();
        return topicNames;
    }

    /**
     * 创建主题
     * @param topicName 主题名称
     * @param numPartitions 分区数
     * @param replicationFactor 副本数
     */
    public static void createTopic(String topicName, int numPartitions, short replicationFactor) {
        AdminClient adminClient = AdminClientFactory.createAdminClientByProperties();
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        List<NewTopic> topicList = new ArrayList<NewTopic>();
        topicList.add(newTopic);
        CreateTopicsResult createTopicsResult = adminClient.createTopics(topicList);
        try {
            createTopicsResult.all().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除主题
     * @param topicName 主题名称
     */
    public static void deleteTopic(String topicName) {
        AdminClient adminClient = AdminClientFactory.createAdminClientByProperties();
        List<String> topicNames = Arrays.asList(topicName);
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicNames);
        try {
            deleteTopicsResult.all().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 增加分区
     * @param topicName 主题名称
     * @param allNumPartitions 所有分区数
     */
    public static void increasePartition(String topicName, int allNumPartitions) {
        AdminClient adminClient = AdminClientFactory.createAdminClientByProperties();
        Map<String, NewPartitions> map = new HashMap<>();
        map.put(topicName, NewPartitions.increaseTo(allNumPartitions));
        CreatePartitionsResult createPartitionsResult = adminClient.createPartitions(map);
        try {
            createPartitionsResult.all().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
