package com.util.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TransportClientUtil {

    public static TransportClient getTransportClient(){
        Settings settings = Settings.builder()
                //.put("cluster.name","es-cluster") //设置集群的名字,默认是elasticsearch
                .put("client.transport.sniff",true) //启用监听器,每5秒刷新一次nodes
                .build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.186.128"),9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

}
