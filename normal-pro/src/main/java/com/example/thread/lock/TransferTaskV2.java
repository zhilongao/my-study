package com.example.thread.lock;

/**
 * 使用资源管理器管理资源，解决死锁问题
 *
 * @author gaozhilong
 * @date 2020/12/21 10:46
 * @since v1.0.0001
 */
public class TransferTaskV2 extends BaseTransferTask {

    private AccountLockManager lockManager;

    public TransferTaskV2() {

    }

    public TransferTaskV2(AccountInfo inAccount, AccountInfo outAccount, long money, AccountLockManager lockManager) {
        super(inAccount, outAccount, money);
        this.lockManager = lockManager;
    }

    @Override
    public void doWork() {
        for (;;) {
            if (lockManager.tryLock(outAccount, inAccount)) {
                try {
                    outAccount.transferOut(money);
                    inAccount.transferIn(money);
                    String outCountName = outAccount.getCountName();
                    long outAllMoney = outAccount.getMoney();
                    String inCountName = inAccount.getCountName();
                    long inAllMoney = inAccount.getMoney();
                    System.err.printf("%s 转出 %s, 总金额: %s   %s 转入 %s, 总金额: %s", outCountName, money, outAllMoney, inCountName, money, inAllMoney);
                    System.err.println();
                } finally {
                    lockManager.release(outAccount, inAccount);
                }
            }
        }
    }
}
