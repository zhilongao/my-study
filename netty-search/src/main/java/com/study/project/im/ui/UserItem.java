package com.study.project.im.ui;

import lombok.Data;

@Data
public class UserItem {

    private String userId;
    private String userName;

    @Override
    public String toString() {
        return this.getUserName();
    }
}
