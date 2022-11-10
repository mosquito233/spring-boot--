package com.example.yuxiaowen.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author yuxiaowen
 * @Date 2022/7/13
 */
public class HelloJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("时间" + sf.format(date));
        System.out.println("Hello Quartz");
        System.out.println("开始生成任务报表 或 开始发送邮件....");
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        System.out.println("jobDetail 的name ： "+key.getName());     //打印jobDetail 的name
        System.out.println("jobDetail 的group ： "+key.getGroup());    //打印jobDetail 的group
        JobDataMap jobDetailDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = jobDetailDataMap.getString("message"); //
        float floatJobValue = jobDetailDataMap.getFloat("FloatJobValue");
        System.out.println("jobDataMap定义的message的值 : "+message );  //打印jobDataMap定义的message的值
        System.out.println("jobDataMap定义的floatJobValue的值 : "+floatJobValue );   //jobDataMap定义的floatJobValue的值53
    }
}
