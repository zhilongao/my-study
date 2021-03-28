package com.example.thread.lock;

import com.example.thread.lock.task.TransferTaskV1;
import com.example.thread.lock.task.TransferTaskV2;
import com.example.thread.lock.task.TransferTaskV3;
import com.example.thread.lock.task.TransferTaskV4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/21 9:56
 * @since v1.0.0001
 */
public class DeadLockApp {

    public static AccountInfo account1 = new AccountInfo("jack", 100000);
    public static AccountInfo account2 = new AccountInfo("tom", 200000);
    public static long money = 10L;
    public static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) {
        // lock1();
        // lock2();
        // lock3();
        lock4();
    }

    /**
     * 测试死锁，死锁造成的原因是两个线程需要同时持有多个资源，并且相互等待
     */
    private static void lock1() {
        TransferTaskV1 opt1 = new TransferTaskV1(account1, account2, money);
        TransferTaskV1 opt2 = new TransferTaskV1(account2, account1, money);
        executorService.execute(opt1);
        executorService.execute(opt2);
    }

    /**
     * 破解死锁方案1：创建资源统一管理器，共享资源由资源管理器统一分配。
     * 资源管理器的实现(只要有一个线程获取到了共享资源，另外一个线程就无法获取，上锁时可以通过tryLock尝试获取锁)
     */
    private static void lock2() {
        AccountLockManager lockManager = new AccountLockManager();
        TransferTaskV2 opt1 = new TransferTaskV2(account1, account2, money, lockManager);
        TransferTaskV2 opt2 = new TransferTaskV2(account2, account1, money, lockManager);
        executorService.execute(opt1);
        executorService.execute(opt2);
    }

    /**
     * 方案2：获取锁实现顺序性(无法满足某些业务需求)
     */
    private static void lock3() {
        TransferTaskV3 opt1 = new TransferTaskV3(account1, account2, money);
        TransferTaskV3 opt2 = new TransferTaskV3(account2, account1, money);
        executorService.execute(opt1);
        executorService.execute(opt2);
    }

    /**
     * 创建两把锁ReentrantLock，两把锁的获取保持顺序性。
     */
    private static void lock4() {
        Lock fromLock = new ReentrantLock();
        Lock toLock = new ReentrantLock();
        TransferTaskV4 opt1 = new TransferTaskV4(account1, account2, money, fromLock, toLock);
        TransferTaskV4 opt2 = new TransferTaskV4(account2, account1, money, fromLock, toLock);
        executorService.execute(opt1);
        executorService.execute(opt2);
    }

}
