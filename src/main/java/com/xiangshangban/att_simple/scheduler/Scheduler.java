package com.xiangshangban.att_simple.scheduler;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiangshangban.att_simple.service.ClassesService;

/**
 * 定时器类
 * @author 王勇辉
 *
 */
@Component
public class Scheduler {
	
	//每天凌晨2点执行
	private static final String SCHEDULED_TIME = "0 0 2 * * ?";
	
	private final Logger logger = Logger.getLogger(Scheduler.class);
	
	@Autowired
	private ClassesService classesService;
	/**
	 * 自动排班
	 */
	@Scheduled(cron=SCHEDULED_TIME)
	public void autoScheduling(){
		/*classesService.autoScheduling();*/
		logger.info("----自动排班----");
	}
}
