package com.util.redis.jedis;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;


public class App {
    private static final String host = "192.168.222.130";
    private static final int port = 7291;


    public static void main(String[] args) throws IOException {
        App app = new App();
        app.simpleOpt();
        // app.clusterOpt();
    }


    private void simpleOpt() {
        String key1 = "key_001";
        String key2 = "key_002";
        String val1 = "val_001-0001";
        String val2 = "val_002-0001";
        RedisOpt opt = new RedisOpt(host, port);

        String res11 = opt.set(key1, val1);
        String res12 = opt.get(key1);
        System.err.println(res11 + " " + res12);

        String res21 = opt.set(key2, val2);
        String res22 = opt.get(key2);
        System.err.println(res21 + " " + res22);
    }

    private void clusterOpt() {
        String cmd1 = "cluster nodes\r\n";
        SimpleProtocol simpleProtocol = new SimpleProtocol(host, port);
        ClusterInfoUtil clusterInfoUtil = new ClusterInfoUtil();
        String result = null;
        try {
            result = simpleProtocol.execute(cmd1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NodeInfo> nodeInfo = clusterInfoUtil.getNodeInfo(result);
        String message = JSONObject.toJSONString(nodeInfo);
        System.err.println(message);
    }



}
