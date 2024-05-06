package com.wckj.chasstage.api.server.imp.device.internal.asp;


import com.wckj.framework.json.jackson.JsonUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 3)
public class RetryAspect {
    private Logger logger = Logger.getLogger(this.getClass());
    @Pointcut("@annotation(Retryable)")  //表示所有带有Retryable的注解
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Retryable retry = method.getAnnotation(Retryable.class);
        String name = method.getName();
        Object[] args = joinPoint.getArgs();
        String uuid = UUID.randomUUID().toString();
        logger.info("执行重试切面"+uuid+", 方法名称"+name+", 方法参数"+ JsonUtil.getJsonString(args));
        int times = retry.times();
        long waitTime = retry.waitTime();
        Class[] needThrowExceptions = retry.needThrowExceptions();
        Class[] catchExceptions = retry.catchExceptions();
        // check param
        if (times <= 0) {
            times = 1;
        }

        for (; times >= 0; times--) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                // 如果需要抛出的异常不是空的, 看看是否需要抛出
                if (needThrowExceptions.length > 0) {
                    for (Class exception : needThrowExceptions) {
                        if (exception == e.getClass()) {
                            logger.warn("执行重试切面"+uuid+"失败, 异常在需要抛出的范围"+needThrowExceptions+", 业务抛出的异常类型"+e.getClass().getName());
                            throw e;
                        }
                    }
                }

                // 如果需要抛出异常，而且需要捕获的异常为空那就需要再抛出
                if (catchExceptions.length > 0) {
                    boolean needCatch = false;
                    for (Class catchException : catchExceptions) {
                        if (e.getClass() == catchException) {
                            needCatch = true;
                            break;
                        }
                    }
                    if (!needCatch) {
                        logger.warn("执行重试切面"+uuid+"失败, 异常不在需要捕获的范围内, 需要捕获的异常"+catchExceptions+", 业务抛出的异常类型"+e.getClass().getName() );
                        throw e;
                    }
                }

                // 如果接下来没有重试机会的话，直接报错
                if (times <= 0) {
                    logger.warn("执行重试切面"+uuid+"失败");
                    throw e;
                }

                // 休眠 等待下次执行
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }

                logger.warn("执行重试切面"+uuid+", 还有"+times+"次重试机会, 异常类型"+e.getClass().getName()+", 异常信息"+e.getMessage()+", 栈信息"+ e.getStackTrace());
            }
        }
        return false;
    }
}
