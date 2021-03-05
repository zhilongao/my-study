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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import springfox.documentation.annotations.ApiIgnore;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:06
 * @since v1.0.0001
 */
@Configuration
@MapperScan(basePackages = "com.study.trans.common.mapper.order", sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderDataSourceConfig {

    @Bean(value = "orderDataSource")
    @Primary
    public DataSource orderDataSource(@Autowired OrderConfig orderConfig) throws SQLException {
        // 1.创建Mysql XADataSource
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(orderConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(orderConfig.getPassword());
        mysqlXaDataSource.setUser(orderConfig.getUserName());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 2.将本地事务注册到Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(orderConfig.getUniqueResourceName());
        xaDataSource.setMinPoolSize(orderConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(orderConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(orderConfig.getMaxLifeTime());
        xaDataSource.setBorrowConnectionTimeout(orderConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(orderConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(orderConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(orderConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(orderConfig.getTestQuery());
        return xaDataSource;
    }

    @Bean("orderSqlSessionFactory")
    @Primary
    public SqlSessionFactory orderSqlSessionFactory(@ApiIgnore @Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/order/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("orderSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate orderSqlSessionTemplate(@Autowired @Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
