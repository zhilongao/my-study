package com.example.thread.code;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/23 18:53
 * @since v1.0.0001
 */
public class ThreadLocalApp {
    public static void main(String[] args) {
        ThreadLocal local = new ThreadLocal();
        local.set(null);
        local.get();
        local.remove();
    }
}
