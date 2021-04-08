package com.study.search.aop.search.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(public * com.study.search.aop.search.service.*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        System.err.println("execute method before");
    }

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        System.err.println("execute method after");
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        System.err.println("around before");
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.err.println("all cost time " + (end - start));
        return  result;
    }
}
