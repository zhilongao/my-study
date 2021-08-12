package com.example.spring.resource.protocol;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * spring自定义协议解析
 *
 * @author gaozhilong
 * @date 2021/1/29 13:33
 * @since v1.0.0001
 */
public class App {
    public static void main(String[] args) throws Exception {
        // 资源加载器
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        // Dog协议解析器
        DogProtocolResolver dogProtocolResolver = new DogProtocolResolver();
        // 给资源加载器添加协议解析器
        resourceLoader.addProtocolResolver(dogProtocolResolver);
        //资源加载器获取到资源
        Resource resource = resourceLoader.getResource("dog:Dog.txt");
        // 打印资源
        printResource(resource);
    }

    private static void printResource(Resource resource) throws Exception{
        InputStream inputStream = resource.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);
        String readLine;
        while ((readLine = br.readLine()) != null) {
            System.err.println(readLine);
        }
        br.close();
    }
}
