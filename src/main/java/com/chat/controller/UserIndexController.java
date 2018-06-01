package com.chat.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chat.main.entity.LoginUser;
import com.chat.main.entity.SysFile;
import com.chat.main.service.LoginUserService;
import com.chat.main.service.SysFileService;
import com.chat.utils.FTPUtils;
import com.chat.utils.FileLoadUtil;
import com.chat.utils.MessageUtils;

@Controller
public class UserIndexController {
	
	@Autowired
	private SysFileService sysFileService;
	@Autowired
	private LoginUserService loginUserService;

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
	
	@RequestMapping("loadImage.do")
	public void loadImage(HttpServletRequest req,HttpServletResponse resp) {
		String fileId = req.getParameter("fileId");
		SysFile sysFile = sysFileService.selectByPrimaryKey(Integer.valueOf(fileId));
		resp.setContentType("image/*");
		try {
			InputStream is = FTPUtils.downloadFile(sysFile.getFilepath(), sysFile.getFilename());
			BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
			int len = 0;
			byte[] b = new byte[2014];
			while((len = is.read(b)) > 0) {
				bos.write(b, 0, len);
			}
			bos.flush();
			is.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("uploadHeadPortrait.do")
	@ResponseBody
	public Map<String, Object> uploadHeadPortrait(@RequestParam MultipartFile myFile,Integer x,Integer y,Integer width,Integer height,HttpServletRequest request) {
		String fileName = myFile.getOriginalFilename();
		//获取文件后缀名
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = sdf.format(new Date());
		fileName = format+"."+suffix;
		//解码具有给定后缀的文件
		Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(suffix);
		Map<String, Object> map = null;
		if(iterator.hasNext()) {
			ImageReader imgReader = iterator.next();
			try {
				InputStream is = myFile.getInputStream();
				//获取图片输入流
				ImageInputStream iis = ImageIO.createImageInputStream(is);
				imgReader.setInput(iis, true);
				ImageReadParam param = imgReader.getDefaultReadParam();
				
				Rectangle rect = new Rectangle(x, y, width, height);
				param.setSourceRegion(rect);
				BufferedImage bi = imgReader.read(0, param);
				//获取存储路径
				Properties properties = FileLoadUtil.loadProperties("ftp.properties");
				String basicPath = properties.getProperty("chat.image.head");
				//得到ftp的输出流
				OutputStream os = FTPUtils.updateFileToFTP(fileName, basicPath);
				if(os != null) {
					//图片写入
					ImageIO.write(bi, suffix, os);
					os.flush();
					os.close();
				}
				//保存头像相关信息
				SysFile sysFile = new SysFile();
				sysFile.setCreatedate(new Date());
				sysFile.setFilename(fileName);
				sysFile.setFilepath(basicPath);
				sysFile.setFiletype(myFile.getContentType());
				sysFileService.insertSelective(sysFile);
				//将上传头像于当前用户绑定
				LoginUser loginUser = (LoginUser) request.getSession().getAttribute("user");
				if(loginUser == null) {
					return MessageUtils.errorMessage("请先登录!");
				}
				loginUser.setHeadFileId(sysFile.getFileid());
				loginUserService.updateByPrimaryKeySelective(loginUser);
				map = MessageUtils.successMessage("修改头像成功!");
				map.put("fileId", sysFile.getFileid());
			} catch (Exception e) {
				e.printStackTrace();
				map = MessageUtils.errorMessage("修改头像失败!");
				map.put("path", "/images/tubiao.png");
			}
		}
		return map;
	}
}
