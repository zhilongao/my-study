package com.study.search.aop.search.es.dao;


import com.study.search.aop.search.es.entity.UserScore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserScoreRepository extends ElasticsearchRepository<UserScore, Long> {


}
