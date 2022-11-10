package com.example.yuxiaowen.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author yuxiaowen
 * @Date 2022/9/6
 */
@Configuration
@ConfigurationProperties(prefix = "my2")
@PropertySource("classpath:my2.properties")
@Data
public class MyProperties2 {
    private int age;
    private String name;
    private String sex;

    @Override
    public String toString() {
        return "MyProperties2{" + "age=" + age + "," + "name=" + name + "," + "sex=" + sex + "}";
    }
}
