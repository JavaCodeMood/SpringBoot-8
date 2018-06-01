package com.qingguohd.red.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: YuGenHai
 * @name: ContextListener.java
 * @creation: 2018年5月29日 上午11:45:26
 * @notes:  监听器
 */
public class ContextListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	logger.info("web 监听器初始化........");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	logger.info("web 监听器销毁........");
    }

}