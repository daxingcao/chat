package com.chat.designmode;

/**
 * 装饰器模式例子
 * @author daxing.cao create by 2018/08/06
 * @version 1.0
 * 
 */
public class DecoratorExample {
	
	public static void main(String[] args) {
		DecoratorSource decorator = new DecoratorSource(new SourceImpl());
		System.out.println("----------------------------------");
		decorator.work();
	}

}

interface Source{
	
	public abstract void work();
	
}

class SourceImpl implements Source{
	
	@Override
	public void work() {
		System.out.println("source working!");
	}
}

class DecoratorSource implements Source{
	private Source source;
	
	public DecoratorSource() {}
	
	public DecoratorSource(Source source) {
		this.source = source;
	}
	
	@Override
	public void work() {
		System.out.println("before source");
		source.work();
		System.out.println("after source");
	}
	
	
}