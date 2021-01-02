package com.example.zk.lock;

import com.example.zk.util.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/29 11:50
 * @since v1.0.0001
 */
public class LockDemo {


    public static void main(String[] args) {
        simpleLock();
    }

    public static void simpleLock(){
        CuratorFramework framework = CuratorUtil.curatorFramework();
        framework.start();
        final InterProcessMutex lock = new InterProcessMutex(framework,"/locks");
        int lockNum = 10;
        for(int i = 0; i < lockNum; i++){
            new Thread(()->{
                System.err.println(Thread.currentThread().getName()+"->尝试竞争锁");
                try {
                    // 阻塞竞争锁
                    lock.acquire();
                    System.err.println(Thread.currentThread().getName()+"->成功获得了锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //释放锁
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"Thread-" + i).start();
        }
    }
}
