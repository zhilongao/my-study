package com.study.trans.common.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.trans.distribute.entity.user.UserInfo;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/3/4 15:26
 * @since v1.0.0001
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    int saveUser(UserInfo userInfo);
}
