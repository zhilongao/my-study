package com.example.thread.lock;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/21 10:54
 * @since v1.0.0001
 */
public class BaseTransferTask implements Runnable {

    protected AccountInfo inAccount;

    protected AccountInfo outAccount;

    protected long money;

    public BaseTransferTask() {

    }

    public BaseTransferTask(AccountInfo inAccount, AccountInfo outAccount, long money) {
        this.inAccount = inAccount;
        this.outAccount = outAccount;
        this.money = money;
    }

    @Override
    public void run() {
        work();
    }

    public void work() {
        doWork();
    }

    protected void doWork() {}

    public AccountInfo getInAccount() {
        return inAccount;
    }

    public void setInAccount(AccountInfo inAccount) {
        this.inAccount = inAccount;
    }

    public AccountInfo getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(AccountInfo outAccount) {
        this.outAccount = outAccount;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
