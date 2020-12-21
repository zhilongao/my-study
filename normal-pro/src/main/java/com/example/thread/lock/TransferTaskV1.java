package com.example.thread.lock;

/**
 * 模拟普通死锁任务
 *
 * @author gaozhilong
 * @date 2020/12/21 10:48
 * @since v1.0.0001
 */
public class TransferTaskV1 extends BaseTransferTask {

    public TransferTaskV1() {

    }

    public TransferTaskV1(AccountInfo inAccount, AccountInfo outAccount, long money) {
        super(inAccount, outAccount, money);
    }


    @Override
    public void doWork() {
        while (true) {
            synchronized (outAccount) {
                synchronized (inAccount) {
                    long money = 10L;
                    outAccount.transferOut(money);
                    inAccount.transferIn(money);
                    String outCountName = outAccount.getCountName();
                    long outAllMoney = outAccount.getMoney();
                    String inCountName = inAccount.getCountName();
                    long inAllMoney = inAccount.getMoney();
                    System.err.printf("%s 转出 %s, 总金额: %s   %s 转入 %s, 总金额: %s", outCountName, money, outAllMoney, inCountName, money, inAllMoney);
                    System.err.println();
                }
            }
        }
    }
}
