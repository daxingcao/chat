package com.chat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ApplicationContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext application) throws BeansException {
		applicationContext = application;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
