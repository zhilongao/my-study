package com.util.redis.jedis;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;


public class App {
    public static void main(String[] args) throws IOException {
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        ClusterInfoUtil clusterInfoUtil = new ClusterInfoUtil();
        String cmd = "cluster nodes\r\n";
        String host = "192.168.37.130";
        int port = 7291;
        try {
            String result = simpleProtocol.execute(cmd, host, port);
            List<NodeInfo> nodeInfo = clusterInfoUtil.getNodeInfo(result);
            String message = JSONObject.toJSONString(nodeInfo);
            System.err.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
