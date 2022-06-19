package com.study.datasource.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.datasource.datasource.DataSourceNames;
import com.study.datasource.datasource.TargetDataSource;
import com.study.datasource.entity.SysUser;
import com.study.datasource.mapper.SysUserMapper;
import com.study.datasource.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 在切面中，如果没有指定，就使用第一个数据源
     * @param id
     * @return
     */
    @Override
    public SysUser findUserByFirstDb(long id) {
        return this.baseMapper.selectById(id);
    }

    @TargetDataSource(name = DataSourceNames.SECOND)
    @Override
    public SysUser findUserBySecondDb(long id) {

        return this.baseMapper.selectById(id);
    }

}
