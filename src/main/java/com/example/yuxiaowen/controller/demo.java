package com.example.yuxiaowen.controller;

import com.example.yuxiaowen.service.LoginDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@Slf4j
@RestController
@RequestMapping("/demo")
public class demo {

    @Autowired
    LoginDemoService login;
    @Autowired
    DataSource dataSource;

//    @GetMapping("/first")
//    public String Hello() {
//        return "hello world!";
//    }
//
//    @PostMapping("/second")
//    public ArrayList<Integer> Hello1(@RequestBody Body body) {
//        ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        arrayList.add(1);
//        arrayList.add(2);
//        arrayList.add(3);
//        return arrayList;
//    }

//    @PostMapping("/four")
//    public ArrayList<Integer> Hello3(@RequestBody Body body) {
//
//    }

//    @PostMapping("/third")
//    public ArrayList<String> Hello2(@RequestBody Body body) {
//        ArrayList<String> arrayList = new ArrayList<String>();
////        Connection connection = null;
////        try {
////            connection = dataSource.getConnection();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        log.info(connection.toString());
////        arrayList.add(1);
////        arrayList.add(2);
////        arrayList.add(3);
//        login.test();
//        return arrayList;
//    }
//
//    @Data
//    static class Body{
//        Integer id;
//    }
}
