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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 20:23
 * @since v1.0.0001
 */
@Configuration
@MapperScan(basePackages = "com.study.trans.common.mapper.user", sqlSessionFactoryRef = "userSqlSessionFactory")
public class UserDataSourceConfig {

    @Bean(value = "userDataSource")
    public DataSource userDataSource(@Autowired UserConfigInfo userConfigInfo) {
        return DataSourceBuilder.create()
                .driverClassName(userConfigInfo.getDriverClassName())
                .url(userConfigInfo.getUrl())
                .username(userConfigInfo.getUserName())
                .password(userConfigInfo.getPassword())
                .build();
    }

    @Bean(value = "userDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("userDataSource") @Autowired DataSource userDataSource) {
        return new DataSourceTransactionManager(userDataSource);
    }

    @Bean(value = "userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource userDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(userDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/user/*.xml"));
        return bean.getObject();
    }

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(@Qualifier("userSqlSessionFactory") SqlSessionFactory userSqlSessionFactory){
        return new SqlSessionTemplate(userSqlSessionFactory);
    }
}
