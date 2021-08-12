package com.example.spring.auto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("Java7")
public class IterationCalculatingService implements CalculatingService {
    @Override
    public Integer sum(Integer... values) {
        System.err.println("java7迭代实现");
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum;
    }
}
