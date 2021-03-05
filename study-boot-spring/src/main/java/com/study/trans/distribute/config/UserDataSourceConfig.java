package com.study.trans.distribute.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:20
 * @since v1.0.0001
 */
@Configuration
@MapperScan(basePackages = "com.study.trans.common.mapper.user", sqlSessionTemplateRef = "userSqlSessionTemplate")
public class UserDataSourceConfig {

    @Bean("userDataSource")
    public DataSource userDataSource(@Autowired UserConfig userConfig) throws SQLException {
        // 1、创建Mysql XADataSource
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(userConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(userConfig.getPassword());
        mysqlXaDataSource.setUser(userConfig.getUserName());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 2、将本地事务注册到 Atomikos 全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(userConfig.getUniqueResourceName());
        xaDataSource.setMinPoolSize(userConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(userConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(userConfig.getMaxLifeTime());
        xaDataSource.setBorrowConnectionTimeout(userConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(userConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(userConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(userConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(userConfig.getTestQuery());
        return xaDataSource;
    }

    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/user/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(@Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
