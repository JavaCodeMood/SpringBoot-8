package com.qingguohd.red.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author: YuGenHai
 * @E_mail: yugh@Chinatelling.com
 * @Name: StringUtils.java
 * @Creation: 2016年4月11日 上午9:33:11
 * @Notes: 
 */

public class StringUtils {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT5 = "yyyy-MM-dd HH24:mm:ss";
	public static final String DATE_FORMAT_ORA = "yyyy-mm-dd hh24:mi:ss";
	public static final String DATE_FORMAT1 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT2 = "yyyy-MM-dd";
	public static final String DATE_FORMAT3 = "yyyyMMdd";
	public static final String DATE_FORMAT4 = "yyMMdd";
	public static final String DATA_FORMAT_HAOMIAO = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 返回当前日期时间字符串 默认格式:yyyy-mm-dd hh:mm:ss
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime() {
		// String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return f.format(date);
	}

	// 测试
	public static String getCurrentTime1() {
		// String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return f.format(date);
	}

	/**
	 * 返回自定义格式的当前日期时间字符串
	 * 
	 * @param format
	 *            格式规则
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	public static String getDateStr() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置显示格式
		String nowTime = "";
		nowTime = df.format(dt);
		return nowTime;
	}

	/**
	 * @return date时间
	 */
	public static Object simpleDate() {
		Date date = new Date();
		java.sql.Date aDate = new java.sql.Date(date.getTime());
		return aDate;

	}

	
	public static void main(String[] args) throws Exception {
		System.out.println(stringFormateDate("2017-05-19 17:17:06"));
	}

	
	public static String getSystemDateTime(String datepattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(datepattern);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		return df.format(Calendar.getInstance().getTime());
	}

	// 返回当前系统时间
	public static Date getSystemDateTime() {
		return Calendar.getInstance().getTime();
	}
	
	
	/**
	 * string format date
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date stringFormateDate(String date) throws Exception{
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 ParsePosition pos = new ParsePosition(0);
		 Date strtodate = format.parse(date, pos);
		 return strtodate;
	}
	
	
	/**
	 * 针对银行拆分
	 * @param str
	 * @return
	 */
	public static String[] matchChinese(String str){
		String chinese = "[^(\\u4e00-\\u9fa5)]";//中文
		//String bankCode = "[^(0-9)]";//银行账号
		return chinese.split(chinese, 2);
	}
	
	
}
