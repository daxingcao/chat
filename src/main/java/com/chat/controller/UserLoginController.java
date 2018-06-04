package com.chat.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.chat.utils.MD5Utils;
import com.chat.utils.MessageUtils;

@Controller
@RequestMapping("main")
public class UserLoginController {
	
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private SysFileService sysFileService;

	@RequestMapping("loginValid")
	public ModelAndView loginValid(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userName = request.getParameter("userName");
		String origPassword = request.getParameter("password");
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(origPassword)) {
			mav.addObject("info", "用户名或密码为空！");
			mav.setViewName("login");
			return mav;
		}
		String encrPassword = MD5Utils.getMD5(origPassword);
		LoginUser loginUser = loginUserService.loginValid(userName, encrPassword);
		if(loginUser == null || loginUser.getUsername() == null) {
			mav.addObject("info", "用户名或密码错误");
			mav.setViewName("login");
			return mav;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("user", loginUser);
		mav.setViewName("redirect:/main/index");
		return mav;
	}
	
	@RequestMapping("/index")
	public ModelAndView chatView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser)session.getAttribute("user");
		List<LoginUser> friendList = loginUserService.getFriendList(loginUser.getUsername());
		mav.addObject("friendList", friendList);
		mav.setViewName("WEB-INF/views/hello");
		return mav;
	}
	
	@RequestMapping("/edit")
	public ModelAndView infoEdit(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser)session.getAttribute("user");
		List<LoginUser> friendList = loginUserService.getFriendList(loginUser.getUsername());
		mav.addObject("friendList", friendList);
		mav.setViewName("WEB-INF/views/info_edit");
		return mav;
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
	
}
