package com.study.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.InitializingBean;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 15:41
 * @since v1.0.0001
 */
public class SelfDataSource extends DruidDataSource implements InitializingBean {

    private String url;

    private String userName;

    private String passWord;

    private String driverClassName;


    public void afterPropertiesSet() throws Exception {
        setUrl(url);
        setUsername(userName);
        setPassword(passWord);
        setDriverClassName(driverClassName);
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
