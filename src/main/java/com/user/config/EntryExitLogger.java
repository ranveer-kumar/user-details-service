package com.user.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class EntryExitLogger {

    @Pointcut("within(com.user.service.impl..*) " +
    "|| within(com.user.controller..*)")
    public void logMethods() {}

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("logMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        log.info("[Audit Entry: ] method [{}] with arguments: {}", joinPoint.getSignature(), Arrays.toString(args));

        // Execute the target method and capture the return value
        Object result = joinPoint.proceed();

        // Log method exit and return value
        log.info("Audit Exit method [{}] with result: {}", joinPoint.getSignature(), result);

        // Return the result
        return result;
    }
}
