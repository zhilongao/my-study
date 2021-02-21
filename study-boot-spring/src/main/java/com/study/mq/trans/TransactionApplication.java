package com.study.mq.trans;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/2/21 9:49
 * @since v1.0.0001
 */
@SpringBootApplication
@MapperScan("com.study.mq.trans.mapper")
@EnableTransactionManagement(proxyTargetClass=true)
public class TransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class);
    }
}
