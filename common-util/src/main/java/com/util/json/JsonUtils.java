package com.util.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtils {

    private static int jsonType = 1;

    private static ObjectMapper mappers = new ObjectMapper();

    public static String toJson(Object obj) {
        String result = "";
        if (1 == jsonType) {
            return JSONObject.toJSONString(obj);
        } else if (2 == jsonType) {
            try {
                return mappers.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
