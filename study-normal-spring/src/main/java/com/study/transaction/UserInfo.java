package com.study.transaction;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/30 16:31
 * @since v1.0.0001
 */
public class UserInfo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
