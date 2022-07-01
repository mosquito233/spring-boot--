package com.example.yuxiaowen.threadLocal;


public class RequestContextHolder {
    private static final ThreadLocal<String> mapThreadLocal = new ThreadLocal<>();

    //获取当前线程存的变量
    public static String get() {
        return mapThreadLocal.get();
    }

    //设置当前线程存的变量
    public static void set(String map) {
        mapThreadLocal.set(map);
    }

    //移除当前线程存的变量
    public static void remove(){
        mapThreadLocal.remove();
    }
}
