package com.example.mq.kafka.zk;

import com.example.mq.kafka.CommonConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: qingshan
 */
public class ZookeeperUtils {

    public static List<String> toDeleteList = new ArrayList<>();

    static {
        toDeleteList.add("/brokers");
        toDeleteList.add("/admin");
        toDeleteList.add("/cluster");
        toDeleteList.add("/config");
        toDeleteList.add("/consumers");
        toDeleteList.add("/controller_epoch");
        toDeleteList.add("/isr_change_notification");
        toDeleteList.add("/latest_producer_id_block");
        toDeleteList.add("/log_dir_event_notification");
    }

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
            for (String delNode : toDeleteList) {
                client.delete().deletingChildrenIfNeeded().forPath(delNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
