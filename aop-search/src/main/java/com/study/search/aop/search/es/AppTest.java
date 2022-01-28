package com.study.search.aop.search.es;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

public class AppTest {

    public static void main(String[] args) {
        String message = "AD_INFO_%s";
        String format = String.format(message, "1001");
        System.err.println(format);
    }

}
