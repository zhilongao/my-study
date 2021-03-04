package com.study.trans.local.service.impl;

import com.study.trans.common.entity.sys.SysUser;
import com.study.trans.common.mapper.sys.SysUserMapper;
import com.study.trans.local.service.SysUserService;
import com.study.trans.local.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    private SysUserMapper sysUserMapper;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TransactionService transactionService;

    @Override
    public String insert(SysUser sysUser) {
        SysUser u1 = new SysUser();
        u1.setId(11L);
        u1.setName("jack");
        SysUser u2 = new SysUser();
        u2.setId(12L);
        u2.setName("tom");
        // 普通方法调用1  事务未生效
        /*
        doInsert(u1);
        try {
            doInsert(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doInsert(u2);
        */
        // 事务生效方案一，获取当前类的代理类调用方法
        SysUserServiceImpl sysUserService = context.getBean(this.getClass());
        sysUserService.doInsert(u1);
        try {
            sysUserService.doInsert(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sysUserService.doInsert(u2);
        // 事务生效方案二，将事务方法放入到其他类中
        /*
        transactionService.doInsert(u1);
        try {
            transactionService.doInsert(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transactionService.doInsert(u2);
        */
        return "ok";
    }

    @Override
    @Transactional
    public String execute() {
        try {
            SysUser u1 = new SysUser();
            u1.setId(24L);
            u1.setName("test");
            sysUserMapper.insert(u1);
            if (u1.getId() == 24L) {
                throw new RuntimeException();
            }
        } catch (RuntimeException e) {
            throw e;
        }
        System.err.println("hello,world");
        return "hello";
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int doInsert(SysUser sysUser) {
        int insert = sysUserMapper.insert(sysUser);
        if (sysUser.getId() == 1L) {
            throw new RuntimeException();
        }
        return insert;
    }
}
