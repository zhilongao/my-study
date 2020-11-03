package com.example.jvm.service;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/2 19:59
 * @since v1.0.0001
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock lock1 = new DeadLock(true);
        DeadLock lock2 = new DeadLock(false);
        Thread t1 = new Thread(lock1);
        Thread t2 = new Thread(lock2);
        t1.start();
        t2.start();
    }
}

class MyLock{
    public static Object object1 = new Object();
    public static Object object2 = new Object();
}

class DeadLock implements Runnable {

    private boolean flag;

    DeadLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (MyLock.object1) {
                    System.out.println(Thread.currentThread().getName()+"----if获得obj1锁");
                    synchronized (MyLock.object2) {
                        System.out.println(Thread.currentThread().getName()+"---if获得obj2锁");
                    }
                }
            }
        } else {
            while (true) {
                synchronized (MyLock.object2) {
                    System.out.println(Thread.currentThread().getName()+"----else获得obj2锁");
                    synchronized (MyLock.object1) {
                        System.out.println(Thread.currentThread().getName()+"---else获得obj1锁");
                    }
                }
            }
        }
    }
}
