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
        props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        return props;
    }

    public static Properties commonConsumerProps() {
        Properties props = new Properties();
        // 服务端信息
        props.put("bootstrap.servers", bootstrapServers);
        // key和value的反序列化方式
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

}
