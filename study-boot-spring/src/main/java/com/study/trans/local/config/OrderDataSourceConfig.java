package com.study.trans.local.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 20:22
 * @since v1.0.0001
 */
@Configuration
@MapperScan(basePackages = "com.study.trans.common.mapper.order", sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderDataSourceConfig {

    @Primary
    @Bean(value = "orderDataSource")
    public DataSource orderDataSource(@Autowired OrderConfigInfo orderConfigInfo) {
        return DataSourceBuilder.create()
                .driverClassName(orderConfigInfo.getDriverClassName())
                .url(orderConfigInfo.getUrl())
                .username(orderConfigInfo.getUserName())
                .password(orderConfigInfo.getPassword())
                .build();
    }

    @Primary
    @Bean(value = "orderDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("orderDataSource") @Autowired DataSource orderDataSource) {
        return new DataSourceTransactionManager(orderDataSource);
    }

    @Primary
    @Bean(value = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource orderDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(orderDataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/order/*.xml");
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    @Primary
    @Bean("orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory orderSqlSessionFactory){
        return new SqlSessionTemplate(orderSqlSessionFactory);
    }
}
