package com.app.service.security;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
@Slf4j
public class Licensing {

    @Pointcut("within(com.app..*Controller)")
    public void controllers() {
    }

    @Before("controllers()")
    public void checkLicense() {
        val currentDate  = LocalDate.now();
        if(currentDate.compareTo(LocalDate.of(2023, 9, 30)) > 0) {
            log.warn("***********************");
            log.warn("Shutting down....");
            log.info("Your trial period is over. Get your license");
            log.warn("***********************");
            System.exit(0);
        }
    }
}
