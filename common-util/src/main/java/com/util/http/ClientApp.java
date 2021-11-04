package com.util.http;


import com.util.http.common.BatchReq;
import com.util.http.common.SelectedHttpClient;
import com.util.http.common.StrategyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientApp {

    public static final String getUrl = "http://localhost:9527/test/get";
    public static final String postUrl = "http://localhost:9527/test/post";

    static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        // executeGet();
        // executePost();
        testHttpClient(StrategyType.STRATEGY_HTTP_CLIENT);
        // testHttpClient(StrategyType.STRATEGY_HUTOOL_CLIENT);
        // testHttpClient(StrategyType.STRATEGY_OK_HTTP);
    }

    public static void testHttpClient(StrategyType type) {
        SelectedHttpClient client = new SelectedHttpClient();
        client.setStrategyType(type);
        Map<String, String> headers = initHeaders();
        Map<String, Object> params = initParams();
        List<BatchReq> reqs = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            BatchReq batchReq = new BatchReq();
            batchReq.setUrl(getUrl);
            batchReq.setHeaders(headers);
            batchReq.setParams(params);
            reqs.add(batchReq);
            // client.doGetAsync(getUrl, headers, params);
        }
        client.doGetAsyncBatch(reqs);
        long end1 = System.currentTimeMillis();
        System.err.println(type.name() + " all cost time:" + (end1 - start));

    }

    public static void executeGet() {
        for (int i = 0; i < 3; i++) {
            simpleTest(StrategyType.STRATEGY_HTTP_CLIENT, "get");
            simpleTest(StrategyType.STRATEGY_REST_TEMPLATE, "get");
            simpleTest(StrategyType.STRATEGY_OK_HTTP, "get");
            simpleTest(StrategyType.STRATEGY_WEB_CLIENT, "get");
            simpleTest(StrategyType.STRATEGY_HUTOOL_CLIENT, "get");
        }
    }


    public static void executePost() {
        for (int i = 0; i < 3; i++) {
            simpleTest(StrategyType.STRATEGY_HTTP_CLIENT, "post");
            simpleTest(StrategyType.STRATEGY_REST_TEMPLATE, "post");
            simpleTest(StrategyType.STRATEGY_OK_HTTP, "post");
            simpleTest(StrategyType.STRATEGY_WEB_CLIENT, "post");
            simpleTest(StrategyType.STRATEGY_HUTOOL_CLIENT, "post");
        }
    }

    public static void simpleTest(StrategyType type, String methodType) {
        SelectedHttpClient client = new SelectedHttpClient();
        client.setStrategyType(type);
        Map<String, String> headers = initHeaders();
        Map<String, Object> params = initParams();

        long start = System.currentTimeMillis();
        int times = 1000;
        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            executor.execute(new HttpExecuteTask(client, headers, params, latch, methodType));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.err.printf("使用策略:%s  耗时:%s ms \n", client.getStrategyType().name(), (end - start));
    }

    public static class HttpExecuteTask implements Runnable {
        private SelectedHttpClient client;
        private CountDownLatch latch;
        private Map<String, String> headers;
        private Map<String, Object> params;
        private String methodType;

        public HttpExecuteTask(SelectedHttpClient client, Map<String, String> headers, Map<String, Object> params, CountDownLatch latch, String methodType) {
            this.client = client;
            this.latch = latch;
            this.headers = headers;
            this.params = params;
            this.methodType = methodType;
        }

        @Override
        public void run() {
            if ("get".equals(methodType)) {
                client.doGet(getUrl, headers, params);
            } else if ("post".equals(methodType)) {
                client.doPost(postUrl, headers, params);
            }
            latch.countDown();
        }
    }

    private static Map<String, String> initHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("head1", "value1");
        headers.put("head2", "value2");
        headers.put("head3", "value3");
        return headers;
    }

    private static Map<String, Object> initParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "jack");
        return params;
    }
}
