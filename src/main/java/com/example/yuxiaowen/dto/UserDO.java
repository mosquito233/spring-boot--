package com.example.yuxiaowen.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {
    /**
     * 唯一索引
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 标识
     */
    private Integer flag;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModify;
}
