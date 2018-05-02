package com.chat.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.main.entity.LoginUser;
import com.chat.main.mapper.LoginUserMapper;
import com.chat.main.service.LoginUserService;

@Service
public class LoginUserServiceImpl implements LoginUserService {

	@Autowired
	private LoginUserMapper loginUserMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return loginUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LoginUser record) {
		return loginUserMapper.insert(record);
	}

	@Override
	public int insertSelective(LoginUser record) {
		return loginUserMapper.insertSelective(record);
	}

	@Override
	public LoginUser selectByPrimaryKey(Integer id) {
		return loginUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginUser record) {
		return loginUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginUser record) {
		return loginUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public LoginUser loginValid(String userName, String password) {
		return loginUserMapper.loginValid(userName, password);
	}

	@Override
	public List<LoginUser> getFriendList(String username) {
		return loginUserMapper.getFriendList(username);
	}

}
