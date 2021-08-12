package com.example.spring.auto;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;

@ComponentScan(basePackageClasses = CalculatingService.class)
public class App {

    static {
        // System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "Java7");
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "Java8");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(App.class);
        context.refresh();
        CalculatingService service = context.getBean(CalculatingService.class);
        service.sum(1, 2, 3, 4, 5);
        context.close();
    }
}
