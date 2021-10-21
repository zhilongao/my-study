package com.util.http;

import org.apache.juli.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.util.http.SelectedHttpClient.StrategyType.*;

public class ClientApp {

    public static final String getUrl = "http://localhost:9527/test/get";
    public static final String postUrl = "http://localhost:9527/test/post";

    static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {

        //client.setStrategyReq(STRATEGY_WEB_CLIENT);

        for (int i = 0; i < 3; i++) {
            simpleTest(STRATEGY_HTTP_CLIENT);
            simpleTest(STRATEGY_REST_TEMPLATE);
            simpleTest(STRATEGY_OK_HTTP);
            simpleTest(STRATEGY_WEB_CLIENT);
            System.err.println();
        }



//        System.err.println("get result:" + getResult);
//
//        Map<String, Object> reqBody = new HashMap<>();
//        reqBody.put("name", "tom");
//        String postResult = client.post(postUrl, headers, reqBody);
//        System.err.println("post result:" + postResult);
    }

    public static void simpleTest(SelectedHttpClient.StrategyType type) {
        SelectedHttpClient client = new SelectedHttpClient();
        client.setStrategyReq(type);
        Map<String, String> headers = initHeaders();
        Map<String, Object> params = initParams();

        long start = System.currentTimeMillis();
        int times = 10000;
        CountDownLatch latch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            executor.execute(new HttpExecuteTask(client, headers, params, latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.err.printf("使用策略:%s  耗时:%s ms \n", client.getStrategyReq().code, (end - start));
    }

    public static class HttpExecuteTask implements Runnable {
        private SelectedHttpClient client;
        private CountDownLatch latch;
        private Map<String, String> headers;
        private Map<String, Object> params;

        public HttpExecuteTask(SelectedHttpClient client, Map<String, String> headers, Map<String, Object> params, CountDownLatch latch) {
            this.client = client;
            this.latch = latch;
            this.headers = headers;
            this.params = params;
        }

        @Override
        public void run() {
            client.get(getUrl, headers, params);
            latch.countDown();
        }
    }


    private static Map<String, String> initHeaders() {
        Map<String, String> headers = new HashMap<>();
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
