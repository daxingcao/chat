package com.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date,String format) {
		if(date != null && format != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return null;
	}
	
}
