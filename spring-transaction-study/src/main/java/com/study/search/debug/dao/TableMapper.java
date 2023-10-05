package com.study.search.debug.dao;

import com.study.search.debug.entity.TableEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TableMapper {

    @Insert("INSERT INTO tablea(id, name) " +
            "VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTableA(TableEntity tableEntity);

    @Insert("INSERT INTO tableb(id, name) " +
            "VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTableB(TableEntity tableEntity);

}
