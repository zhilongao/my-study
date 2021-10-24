package com.util.http.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.util.http.BaseWorkStrategy;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class OkHttpStrategy extends BaseWorkStrategy {


    // OkHttpClient
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        String reqUrl = extentUrl(url, params);
        Request.Builder reqBuilder = new Request.Builder()
                .url(reqUrl)
                .get();
        Set<String> headerKeys = headers.keySet();
        for (String key : headerKeys) {
            reqBuilder.addHeader(key, headers.get(key));
        }
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            String message = body.string();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        String bodyStr = "";
        try {
            bodyStr = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyStr);
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(reqBody);
        Set<String> headerKeys = headers.keySet();
        for (String key : headerKeys) {
            reqBuilder.addHeader(key, headers.get(key));
        }
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody resBody = response.body();
            String message = resBody.string();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
