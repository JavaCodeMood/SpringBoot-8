package com.qingguohd.red.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

	private static final Logger logger = LoggerFactory.getLogger(TimeAspect.class);
	
	/**
	 * ######### 切面处理
	 * @author yugenhai
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
    @Around("execution(* com.qingguohd.red.controller..*(..))")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {
    	logger.info("切面监听 Controller ..........");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
        	logger.info("参数为..........:" + arg);
        }
        long start = System.currentTimeMillis();
        Object object = pjp.proceed();
        logger.info("切面监听 Controller..........  耗时:" + (System.currentTimeMillis() - start));
        return object;
    }
}