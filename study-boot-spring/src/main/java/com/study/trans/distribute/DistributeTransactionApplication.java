package com.study.trans.distribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/2/21 9:49
 * @since v1.0.0001
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass=true)
public class DistributeTransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributeTransactionApplication.class);
    }
}
