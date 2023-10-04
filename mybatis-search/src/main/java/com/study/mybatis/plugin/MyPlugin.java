package com.study.mybatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
    // 注意看这个⼤花括号，也就这说这⾥可以定义多个@Signature对多个地⽅拦截，都⽤这个拦截器
    @Signature(
        type = StatementHandler.class , //这是指拦截哪个接⼝
        method = "prepare", //这个接⼝内的哪个⽅法名
        args = {Connection.class, Integer.class}),// 这是拦截的⽅法的⼊参，按顺序写到这，如果⽅法重载，可是要通过⽅法名和⼊参来确定唯⼀的
})
public class MyPlugin implements Interceptor {

    /**
     * 这⾥是每次执⾏操作的时候，都会进⾏这个拦截器的⽅法内
     * @param invocation invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 增强逻辑
        String name = invocation.getMethod().getName();
        System.out.printf("----->对方法 %s 进行了增强<-----\n", name);

        // 执行原方法
        return invocation.proceed();
    }

    /**
     * 将这个拦截器生成代理,放到拦截器链中
     * @param target target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("将要包装的⽬标对象："+target);
        return Interceptor.super.plugin(target);
    }

    /**
     * 获取配置文件的属性
     * @param properties 配置的属性
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的初始化参数：" + properties );
        Interceptor.super.setProperties(properties);
    }
}
