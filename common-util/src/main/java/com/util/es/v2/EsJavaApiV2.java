package com.util.es.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.es.entity.User;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class EsJavaApiV2 {

    public static void main(String[] args) throws Exception {
        EsJavaApiV2 api = new EsJavaApiV2();

        String index = "user";

        //api.createIndex(index);
        //api.delIndex(index);
        //api.queryIndex(index);

        String id = "10900";
        User u1 = new User();
        u1.setAge(18);
        u1.setGener("男");
        u1.setName("jack009");
        //api.createDoc(index, id, u1);

        //api.updateDoc(index, id);

        //api.queryDoc(index, id);
        //api.deleteDoc(index, id);
        //api.queryDoc(index, id);


        // batch opt

        /*
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setName("用户:" + i);
            u.setAge(i + 10);
            u.setGener("女");
            u.setId(UUID.randomUUID().toString().replace("-", ""));
            users.add(u);
        }
        api.createBatchDoc(index, users);
        */

        List<String> ids = new ArrayList<>();
        ids.add("1d2ae4e9be284fdd9c1d64d7e9fbcc48");
        ids.add("f696962a52d14e48879953371888a763");
        api.deleteBatchDoc(index, ids);
    }


    /**
     * 索引创建
     * @throws IOException
     */
    public void createIndex(String index) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        CreateIndexRequest req = new CreateIndexRequest(index);
        CreateIndexResponse response = client.indices().create(req, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.err.println("索引操作:" + acknowledged);
        client.close();
    }

    /**
     * 查询索引
     * @param index index
     * @throws IOException
     */
    public void queryIndex(String index) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        GetIndexRequest req = new GetIndexRequest(index);
        GetIndexResponse response = client.indices().get(req, RequestOptions.DEFAULT);
        Map<String, List<AliasMetadata>> aliases = response.getAliases();
        Map<String, Settings> settings = response.getSettings();
        Map<String, MappingMetadata> mappings = response.getMappings();
        System.err.println(aliases);
        System.err.println(settings);
        System.err.println(mappings);
        client.close();
    }

    /**
     * 删除索引
     * @param index index
     * @throws IOException
     */
    public void delIndex(String index) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        DeleteIndexRequest req = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices().delete(req, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.err.println("删除索引:" + acknowledged);
        client.close();
    }


    /**
     * 创建文档
     */
    public void createDoc(String index, String id, User user) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        ObjectMapper mapper = new ObjectMapper();
        String souce = mapper.writeValueAsString(user);
        IndexRequest req = new IndexRequest();
        req.index(index).id(id).source(souce, XContentType.JSON);
        IndexResponse response = client.index(req, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        System.err.println("创建文档:" + result);
        client.close();
    }

    /**
     * 创建文档(批量)
     * @param index index
     * @param users users
     * @throws IOException
     */
    public void createBatchDoc(String index, List<User> users) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        ObjectMapper mapper = new ObjectMapper();
        // 批量插入数据
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String source = mapper.writeValueAsString(user);
            request.add(new IndexRequest().index(index).id(user.getId())
                    .source(source, XContentType.JSON));
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());
        client.close();
    }

    /**
     * 更新文档
     */
    public void updateDoc(String index, String id) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        UpdateRequest req = new UpdateRequest();
        req.index(index).id(id);
        req.doc(XContentType.JSON, "sex", "女");
        UpdateResponse response = client.update(req, RequestOptions.DEFAULT);
        System.err.println("更新文档:" + response.getResult());
        client.close();
    }

    /**
     * 查询文档
     * @param index index
     * @param id id
     */
    public void queryDoc(String index, String id) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        GetRequest req = new GetRequest();
        req.index(index).id(id);
        GetResponse response = client.get(req, RequestOptions.DEFAULT);
        System.err.println("查询文档:" + response.getSourceAsString());
        client.close();
    }


    /**
     * 删除文档
     * @param index index
     * @param id id
     * @throws IOException
     */
    public void deleteDoc(String index, String id) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        DeleteRequest request = new DeleteRequest();
        request.index(index).id(id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        client.close();
    }

    /**
     * 删除文档(批量)
     * @param index index
     * @param ids ids
     * @throws IOException
     */
    public void deleteBatchDoc(String index, List<String> ids) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        // 批量删除数据
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < ids.size(); i++) {
            request.add(new DeleteRequest().index(index).id(ids.get(i)));
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        System.out.println(response.getItems());
        client.close();
    }


}
