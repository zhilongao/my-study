package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/25 9:40
 * @since v1.0.0001
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    EventAliasConfig eventAliasConfig;

    @PostMapping("/test")
    public String test(@RequestBody JSONObject object) {
        Map<String, String> alias = eventAliasConfig.getAlias();
        return object.toJSONString();
    }

    @GetMapping("/alias")
    public Map<String, String> alias() {
        return eventAliasConfig.getAlias();
    }
}
