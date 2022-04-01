package com.example.yuxiaowen.controller;


import com.example.yuxiaowen.dto.UserDO;
import com.example.yuxiaowen.bo.CommonResultDTO;
import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.*;

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

    //    @Autowired
//    ErrorValidate errorValidate;
    //注册
    @PostMapping("/register")
    public CommonResultDTO<?> register(@RequestBody @Valid LoginBO loginBO) {
        log.info("注册信息loginBO:{}", loginBO);
//        errorValidate.handleValidationExceptions();
        // 参数校验
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<LoginDO>> violations = validator.validate(loginDO);
//        for (ConstraintViolation<LoginDO> violation : violations) {
//            log.error(violation.getMessage());
//        }
//        LoginBO response = loginService.register(loginDO);
        return loginService.register(loginBO);
//        throw new RuntimeException();
//        throw new IllegalArgumentException();
//        return new CommonResult<>(200, "success", response);
    }

    //登录
    @PostMapping("/login")
    public CommonResultDTO<?> login(HttpServletRequest request, @RequestBody @Valid LoginBO loginBO) {
        log.info("登录信息loginBO:{},{}", loginBO, request);
        HttpSession session = request.getSession();
        log.info("sessionId:{}",session.getId());
//        Integer userId = strRedisTemplate.opsForValue().get("userId");
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            return new CommonResultDTO<>(400, "用户已经登陆", null);
        }
        UserDO response = loginService.login(loginBO);
        if (StringUtils.isEmpty(response)) {
            return new CommonResultDTO<>(400, "没有这个账户", null);
        }
        log.info("userId:{}", response.getId());
//        strRedisTemplate.opsForValue().set("userId", response.getId());
        //赋值，设置过期时间
//        strRedisTemplate.opsForValue().set("userId", response.getId(), 20, TimeUnit.SECONDS);
        session.setAttribute("userId", response.getId());
        session.setMaxInactiveInterval(60);
        return new CommonResultDTO<>(200, "登录成功", response);
    }

    //注销
    @PostMapping("/loginout")
    public CommonResultDTO<?> loginout(HttpServletRequest request) {
        log.info("登录信息loginBO:{}", request);
        HttpSession session = request.getSession();
        log.info("sessionId Loginout:{}",session.getId());
//        Integer userId = strRedisTemplate.opsForValue().get("userId");
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new CommonResultDTO<>(400, "未有登陆用户", null);
        }
        loginService.loginout(session);
        return new CommonResultDTO<>(200, "注销成功", null);
    }
}
