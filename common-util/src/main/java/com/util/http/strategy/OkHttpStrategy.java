package com.util.http.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class OkHttpStrategy extends BaseWorkStrategy {

    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        String reqUrl = extentUrl(url, params);
        Request.Builder reqBuilder = new Request.Builder()
                .url(reqUrl)
                .get();
        return execute(reqBuilder, headers);
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder reqBuilder = buildPostReqBuilder(url, params);
        if (reqBuilder != null) {
            return execute(reqBuilder, headers);
        } else {
            return "";
        }
    }

    @Override
    public void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        String reqUrl = extentUrl(url, params);
        Request.Builder reqBuilder = new Request.Builder()
                .url(reqUrl)
                .get();
        executeAsync(reqBuilder, headers);
    }

    @Override
    public void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        Request.Builder reqBuilder = buildPostReqBuilder(url, params);
        if (reqBuilder != null) {
            executeAsync(reqBuilder, headers);
        } else {
            System.err.println("execute error");
        }
    }

    public Request.Builder buildPostReqBuilder(String url, Map<String, Object> params) {
        String bodyStr = "";
        try {
            bodyStr = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyStr);
        return new Request.Builder()
                .url(url)
                .post(reqBody);
    }

    private String execute(Request.Builder reqBuilder, Map<String, String> headers) {
        addHeaders(reqBuilder, headers);
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody resBody = response.body();
            return resBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void executeAsync(Request.Builder reqBuilder, Map<String, String> headers) {
        addHeaders(reqBuilder, headers);
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.body().toString();
                System.err.println(message);
            }
        });
    }

    private void addHeaders(Request.Builder reqBuilder, Map<String, String> headers) {
        Set<String> headerKeys = headers.keySet();
        for (String key : headerKeys) {
            reqBuilder.addHeader(key, headers.get(key));
        }
    }
}
