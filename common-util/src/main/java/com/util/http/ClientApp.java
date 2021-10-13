package com.util.http;

import java.util.HashMap;
import java.util.Map;

import static com.util.http.SelectedHttpClient.StrategyType.STRATEGY_WEB_CLIENT;

public class ClientApp {
    public static void main(String[] args) {
        SelectedHttpClient client = new SelectedHttpClient();
        client.setStrategyReq(STRATEGY_WEB_CLIENT);

        Map<String, String> headers = new HashMap<>();
        headers.put("head1", "value1");
        headers.put("head2", "value2");
        headers.put("head3", "value3");

        String getUrl = "http://localhost:9527/test/get";
        String postUrl = "http://localhost:9527/test/post";

        Map<String, Object> params = new HashMap<>();
        params.put("name", "jack");
        String getResult = client.get(getUrl, headers, params);
        System.err.println("get result:" + getResult);

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("name", "tom");
        String postResult = client.post(postUrl, headers, reqBody);
        System.err.println("post result:" + postResult);
    }
}
