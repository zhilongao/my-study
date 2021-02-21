package com.study.mq.trans.service;

import com.study.mq.trans.entity.SysUser;
import com.study.mq.trans.mapper.SysUserMapper;
import com.study.mq.trans.service.impl.SysUserService;
import com.study.mq.trans.service.impl.TestService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/2/21 10:24
 * @since v1.0.0001
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private TestService testService;

    @Override
    public String insert(SysUser sysUser) {
        // 事务生效
        // SysUserServiceImpl sysUserService  = (SysUserServiceImpl)AopContext.currentProxy();
        // sysUserService.insert1(sysUser);
        SysUser u1 = new SysUser();
        u1.setId(10L);
        u1.setName("tom");
        testService.insert(u1);
        // 事务不生效
        testService.insert(sysUser);
        return "ok";
    }
}
