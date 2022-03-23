package com.example.yuxiaowen.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginBO {
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
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModify;
}
