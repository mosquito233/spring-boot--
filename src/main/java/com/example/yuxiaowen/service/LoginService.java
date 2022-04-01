package com.example.yuxiaowen.service;

import com.example.yuxiaowen.dto.UserDO;
import com.example.yuxiaowen.bo.CommonResultDTO;
import com.example.yuxiaowen.bo.LoginBO;

import javax.servlet.http.HttpSession;

public interface LoginService {
    /**
     * 注册
     * @param loginBO
     * @return
     */
    CommonResultDTO<?> register(LoginBO loginBO);

    /**
     * 登录
     * @param loginBO
     * @return
     */
    UserDO login(LoginBO loginBO);

    /**
     *
     */
    Boolean loginout(HttpSession session);
}
