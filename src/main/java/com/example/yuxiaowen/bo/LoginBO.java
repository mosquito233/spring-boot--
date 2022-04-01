package com.example.yuxiaowen.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginBO {
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
