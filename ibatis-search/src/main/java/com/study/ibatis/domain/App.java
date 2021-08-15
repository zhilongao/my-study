package com.study.ibatis.domain;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;

public class App {

    private static SqlMapClient sqlMap;

    public static void main(String[] args) {
        try {
            Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
            sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
            sqlMap.startTransaction();
            Account account = new Account();
            account.setId(4);
            account.setFirstName("firstName:jack");
            account.setLastName("lastName:tom");
            account.setEmailAddress("2323@qq.com");
            sqlMap.insert("Account.insert", account);

            User u1 = new User();
            u1.setId(1);
            u1.setName("jack");
            sqlMap.insert("User.insert", u1);

            sqlMap.commitTransaction();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
