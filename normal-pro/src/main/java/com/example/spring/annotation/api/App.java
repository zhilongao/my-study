package com.example.spring.annotation.api;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        // test1();
        // test2();
        test3();
    }


    private static void test1() {
        // 1. 元数据读取工厂
        SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        try {
            // 2. 创建元数据读取器
            MetadataReader metadataReader = factory.getMetadataReader("com.example.spring.annotation.api.SpringPo1");
            // 3. 读取元数据
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            // 4. 读取元数据的注解类型
            Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
            // 5. 循环输出
            annotationTypes.forEach(System.err::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        // 1. 直接构建元数据
        AnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(SpringPo1.class);
        // 2. 读取元数据的注解类型
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        // 3. 循环输出
        annotationTypes.forEach(System.err::println);
    }

    private static void test3() {
        // 1. 构建元数据
        AnnotationMetadata annotationMetadata = AnnotationMetadata.introspect(SpringPo1.class);
        // 2. 读取元数据的注解类型
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        // 3. 循环输出
        annotationTypes.forEach(System.err::println);
    }

}
