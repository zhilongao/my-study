package com.study.trans.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 17:48
 * @since v1.0.0001
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = "com.study.trans.local")
@EnableTransactionManagement(proxyTargetClass=true)
public class LocalTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalTransactionApplication.class);
    }
}
