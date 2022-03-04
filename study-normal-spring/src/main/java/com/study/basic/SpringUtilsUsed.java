package com.study.basic;


import org.springframework.core.GenericTypeResolver;


/**
 * @author gaozhilong
 * @date 2022/2/24 14:59
 */
public class SpringUtilsUsed {
    public static void main(String[] args) {
        testGenericTypeUsed();
    }

    /**
     * 测试spring工具类GenericTypeResolver的使用
     */
    public static void testGenericTypeUsed() {
        SelfClass selfClass = new SelfClass();
        Class<?> cls = GenericTypeResolver.resolveTypeArgument(selfClass.getClass(), SelfInterface.class);
        System.err.println(cls);
    }

    public static interface SelfInterface<T> {
        void printInfo(String info);
    }

    public static class SelfClass implements SelfInterface<String> {
        @Override
        public void printInfo(String info) {

        }
    }


}
