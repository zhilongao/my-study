package com.study.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private MyBatisUtils() {

    }

    private static volatile SqlSessionFactory SQL_SESSION_FACTORY = null;

    /**
     * 获取SqlSessionFactory对象
     * @return
     * @throws IOException
     */
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        if (SQL_SESSION_FACTORY == null) {
            synchronized (MyBatisUtils.class) {
                if (SQL_SESSION_FACTORY == null) {
                    InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
                    SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(is);
                }
            }
        }

        return SQL_SESSION_FACTORY;
    }

    public static SqlSession openSession() throws IOException {
        return getSqlSessionFactory().openSession();
    }
}
