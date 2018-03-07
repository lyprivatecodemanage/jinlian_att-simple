package com.xiangshangban.att_simple.scheduler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiangshangban.att_simple.service.AlgorithmService;
import com.xiangshangban.att_simple.service.ClassesService;
import com.xiangshangban.att_simple.utils.TimeUtil;
/**
 * 定时器类
 * @author 王勇辉
 *
 */
@Component
public class Scheduler {
	
	//每天凌晨2点执行
	private static final String SCHEDULED_TIME = "0 0 2 * * ?";
	//每天凌晨1点执行
	private static final String REPORT_TIME = "0 0 1 * * ?";//日报计算时间
	//每天凌晨0点执行
	private static final String REPORT_MONTH = "30 55 16 * * ?";//月报计算时间
	private final Logger logger = Logger.getLogger(Scheduler.class);
	
	@Autowired
	private ClassesService classesService;
	@Autowired
	private AlgorithmService algorithmService;
	
	/**
	 * 自动排班
	 */
	@Scheduled(cron=SCHEDULED_TIME)
	public void autoScheduling(){
		classesService.autoScheduling();
		logger.info("----自动排班----");
	}
	/**
	 * 日报计算
	 */
	@Scheduled(cron=REPORT_TIME)
	public void countReportDaily(){
		logger.info("日报计算开始时间"+TimeUtil.getCurrentTime()); 
		String countDate = TimeUtil.getLastDayDate(TimeUtil.getCurrentDate());
		algorithmService.calculate(countDate);
		algorithmService.calculateMonth(countDate);//计算当前日期之后月报的应出勤天数、时长
		logger.info(countDate+"日报计算完成");
		logger.info("日报计算完成时间"+TimeUtil.getCurrentTime()); 
	}
	/**
	 * 月报计算（测试用）
	 */
	//@Scheduled(cron=REPORT_MONTH)
	public void countReportMonth(){
		logger.info("月报计算开始时间"+TimeUtil.getCurrentTime()); 
		String countDate = TimeUtil.getCurrentDate();
		//algorithmService.calculate("001F15DE6BB64656B5F35FD9112F7DDB", "37EB363339AF405B81FB33E83C259C91", "2018-03-01");
		
		//algorithmService.calculate("001F15DE6BB64656B5F35FD9112F7DDB", "20A215C019864D1684316F4A8A5EE22E", "2018-03-05", "2018-03-05");
		//algorithmService.calculateByCompany("CBBF109CBECF4DE0BA0604E395532902", "2018-02-01", "2018-02-28");
		//algorithmService.calculateMonth(countDate);//计算当前日期之后月报的应出勤
		algorithmService.calculate("2018-03-05", "2018-03-05");
		logger.info("月报计算完成");
		logger.info("月报计算完成时间"+TimeUtil.getCurrentTime()); 
	}
}
