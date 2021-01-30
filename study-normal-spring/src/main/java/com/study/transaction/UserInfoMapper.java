package com.study.transaction;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 16:31
 * @since v1.0.0001
 */
public interface UserInfoMapper {

    List<UserInfo> selectByName(@Param("name") String name);

    int insert(UserInfo userInfo);
}
