package com.example.yuxiaowen.service.impl;

import com.example.yuxiaowen.dto.UserDO;
import com.example.yuxiaowen.bo.CommonResultDTO;
import com.example.yuxiaowen.mapper.LoginMapper;
import com.example.yuxiaowen.bo.LoginBO;
import com.example.yuxiaowen.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.validation.Validator;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    public LoginMapper loginMapper;

    @Autowired
    public Validator validator;

    @Autowired
    public RedisTemplate<String,Integer> redisTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public CommonResultDTO<?> register(LoginBO loginBO) {
        // userName unique
        // userName是否已经在数据库里，若存在，则提示该用户已存在，return；
        Integer id = loginMapper.queryByUserName(loginBO.getUserName());
        log.info("id:{}", id);
        if (id == null) {
            // new一个新的实例
            LoginBO loginBO1 = new LoginBO();
            // copy bean DO->新的实例
            BeanUtils.copyProperties(loginBO, loginBO1);
            log.info("loginBO1:{}", loginBO1);
            // 将password加密
            String encoded = passwordEncoder.encode(loginBO1.getPassword());
            log.info("encoded:{}", encoded);
            loginBO1.setPassword(encoded);
            try {
                loginMapper.insert(loginBO1);
//                throw new RuntimeException();
            } catch (Exception e) {
                log.error("insert register error! e:{}", e);
                return new CommonResultDTO<>(404, "捕获异常", null);
            }
        } else {
            return new CommonResultDTO<>(400, "注册失败", null);
        }
        return new CommonResultDTO<>(200, "注册成功", null);
    }

    @Override
    public UserDO login(LoginBO loginBO) {
        UserDO response = loginMapper.queryAllByUserName(loginBO);
        log.info("查询之后的结果:{}", response);
        String encryptCode = response.getPassword();
        boolean loginFlag = passwordEncoder.matches(loginBO.getPassword(), encryptCode);
        log.info("result:{}", loginFlag);
        if (!loginFlag) {
            return null;
        }
        return response;
    }

    @Override
    public Boolean loginout(HttpSession session){
        session.removeAttribute("userId");
//        redisTemplate.delete("userId");
        return true;
    }
}
