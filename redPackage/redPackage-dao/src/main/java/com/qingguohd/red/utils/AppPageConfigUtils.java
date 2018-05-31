package com.qingguohd.red.utils;
/**
 * @Author: YuGenhai
 * @E_mail: yugh@chinatelling.com
 * @Name: AppPageConfigUtils.java
 * @Creation: 2016-6-23 下午4:23:34
 * @Notes:  APP分页
 */
public class AppPageConfigUtils {

	
	/**
	 * 移动端刷新加载数据
	 * @param pageNo
	 * @return
	 */
	public static AppCommonPageVO getPaginationSupport(String pageNo) {
		AppCommonPageVO pageUtil = new AppCommonPageVO();
		int nowPage = 1;
		if (pageNo != null && !"".equals(pageNo)) {
			nowPage = new Integer(pageNo).intValue();
		}
		int pageSize = 20;
		pageUtil.setItems(null);
		pageUtil.setTotalCount(0);
		pageUtil.setPageSize(pageSize);
		pageUtil.setStartIndex((nowPage - 1) * pageSize);
		pageUtil.setCurrPageNo(nowPage+"");
		return pageUtil;
	}
}
