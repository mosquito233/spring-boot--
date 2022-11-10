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
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.ObjectUtils;
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

    @Autowired
    private MyProperties1 myProperties1;

    @Autowired
    private MyProperties2 myProperties2;

    @PostMapping("/1")
    public String list1(){
        System.out.println(myProperties1.toString());
        System.out.println(myProperties2.toString());
        return "success";
    }

    @Value("${timeout}")
    private String timeout;

    @PostMapping("/apolloTest")
    public String list() {
        System.out.println(timeout);
        return "11" + timeout;
    }



//        public static void main(String[] args) {
//
//            String openId = "baa6022bea3743d9b9758d4a3ab6e0ce";
//
//            int SHARDING_THRESHOLD = 0xffff;
//
//            int seed = 131;
//
//            long hash = 0;
//
//            for (int i = 0; i < openId.length(); i++) {
//
//                hash = (hash * seed) + openId.charAt(i);
//
//            }
//
//            int shardValue = (int) (hash & 0x7fffffffffffffffL & SHARDING_THRESHOLD);
//
//            System.out.println(shardValue);
//
//        }



    public static boolean uumain(String[] args) {
        System.out.println("111111");
        String demo1 = "sssrrruuu";
        String demo2 = "rrsssruuu";
//        Map<String, Integer> map = new HashMap<>();
//        Map<String, Integer> map1 = new HashMap<>();
//        Integer count = 0;
//        Integer count1 = 0;
//        for (int i = 0; i < demo1.length(); i++) {
//            map.put(split[i], count++);
//        }
//        for (int i = 0; i < demo2.length(); i++) {
//            map1.put(demo2[i], count1++);
//        }
        //数组1
        int[] num1 = new int[26];
        //数组2
        int[] num2 = new int[26];
        for (int i = 0; i < demo1.length(); i++) {
            num1[demo1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < demo2.length(); i++) {
            num2[demo2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (num1[i] != num2[i]) {
                return false;
            }
        }
        return true;
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
    @Cacheable(cacheNames = "user",key = "id")
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

        //collection sort排序
        List<Integer> collection = new ArrayList<>();
        collection.add(5);
        collection.add(1);
        collection.add(3);
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            log.info("数组：{}", iterator.next());
        }
        log.info("数组1：{}", collection);
        Collections.sort(collection, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        log.info("排序后数组2：{}", collection);

        //字符串collection排序
        List<String> strs = new ArrayList<>();
        strs.add("i");
        strs.add("h");
        strs.add("l");
        strs.add("o");
        Collections.sort(strs);
        log.info("排序后字符串：{}", strs);
        //判断与当前事件超时，如果超时，就登出
        TokenBO test = new TokenBO();
        //判断两个对象是否equals，有时候对象为null，会报空指针这个方法很好地避免了这种情况
        if (ObjectUtils.nullSafeEquals(test.getUserId(), 1)) {
            log.info("对上");
        }
        return null;
    }
}
