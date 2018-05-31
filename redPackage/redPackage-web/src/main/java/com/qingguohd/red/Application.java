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
 * ###### WEB接口入口处,打包目前是以WAR形式
 * ###### 启动方式暂时放tomcat里,可以用命令指定,后期用命令+自定义端口和开发模式加载
 * @author: YuGenHai
 * @name: Application.java
 * @creation: 2018年5月29日 下午5:37:09
 * @notes:  ###########################  -------> 请阅读注释后操作
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