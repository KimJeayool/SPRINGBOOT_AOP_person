package com.cos.person.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice  // Exception 처리
public class MyExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void handler(Exception e) {
        log.info("에러 발생 e={}", e);
    }

}
