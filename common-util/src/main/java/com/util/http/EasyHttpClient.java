package com.util.http;

import okhttp3.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class EasyHttpClient {

   // 可以使用的http客户端
   // 1.spring的RestTemplate
   // 2.Okhttp
   // 3.http client

    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String url = "https://www.baidu.com/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            String message = body.string();
            System.err.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
