package com.example.zk.register;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ImplByZkClient {

    static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("192.168.43.4:2181", 4000, new SimpleWatch());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class SimpleWatch implements Watcher{
        @Override
        public void process(WatchedEvent event) {
            System.err.println("eventType:" + event.getType());
            if(event.getType() == Event.EventType.NodeDataChanged){
                try {
                    zooKeeper.exists(event.getPath(),true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // getData()/  exists  /getChildren
    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        String path="/watcher";
        if(zooKeeper.exists(path,false) == null) {
            zooKeeper.create("/watcher", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        Thread.sleep(1000);
        System.out.println("-----------");
        // true表示使用zookeeper实例中配置的watcher
        Stat stat = zooKeeper.exists(path,true);
        System.in.read();
    }

}
