package com.util.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;


public class AppClient {

    public static void main(String[] args) {
        // testDb();
        // testCollect();
        // testSave();
        // testQuery();
        testByPage();
    }

    // 数据库
    public static void testDb() {
        MongoClient client = MongoClientConfig.getClient();
        MongoIterable<String> dbNames = client.listDatabaseNames();
        MongoCursor<String> iterator = dbNames.iterator();
        while (iterator.hasNext()) {
            System.err.println(iterator.next());
        }
    }

    // 数据库-集合
    public static void testCollect() {
        MongoClient client = MongoClientConfig.getClient();
        MongoIterable<String> dbNames = client.listDatabaseNames();
        MongoCursor<String> iterator = dbNames.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.err.println(next);
            MongoCursor<String> iterator1 = client.getDatabase(next).listCollectionNames().iterator();
            while (iterator1.hasNext()) {
                System.err.println("\t" + iterator1.next());
            }
        }
    }

    // 数据库-集合-存储
    public static void testSave() {
        String dbName = "test1";
        String collName = "coll1";
        MongoCollection<Document> collection = findColl(dbName, collName);
        List<Document> docs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Document doc = new Document();
            doc.put("id", i);
            doc.put("name", "name" + i);
            if (i % 3 == 0) {
                doc.put("age", i);
            }
            // 插入单条
            // collection.insertOne(doc);
            docs.add(doc);
        }
        // 插入多条
        collection.insertMany(docs);
    }

    // 数据库-集合-查询
    public static void testQuery() {
        String dbName = "test1";
        String collName = "coll1";
        MongoCollection<Document> collection = findColl(dbName, collName);
        // 查询过滤器
        Bson eq = Filters.and(
                Filters.lt("id", 5),
                Filters.eq("age", 3)
        );
        // MongoCursor<Document> iterator = collection.find().iterator();
        MongoCursor<Document> iterator = collection.find(eq).iterator();
        print(iterator);
    }

    // 分页查询
    public static void testByPage() {
        String dbName = "test1";
        String collName = "coll1";
        MongoCollection<Document> collection = findColl(dbName, collName);
        int pageNo = 5;
        int pageSize = 2;
        Bson orderBy = new BasicDBObject("_id", 1);
        long count = collection.count();
        MongoCursor<Document> iterator = collection.find().sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
        System.err.println("总数:" + count);
        print(iterator);
    }


    public static MongoCollection<Document> findColl(String dbName, String collName) {
        return MongoClientConfig.getDb(dbName).getCollection(collName);
    }

    private static void print(MongoCursor<Document> iterator) {
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            System.err.println(JSONObject.toJSON(doc));
        }
    }


}
