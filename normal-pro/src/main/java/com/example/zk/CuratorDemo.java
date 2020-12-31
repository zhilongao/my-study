package com.example.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/29 13:48
 * @since v1.0.0001
 */
public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        String zkUrl = CommonConstant.CONNECTION_STR;
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().
                connectString(zkUrl).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start(); //启动
        String path = "/data/program";
        byte[] createData = "test".getBytes();
        byte[] upData = "up".getBytes();
        // CRUD
        createData(curatorFramework, path, createData);
        // updateData(curatorFramework, path, upData);
        // deleteData(curatorFramework, path);
    }

    private static void createData(CuratorFramework curatorFramework, String path, byte[] data) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).
                forPath(path, data);

    }

    private static void updateData(CuratorFramework curatorFramework, String path, byte[] data) throws Exception {
        curatorFramework.setData().forPath(path, data);
    }

    private static void deleteData(CuratorFramework curatorFramework, String path) throws Exception {
        Stat stat = new Stat();
        String value = new String(curatorFramework.getData().storingStatIn(stat).forPath(path));
        System.err.println(value);
        curatorFramework.delete().withVersion(stat.getVersion()).forPath(path);
    }
}
