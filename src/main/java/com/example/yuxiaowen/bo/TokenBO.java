package com.example.yuxiaowen.bo;

import lombok.Data;

import java.util.Date;

@Data
public class TokenBO {
    /**
     * uuid
     */
    private String token;
    /**
     * 登录时间（时间戳）
     */
    private long loginTime;

    /**
     * 用户id
     */
    private Integer userId;
}
