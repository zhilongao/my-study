package com.example.zk.register;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class ServiceRegister {

    private static final String nameSpace = "register";

    private CuratorFramework framework;

    public ServiceRegister() {
        framework = CuratorUtil.curatorFramework(nameSpace);
        framework.start();
    }

    public void register(String serviceName, String serviceAddress) {
        String servicePath = "/" + serviceName;
        try {
            if(framework.checkExists().forPath(servicePath) == null) {
                framework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath);
            }
            String addressPath = servicePath + "/" + serviceAddress;
            framework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
            System.err.println(serviceName + "-" + serviceAddress + " 服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
