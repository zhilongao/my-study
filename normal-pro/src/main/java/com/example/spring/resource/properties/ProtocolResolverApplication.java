package com.example.spring.resource.properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/29 13:33
 * @since v1.0.0001
 */
public class ProtocolResolverApplication {
    public static void main(String[] args) throws Exception {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        DogProtocolResolver dogProtocolResolver = new DogProtocolResolver();
        resourceLoader.addProtocolResolver(dogProtocolResolver);

        Resource resource = resourceLoader.getResource("dog:Dog.txt");
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
//
