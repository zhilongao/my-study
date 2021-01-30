package com.study.transaction;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 15:35
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:transaction/spring-simple1.xml");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> list = userInfoMapper.selectByName("jack");
        System.err.println("over");
    }
}
