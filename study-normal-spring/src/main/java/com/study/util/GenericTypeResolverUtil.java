package com.study.util;

import org.springframework.core.GenericTypeResolver;

/**
 * spring泛型工具类的使用
 */
public class GenericTypeResolverUtil {

    public void testGenericTypeUsed() {
        SelfClass selfClass = new SelfClass();
        Class<?> cls = GenericTypeResolver.resolveTypeArgument(selfClass.getClass(), SelfInterface.class);
        System.err.println(cls);
    }


    public interface SelfInterface<T> {
        void printInfo(String info);
    }

    public class SelfClass implements SelfInterface<String> {
        @Override
        public void printInfo(String info) {

        }
    }


}
