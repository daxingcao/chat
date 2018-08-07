package com.chat.main.service.impl;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.chat.main.service.MessageQueueService;

@Service
public class MessageQueueServiceImpl implements MessageQueueService {
	
	@Resource(name="jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;

	@Override
	public void onSendMessage(String queueName, final String message) {
		jmsQueueTemplate.send(queueName, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("name", message);
				return mapMessage;
			}
		});
	}

}
