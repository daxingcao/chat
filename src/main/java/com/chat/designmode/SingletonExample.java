package com.chat.designmode;

/**
 * 单例模式例子
 * @author daxing.cao create by 2018/08/06
 * @version 1.0
 * 
 */
public class SingletonExample{
	
	public static void main(String[] args) {
		for(int i=0;i<2;i++) {
			Singleton instance = Singleton.getInstance();
			System.out.println(instance.toString());
		}
	}
	
}

class Singleton {
	
	private static final Singleton singleton = new Singleton();
	
	private Singleton() {}
	
	public static Singleton getInstance() {
		return singleton;
	}
	
}