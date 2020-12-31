package com.example.redis.cluster;

import com.example.redis.CommonConstant;
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


    public static void main(String[] args) {
        dataOpt();
    }

    public static Set<HostAndPort> addressParser() {
        String[] addresses = CommonConstant.clusterAddress.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String address : addresses) {
            String[] hostAndPortArr = address.split(":");
            String host = hostAndPortArr[0];
            Integer port = Integer.parseInt(hostAndPortArr[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            nodes.add(hostAndPort);
        }
        return nodes;
    }

    public static boolean dataOpt() {
        Set<HostAndPort> nodes = addressParser();
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
        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
