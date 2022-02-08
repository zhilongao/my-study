package com.util.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoClientConfig {

    private static final String host = "localhost";

    private static final int port = 27017;

    public static MongoClient getClient() {
        return new MongoClient( host, port);
    }

    public static MongoDatabase getDb(String dbName) {
        MongoClient client = getClient();
        return client.getDatabase(dbName);
    }

}
