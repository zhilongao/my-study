package com.study.transaction;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

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
        // 1. 加载spring上下文ApplicationContext
        // 2. 加载MyBatis的SqlSessionFactory
        // 3. 加载MyBatis的SqlSessionFactory中获取到的SqlSession
        // 4. 从SqlSession中获取指定的Mapper接口(代理类)
        // 5. 使用Mapper接口调用相关的方法
        String configLocation = "classpath:transaction/spring-simple1.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)context.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        List<UserInfo> userInfoList = userInfoMapper.selectByName("jack");
        for (UserInfo userInfo : userInfoList) {
            System.err.println(userInfo);
        }
        System.err.println("------------------------");
        int batchNum = 10;
        for (int i = 0; i < batchNum; i++) {
            UserInfo userInfo = new UserInfo();
            String name = "test:" + i;
            userInfo.setName(name);
            userInfoMapper.insert(userInfo);
        }
        sqlSession.rollback();
        System.err.println("------------------------");

    }

}
