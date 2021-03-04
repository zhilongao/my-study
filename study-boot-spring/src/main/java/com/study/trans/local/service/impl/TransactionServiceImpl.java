package com.study.trans.local.service.impl;

import com.study.trans.common.entity.sys.SysUser;
import com.study.trans.common.mapper.sys.SysUserMapper;
import com.study.trans.local.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/2/21 13:57
 * @since v1.0.0001
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int doInsert(SysUser sysUser) {
        int insert = sysUserMapper.insert(sysUser);
        if (sysUser.getId() == 1L) {
            throw new RuntimeException();
        }
        return insert;
    }
}
