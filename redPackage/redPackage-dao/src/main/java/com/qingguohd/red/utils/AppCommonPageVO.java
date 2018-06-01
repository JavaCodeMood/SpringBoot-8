package com.qingguohd.red.utils;

import java.io.Serializable;
import java.util.List;


/**
 * @Author: YuGenhai
 * @E_mail: yugh@chinatelling.com
 * @Name: AppCommonPageVO.java
 * @Creation: 2016-6-23 下午4:15:36
 * @Notes:  APP分页
 */

@SuppressWarnings("rawtypes")
public class AppCommonPageVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private List items;// 数据对象(类型安全由使用时控制)
	private Integer startIndex;// 页码  -- 
	private Integer totalCount;// 数据总数 --
	private Integer pageSize = 20;// 每页数量 
	private String currPageNo;// 当前页码 --
	private Integer totalPages;// 总页数 -- 

	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getCurrPageNo() {
		return currPageNo;
	}
	public void setCurrPageNo(String currPageNo) {
		this.currPageNo = currPageNo;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

}
