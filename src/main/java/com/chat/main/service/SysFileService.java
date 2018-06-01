package com.chat.main.service;

import java.util.List;
import java.util.Map;

import com.chat.main.entity.SysFile;

public interface SysFileService {

	int deleteByPrimaryKey(Integer fileid);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Integer fileid);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);
    
    /**
     * 根据文件路径，文件类型，查询的条数查询文件列表
     * @param filePath 文件路径
     * @param fileType 文件类型
     * @param showNum 查询的条数
     * @return
     */
    List<SysFile> selectSysFileList(String filePath, String fileType, int showNum);
    
    Map<String, Object> findAllFile(int currentPage, int pageSize);
	
}
