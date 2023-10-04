package com.study.mybatis;

import com.github.pagehelper.PageHelper;
import com.study.mybatis.mapper.UserMapper;
import com.study.mybatis.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestMyBatis {

    @Test
    public void testSelectListV1() throws IOException {
        try (SqlSession sqlSession = MyBatisUtils.openSession()){
            // 执行sql语句
            //List<User> userList = sqlSession.selectList("userMapper.selectList");
            List<User> userList = sqlSession.selectList("com.study.mybatis.mapper.UserMapper.selectList");
            // 遍历数据
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectListV2() {
        try (SqlSession sqlSession = MyBatisUtils.openSession()){
            // 获取mapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 查询数据
            List<User> userList = userMapper.selectList();
            // 遍历数据
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPageHelper() {
        try (SqlSession sqlSession = MyBatisUtils.openSession()){
            //设置分⻚参数
            PageHelper.startPage(2, 2);
            // 获取mapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 查询数据
            List<User> userList = userMapper.selectList();
            // 遍历数据
            userList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
