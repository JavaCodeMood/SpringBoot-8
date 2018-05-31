package com.qingguohd.red.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TimeInterceptor implements HandlerInterceptor {
    
	private static final Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	logger.info("invoking Controller ------ >" + ((HandlerMethod)handler).getBean().getClass().getName());
    	logger.info("invoking Controller MethodName ------ >" + ((HandlerMethod)handler).getMethod().getName());
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    	logger.info("========postHandle=========");
        Long start = (Long) request.getAttribute("startTime");
        logger.info("耗时:"+(System.currentTimeMillis() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
            throws Exception {
    	logger.info("========afterCompletion=========");
        Long start = (Long) request.getAttribute("startTime");
        logger.info("耗时:"+(System.currentTimeMillis() - start));
        if(null != exception) {
        	logger.info(exception.getMessage());
        }
    }

}