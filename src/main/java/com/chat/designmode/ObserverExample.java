package com.chat.designmode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 观察者模式例子
 * @author daxing.cao create by 2018/08/07
 * @version 1.0
 * 
 */
public class ObserverExample {
	
	public static void main(String[] args) {
		Observer observer1 = new Observer1();
		Observer observer2 = new Observer2();
		Subject.addObserver(observer1);
		Subject.addObserver(observer2);
		Map<String, Object> param = new HashMap<>();
		param.put("name", "zhangsan");
		param.put("age", 18);
		Subject.notifyObserver(param);
		Subject.delObserver(observer2);
		Subject.notifyObserver(param);
	}
	
}

class Subject{
	
	private static List<Observer> observerList = new ArrayList<>();
	
	public static void addObserver(Observer observer) {
		observerList.add(observer);
		System.out.println("add observer success...");
	}
	
	public static void delObserver(Observer observer) {
		observerList.remove(observer);
		System.out.println("delete observer success...");
	}
	
	public static void notifyObserver(Map<String, Object> param) {
		
		for (Observer observer : observerList) {
			observer.update(param);
		}
	}
	
}

interface Observer{
	
	void update(Map<String, Object> param);
	
}

class Observer1 implements Observer{

	@Override
	public void update(Map<String, Object> param) {
		System.out.println(this.toString()+"订阅的已更新,参数为"+JSONObject.toJSONString(param));
	}
	
}

class Observer2 implements Observer{

	@Override
	public void update(Map<String, Object> param) {
		System.out.println(this.toString()+"订阅的已更新,参数为"+JSONObject.toJSONString(param));
	}
	
}