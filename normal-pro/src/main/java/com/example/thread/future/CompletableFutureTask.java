package com.example.thread.future;


import java.util.concurrent.CompletableFuture;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/19 10:40
 * @since v1.0.0001
 */
public class CompletableFutureTask {
    public static void main(String[] args) throws InterruptedException {
        v1();
        // v2();
        // v3();
    }

    private static void v1() throws InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            return SimpleQueryUtil.fetchPrice("111");
        });
        // 执行正常和异常监听
        cf.thenAccept((result) -> {
            System.err.println("price: " + result);
        });
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        // 主线程不要结束，否则CompletableFuture默认的线程池会关闭
        Thread.sleep(200);
    }

    private static void v2() throws InterruptedException {
        // 第一个任务
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            return SimpleQueryUtil.queryCode("中国石油");
        });
        // 第一个任务执行成功后执行第二个任务
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            return SimpleQueryUtil.fetchPrice(code);
        });
        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(4000);
    }

    private static void v3() throws InterruptedException {
        // 开启两个任务，同时从sina和163查询中国石油证券代码
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return SimpleQueryUtil.queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return SimpleQueryUtil.queryCode("中国石油", "https://money.163.com/code/");
        });
        // 使用anyOf合并成一个新的CompletableFuture
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);
        // 两个CompletableFuture执行异步查询
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApply((code) -> {
            return SimpleQueryUtil.fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return SimpleQueryUtil.fetchPrice((String) code, "https://money.163.com/price/");
        });
        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);
        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(5000);
    }
}
