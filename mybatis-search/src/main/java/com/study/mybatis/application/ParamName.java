package com.study.mybatis.application;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;

public class ParamName {

    public static void main(String[] args) {
        ParamName paramName = new ParamName();
        paramName.test1();
    }

    public void test1() {
        // 获取参数名
        Configuration config = new Configuration();
        config.setUseActualParamName(true);
        Method[] methods = ParamFun.class.getDeclaredMethods();
        Method m = methods[0];
        ParamNameResolver resolver = new ParamNameResolver(config, m);
        String[] names = resolver.getNames();
        for (String name : names) {
            System.out.print(name + "\t");
        }
        System.out.println("\n");

        Object[] args = new Object[3];
        args[0] = "a1";
        args[1] = 10;
        args[2] = "a2";
        Object namedParams = resolver.getNamedParams(args);
        System.out.println(namedParams);
    }

    public void test2() {

    }

    public static class ParamFun {
        public void m1(@Param(value = "m11") String m1, int a1, @Param(value = "m22") String m2) {

        }
    }
}
