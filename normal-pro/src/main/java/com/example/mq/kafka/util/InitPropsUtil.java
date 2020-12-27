package com.example.mq.kafka.util;

import com.example.mq.kafka.CommonConstant;

import java.util.Properties;

public class InitPropsUtil {

    private static final String bootstrapServers = CommonConstant.bootstrapServers;

    /**
     * 初始化生产者通用属性
     * @return
     */
    public static Properties commonProducerProps() {
        Properties props = new Properties();
        // 服务端信息
        props.put("bootstrap.servers", bootstrapServers);
        // key和value序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 0 发出去就确认 | 1 leader 落盘就确认| all 所有Follower同步完才确认
        props.put("acks", "1");
        // 异常自动重试次数
        props.put("retries", 3);
        // 多少条数据发送一次，默认16K
        props.put("batch.size", 16384);
        // 批量发送的等待时间
        props.put("linger.ms", 5);
        // 客户端缓冲区大小，默认32M，满了也会触发消息发送
        props.put("buffer.memory", 33554432);
        // 获取元数据时生产者的阻塞时间，超时后抛出异常
        props.put("max.block.ms", 3000);
        return props;
    }

    public static Properties commonConsumerProps(String groupId) {
        Properties props = commonConsumerProps();
        props.put("group.id", groupId);
        return props;
    }

    public static Properties commonConsumerProps() {
        Properties props = new Properties();
        // 服务端信息
        props.put("bootstrap.servers", bootstrapServers);
        // key和value的反序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        // 从最早的数据开始消费 earliest | latest | none
        props.put("auto.offset.reset", "earliest");
        // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
        props.put("enable.auto.commit", "true");
        // 消费者自动提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        return props;
    }
}
