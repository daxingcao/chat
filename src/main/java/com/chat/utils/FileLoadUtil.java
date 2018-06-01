package com.chat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileLoadUtil {

	private static Properties pros;
	
	public static Properties loadProperties(String fileName) {
		InputStream is = FileLoadUtil.class.getClassLoader().getResourceAsStream(fileName);
		pros = new Properties();
		try {
			pros.load(is);
			return pros;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
