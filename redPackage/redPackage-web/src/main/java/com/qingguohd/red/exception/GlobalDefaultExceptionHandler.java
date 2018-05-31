package com.qingguohd.red.exception;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingguohd.red.utils.Pager;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author: YuGenHai
 * @name: GlobalDefaultExceptionHandler.java
 * @creation: 2018年5月31日 上午10:56:56
 * @notes: 全局拦截
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	/**
	 * 不需要返回数据，只需要成功标识的接口返回内容 updated by yangxutong, 17/4/15, 增加了默认内层code201
	 */
	public static final String SUCCESS_CODE_200 = "{\"data\":\"\", \"resultCode\":\"200\", \"resultMessage\":\"成功\"}";

	/**
	 * 系统出现异常，返回的内容
	 */
	public static final String ERROR_CODE_500 = "{\"data\":\"\", \"resultCode\":\"500\", \"resultMessage\":\"网络异常\"}";

	/**
	 * 没有登录，或者登录信息不能通过验证45607901f302ad89d91a9249d5
	 */
	public static final String ERROR_CODE_300 = "{\"data\":\"\", \"resultCode\":\"300\", \"resultMessage\":\"请先登录\"}";

	/**
	 * 非法请求，请求加密验证不通过的话，返回此结果
	 */
	public static final String ERROR_CODE_404 = "{\"data\":\"\", \"resultCode\":\"404\", \"resultMessage\":\"bad request\"}";

	/**
	 * json入数据的格式设置
	 */
	protected static final JsonConfig JSON_CONFIG = new JsonConfig();
	
	
	private String resultCode = "200";

	private String resultMessage = "";

	private Map<String, Object> data;

	/**
	 * 成功的返回对象
	 */
	public GlobalDefaultExceptionHandler() {
		this.data = new HashMap<String, Object>();
	}

	/**
	 * 自定义返回对象，如果resultCode=200, data中会主动增加201code，
	 * 否则data中不会增加code，注意手动增加
	 * 如果201是自己想要的code，记得替换
	 * 
	 * @param resultCode
	 * @param resultMessage
	 */
	public GlobalDefaultExceptionHandler(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.data = new HashMap<String, Object>();
	}

	/**
	 * 自动生成【成功】的的列表返回数据
	 * 
	 * @param list
	 *            数据中的list
	 * @param pager
	 *            分页信息
	 */
	public GlobalDefaultExceptionHandler(Collection<?> list, Pager pager) {
		this();
		this.addListToData(list);
		this.optPaginationDataParam(pager);
	}

	/**
	 * 自动生成【成功】的实体返回数据 ps:由于是实体，实体的属性在data中的第一层
	 * 
	 * @param obj
	 */
	public GlobalDefaultExceptionHandler(Object obj) {
		this();
		this.addObjectPropToData(obj);
	}

	/**
	 * data中增加数据，如果已有，会替换， 如果为dataValue为空，则设置为空字符串
	 *
	 * @author yangxutong
	 * @date 2017年4月10日 下午2:57:52
	 * @param dataKey
	 *            dataJson中的key
	 * @param dataValue
	 *            可以为对象，数组，字符串
	 */
	public void addDataValue(String dataKey, Object dataValue) {
		if (null == dataValue) {
			
			dataValue = "";
		}
		this.data.put(dataKey, dataValue);
	}

	/**
	 * 把addMap中所有数据加入data中，如果data中已经有了，会覆盖
	 *
	 * @author yangxutong
	 * @date 2017年4月13日 下午7:46:13
	 * @param addMap
	 */
	public void addMapToData(Map<String, Object> addMap) {
		this.data.putAll(addMap);
	}

	/**
	 * 在data中添加list对应的数组，如果已经有了会覆盖
	 *
	 * @author yangxutong
	 * @date 2017年4月11日 下午12:38:30
	 * @param list
	 */
	public void addListToData(Collection<?> list) {
		this.data.put("list", list);
	}


	/**
	 * 将分页数据放入data中
	 *
	 * @author yangxutong
	 * @date 2017年4月10日 下午3:03:15
	 * @param pageResponse
	 */
	public void optPaginationDataParam(Pager pager) {
		this.data.put("page", pager.getPageIndex());
		this.data.put("pageCount", pager.getPageCount());
		this.data.put("totalRecords", pager.getTotalCount());
		this.data.put("pageSize", pager.getPageSize());
	}

	/**
	 * 将传入对象中可以get获得的属性放入data中
	 *
	 * @author yangxutong
	 * @date 2017年4月10日 下午3:10:18
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void addObjectPropToData(Object obj) {
		if (null == obj) {
			return;
		}
		JSONObject addJson = JSONObject.fromObject(obj, JSON_CONFIG);
		this.data.putAll(addJson);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this, JSON_CONFIG).toString();
	}
	
	
	
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