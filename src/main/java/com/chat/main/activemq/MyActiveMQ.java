package com.chat.main.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component("myActiveMQListener")
public class MyActiveMQ implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MapMessage map = (MapMessage) message;
		try {
			String string = map.getString("name");
			System.out.println(string);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
