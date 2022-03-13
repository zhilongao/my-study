package com.util.redis.jedis;

import java.util.List;

/**
 * 获取redis的集群信息
 */
public class ClusterInfoUtil {

    public static final String STRATEGY_REDIS_CLUSTER = "RedisCluster";

    public static final String STRATEGY = STRATEGY_REDIS_CLUSTER;

    private RedisClusterStrategy redisClusterStrategy = new RedisClusterStrategy();

    public List<NodeInfo> getNodeInfo(String message) {
        if (STRATEGY.equals(STRATEGY_REDIS_CLUSTER)) {
            return redisClusterStrategy.getNodeInfo(message);
        }
        return null;
    }

}
