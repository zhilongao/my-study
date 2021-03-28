package com.example.thread.lock.task;

import com.example.thread.lock.AccountInfo;
import com.example.thread.lock.task.BaseTransferTask;

import java.util.concurrent.locks.Lock;

/**
 * 使用java的可重入锁的tryLock方法解决死锁问题
 *
 * @author gaozhilong
 * @date 2020/12/21 11:09
 * @since v1.0.0001
 */
public class TransferTaskV4 extends BaseTransferTask {

    private Lock fromLock;

    private Lock toLock;

    public TransferTaskV4() {

    }

    public TransferTaskV4(AccountInfo inAccount, AccountInfo outAccount, long money, Lock fromLock, Lock toLock) {
        super(inAccount, outAccount, money);
        this.fromLock = fromLock;
        this.toLock = toLock;
    }

    @Override
    protected void doWork() {
        for (;;) {
            if (fromLock.tryLock()) {
                try {
                    if (toLock.tryLock()) {
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
                            toLock.unlock();
                        }
                    }
                } finally {
                    fromLock.unlock();
                }

            }
        }
    }
}
