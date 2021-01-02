package com.example.zk.register;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class LoadBalance {

    public String selectHost(List<String> hosts) {
        System.err.println("负载均衡选择");
        System.err.println(JSONObject.toJSON(hosts));
        if (hosts == null) {
            return "";
        }
        if (hosts.size() == 1) {
            return hosts.get(0);
        }
        return doSelectHost(hosts);
    }

    protected String doSelectHost(List<String> hosts) {
        return hosts.get(0);
    }
}
