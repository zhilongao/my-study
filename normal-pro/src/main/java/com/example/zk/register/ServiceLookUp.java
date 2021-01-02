package com.example.zk.register;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;

import java.util.*;

public class ServiceLookUp {

    private static final String nameSpace = "register";

    private CuratorFramework framework;

    private Map<String, List<String>> serviceMap = new HashMap<>();

    public LoadBalance loadBalance;

    public ServiceLookUp() {
        loadBalance = new LoadBalance();
        framework = CuratorUtil.curatorFramework(nameSpace);
        framework.start();
    }

    /**
     * 获取某个服务的节点
     * @param serviceName
     * @return
     */
    public String discovery(String serviceName) {
        String path = "/" + serviceName;
        try {
            List<String> addresses = framework.getChildren().forPath(path);
            // 更新缓存
            update(path, addresses);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadBalance.selectHost(serviceMap.get(path));
    }

    /**
     * 监听某个path
     * @param path
     * @throws Exception
     */
    private void registryWatch(String path) throws Exception {
        PathChildrenCache nodeCache = new PathChildrenCache(framework, path, true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1, event) -> {
            String type = event.getType().name();
            System.out.println("客户端收到节点变更的事件 " + type);
            // 再次更新本地的缓存地址
            List<String> addresses = curatorFramework1.getChildren().forPath(path);
            update(path, addresses);
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * 更新某个path下的本地缓存
     * @param path
     * @param addresses
     */
    private void update(String path, List<String> addresses) {
        List<String> addressList = serviceMap.get(path);
        if (addressList == null) {
            addressList = new ArrayList<String>();
            serviceMap.put(path, addressList);
        }
        addressList.clear();
        addressList.addAll(addresses);
    }
}
