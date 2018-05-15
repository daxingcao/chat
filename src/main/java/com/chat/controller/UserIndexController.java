package com.chat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserIndexController {

	@RequestMapping("/index.jhtml")
	public ModelAndView skipLoginView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("WEB-INF/views/login");
		return mav;
	}
	
	@RequestMapping("/chatView")
	public ModelAndView chatView() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("WEB-INF/views/chat");
		return mav;
	}
	
}
