package com.util.http.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.http.common.BatchReq;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseWorkStrategy {

    protected ObjectMapper mapper = new ObjectMapper();


    public abstract String doGet(String url, Map<String, String> headers, Map<String, Object> params);


    public abstract String doPost(String url, Map<String, String> headers, Map<String, Object> params);


    public abstract void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params);

    public abstract void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params);

    public void doGetAsyncBatch(List<BatchReq> reqs) {
        throw new RuntimeException("not support!");
    }

    public void doPostAsyncBatch(List<BatchReq> reqs) {
        throw new RuntimeException("not support!");
    }


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
