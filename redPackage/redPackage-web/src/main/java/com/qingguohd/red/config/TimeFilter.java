package com.qingguohd.red.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: YuGenHai
 * @name: TimeFilter.java
 * @creation: 2018年5月29日 上午11:45:26
 * @notes:  记录状态
 */
public class TimeFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("web 时间Filter初始化..........");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("web 时间Filter销毁..........");
	}

}
