package com.util.es.v2;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;


public class EsClientUtil {

    private static final String host = "127.0.0.1";

    private static final int port = 9200;

    private static final String schema = "http";


    public static RestHighLevelClient getEsClient() {
        // 创建es客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, schema)));
        return client;
    }
}
