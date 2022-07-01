package com.example.yuxiaowen.bo;

import lombok.Data;

@Data
public class TokenBO {

    /**
     * 登录时间（时间戳）
     */
    private long loginTime;

    /**
     * 用户id
     */
    private Integer userId;
}
