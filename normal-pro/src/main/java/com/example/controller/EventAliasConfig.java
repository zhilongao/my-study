package com.example.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/25 13:34
 * @since v1.0.0001
 */
@Configuration
@ConfigurationProperties(prefix = "event.config")
@PropertySource("classpath:application.properties")
public class EventAliasConfig {

    private Map<String, String> alias = new HashMap<>();

    public Map<String, String> getAlias() {
        return alias;
    }

    public void setAlias(Map<String, String> alias) {
        this.alias = alias;
    }
}
