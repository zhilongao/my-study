package com.util.json;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

    public static String toJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }

}
