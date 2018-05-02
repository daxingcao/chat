package com.chat.main.service;

import java.util.List;

import com.chat.main.entity.LoginUser;

public interface LoginUserService {

    int deleteByPrimaryKey(Integer id);

    int insert(LoginUser record);

    int insertSelective(LoginUser record);

    LoginUser selectByPrimaryKey(Integer id);
    
    /**
     * 用户登录验证
     * @param userName
     * @param password
     * @return
     */
    public LoginUser loginValid(String userName,String password);
    
    /**
     * 得到好友列表
     * @param username
     * @return
     */
    public List<LoginUser> getFriendList(String username);

    int updateByPrimaryKeySelective(LoginUser record);

    int updateByPrimaryKey(LoginUser record);
}
