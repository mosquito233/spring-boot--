package com.example.yuxiaowen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //全参构造器
public class CommonResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
}
