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
        HostAndPort hostAndPort1 = new HostAndPort("192.168.43.4", 7291);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.43.4", 7292);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.43.4", 7293);
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(hostAndPort1);
        nodes.add(hostAndPort2);
        nodes.add(hostAndPort3);
        JedisCluster cluster = new JedisCluster(nodes);
        System.err.println("---------------->");
        for (int i = 0; i < 10; i++) {
            cluster.set("message" + i, "test-info" + i);
        }
        System.err.println("---------------->");
        for (int i = 0; i < 10; i++) {
            cluster.set("{misss}"+i, "test-info" + i);
        }
        System.err.println("---------------->");
        cluster.close();
    }
}
