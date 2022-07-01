package com.example.yuxiaowen.controller;


import com.example.yuxiaowen.bo.TokenBO;
import com.example.yuxiaowen.dto.UserDO;
import com.example.yuxiaowen.bo.CommonResultDTO;
import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.service.LoginService;
import com.example.yuxiaowen.threadLocal.RequestContextHolder;
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
import javax.servlet.http.HttpSession;
import javax.validation.*;
import java.text.SimpleDateFormat;
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
        log.info("登录信息loginBO:{}", loginBO);

        UserDO response = loginService.login(loginBO);

        if (StringUtils.isEmpty(response)) {
            return new CommonResultDTO<>(400, "没有这个账户，请重新登录", null);
        }

        //登录成功，生成一个随机的uuid，当成token存入redis
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        log.info("uuid:{}", uuid);
        String key = "LoginToken_" + uuid;
        log.info("token key:{}", key);
        Map<String, String> value = new HashMap<>();
        value.put("userId", String.valueOf(response.getId()));
        value.put("loginTime", String.valueOf(new Date().getTime()));
        log.info("token value:{}", value);
        stringRedisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value), 10, TimeUnit.MINUTES);

        Map<String, String> data = new HashMap<>();
        data.put("token", uuid);
        return new CommonResultDTO<>(200, "登录成功", data);
    }

    //注销
    @PostMapping("/loginout")
    public CommonResultDTO<?> loginout(HttpServletRequest request) {
        log.info("登录信息loginBO:{}", request);
        HttpSession session = request.getSession();
        log.info("sessionId Loginout:{}", session.getId());
//        Integer userId = strRedisTemplate.opsForValue().get("userId");
        //获取之后，del
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new CommonResultDTO<>(400, "未有登陆用户", null);
        }
        loginService.loginout(session);
        return new CommonResultDTO<>(200, "注销成功", null);
    }

    @SneakyThrows
    @PostMapping("/handle")
    public CommonResultDTO<?> handle(HttpServletRequest request) {
        log.info("handle request:{}", request.getHeader("token"));
        String token = request.getHeader("token");
        String key = "LoginToken_" + token;
        log.info("handle Token result:{}", stringRedisTemplate.opsForValue().get(key));
        TokenBO tokenBO = objectMapper.readValue(stringRedisTemplate.opsForValue().get(key), TokenBO.class);
        log.info("convert Json to Class:{}", tokenBO);
        //判断与当前事件超时，如果超时，就登出
        return null;
    }
}
