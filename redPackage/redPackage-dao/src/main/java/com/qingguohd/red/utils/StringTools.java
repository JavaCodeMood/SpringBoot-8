package com.qingguohd.red.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: YuGenHai
 * @E_mail: yugh@Chinatelling.com
 * @Name: StringTools.java
 * @Creation: 2016年5月20日 下午4:42:54
 * @Notes: 
 */
public class StringTools {
	
	
	public static String getNextCardCode(String oldCode) {
		String newCode = String.valueOf(Long.parseLong(oldCode) + 1L);
		while (newCode.length() < 16) {
			newCode = "0" + newCode;
		}
		return newCode;
	}

	public static String[] split(String str, char ch) {
		if (str == null) {
			return null;
		}
		if (str.charAt(0) == ch) {
			str = str.substring(1);
		}
		if (str.charAt(str.length() - 1) == ch) {
			str = str.substring(0, str.length() - 1);
		}
		return str.split(Character.toChars(ch).toString());
	}

	public static void main(String[] args) {
		String[] strArg = "1$WX0102$0000000000000001$25036671".split("\\$");
		System.out.println(strArg.length);
		for (int i = 0; i < strArg.length; i++) {
			System.out.println(strArg[i]);
		}
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0) || (str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null) && (!"".equals(str.trim()));
	}

	public static boolean isNotEmpty(Long o) {
		return o != null;
	}

	public static boolean isNotEmpty(Integer o) {
		return o != null;
	}

	public static boolean isNotEmpty(Date o) {
		return o != null;
	}

	public static boolean isNotEmpty(BigDecimal o) {
		return o != null;
	}

	public static boolean isNotEmpty(Object o) {
		return o != null;
	}
}
