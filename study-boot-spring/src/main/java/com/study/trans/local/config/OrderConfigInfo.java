package com.study.trans.local.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:08
 * @since v1.0.0001
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.order")
@Data
public class OrderConfigInfo {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifeTime;
    private int maxIdleTime;
    private int loginTimeout;
    private int maintenanceInterval;
    private int borrowConnectionTimeout;
    private String testQuery;
    private String uniqueResourceName;
}
