package com.study.search.aop.search;

import org.json.JSONObject;

public class test2 {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("type", 1);
        obj.put("val", 12);


        int type = obj.optInt("type");
        int type1 = obj.optInt("type1", -1);
        System.err.println(type);
        System.err.println(type1);
    }
}
