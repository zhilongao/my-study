package com.example.thread;

public class SimpleTask1 implements Runnable {
    @Override
    public void run() {
        System.err.println(Thread.currentThread().getName() + "--execute!");
    }
}
