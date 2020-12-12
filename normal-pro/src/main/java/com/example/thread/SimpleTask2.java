package com.example.thread;

import java.util.concurrent.Callable;

public class SimpleTask2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.err.println(Thread.currentThread().getName() + "--execute!");
        return "hello,world";
    }
}
