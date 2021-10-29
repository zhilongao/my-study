package com.util.http.strategy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public class HutoolHttpStrategy extends BaseWorkStrategy {

    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        String extentUrl = extentUrl(url, params);
        HttpRequest request = HttpRequest.get(extentUrl);
        request.addHeaders(headers);
        HttpResponse response = request.execute();
        return response.body();
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);
        request.addHeaders(headers);
        try {
            request.body(mapper.writeValueAsString(params));
            HttpResponse execute = request.execute();
            return execute.body();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        String extentUrl = extentUrl(url, params);
        HttpRequest request = HttpRequest.get(extentUrl);
        request.addHeaders(headers);
        HttpResponse response = request.execute(true);

        response.body();
        // todo 处理响应结果
    }

    @Override
    public void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);
        request.addHeaders(headers);
        try {
            request.body(mapper.writeValueAsString(params));
            HttpResponse response = request.execute(true);
            // todo 处理响应结果
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
