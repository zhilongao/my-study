package com.example.design.agent.jdk;

/**
 * 玩家角色接口
 * @author gaozhilong
 */
public class Player {

    private String name;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
