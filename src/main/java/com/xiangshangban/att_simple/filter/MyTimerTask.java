package com.xiangshangban.att_simple.filter;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.xiangshangban.att_simple.service.AnnualLeaveJobService;

/**
 * 任务类
 * @author mian
 *
 */
public class MyTimerTask extends TimerTask{

	@Autowired
	AnnualLeaveJobService annualLeaveJobService;
	
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次  
    public void statusCheck() {      
    } 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}