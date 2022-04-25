package com.charles.dubbo.test.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.StringUtils;

/**
 * zk连接工具类
 */
public class CuratorUtil {

    public static CuratorFramework curatorFramework() {
        return curatorFramework(null);
    }

    public static CuratorFramework curatorFramework(String namespace) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().
                connectString(DefaultConfigInfo.ZK_URL).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3));
        if (!StringUtils.isEmpty(namespace)) {
            builder.namespace(namespace);
        }
        return builder.build();
    }
}
