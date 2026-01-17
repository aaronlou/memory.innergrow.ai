package ai.innergrow.memory.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* ai.innergrow.memory.interfaces.rest..*Controller.*(..))")
    public void restControllerPointcut() {}

    @Around("restControllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.info("Request: [{} {}] Args: {}", request.getMethod(), request.getRequestURI(), Arrays.toString(joinPoint.getArgs()));
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error in method: {} with message: {}", joinPoint.getSignature().toShortString(), throwable.getMessage());
            throw throwable;
        }

        long executionTime = System.currentTimeMillis() - start;
        log.info("Response: {} | Execution Time: {}ms", result, executionTime);
        
        return result;
    }
}
