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
	private static final String REPORT_MONTH = "30 56 20 * * ?";//月报计算时间
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
	@Scheduled(cron=REPORT_MONTH)
	public void countReportMonth(){
		logger.info("月报计算开始时间"+TimeUtil.getCurrentTime()); 
		String countDate = TimeUtil.getCurrentDate();
		algorithmService.calculate("202552814E3C46A1A47742489F3250BE", "5DCDA1ACA4174B37B84A4386694EB70E", "2018-01-26", "2018-01-26");
		algorithmService.calculateByCompany("202552814E3C46A1A47742489F3250BE", "2018-01-01", "2018-01-29");
		algorithmService.calculateMonth(countDate);//计算当前日期之后月报的应出勤
		logger.info("月报计算完成");
		logger.info("月报计算完成时间"+TimeUtil.getCurrentTime()); 
	}
}
