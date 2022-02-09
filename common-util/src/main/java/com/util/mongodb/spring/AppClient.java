package com.util.mongodb.spring;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

public class AppClient {

    public static void main(String[] args) {
        String _id = "62020240f77e9351a17d41cb";
        String collectName = "coll1";
        findOne(_id, collectName);
    }


    public static void findOne(String _id, String collectName) {
        MongoTemplate template = MongoConfiguration.getTemplate();
        Criteria criteria = new Criteria();
        criteria.and("_id").is(_id);
        Query query = new Query();
        query.addCriteria(criteria);
        query.fields().include("_id");
        query.fields().include("id");
        query.fields().include("name");
        Map one = template.findOne(query, Map.class, collectName);
        System.err.println(one);
    }
}
