package com.util.redis.jedis;


import java.util.ArrayList;
import java.util.List;

/**
 * redis cluster策略
 */
public class RedisClusterStrategy {


    public List<NodeInfo> getNodeInfo(String message) {
        System.err.println("集群原始信息:");
        System.err.println(message);
        String[] nodeArray = message.split("\n");
        List<NodeInfo> list = new ArrayList<>();
        for (String nodeMess : nodeArray) {
            String[] nodeMessArr = nodeMess.split(" ");
            if (nodeMessArr.length <= 1) {
                continue;
            }
            NodeInfo node = new NodeInfo();
            node.setId(nodeMessArr[0]);
            node.setHost(nodeMessArr[1]);
            node.setFlags(nodeMessArr[2]);
            list.add(node);
        }
        return list;
    }

}
