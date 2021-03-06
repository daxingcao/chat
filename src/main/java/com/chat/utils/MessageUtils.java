package com.chat.utils;

import java.util.HashMap;
import java.util.Map;

import com.chat.main.entity.ReturnS;

public class MessageUtils {
	
	/**
	 * 操作类返回值处理
	 * 
	 * @param value
	 *            错误信息
	 * @return
	 */
	public static Map<String, Object> message(String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", value);
		return map;
	}
	
	public static Map<String, Object> successMessage(String msg){
		return message(true, msg, null);
	}
	
	public static Map<String, Object> errorMessage(String msg){
		return message(false, msg, null);
	}
	
	public static Map<String, Object> errorMessage(String msg, String returnCode){
		return message(false, msg, returnCode);
	}
	
	/**
	 * 生成结果Map(包含success、msg、returnCode两个键)
	 * @param success
	 * @param msg
	 * @param returnCode
	 * @return
	 */
	public static Map<String, Object> message(boolean success, String msg, String returnCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("msg", msg);
		map.put("returnCode", returnCode == null ? (success ? "0" : "1") : returnCode);
		return map;
	}
	
	public static ReturnS successReturnS(String msg){
		return returnS(true, msg, null);
	}
	
	public static ReturnS errorReturnS(String msg){
		return returnS(false, msg, null);
	}
	
	public static ReturnS errorReturnS(String msg, String returnCode){
		return returnS(false, msg, returnCode);
	}
	
	public static ReturnS returnS(boolean success, String msg, String returnCode){
		ReturnS rs = new ReturnS();
		rs.setSuccess(success);
		rs.setReturnCode(returnCode == null ? (success ? "0" : "1") : returnCode);
		rs.setMsg(msg);
		return rs;
	}
	
}
