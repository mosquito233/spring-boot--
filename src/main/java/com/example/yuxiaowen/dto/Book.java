package com.example.yuxiaowen.dto;

import lombok.Data;

/**
 * @Author yuxiaowen
 * @Date 2022/11/7
 */
@Data
public class Book implements java.io.Serializable{
    private static final long serialVersionUID = -2164058270260403154L;
    //书码
    private String id;
    //书名
    private String name;
}
