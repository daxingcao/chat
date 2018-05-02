package com.chat.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.chat.main.entity.LoginUser;
@Mapper
public interface LoginUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(LoginUser record);

    int insertSelective(LoginUser record);

    LoginUser selectByPrimaryKey(Integer id);
    
    /**
     * 得到好友列表
     * @param username
     * @return
     */
    List<LoginUser> getFriendList(String username);
    
    /**
     * 用户登录验证
     * @param userName
     * @param password
     * @return
     */
    public LoginUser loginValid(@Param("userName")String userName,@Param("password")String password);

    int updateByPrimaryKeySelective(LoginUser record);

    int updateByPrimaryKey(LoginUser record);
}