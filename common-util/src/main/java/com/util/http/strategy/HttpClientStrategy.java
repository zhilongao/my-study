package com.util.http.strategy;

import com.util.http.common.BatchReq;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * HttpClient
 * 封装同步和异步http请求，两者在功能上的不同点在于同步可以直接返回结果,但是异步需要在回调中处理结果
 */
public class HttpClientStrategy extends BaseWorkStrategy {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final CloseableHttpAsyncClient asyncHttpClient;

    static {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setSocketTimeout(50000)
                .setConnectionRequestTimeout(1000)
                .build();
        asyncHttpClient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    /**
     * 同步get请求
     * @param url url
     * @param headers 请求头
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public String doGet(String url, Map<String, String> headers, Map<String, Object> params){
        // 1. 创建请求
        HttpGet getRequest = getRequest(url, headers, params);
        // 2. 发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(getRequest);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 同步post请求
     * @param url url
     * @param headers 请求头
     * @param params 请求参数
     * @return 响应结果
     */
    @Override
    public String doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        // 1. 创建请求
        HttpPost httpPost = postRequest(url, headers);
        // 2. 发送请求
        CloseableHttpResponse response = null;
        try {
            String jsonStr = mapper.writeValueAsString(params);
            httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 异步get请求
     * @param url url
     * @param headers 请求头
     * @param params 请求参数
     */
    @Override
    public void doGetAsync(String url, Map<String, String> headers, Map<String, Object> params) {
        // 1. 创建请求
        HttpGet request = getRequest(url, headers, params);
        // 2. 执行请求
        doAsync(Collections.singletonList(request));
    }

    @Override
    public void doGetAsyncBatch(List<BatchReq> reqs) {
        List<HttpRequestBase> requests = new ArrayList<>();
        for (BatchReq req : reqs) {
            HttpGet request = getRequest(req.getUrl(), req.getHeaders(), req.getParams());
            requests.add(request);
        }
        doAsync(requests);
    }


    /**
     * 异步post请求
     * @param url url
     * @param headers 请求头
     * @param params 请求参数
     */
    @Override
    public void doPostAsync(String url, Map<String, String> headers, Map<String, Object> params){
        // 1. 创建请求
        HttpPost request = postRequest(url, headers);
        // 2. 发送请求
        doAsync(Collections.singletonList(request));
    }

    /**
     * 执行异步请求(批量)
     * @param requests requests
     */
    private void doAsync(List<HttpRequestBase> requests) {
        // 使用CountDownLatch控制client关闭的时机
        CountDownLatch latch = new CountDownLatch(requests.size());
        asyncHttpClient.start();
        for (HttpRequestBase req : requests) {
            doAsync(req, latch);
        }
        // 等待任务执行完毕
        try {
            latch.await();
            asyncHttpClient.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.err.println("execute all");
    }

    /**
     * 执行异步请求(单个)
     * @param request request
     */
    private void doAsync(HttpRequestBase request, CountDownLatch latch) {
        Future<HttpResponse> future = asyncHttpClient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse response) {
                try {
                    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.err.println(content);
                    // todo 处理内容
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
            @Override
            public void failed(Exception ex) {
                latch.countDown();
            }

            @Override
            public void cancelled() {
                latch.countDown();
            }
        });
    }



    /****************************************** 工具方法 *******************************/
    private HttpGet getRequest(String url, Map<String, String> headers, Map<String, Object> params) {
        // 扩展url参数
        String reqUrl = extentUrl(url, params);
        HttpGet httpGet = new HttpGet(reqUrl);
        // 添加请求头
        addHeader(httpGet, headers);
        return httpGet;
    }

    private HttpPost postRequest(String url, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头
        addHeader(httpPost, headers);
        return httpPost;
    }

    private void addHeader(HttpRequestBase request, Map<String, String> headers) {
        // 添加请求头
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            request.addHeader(headerName, headers.get(headerName));
        }
    }
}
