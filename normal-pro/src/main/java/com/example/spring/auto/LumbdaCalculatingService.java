package com.example.spring.auto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Profile("Java8")
public class LumbdaCalculatingService implements CalculatingService {

    @Override
    public Integer sum(Integer... values) {
        System.err.println("java8 lumbda实现");
        int sum = Stream.of(values).reduce(0, Integer::sum);
        return sum;
    }
}
