package com.example.mq.kafka.admin;

import com.example.mq.kafka.CommonConstant;
import com.google.common.collect.Maps;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.Map;
import java.util.Properties;

public class AdminClientFactory {
    /**
     * 创建AdminClient客户端对象
     * 配置项参考：http://www.manongjc.com/article/26490.html
     */
    public static AdminClient createAdminClientByProperties() {
        Properties prop = new Properties();
        // 配置Kafka服务的访问地址及端口号
        String bootstrapServersVal = CommonConstant.bootstrapServers;
        String requestTimeOutVal = "20000";
        String retriesVal = "3";
        prop.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersVal);
        prop.setProperty(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeOutVal);
        prop.setProperty(AdminClientConfig.RETRIES_CONFIG, retriesVal);
        // 创建AdminClient实例
        AdminClient adminClient = KafkaAdminClient.create(prop);
        return adminClient;
    }

    /**
     * 创建AdminClient的第二种方式
     */
    public static AdminClient createAdminClientByMap() {
        Map<String, Object> conf = Maps.newHashMap();
        // 配置Kafka服务的访问地址及端口号
        String bootstrapServersVal = CommonConstant.bootstrapServers;
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersVal);
        // 创建AdminClient实例
        AdminClient adminClient = AdminClient.create(conf);
        return adminClient;
    }
}
