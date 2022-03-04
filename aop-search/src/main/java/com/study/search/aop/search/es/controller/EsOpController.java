package com.study.search.aop.search.es.controller;

import com.study.search.aop.search.es.dao.UserRepository;
import com.study.search.aop.search.es.dao.UserScoreRepository;
import com.study.search.aop.search.es.entity.SysUser;
import com.study.search.aop.search.es.entity.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/es")
public class EsOpController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserRepository repository;

    @GetMapping("/createIndex")
    public boolean createIndex(@RequestParam(name = "indexName") String indexName) {
        return elasticsearchTemplate.createIndex(indexName);
        // return elasticsearchTemplate.createIndex(SysUser.class);
    }

    @GetMapping("/deleteIndex")
    public boolean deleteIndex(@RequestParam(name = "indexName") String indexName) {
        return elasticsearchTemplate.deleteIndex(indexName);
        // return elasticsearchTemplate.deleteIndex(SysUser.class);
    }

    @PostMapping("/save")
    public SysUser save(@RequestBody SysUser user) {
        return repository.save(user);
    }

    @PostMapping("/saveAll")
    public Iterable<SysUser> saveAll(@RequestBody List<SysUser> users) {
        return repository.saveAll(users);
    }

    @PostMapping("/findAll")
    public Iterable<SysUser> findAll() {
        return repository.findAll();
    }

    @PostMapping("/findAllAndSort")
    public Iterable<SysUser> findAllAndSort(){
        return repository.findAll(Sort.by("password").ascending());
    }

    @Autowired
    private UserScoreRepository userScoreRepository;

    @PostMapping("/saveScore")
    public UserScore saveScore(@RequestBody UserScore user) {
        return userScoreRepository.save(user);
    }

}
