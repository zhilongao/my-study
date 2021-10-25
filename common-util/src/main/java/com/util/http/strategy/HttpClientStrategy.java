package com.util.http.strategy;

import org.apache.http.HttpResponse;
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
import java.util.Map;
import java.util.Set;

/**
 * HttpClient
 * 封装同步和异步http请求，两者在功能上的不同点在于同步可以直接返回结果,但是异步需要在回调中处理结果
 */
public class HttpClientStrategy extends BaseWorkStrategy {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private CloseableHttpAsyncClient asyncHttpClient = HttpAsyncClients.createDefault();

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
        doAsync(request);
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
        HttpPost httpPost = postRequest(url, headers);
        // 2. 发送请求
        doAsync(httpPost);
    }

    /**
     * 异步请求的封装
     * @param request 请求
     */
    public void doAsync(HttpRequestBase request) {
        asyncHttpClient.start();
        asyncHttpClient.execute(request, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse response) {
                try {
                    String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.err.println(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failed(Exception ex) {
                System.out.println(request.getRequestLine() + "->" + ex);
                System.out.println(" callback thread id is : " + Thread.currentThread().getId());
            }
            @Override
            public void cancelled() {
                System.out.println(request.getRequestLine() + " cancelled");
                System.out.println(" callback thread id is : " + Thread.currentThread().getId());
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
