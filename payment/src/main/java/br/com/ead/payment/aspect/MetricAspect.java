package br.com.ead.payment.aspect;

import br.com.ead.payment.annotations.Metric;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Log4j2
public class MetricAspect {
//    @Pointcut(value = "execution(* br.com.ead.payment.service.*.*.*(..))")
//    public void loggingPointCut() {
//
//    }
//
//    @Around("loggingPointCut() && @annotation(br.com.ead.payment.annotations.Metric)")
//    public Object loggingAdvice(ProceedingJoinPoint pj) throws Throwable {
//        MethodSignature signature = (MethodSignature) pj.getSignature();
//        Method method = signature.getMethod();
//
//        Metric myAnnotation = method.getAnnotation(Metric.class);
//        myAnnotation.value();
//            log.info("Inside "+className+ "class "+methodName+" method, with request : "+mapper.writeValueAsString(array));
//
//        return pj.proceed();
//    }
//
//    @AfterThrowing("loggingPointCut()")
//    public void logError(JoinPoint joinPoint){
//        //Advice
//        log.info(" Check for user access ");
//        log.info(" Allowed execution for {}", joinPoint);
//    }
}
