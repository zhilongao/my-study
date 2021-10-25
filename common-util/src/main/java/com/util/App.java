package com.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

//@SpringBootApplication
public class App {

    public static void main(String[] args) {
        //ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        BigDecimal decimal = new BigDecimal(55);
        BigDecimal decimal1 = BigDecimal.valueOf(1000);

        int count = 500;

        BigDecimal totalPrice = decimal.divide(decimal1).multiply(BigDecimal.valueOf(count));
        System.err.println(totalPrice);

        BigDecimal costAmount = BigDecimal.valueOf(10);
        BigDecimal subtract = totalPrice.subtract(costAmount);
        System.err.println(subtract);
    }
}