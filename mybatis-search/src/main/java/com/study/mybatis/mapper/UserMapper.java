package com.study.mybatis.mapper;

import com.study.mybatis.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * 查询用户列表
     * @return
     */
    List<User> selectList();
}
