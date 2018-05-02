package com.chat.main.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.chat.main.entity.SimpleMessage;

@Mapper
public interface SimpleMessageMapper {

    int deleteByPrimaryKey(String id);

    int insert(SimpleMessage record);

    int insertSelective(SimpleMessage record);

    SimpleMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SimpleMessage record);

    int updateByPrimaryKey(SimpleMessage record);
}