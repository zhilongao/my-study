package com.util.mongodb.spring;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

public class MongoConfiguration {

    private static final String host = "127.0.0.1";

    private static final int port = 27017;

    private static final String dbName = "test1";

    public static MongoTemplate getTemplate() {
        ServerAddress address = new ServerAddress(host, port);
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClient mongoClient = new MongoClient(address, builder.build());
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, dbName);
        return new MongoTemplate(factory);
    }
}
