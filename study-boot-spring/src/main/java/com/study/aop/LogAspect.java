package com.study.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    /**
     * 切入com.study.aop一级包下面的所有类的所有方法
     */
    @Before("execution(public * com.study.aop.*.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        System.err.println("doBefore run ...");
    }

    /**
     * 切入被LogBack注解标记的方法
     * @param joinPoint
     */
    @After("@annotation(com.study.aop.LogBack)")
    public void doAfter(JoinPoint joinPoint) {
        System.err.println("doAfter run ...");
    }


    /**
     * 切入com.example.demo.service.DemoService类的所有方法中第一个参数为Serializable类型的方法
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "execution(public * com.example.demo.service.DemoService.*(java.io.Serializable, ..))",
            returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("doAfterReturning run, result: " + result);
    }

    /**
     * 切入com.example.demo下所有的controller包下面的所有类的所有方法
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "execution(public * com.example.demo..controller.*(..))", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("doAfterThrowing catch exception: " + ex.getMessage());
    }

    /**
     * 切入com.example.demo.controller.DemoController的所有返回值为String的方法
     * @param joinPoint
     * @return
     */
    @Around("execution(public String com.example.demo.controller.DemoController.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        System.out.println("doAround run...");
        Object result = null;
        try {
            System.out.println("method before invoke...");
            result = joinPoint.proceed();
            System.out.println("method invoked, result: " + result);
        } catch (Throwable throwable) {
            System.out.println("method throws Exception: " + throwable.getMessage());
            throwable.printStackTrace();
        }
        return result;
    }
}
