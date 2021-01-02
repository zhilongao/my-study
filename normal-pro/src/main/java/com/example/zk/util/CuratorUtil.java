package com.example.zk.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.StringUtils;

public class CuratorUtil {

    private static final String zkUrl = CommonConstant.CONNECTION_STR;

    public static CuratorFramework curatorFramework() {
        return curatorFramework(null);
    }

    public static CuratorFramework curatorFramework(String namespace) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().
                connectString(zkUrl).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3));
        if (!StringUtils.isEmpty(namespace)) {
            builder.namespace(namespace);
        }
        return builder.build();
    }
}
