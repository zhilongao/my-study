package com.util.collect;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppClient {

    public static void main(String[] args) {
        multiMap();
    }

    // 复杂集合的操作
    public static void multiMap() {
        Map<String, MultiValueMap<String, String>> cache = new HashMap<>();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("001", "1");
        map.add("001", "2");

        map.add("002", "val_1");
        map.add("002", "val_2");

        List<String> values1 = map.get("001");
        List<String> values2 = map.get("002");

        System.err.println(values1);
        System.err.println(values2);
    }
}
