package com.chat.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chat.main.entity.LoginUser;
import com.chat.main.service.LoginUserService;
import com.chat.utils.MD5Utils;

@Controller
@RequestMapping("sys")
public class UserLoginController {
	
	@Autowired
	private LoginUserService loginUserService;

	@RequestMapping("loginValid")
	public ModelAndView loginValid(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userName = request.getParameter("userName");
		String origPassword = request.getParameter("password");
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(origPassword)) {
			mav.addObject("info", "用户名或密码为空！");
			mav.setViewName("login");
			return mav;
		}
		String encrPassword = MD5Utils.getMD5(origPassword);
		LoginUser loginUser = loginUserService.loginValid(userName, encrPassword);
		if(loginUser == null || loginUser.getUsername() == null) {
			mav.addObject("info", "用户名或密码错误");
			mav.setViewName("login");
			return mav;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("user", loginUser);
		mav.setViewName("redirect:/sys/index");
		return mav;
	}
	
	@RequestMapping("/index")
	public ModelAndView chatView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser)session.getAttribute("user");
		List<LoginUser> friendList = loginUserService.getFriendList(loginUser.getUsername());
		mav.addObject("friendList", friendList);
		mav.setViewName("WEB-INF/views/hello");
		return mav;
	}
	
}
