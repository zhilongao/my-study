package com.study.search.aop.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.annotation.Log;
import com.study.search.aop.search.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {


    @Override
    @Log
    public List<String> findList(String customerId) {
        System.err.println("execute findList method");
        return Arrays.asList("111", "222", "333");
    }


    /*public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        // headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<String, String>();
        params.put("jack", "1");
        params.put("age", "11");
        String o = JSONObject.toJSONString(params);
        HttpEntity<String> httpEntity = new HttpEntity<String>(o, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/index/index1", HttpMethod.POST, httpEntity, String.class);
        System.err.print(response);
    }*/
}
