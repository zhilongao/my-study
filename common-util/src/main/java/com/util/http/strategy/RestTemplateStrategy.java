package com.util.http.strategy;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

public class RestTemplateStrategy extends BaseWorkStrategy {

    RestTemplate template = new RestTemplate();

    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params) {
        String reqUrl = extentUrl(url, params);
        HttpHeaders httpHeaders = createHttpHeaders(headers);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = template.exchange(reqUrl, HttpMethod.GET, httpEntity, String.class);
        return response.getBody();
    }

    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpHeaders httpHeaders = createHttpHeaders(headers);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    @Override
    public void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params) {

    }

    @Override
    public void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params) {

    }

    private HttpHeaders createHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httpHeaders.add(key, headers.get(key));
        }
        return httpHeaders;
    }
}
