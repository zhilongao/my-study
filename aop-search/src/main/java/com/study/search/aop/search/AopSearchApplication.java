package com.study.search.aop.search;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopSearchApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(AopSearchApplication.class, args);
//    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("kuaidiCom", "shentong");
        map.put("sendAddress", "广东云浮市罗定市罗城街道");
        map.put("recAddress", "广东云浮市罗定市罗城街道");
        map.put("weight", "1");
        map.put("serviceType", "");
        System.err.println(JSONObject.toJSONString(map));
    }
}
