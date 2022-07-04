
package com.example.yuxiaowen.webconfig;

import com.example.yuxiaowen.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenInterceptor interceptorConfig = new TokenInterceptor();
        interceptorConfig.setStringRedisTemplate(stringRedisTemplate);
        String[] path = {"/**"}; // 如果拦截全部可以设置为 /**
        String[] excludePath = {"/**/login","/**/register"}; // 不需要拦截的接口路径
        registry.addInterceptor(interceptorConfig).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}
