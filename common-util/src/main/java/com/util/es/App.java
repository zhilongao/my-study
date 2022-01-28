package com.util.es;



import com.util.es.entity.User;
import com.util.es.v2.EsJavaApiOpt;
import com.util.es.v2.EsJavaApiSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) throws IOException {
       // v1();
       v2();
       // v3();
    }

//    private static void v1() {
//        EsJavaApiV1 apiV1 = new EsJavaApiV1();
//        String index = "book";// 数据库
//        String type = "book";// 表
//        String id = "100";//id
//        // 保存
//        Map<String, Object> addDoc = new HashMap<>();
//        addDoc.put("name", "book2");
//        addDoc.put("price", 26);
//        apiV1.save(index, type, id, addDoc);
//        // 检索
//        apiV1.search(index, type, id);
//        // 更新
//        Map<String, Object> upDoc = new HashMap<>();
//        upDoc.put("name", "book2_001");
//        upDoc.put("price", 58);
//        upDoc.put("author", "金庸");
//        apiV1.update(index, type, id, upDoc);
//        // 检索
//        apiV1.search(index, type, id);
//        // 删除
//        apiV1.delete(index, type, id);
//        // 检索
//        apiV1.search(index, type, id);
//    }


    private static void v2() throws IOException {
        EsJavaApiOpt api = new EsJavaApiOpt();
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
        // List<String> ids = new ArrayList<>();
        // ids.add("1d2ae4e9be284fdd9c1d64d7e9fbcc48");
        // ids.add("f696962a52d14e48879953371888a763");
        // api.deleteBatchDoc(index, ids);
    }

    private static void v3() throws IOException {
        String index = "user";
        EsJavaApiSearch api = new EsJavaApiSearch();
        // api.searchAll(index);
        // api.searchByCondition(index);
        // api.searchByPage(index);
        // api.searcBySort(index);
        // api.searcByFileter(index);
        // api.searcByCombine(index);
        api.searcByLike(index);
    }


}
