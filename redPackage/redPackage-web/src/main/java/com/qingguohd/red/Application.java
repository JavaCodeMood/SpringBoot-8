package com.qingguohd.red;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import com.qingguohd.red.config.ContextListener;
import com.qingguohd.red.config.TimeFilter;

/**
 * @author: YuGenHai
 * @name: Application.java
 * @creation: 2018年5月29日 下午5:37:09
 * @notes:  ###########################  -------> 入口
 */
@EnableCaching
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addFilter("timeFilter",new TimeFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        servletContext.addListener(new ContextListener());
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}