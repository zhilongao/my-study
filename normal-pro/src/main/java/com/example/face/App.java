package com.example.face;

import java.util.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/4 17:46
 * @since v1.0.0001
 */
public class App {

    public static void main(String[] args) {
        ThreadLocal<Integer> local = new ThreadLocal<>();
        local.set(1);
        local.get();

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}


