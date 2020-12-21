package com.example.thread.lock;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/21 9:56
 * @since v1.0.0001
 */
public class AccountInfo {

    private String countName;

    private long money;

    public AccountInfo() {

    }

    public AccountInfo(String countName, long money) {
        this.countName = countName;
        this.money = money;
    }

    public boolean transferIn(long inMoney) {
        this.money += inMoney;
        return true;
    }

    public boolean transferOut(long outMoney) {
        this.money -= outMoney;
        return true;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
