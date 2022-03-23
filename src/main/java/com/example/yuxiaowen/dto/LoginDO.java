package com.example.yuxiaowen.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空~")
    private String userName;
    /**
     * 密码
     */
    @NotBlank
    private String password;
//    /**
//     * 邮箱
//     */
//    @Email
//    @NotEmpty
//    private String email;
}
