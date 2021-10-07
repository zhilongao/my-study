package com.open.api.feishu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public enum  FeiShuApi {

    INSTANE;

    String V3_TENANT_ACCESS_TOKEN = "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal";

    private String doPost(String appId, String appSecret, String url) {
        RestTemplate client = new RestTemplate();
        Map<String, String> body = new HashMap<>();
        body.put("app_id", appId);
        body.put("app_secret", appSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> exchange = client.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String respBody = exchange.getBody();
        System.err.println(respBody);
        return respBody;
    }
}
