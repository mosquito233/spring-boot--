package com.example.yuxiaowen.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author yuxiaowen
 * @Date 2022/9/6
 */
@Configuration
@ConfigurationProperties(prefix = "my1")
@Data
public class MyProperties1 {
    private int age;
    private String name;

    @Override
    public String toString(){
        return "MyProperties1{"+"age="+age+","+"name="+name+"}";
    }
}
