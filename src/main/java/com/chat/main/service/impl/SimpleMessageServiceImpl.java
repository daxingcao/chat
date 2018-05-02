package com.chat.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.main.entity.SimpleMessage;
import com.chat.main.mapper.SimpleMessageMapper;
import com.chat.main.service.SimpleMessageService;

@Service
public class SimpleMessageServiceImpl implements SimpleMessageService {

	@Autowired
	private SimpleMessageMapper simpleMessageMapper;
	
	@Override
	public int insert(SimpleMessage simpleMessage) {
		return simpleMessageMapper.insertSelective(simpleMessage);
	}

}
