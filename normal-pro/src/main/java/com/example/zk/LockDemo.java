package com.example.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/29 11:50
 * @since v1.0.0001
 */
public class LockDemo {

    private static final int sessionTimeOut = 50000000;

    private static final int baseSleepTimeMs = 1000;

    private static final int maxRetries = 3;

    public static void main(String[] args) {
        String zkUrl = CommonConstant.CONNECTION_STR;
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(zkUrl).sessionTimeoutMs(sessionTimeOut).
                retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries)).build();
        curatorFramework.start();
        final InterProcessMutex lock = new InterProcessMutex(curatorFramework,"/locks");
        int lockNum = 10;
        for(int i = 0; i < lockNum; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"->尝试竞争锁");
                try {
                    lock.acquire(); //阻塞竞争锁
                    System.out.println(Thread.currentThread().getName()+"->成功获得了锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(400000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release(); //释放锁
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"Thread-" + i).start();
        }
    }
}
