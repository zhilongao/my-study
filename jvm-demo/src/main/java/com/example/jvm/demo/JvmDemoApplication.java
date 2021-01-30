package com.example.jvm.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.Conventions;

@SpringBootApplication
public class JvmDemoApplication {

    public static void main(String[] args) {
        // SpringApplication.run(JvmDemoApplication.class, args);
        String configurationClass = Conventions.getQualifiedAttributeName(ConfigurationClassPostProcessor.class, "configurationClass");
        System.err.println(configurationClass);
    }
    // CachingMetadataReaderFactoryPostProcessor
    // ConfigurationWarningsPostProcessor
}