package com.study.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * 自定义bean实例化后置处理器
 *
 * @author gaozhilong
 * @date 2020/12/30 10:35
 * @since v1.0.0001
 */
public class SelfInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    public SelfInstantiationAwareBeanPostProcessor() {
        super();
        System.err.println("SelfInstantiationAwareBeanPostProcessor 初始化完成!");
    }


    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.err.println("postProcessBeforeInstantiation----" + beanClass.getName());
        return null;
    }

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.err.println("postProcessAfterInstantiation----" + beanName);
        return false;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.err.println("postProcessPropertyValues----" + beanName);
        return null;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("postProcessBeforeInitialization----" + beanName);
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("postProcessAfterInitialization----" + beanName);
        return null;
    }
}
