package com.example.spring.annotation.use;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class SystemCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> type = metadata.getAnnotationAttributes("type");
        MergedAnnotations annotations = metadata.getAnnotations();
        annotations.stream().forEach(p -> {
            System.err.println(p);
        });
        return false;
    }
}
