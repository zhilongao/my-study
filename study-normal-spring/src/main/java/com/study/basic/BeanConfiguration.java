package com.study.basic;

import com.study.extend.SelfInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/30 10:20
 * @since v1.0.0001
 */
@Configuration
public class BeanConfiguration {

    @Bean
    @Lazy
    public SearchService searchService() {
        return new SearchService();
    }

    @Bean
    public InstantiationAwareBeanPostProcessor postProcessor() {
        InstantiationAwareBeanPostProcessor postProcessor = new SelfInstantiationAwareBeanPostProcessor();
        return postProcessor;
    }
}
