package com.study.search.aop.search.es.dao;

import com.study.search.aop.search.es.entity.SysUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<SysUser, Long> {

    List<SysUser> findByNickname(String nickName);

    List<SysUser> findByNicknameOrPassword(String nickName,String Password);
}
