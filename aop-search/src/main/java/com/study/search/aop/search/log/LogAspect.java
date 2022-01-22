package com.study.search.aop.search.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义切点(包扫描的形式)
     */
    @Pointcut("execution(public * com.study.search.aop.search.service.*.*(..))")
    public void pointCut() {

    }

    /**
     * 定义切点(注解的形式)
     */
    @Pointcut("@annotation(com.study.search.aop.search.log.Log)")
    public void pointCutV2() {

    }


    @Before("pointCutV2()")
    public void doBefore(JoinPoint joinPoint) {

    }

    @After("pointCutV2()")
    public void doAfter(JoinPoint joinPoint) {

    }

    @Around("pointCutV2()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        String name = joinPoint.getSignature().getName();
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("execute type name:{}, method name:{}, all cost time:{} ms", typeName, name, end - start);
        return  result;
    }
}
