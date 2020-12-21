package com.example.thread.lock;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/21 11:04
 * @since v1.0.0001
 */
public class TransferTaskV3 extends BaseTransferTask {


    public TransferTaskV3() {

    }

    public TransferTaskV3(AccountInfo inAccount, AccountInfo outAccount, long money) {
        super(inAccount, outAccount, money);
    }

    @Override
    protected void doWork() {
        for (;;) {
            AccountInfo left;
            AccountInfo right;
            if (inAccount.hashCode() > outAccount.hashCode()) {
                left = inAccount;
                right = outAccount;
            } else {
                left = outAccount;
                right = inAccount;
            }
            synchronized (left) {
                synchronized (right) {
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
