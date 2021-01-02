package com.example.zk.simple;

import com.example.zk.util.CommonConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/29 13:40
 * @since v1.0.0001
 */
public class WatchDemo {

    private static final String zkUrl = CommonConstant.CONNECTION_STR;
    public static void addListener() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(zkUrl).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        String path = "order-service";
        NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.err.println("receive node changed");
                System.err.println(nodeCache.getCurrentData().getPath() + "---" + new String(nodeCache.getCurrentData().getData()));
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        try {
            nodeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // PathChildCache  --针对于子节点的创建、删除和更新 触发事件
        // NodeCache  针对当前节点的变化触发事件
        // TreeCache  综合事件
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(zkUrl).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        String path = "/data/program";
        addListenerWithNode(curatorFramework, path);
        // addListenerWithChild(curatorFramework, path);
        System.in.read();
    }

    private static void addListenerWithNode(CuratorFramework curatorFramework, String path) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.err.println("receive node changed");
                System.err.println(nodeCache.getCurrentData().getPath() + "---" + new String(nodeCache.getCurrentData().getData()));
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    private static void addListenerWithChild(CuratorFramework curatorFramework, String path) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println(pathChildrenCacheEvent.getType()+"->"+new String(pathChildrenCacheEvent.getData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start(PathChildrenCache.StartMode.NORMAL);
    }
}
