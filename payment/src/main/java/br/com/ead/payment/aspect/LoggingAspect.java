package br.com.ead.payment.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {
    @Pointcut(value = "execution(* br.com.ead.payment.service.*.*.*(..))")
    public void loggingPointCut() {

    }

    @Around("loggingPointCut()")
    public Object loggingAdvice(ProceedingJoinPoint pj) throws Throwable {
        Object response = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String methodName = pj.getSignature().getName();
            String className = pj.getTarget().getClass().toString();

            Object[] array = pj.getArgs();
            log.info("Inside "+className+ "class "+methodName+" method, with request : "+mapper.writeValueAsString(array));

            response = pj.proceed();

            log.info("Inside "+className+ "class "+methodName+" method, with response : "+mapper.writeValueAsString(response));

        }catch (Exception e){

        }
        return response;
    }

    @AfterThrowing("loggingPointCut()")
    public void logError(JoinPoint joinPoint){
        //Advice
        log.info(" Check for user access ");
        log.info(" Allowed execution for {}", joinPoint);
    }
}
