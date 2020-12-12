package com.example.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App {
    public static void main(String[] args) {
        // Thread t1 = new Thread(new SimpleTask1());
        // t1.start();
        FutureTask futureTask = new FutureTask(new SimpleTask2());
        Thread t2 = new Thread(futureTask);
        t2.start();
        try {
            Object o = futureTask.get();
            System.err.println(o.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.err.println("main thread execute!");
    }
}
