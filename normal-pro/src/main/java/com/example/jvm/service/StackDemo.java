package com.example.jvm.service;

/**
 * 模式jvm栈溢出 java.lang.StackOverflowError
 * 设置参数： -Xss128k 设置每个线程的堆栈大小。
 * @author gaozhilong
 * @date 2020/11/2 16:59
 * @since v1.0.0001
 */
public class StackDemo {

    public static long count = 0;

    public static void method(long i) {
        System.err.println(count++);
        method(i);
    }

    public static void main(String[] args) {
        method(1);
    }
}
