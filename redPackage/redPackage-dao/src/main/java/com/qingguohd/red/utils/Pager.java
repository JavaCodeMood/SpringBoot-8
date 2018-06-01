package com.qingguohd.red.utils;

import java.io.Serializable;

public class Pager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1044727010286916209L;

	public static final Integer MAX_PAGE_SIZE = 9999999;// 每页最大记录数限制

	private Integer pageIndex = 1;// 当前页码

	private Integer pageSize = 20;// 每页记录数

	private Integer totalCount = 0;// 总记录数

	private Integer pageCount = 0;// 总页数

	private Integer startNum = 0;// 开始行数

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getStartNum() {
		startNum = (pageIndex - 1) * pageSize;
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
}
