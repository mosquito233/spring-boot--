package com.example.yuxiaowen.service;

import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.dto.CommonResultDTO;
import com.example.yuxiaowen.dto.LoginDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {
    /**
     * 注册
     * @param loginDO
     * @return
     */
    CommonResultDTO<?> register(LoginDO loginDO);

    /**
     * 登录
     * @param loginDO
     * @return
     */
    LoginBO login(LoginDO loginDO);

    /**
     *
     */
    Boolean loginout(HttpSession session);
}
