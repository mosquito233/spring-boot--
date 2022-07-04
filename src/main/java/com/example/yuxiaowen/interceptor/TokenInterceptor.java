package com.example.yuxiaowen.interceptor;

import com.example.yuxiaowen.bo.TokenBO;
import com.example.yuxiaowen.threadlocal.RequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

@Slf4j
@Service
public class TokenInterceptor implements HandlerInterceptor {

    public StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate1) {
        stringRedisTemplate = stringRedisTemplate1;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        1.没有token，提示"系统无token";
//        2.token存在，redis里面无对应的token，提示"redis无对应的token"，否则，提示"redis中token匹配成功"
        System.out.println("执行了拦截器里preHandle方法");
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            log.info("系统无token");
            return false;
        }
        String key = "LoginToken_" + token;
        String value = stringRedisTemplate.opsForValue().get(key);
        log.info("handle Token result:{}", value);
        if (StringUtils.isEmpty(value)){
            log.info("redis无对应的token");
            return false;
        }
        //将redis中缓存的token存入threadLocal;
        RequestContextHolder.set(value);
        log.info("redis中token匹配成功");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("执行了拦截器里postHandle方法");
        RequestContextHolder.remove();
    }
}
