package com.example.spring.annotation.use;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

public class OnSystemPropertyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 打印注解元数据的信息
        printInfo(metadata);
        // 1. 获取ConditionalOnSystemProperty所有的属性方法值
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(ConditionalOnSystemProperty.class.getName());
        // 2. 获取ConditionalOnSystemProperty#name()方法值(单值)
        String propertyName = (String)attributes.getFirst("name");
        // 3. 获取ConditionalOnSystemProperty#value()方法值(单值)
        String propertyValue = (String)attributes.getFirst("value");
        // 4. 获取系统属性值
        String systemPropertyValue = System.getProperty(propertyName);
        // 5. 比较系统属性值与ConditionalOnSystemProperty#value()方法值是否相等
        if (Objects.equals(systemPropertyValue, propertyValue)) {
            System.out.printf("系统属性[名称:%s] 找到匹配值: %s\n", propertyName, propertyValue);
            return true;
        }
        return false;
    }

    private static void printInfo(AnnotatedTypeMetadata metadata) {
        System.err.println("----start----");
        String message = "";
        if (metadata instanceof ClassMetadata) {
            ClassMetadata classMetadata = (ClassMetadata)metadata;
            message = classMetadata.getClassName();
        } else {
            MethodMetadata methodMetadata = (MethodMetadata) metadata;
            message = methodMetadata.getDeclaringClassName() + "#" + methodMetadata.getMethodName();
        }
        System.err.println(message);
        // com.example.spring.annotation.use.BeanConfig#p5
        System.err.println("----end----");
    }
}
