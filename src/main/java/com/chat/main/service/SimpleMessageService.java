package com.chat.main.service;

import com.chat.main.entity.SimpleMessage;

public interface SimpleMessageService {
	
	/**
	 * 将发送的信息插入数据库
	 * @param simpleMessage
	 * @return
	 */
	public int insert(SimpleMessage simpleMessage);

}
