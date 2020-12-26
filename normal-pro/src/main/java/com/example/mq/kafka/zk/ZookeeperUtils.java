package com.example.mq.kafka.zk;

import com.example.mq.kafka.CommonConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: qingshan
 */
public class ZookeeperUtils {

    public static void deleteSomeNodes() {
        RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000,3);
        String zkServer = CommonConstant.zkServer;
        int sessionTimeoutVal = 3000;
        int connectionTimeoutVal = 5000;
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(zkServer)
                .sessionTimeoutMs(sessionTimeoutVal)
                .connectionTimeoutMs(connectionTimeoutVal)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        try {
            client.delete().deletingChildrenIfNeeded().forPath("/brokers");
            client.delete().deletingChildrenIfNeeded().forPath("/admin");
            client.delete().deletingChildrenIfNeeded().forPath("/cluster");
            client.delete().deletingChildrenIfNeeded().forPath("/config");
            client.delete().deletingChildrenIfNeeded().forPath("/consumers");
            client.delete().deletingChildrenIfNeeded().forPath("/controller_epoch");
            client.delete().deletingChildrenIfNeeded().forPath("/isr_change_notification");
            client.delete().deletingChildrenIfNeeded().forPath("/latest_producer_id_block");
            client.delete().deletingChildrenIfNeeded().forPath("/log_dir_event_notification");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
