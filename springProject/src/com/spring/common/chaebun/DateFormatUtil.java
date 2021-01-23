package com.spring.common.chaebun;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormatUtil {

	public static String ymdFormats(String t) {
		
		String sd = "";
		Date d = new Date();
		
		if("D".equals(t.toUpperCase())) {
			sd = new SimpleDateFormat("yyyymmdd").format(d);
		}
		if("M".equals(t.toUpperCase())) {
			sd = new SimpleDateFormat("yyyymm").format(d);
		}
		if("Y".equals(t.toUpperCase())) {
			sd = new SimpleDateFormat("yyyy").format(d);
		}
		if("N".equals(t.toUpperCase())) {
			sd = "";
		}
		
		return sd;
	}
}
