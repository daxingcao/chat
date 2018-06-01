package com.chat.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.main.entity.SysFile;
import com.chat.main.mapper.SysFileMapper;
import com.chat.main.service.SysFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 上传文件
 * @author Administrator
 *
 */
@Service
public class SysFileServiceImpl implements SysFileService {

	@Autowired
	private SysFileMapper sysFileDao;
	
	@Override
	public int deleteByPrimaryKey(Integer fileid) {
		return sysFileDao.deleteByPrimaryKey(fileid);
	}

	@Override
	public int insert(SysFile record) {
		return sysFileDao.insert(record);
	}

	@Override
	public int insertSelective(SysFile record) {
		return sysFileDao.insertSelective(record);
	}

	@Override
	public SysFile selectByPrimaryKey(Integer fileid) {
		return sysFileDao.selectByPrimaryKey(fileid);
	}

	@Override
	public int updateByPrimaryKeySelective(SysFile record) {
		return sysFileDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysFile record) {
		return sysFileDao.updateByPrimaryKey(record);
	}

	@Override
	public List<SysFile> selectSysFileList(String filePath, String fileType, int showNum) {
		return sysFileDao.selectSysFileList(filePath, fileType, showNum);
	}

	@Override
	public Map<String, Object> findAllFile(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<SysFile> sysFileList = sysFileDao.findAllFile();
		PageInfo<SysFile> page= new PageInfo<>(sysFileList);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", page.getList());
		map.put("total", page.getTotal());
		return map;
	}

}
