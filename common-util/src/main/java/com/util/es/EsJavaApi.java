package com.util.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.*;

public class EsJavaApi {

    private static TransportClient client = TransportClientUtil.getTransportClient();

    public static void main(String[] args) {
        Map<String, Object> source = new HashMap<>();
        source.put("name", "book2");
        source.put("price", 26);
        String index = "book";// 数据库
        String type = "book";// 表
        save(index, type, source);
    }

    /**
     * 新增
     * @param index index
     * @param type type
     * @param map map
     */
    private static void save(String index, String type, Map<String, Object> map){
        try {
            IndexResponse response = client.prepareIndex(index, type, "100")
                    .setSource(map)
                    .setSource()
                    .get();
            System.out.println(response.getResult()); //CREATED
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     */
    private static void search() {

    }
}
