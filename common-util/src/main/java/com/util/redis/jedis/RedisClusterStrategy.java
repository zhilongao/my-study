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
            String id = nodeMessArr[0];
            String[] hostAndPort = nodeMessArr[1].split(":");
            String host = hostAndPort[0];
            String port = hostAndPort[1];
            String flag = nodeMessArr[2];

            node.setId(id);
            node.setHost(host);
            node.setPort(port);
            node.setFlags(flag);
            list.add(node);
        }
        return list;
    }

}
