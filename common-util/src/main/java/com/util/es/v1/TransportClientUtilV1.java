//package com.util.es.v1;
//
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//public class TransportClientUtilV1 {
//
//    private static final String host = "127.0.0.1";
//
//    private static final int port = 9300;
//
//    public static TransportClient getTransportClient(){
//        Settings settings = Settings.builder()
//                //.put("cluster.name","es-cluster") //设置集群的名字,默认是elasticsearch
//                .put("client.transport.sniff",true) //启用监听器,每5秒刷新一次nodes
//                .build();
//        TransportClient client = null;
//        try {
//            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return client;
//    }
//
//}
