package com.chat.designmode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理模式例子
 * @author daxing.cao create by 2018/08/07
 * @version 1.0
 *
 */
public class ProxyExample {
	
	public static void main(String[] args) {
//		UserDaoImpl target = new UserDaoImpl();
//		UserDaoImpl proxy = (UserDaoImpl) new ProxyFactory(target).getPorxyInstance();
//		proxy.save();
		System.out.println("==================================");
		UserDaoImpl user = new UserDaoImpl();
		UserDaoImpl userDaoImpl = (UserDaoImpl) new ProxyFactoryTwo(user).getProxyInstance();
		userDaoImpl.save();
	}

}
/**************必须实现接口的动态代理*****************/

class UserDaoImpl{

	public void save() {
		System.out.println("保存数据成功...");
	}
}

class ProxyFactory{
	private Object target;
	
	public ProxyFactory(Object target) {
		this.target = target;
	}
	
	public Object getPorxyInstance() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("事务开始");
				Object invoke = method.invoke(target, args);
				System.out.println("事务结束");
				return invoke;
			}
		});
	}
}

/*****************不需要实现接口的动态代理********************/
class ProxyFactoryTwo implements MethodInterceptor {
	
	private Object target;
	
	public ProxyFactoryTwo(Object target) {
		this.target = target;
	}
	
	public Object getProxyInstance() {
		Enhancer en = new Enhancer();
		en.setSuperclass(target.getClass());
		en.setCallback(this);
		return en.create();
	}

	@Override
	public Object intercept(Object arg0, Method method, Object[] arg2, MethodProxy methodProxy) throws Throwable {
		System.out.println("保存数据开始");
		Object invoke = method.invoke(target,arg2);
		System.out.println("保存数据结束");
		return invoke;
	}
	
}
