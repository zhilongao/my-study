package com.util.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.*;

/**
 * es版本 6.3.1
 * api版本 6.3.1
 */
public class EsJavaApiV1 {

    private static TransportClient client = TransportClientUtil.getTransportClient();

    /**
     * 新增文档
     * @param index index
     * @param type type
     * @param id id
     * @param doc doc
     */
    public void save(String index, String type, String id, Map<String, Object> doc){
        try {
            IndexResponse response = client.prepareIndex(index, type, id)
                    .setSource(doc)
                    .get();
            System.out.println(response.getResult()); //CREATED
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询文档
     * @param index index
     * @param type type
     * @param id id
     */
    public void search(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id)
                .get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 更新文档
     * @param index index
     * @param type type
     * @param id id
     */
    public void update(String index, String type, String id, Map<String, Object> doc) {
        UpdateRequest req = new UpdateRequest();
        req.index(index);
        req.type(type);
        req.id(id);
        req.doc(doc);
        try {
            UpdateResponse response = client.update(req).get();
            System.err.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文档
     * @param index index
     * @param type type
     * @param id id
     */
    public void delete(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id)
                .get();
        System.err.println(response);
    }
}
