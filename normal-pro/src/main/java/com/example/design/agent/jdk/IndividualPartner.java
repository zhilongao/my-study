package com.example.design.agent.jdk;

/**
 * 个人陪玩角色
 * @author gaozhilong
 */
public class IndividualPartner implements Partner{

    private String name;

    public IndividualPartner() {

    }

    public IndividualPartner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void receiveMoney(int money) {
        System.err.println("陪玩" + this.name + "收到佣金:" + money + "元");
    }

    @Override
    public void playWith(Player player) {
        System.err.println("陪玩" + this.name + " 与" + player.getName() + "一起愉快的玩耍!");
    }
}
