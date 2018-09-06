package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chat.main.entity.LoginUser;
import com.chat.main.service.LoginUserService;
import com.chat.main.service.MessageQueueService;
import com.chat.utils.MD5Utils;

@Controller
public class TestszcvController {

	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private MessageQueueService messageQueueService;
	
	@RequestMapping("test")
	@ResponseBody
	public LoginUser test() {
		LoginUser selectByPrimaryKey = loginUserService.selectByPrimaryKey(1);
		return selectByPrimaryKey;
	}
	
	@RequestMapping("testMQ")
	@ResponseBody
	public String testMQ(String mqName,String message) {
		messageQueueService.onSendMessage(mqName, message);
		return "发送成功";
	}
	
	public static void main(String[] args) {
//		Teacher t = new Teacher("张三", "语文");
//		Student s = new Student("lisi", 18, t);
//		JSONObject fromObject = JSONObject.fromObject(s);
//		String jsonString = JSONObject.toJSONString(s);
//		JSONObject parseObject = JSONObject.parseObject(jsonString);
//		Object object = parseObject.get("teacher");
//		System.out.println(parseObject.toString());
//		System.out.println(JSONObject.parseObject(jsonString, Student.class));
//		System.out.println(JSONObject.parseObject(object.toString(), Teacher.class));
		String str = "sfds23f5";
		System.out.println(str.replaceAll("\\D", ""));
	}
	
	
}
//class Teacher{
//	private String name;
//	private String course;
//	public Teacher() {}
//	public Teacher(String name,String course) {
//		this.name = name;
//		this.course = course;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getCourse() {
//		return course;
//	}
//	public void setCourse(String course) {
//		this.course = course;
//	}
//	
//}
//class Student{
//	private String name;
//	private int age;
//	private Teacher teacher;
//	public Student() {}
//	public Student(String name,int age,Teacher teacher) {
//		this.name = name;
//		this.age = age;
//		this.teacher = teacher;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
//	public Teacher getTeacher() {
//		return teacher;
//	}
//	public void setTeacher(Teacher teacher) {
//		this.teacher = teacher;
//	}
//}
