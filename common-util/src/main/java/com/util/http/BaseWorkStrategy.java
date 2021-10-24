package com.util.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;

public abstract class BaseWorkStrategy {

    protected ObjectMapper mapper = new ObjectMapper();


    protected abstract String doGet(String url, Map<String, String> headers, Map<String, Object> params);


    protected abstract String doPost(String url, Map<String, String> headers, Map<String, Object> params);

    protected String extentUrl(String url, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder(url);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (builder.indexOf("?") != -1) {
                builder.append("&");
            } else {
                builder.append("?");
            }
            builder.append(key).append("=").append(params.get(key));
        }
        return builder.toString();
    }

}
