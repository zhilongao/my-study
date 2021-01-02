package com.example.zk.simple;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/29 13:48
 * @since v1.0.0001
 */
public class SimpleDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework framework = CuratorUtil.curatorFramework();
        framework.start(); //启动
        String path = "/data/program";
        byte[] createData = "test".getBytes();
        byte[] upData = "up".getBytes();
        // CRUD
        createData(framework, path, createData);
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
