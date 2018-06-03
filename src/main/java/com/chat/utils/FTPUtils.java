package com.chat.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtils {

	private static String host;
	private static int port;
	private static String userName;
	private static String pwd;
	
	static {
		Properties pros = FileLoadUtil.loadProperties("ftp.properties");
		if(pros != null) {
			host = pros.getProperty("ftp.host");
			port = Integer.parseInt(pros.getProperty("ftp.port"));
			userName = pros.getProperty("ftp.username");
			pwd = pros.getProperty("ftp.password");
		}
	}
	//登录连接
	private static FTPClient loginFTP() throws Exception {
		FTPClient ftp = new FTPClient();
		ftp.connect(host, port);
		ftp.login(userName, pwd);
		int code = ftp.getReplyCode();
		if(!FTPReply.isPositiveCompletion(code)) {
			ftp.disconnect();
			return null;
		}
		return ftp;
	}
	
	//跳转至指定目录
	private static FTPClient skipDirectory(FTPClient ftp,String basicPath) throws Exception {
		StringBuffer str = new StringBuffer();
		if(!ftp.changeWorkingDirectory(basicPath)) {
			String[] dirs = basicPath.split("/");
			for (String dir : dirs) {
				if(dir == null || "".equals(dir))
					continue;
				str.append("/" +dir);
				if(!ftp.changeWorkingDirectory(str.toString())) {
					if(ftp.makeDirectory(str.toString())) {
						ftp.changeWorkingDirectory(str.toString());
						basicPath = str.toString();
					}else {
						return null;
					}
				}
			}
		}
		return ftp;
	}
	
	public static Map<String, Object> updateFileToFTP(InputStream is ,String fileName ,String basicPath) throws Exception {
		//连接ftp
		FTPClient ftp = loginFTP();
		if(ftp == null) {
			return MessageUtils.errorMessage("登录失败!");
		}
		//切换至指定目录
		ftp = skipDirectory(ftp, basicPath);
		if(ftp == null) {
			return MessageUtils.errorMessage("切换目录失败!");
		}
		//设置文件类型为二进制
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		if(ftp.storeFile(fileName, is)) {
			Map<String, Object> map = MessageUtils.successMessage("上传成功！");
			map.put("realPath", basicPath);
			ftp.logout();
			ftp.disconnect();
			is.close();
			return map;
		}
		return MessageUtils.errorMessage("上传失败！");
		
	}
	
	public static OutputStream updateFileToFTP(String fileName ,String basicPath) throws Exception {
		//连接ftp
		FTPClient ftp = loginFTP();
		if(ftp == null) {
			return null;
		}
		//切换至指定目录
		ftp = skipDirectory(ftp, basicPath);
		if(ftp == null) {
			return null;
		}
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		return ftp.storeFileStream(fileName);	
	}
	
	public static InputStream downloadFile(String filePath, String fileName) throws Exception {
		//连接ftp
		FTPClient ftp = loginFTP();
		if(ftp == null) {
			return null;
		}
		if(!ftp.changeWorkingDirectory(filePath)) {
			return null;
		}
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		return ftp.retrieveFileStream(fileName);
	}
	
}
