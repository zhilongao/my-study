package com.example.thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/25 11:24
 * @since v1.0.0001
 */
public class SimpleCallableTaskV1 implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.err.println("start execute task");
        TimeUnit.SECONDS.sleep(5);
        System.err.println("end execute task");
        return "ok";
    }
}
