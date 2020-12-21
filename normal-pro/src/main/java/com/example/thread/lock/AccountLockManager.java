package com.example.thread.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理器，用于资源的统一分配管理
 *
 * @author gaozhilong
 * @date 2020/12/21 10:45
 * @since v1.0.0001
 */
public class AccountLockManager {

    public List<AccountInfo> resourceHolder = new ArrayList<>();

    public synchronized boolean tryLock (AccountInfo accountInfo1, AccountInfo accountInfo2) {
        if (resourceHolder.contains(accountInfo1) || resourceHolder.contains(accountInfo2)) {
            return false;
        }
        resourceHolder.add(accountInfo1);
        resourceHolder.add(accountInfo2);
        return true;
    }

    public synchronized boolean release(AccountInfo accountInfo1, AccountInfo accountInfo2) {
        resourceHolder.remove(accountInfo1);
        resourceHolder.remove(accountInfo2);
        return true;
    }
}
