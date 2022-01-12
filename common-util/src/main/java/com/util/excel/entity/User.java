package com.util.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class User extends BaseRowModel {
    @ExcelProperty(value = "用户id")
    private Integer userId;
    @ExcelProperty(value = "用户名")
    private String userName;
    @ExcelProperty(value = "年龄")
    private Integer age;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
