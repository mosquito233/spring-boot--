package com.example.yuxiaowen.mapper;

import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.dto.UserDO;

public interface LoginMapper {
    /**
     * 新增注册用户
     *
     * @param loginBO
     * @return
     */
    Integer insert(LoginBO loginBO);

    /**
     * 根据用户名查询是否存在该用户
     * @param userName
     * @return
     */
    Integer queryByUserName(String userName);

    /**
     * 登录
     * @param loginBO
     * @return
     */
    UserDO queryAllByUserName(LoginBO loginBO);
}
