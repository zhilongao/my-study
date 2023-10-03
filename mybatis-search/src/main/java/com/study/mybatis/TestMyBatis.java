package com.study.mybatis;

import com.study.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {
    @Test
    public void testSelectList() throws IOException {
        // 1.从classpath加载核心配置文件，构建SqlSession工厂对象
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        // 2.获取SqlSession对象
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3.执行SQL语句 根据要执行的SQL语句选择合适的API
            // p1：SQL语句唯一地址 (SQL映射文件的namespace值.SQL语句的id值)
            List<User> userList = sqlSession.selectList("userMapper.selectList");
            // 4.遍历数据
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
