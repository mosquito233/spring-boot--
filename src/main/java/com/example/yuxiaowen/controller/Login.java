package com.example.yuxiaowen.controller;


import com.example.yuxiaowen.bo.TokenBO;
import com.example.yuxiaowen.dto.UserDO;
import com.example.yuxiaowen.bo.CommonResultDTO;
import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.service.LoginService;
import com.example.yuxiaowen.threadlocal.RequestContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/test")
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class Login {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate<String, Integer> strRedisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${timeout}")
    private String timeout;

    @PostMapping("/apolloTest")
    public String list() {
        System.out.println(timeout);
        return "11" + timeout;
    }

    //注册
    @PostMapping("/register")
    public CommonResultDTO<?> register(@RequestBody @Valid LoginBO loginBO) {
        log.info("注册信息loginBO:{}", loginBO);
//        String productId = RequestContextHolder.get();
//        if (Objects.equals(productId, "1111")) {
//            System.out.println("去老网关");
//        } else {
//            System.out.println("新");
//        }
        return loginService.register(loginBO);
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            System.out.println(uuid);
//        }
//
//        System.out.println(new Date().getTime());
//    }

    //登录
    @SneakyThrows
    @PostMapping("/login")
    public CommonResultDTO<?> login(@RequestBody @Valid LoginBO loginBO) {
        log.info("login loginBO:{}", loginBO);

        UserDO response = loginService.login(loginBO);

        if (StringUtils.isEmpty(response)) {
            return new CommonResultDTO<>(400, "没有这个账户，请重新登录", null);
        }

        //登录成功，生成一个随机的uuid，当成token存入redis
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("uuid:{}", uuid);
        String key = "LoginToken_" + uuid;
        log.info("token key:{}", key);
        TokenBO tokenBO = new TokenBO();
        tokenBO.setLoginTime(new Date().getTime());
        tokenBO.setUserId(response.getId());
        log.info("token value:{}", tokenBO);
        stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(tokenBO), 10, TimeUnit.MINUTES);

        tokenBO.setToken(uuid);
        return new CommonResultDTO<>(200, "登录成功", tokenBO);
    }

    //注销
    @PostMapping("/loginout")
    public CommonResultDTO<?> loginout(HttpServletRequest request) {
        log.info("loginout resquest:{}", request);
        String token = request.getHeader("token");
        String key = "LoginToken_" + token;
        stringRedisTemplate.delete(key);
        return new CommonResultDTO<>(200, "注销成功", null);
    }

    @SneakyThrows
    @PostMapping("/handle")
    public CommonResultDTO<?> handle(HttpServletRequest request) {
        String token = RequestContextHolder.get();
        TokenBO tokenBO = objectMapper.readValue(token, TokenBO.class);
        log.info("convert Json to Class:{}", tokenBO);
        //判断与当前事件超时，如果超时，就登出
        return null;
    }
}
