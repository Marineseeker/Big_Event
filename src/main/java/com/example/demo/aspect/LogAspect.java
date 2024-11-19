package com.example.demo.aspect;

import com.example.demo.annotation.log;
import com.example.demo.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.example.demo.annotation.log)")
    public void logPointcut() {
    }

    @Around("logPointcut() && @annotation(logAnnotation)")
    public Object logAround(ProceedingJoinPoint joinPoint, log logAnnotation) throws Throwable {
        String description = logAnnotation.value();
        String methodName = joinPoint.getSignature().getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }

        if (request != null) {
            logger.info("接收来自IP地址为 {} 的 {} 请求", request.getRemoteAddr(), request.getMethod());
        }
        logger.info("方法 {} 被调用, 描述: {}", methodName, description);

        // 记录方法执行时间
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();  // 执行目标方法
        } catch (Exception e) {
            logger.error("方法 {} 执行出错: {}", methodName, e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis();
        logger.info("方法 {} 执行完成 - 耗时: {} ms", methodName, (endTime - startTime));

        // 捕捉 Result 对象，如果 code 为 1，则记录错误信息
        if (result instanceof Result<?> resultObj){
            if (resultObj.getCode() == 1){
                logger.error("方法 {} 返回错误 - 错误码: {}, 错误信息: {}",
                        methodName, resultObj.getCode(), resultObj.getMessage());
            }
        }
        return result;
    }
}
