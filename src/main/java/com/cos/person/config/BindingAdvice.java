package com.cos.person.config;


import com.cos.person.domain.CommonDTO;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Before : 실행되는 함수 앞에서 제어
 * @After : 실행되는 함수 뒤에서 제어
 * @Around : 실행되는 함수 앞뒤에서 제어
 * */
@Slf4j
@Aspect
@Component
public class BindingAdvice {

    @Before("execution(* com.cos.person.web..*Controller.*(..))")
    public void logCheck() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("logCheck() 실행 URL={}", request.getRequestURI());
    }


    @Around("execution(* com.cos.person.web..*Controller.*(..))")
    public Object validateCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Controller 이름
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        // Method 이름
        String method = proceedingJoinPoint.getSignature().getName();
        log.info("validateCheck() 실행 type={}, method={}", type, method);

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            // BindingResult 파라미터가 있는 경우
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                // Data 유효성 검사
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    // 각 필드의 유효성 검사 실시
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        // 해당 필드와 메세지 담기
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        log.warn(type + "." + method + "() => 필드 : {}", error.getField());
                        log.warn(type + "." + method + "() => 메세지 : {}", error.getDefaultMessage());
                        // Sentry Log
                        Sentry.captureMessage(type + "." + method + "() => 필드 : " + error.getField());
                        Sentry.captureMessage(type + "." + method + "() => 메세지 : " + error.getDefaultMessage());
                    }
                    // 유효성 검사 이상 있음
                    return new CommonDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }

        // 정상 진행
        return proceedingJoinPoint.proceed();
    }
}
