package com.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okhttp3.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * 使用OkHttp发送Post和Get请求
 */
public class SelectedHttpClient {

    public enum StrategyType {
        STRATEGY_OK_HTTP("OkHttp"),

        STRATEGY_REST_TEMPLATE("RestTemplate"),

        STRATEGY_HTTP_CLIENT("HttpClient"),

        STRATEGY_WEB_CLIENT("WebClient");

        String code;
        StrategyType(String code) {
            this.code = code;
        }
    }

    public static final StrategyType STRATEGY_OK_HTTP = StrategyType.STRATEGY_OK_HTTP;

    public static final StrategyType STRATEGY_REST_TEMPLATE = StrategyType.STRATEGY_REST_TEMPLATE;

    public static final StrategyType STRATEGY_HTTP_CLIENT = StrategyType.STRATEGY_HTTP_CLIENT;

    public static final StrategyType STRATEGY_WEB_CLIENT = StrategyType.STRATEGY_WEB_CLIENT;

    public static final StrategyType DEFAULT_STRATEGY = STRATEGY_OK_HTTP;

    private StrategyType strategyReq = DEFAULT_STRATEGY;

    ObjectMapper mapper = new ObjectMapper();

    RestTemplate template = new RestTemplate();

    // OkHttpClient
    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    CloseableHttpClient httpClient = HttpClients.createDefault();

    public void setStrategyReq(StrategyType strategyReq) {
        this.strategyReq = strategyReq;
    }

    public StrategyType getStrategyReq() {
        return this.strategyReq;
    }

    public String get(String url, Map<String, String> headers, Map<String, Object> params) {
        if (STRATEGY_REST_TEMPLATE.equals(this.strategyReq)) {
            // System.err.println("发送Get请求,使用策略:RestTemplate");
            return restTemplateGet(url, headers, params);
        } else if (STRATEGY_HTTP_CLIENT.equals(this.strategyReq)) {
            // System.err.println("发送Get请求,使用策略:HttpClient");
            return httpClientGet(url, headers, params);
        } else if (STRATEGY_WEB_CLIENT.equals(this.strategyReq)) {
            // System.err.println("发送Get请求,使用策略:WebClient");
            return webClientGet(url, headers, params);
        } else {
            // System.err.println("发送Get请求,使用策略:OkHttp");
            return okHttpGet(url, headers, params);
        }
    }

    public String post(String url, Map<String, String> headers, Map<String, Object> body) {
        if (STRATEGY_REST_TEMPLATE.equals(this.strategyReq)) {
            // System.err.println("发送Post请求,使用策略:RestTemplate");
            return restTemplatePost(url, headers, body);
        } else if (STRATEGY_HTTP_CLIENT.equals(this.strategyReq)) {
            // System.err.println("发送Post请求,使用策略:HttpClient");
            return httpClientPost(url, headers, body);
        } else if (STRATEGY_WEB_CLIENT.equals(this.strategyReq)) {
            // System.err.println("发送Post请求,使用策略:WebClient");
            return webClientPost(url, headers, body);
        } else {
            // System.err.println("发送Post请求,使用策略:OkHttp");
            return okHttpPost(url, headers, body);
        }
    }

    //hutool
    // 1. 创建执行的Call对象
    // 2. 调用Call的execute方法

    // 执行的相关组件


    /********************************************* OkHttp **********************************/
    public String okHttpGet(String url, Map<String, String> headers, Map<String, Object> params) {
        String reqUrl = extentUrl(url, params);
        Request.Builder reqBuilder = new Request.Builder()
                .url(reqUrl)
                .get();
        Set<String> headerKeys = headers.keySet();
        for (String key : headerKeys) {
            reqBuilder.addHeader(key, headers.get(key));
        }
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            String message = body.string();
            printInfo(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String okHttpPost(String url, Map<String, String> headers, Map<String, Object> body) {
        String bodyStr = "";
        try {
            bodyStr = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyStr);
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(reqBody);
        Set<String> headerKeys = headers.keySet();
        for (String key : headerKeys) {
            reqBuilder.addHeader(key, headers.get(key));
        }
        Request request = reqBuilder.build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody resBody = response.body();
            String message = resBody.string();
            printInfo(message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /********************************************* RestTemplateGet **********************************/
    public String restTemplateGet(String url, Map<String, String> headers, Map<String, Object> params){
        String reqUrl = extentUrl(url, params);
        HttpHeaders httpHeaders = new HttpHeaders();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httpHeaders.add(key, headers.get(key));
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = template.exchange(reqUrl, HttpMethod.GET, httpEntity, String.class);
        String body = response.getBody();
        printInfo(body);
        return body;
    }

    public String restTemplatePost(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            httpHeaders.add(key, headers.get(key));
        }
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = response.getBody();
        printInfo(body);
        return body;
    }

    /********************************************* HttpClient **********************************/
    public String httpClientGet(String url, Map<String, String> headers, Map<String, Object> params) {
        // 扩展url参数
        String reqUrl = extentUrl(url, params);
        HttpGet httpGet = new HttpGet(reqUrl);
        // 添加请求头
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            httpGet.addHeader(headerName, headers.get(headerName));
        }
        // 发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                org.apache.http.HttpEntity entity = response.getEntity();
                return entityToString(entity);
            }
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

    public String httpClientPost(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            httpPost.addHeader(headerName, headers.get(headerName));
        }
        httpPost.addHeader("Content-type", "application/json");
        // 发送请求
        CloseableHttpResponse response = null;
        try {
            String jsonStr = mapper.writeValueAsString(params);
            httpPost.setEntity(new StringEntity(jsonStr,ContentType.APPLICATION_JSON));
            response = httpClient.execute(httpPost);
            if(response != null && response.getStatusLine().getStatusCode() == 200) {
                org.apache.http.HttpEntity entity = response.getEntity();
                return entityToString(entity);
            }
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

    public String webClientGet(String url, Map<String, String> headers, Map<String, Object> params) {
        WebClient webClient = WebClient.create();
        String reqUrl = extentUrl(url, params);

        WebClient.RequestHeadersSpec<?> reqSpec = webClient.get().uri(reqUrl);
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            reqSpec = reqSpec.header(headerName, headers.get(headerName));
        }
        Mono<String> mono = reqSpec.retrieve().bodyToMono(String.class);
        return mono.block();
    }

    public String webClientPost(String url, Map<String, String> headers, Map<String, Object> params) {
        WebClient webClient = WebClient.create();
        WebClient.RequestBodySpec reqSpec = webClient.post().uri(url);
        Set<String> headerNames = headers.keySet();
        for (String headerName : headerNames) {
            reqSpec = reqSpec.header(headerName, headers.get(headerName));
        }
        reqSpec.body(BodyInserters.fromObject(params));
        Mono<String> mono = reqSpec.retrieve().bodyToMono(String.class);
        return mono.block();
    }



    private String extentUrl(String url, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder(url);
        Set<String> keys = params.keySet();
        for (String key : keys) {
            if (builder.indexOf("?") != -1) {
                builder.append("&");
            } else {
                builder.append("?");
            }
            builder.append(key).append("=").append(params.get(key));
        }
        return builder.toString();
    }

    private String entityToString(org.apache.http.HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                result = EntityUtils.toString(entity,"UTF-8");
            } else {
                InputStreamReader reader = new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8);
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while((l = reader.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }

    private void printInfo(String message) {
        // System.err.println(message);
    }
}
