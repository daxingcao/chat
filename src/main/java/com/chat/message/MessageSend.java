package com.chat.message;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.chat.main.entity.SimpleMessage;
import com.chat.main.service.SimpleMessageService;
import com.chat.utils.ApplicationContextUtils;


/**
 * 聊天服务器类
 * @author shiyanlou
 *
 */
@ServerEndpoint(value ="/sendMessage/{sender}")
public class MessageSend {
	
	//存放每个登录的用户session长连接
	private static Map<String, Session> sessionMap = new Hashtable<>();
		
	@OnOpen
	public void open(Session session,@PathParam("sender")String sender) {
		// 添加初始化操作
		sessionMap.put(sender, session);
		System.out.println(session);
	}
	
	/**
	 * 接受客户端的消息，并把消息发送给所有连接的会话
	 * @param message 客户端发来的消息
	 * @param session 客户端的会话
	 */
	@OnMessage
	public void getMessage(@PathParam("sender")String sender,String message, Session session) {
		// 把客户端的消息解析为JSON对象
		SimpleMessage simpleMessage = JSONObject.parseObject(message, SimpleMessage.class);
		// 在消息中添加发送日期
		simpleMessage.setCreateDt(new Date());
		simpleMessage.setSender(sender);
		//将信息插入数据库
		ApplicationContext act = ApplicationContextUtils.getApplicationContext();
		SimpleMessageService simpleMessageService = act.getBean(SimpleMessageService.class);
		try {
			simpleMessageService.insert(simpleMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//给自己页面显示信息
		simpleMessage.setIsSelf(true);
		session.getAsyncRemote().sendText(JSONObject.toJSONString(simpleMessage));
		//如果存在，给接收者发送信息
		if(sessionMap.get(simpleMessage.getReceiver()) != null) {
			Session receiverSession = sessionMap.get(""+simpleMessage.getReceiver());
			simpleMessage.setIsSelf(false);
			receiverSession.getAsyncRemote().sendText(JSONObject.toJSONString(simpleMessage));
		}
	}
	
	@OnClose
	public void close(@PathParam("sender")String sender) {
		// 添加关闭会话时的操作
		sessionMap.remove(sender);
		System.out.println("我关闭了");
	}
	
	@OnError
	public void error(Throwable t) {
		// 添加处理错误的操作
	}
}
