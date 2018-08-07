package com.chat.main.service;

public interface MessageQueueService {
	
	public void onSendMessage(String queueName,String message);

}
