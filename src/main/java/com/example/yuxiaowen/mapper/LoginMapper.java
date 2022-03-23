package com.example.yuxiaowen.mapper;

import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.dto.LoginDO;

public interface LoginMapper {
    /**
     * 新增注册用户
     *
     * @param loginDO
     * @return
     */
    Integer insert(LoginDO loginDO);

    /**
     * 根据用户名查询是否存在该用户
     * @param userName
     * @return
     */
    Integer queryByUserName(String userName);

    /**
     * 登录
     * @param loginDO
     * @return
     */
    LoginBO queryAllByUserName(LoginDO loginDO);
}
