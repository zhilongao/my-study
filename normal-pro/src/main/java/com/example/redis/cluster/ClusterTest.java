package com.example.redis.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/26 19:52
 * @since v1.0.0001
 */
public class ClusterTest {
    public static void main(String[] args) throws IOException {
        HostAndPort hostAndPort1 = new HostAndPort("10.192.39.1", 2307);
        HostAndPort hostAndPort2 = new HostAndPort("10.192.39.2", 2308);
        HostAndPort hostAndPort3 = new HostAndPort("10.192.39.3", 2309);
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(hostAndPort1);
        nodes.add(hostAndPort2);
        nodes.add(hostAndPort3);
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("gupao:cluster", "qingshan2673");
        System.out.println(cluster.get("gupao:cluster"));;
        cluster.close();
    }
}
