
package com.example.yuxiaowen.webConfig;

import com.example.yuxiaowen.Interceptor.InterceptorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class myConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorConfig interceptorConfig = new InterceptorConfig();
        interceptorConfig.setStringRedisTemplate(stringRedisTemplate);
        String[] path = {"/**"}; // 如果拦截全部可以设置为 /**
        String[] excludePath = {"/**/login","/**/register"}; // 不需要拦截的接口路径
        registry.addInterceptor(interceptorConfig).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}
