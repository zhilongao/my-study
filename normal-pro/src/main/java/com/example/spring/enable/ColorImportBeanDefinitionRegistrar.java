package com.example.spring.enable;

import com.example.spring.entity.Black;
import com.example.spring.entity.Red;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2021/1/7 11:47
 * @since v1.0.0001
 */
public class ColorImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("black", new RootBeanDefinition(Black.class));
        registry.registerBeanDefinition("red", new RootBeanDefinition(Red.class));
    }
}
