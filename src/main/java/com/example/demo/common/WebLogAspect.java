package com.example.demo.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    // 1. 定义切入点：拦截 controller 包下的所有类的所有方法
    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void webLog() {}

    // 2. 环绕通知：在方法执行前后进行拦截
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求详情
        log.info("================  Request Start  ================");
        log.info("URL    : {}", request.getRequestURL().toString());
        log.info("HTTP   : {}", request.getMethod());
        log.info("Class  : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP     : {}", request.getRemoteAddr());
        log.info("Args   : {}", Arrays.toString(joinPoint.getArgs()));

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 打印返回内容和耗时
        long endTime = System.currentTimeMillis();
        log.info("Result : {}", result);
        log.info("Time   : {} ms", endTime - startTime);
        log.info("================  Request End    ================");

        return result;
    }
}
