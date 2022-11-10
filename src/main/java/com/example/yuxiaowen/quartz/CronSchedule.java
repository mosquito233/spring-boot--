package com.example.yuxiaowen.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author yuxiaowen
 * @Date 2022/7/13
 */
public class CronSchedule {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
//        //jobDetail
//        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("cronJob").build();
//
//        //CronTrigger
//        //每日的9点40触发任务
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 47 14 * * ?")).build();
        //1.创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder
                .newJob(HelloJob.class) //定义Job类为HelloJob类，真正的执行逻辑所在
                .withIdentity("myJob", "group1") //定义name 和 group
                .usingJobData("message", "hello myJob1") //加入属性到jobDataMap
                .usingJobData("FloatJobValue", 8.88f) //加入属性到jobDataMh ap
                .build();

        //2.创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();
        //Scheduler实例
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
