package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.main.entity.LoginUser;
import com.chat.main.service.LoginUserService;
import com.chat.utils.MD5Utils;

@Controller
public class TestszcvController {

	@Autowired
	private LoginUserService loginUserService;
	
	@RequestMapping("test")
	@ResponseBody
	public LoginUser test() {
		LoginUser selectByPrimaryKey = loginUserService.selectByPrimaryKey(1);
		return selectByPrimaryKey;
	}
	
	public static void main(String[] args) {
		String md5 = MD5Utils.getMD5("123456");
		System.out.println(md5);
	}
	
}
