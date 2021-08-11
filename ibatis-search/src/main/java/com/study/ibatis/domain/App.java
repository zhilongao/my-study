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
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
