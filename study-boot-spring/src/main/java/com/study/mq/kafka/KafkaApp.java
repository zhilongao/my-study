package com.study.mq.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class KafkaApp {

    public static void main(String[] args) {
       // SpringApplication.run(KafkaApp.class, args);
       SpringApplication application = new SpringApplication(KafkaApp.class);
        Set<ApplicationContextInitializer<?>> initializers = application.getInitializers();
        for (ApplicationContextInitializer initializer : initializers) {
            String simpleName = initializer.getClass().getSimpleName();
            System.err.println(simpleName);
        }
        System.err.println("===================================");
        Set<ApplicationListener<?>> listeners = application.getListeners();
        for (ApplicationListener listener : listeners) {
            String simpleName = listener.getClass().getSimpleName();
            System.err.println(simpleName);
        }
        application.run(args);
        DefaultConversionService conversionService = new DefaultConversionService();
    }
}
