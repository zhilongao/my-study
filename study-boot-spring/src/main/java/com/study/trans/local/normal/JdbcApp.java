package com.study.trans.local.normal;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcApp {

    public static final String url = "jdbc:mysql://192.168.31.203:3306/ds0?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";

    public static final String userName = "root";

    public static final String passWord = "123456";

    public static void main(String[] args) {

    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userName, passWord);
            conn.setAutoCommit(false);
            // todo 事务sql语句

            conn.commit();
            // conn.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
