package com.qingguohd.red.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: YuGenHai
 * @name: GlobalDefaultExceptionHandler.java
 * @creation: 2018年5月31日 上午10:56:56
 * @notes: 全局拦截
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	
	
	/**
	 * 处理 Exception 类型的异常
	 * @author yugenhai
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> defaultExceptionHandler(Exception e) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 500);
		map.put("msg", e.getMessage());
		return map;
	}

}