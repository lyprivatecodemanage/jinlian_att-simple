package com.xiangshangban.att_simple.dao;

import javax.annotation.Resource;  

import org.quartz.CronScheduleBuilder;  
import org.quartz.CronTrigger;  
import org.quartz.JobDetail;  
import org.quartz.Scheduler;  
import org.quartz.SchedulerException;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.scheduling.annotation.EnableScheduling;  
import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.Component;  
  

@Configuration  
@EnableScheduling  
@Component 
public class ScheduleRefreshDatabase {
	
}
