package com.example.yuxiaowen;

//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableApolloConfig
@SpringBootApplication
@MapperScan("com.example.yuxiaowen.mapper")
public class YuxiaowenApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuxiaowenApplication.class, args);
    }
}
