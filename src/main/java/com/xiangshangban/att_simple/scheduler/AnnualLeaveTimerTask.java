package com.xiangshangban.att_simple.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.xiangshangban.att_simple.bean.AnnualLeaveJob;
import com.xiangshangban.att_simple.bean.OperateLog;
import com.xiangshangban.att_simple.bean.ReturnData;
import com.xiangshangban.att_simple.service.AnnualLeaveJobService;
import com.xiangshangban.att_simple.service.VacationService;
import com.xiangshangban.att_simple.utils.HttpRequestFactory;

/**
 * 定时任务类
 * @author mian
 *
 */
@Component  
public class AnnualLeaveTimerTask{

	@Value("${sendUrl}")
	String sendUrl;
	
	@Autowired
	AnnualLeaveJobService annualLeaveJobService;
	
	@Autowired 
	VacationService vacationService;
	
	@Scheduled(cron="0 0 0 * * ?") //每天执行一次
    public void jobExamine() {   
		System.out.println("---------------------------------");
		ReturnData returndata = new ReturnData();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String year = c.get(Calendar.YEAR)+"";
		String month = c.get(Calendar.MONTH)+1+"";
		String day = c.get(Calendar.DAY_OF_MONTH)+"";
		
		if(month.length()<2){
			month = "0"+month;
		}
		
		//查询当天是否存在定时任务
		List<AnnualLeaveJob> list = annualLeaveJobService.SelectTodayJob(year, month, day);
		
		if(list.size()>0){
			for (AnnualLeaveJob alj : list) {
				if(alj.getJobType().equals("1")){//年假一键清零
					returndata = vacationService.ResetAnnualLeave(alj.getCompanyId(), alj.getYear(), alj.getAuditorEmployeeId());
					
					//当返回值为3000 则为成功
					if(returndata.getReturnCode().equals("3000")){
						alj.setJobStatus("1");
						annualLeaveJobService.updateByPrimaryKeySelective(alj);
						
						 OperateLog operateLog = new OperateLog();
						 operateLog.setOperateEmpId(alj.getAuditorEmployeeId().trim());
						 operateLog.setOperateEmpCompanyId(alj.getCompanyId().trim());
						 operateLog.setOperateType("3");
						 operateLog.setOperateContent("假期统计：年假一键清零定时任务");
						 String sendRequet = HttpRequestFactory.sendRequet(sendUrl, operateLog);
						 
						 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					
				}else if(alj.getJobType().equals("2")){//年假一键生成
					
					returndata = vacationService.AnnualLeaveGenerate(alj.getCompanyId(), alj.getYear(), alj.getAuditorEmployeeId());
					
					//当返回值为3000 则为成功
					if(returndata.getReturnCode().equals("3000")){
						alj.setJobStatus("1");
						annualLeaveJobService.updateByPrimaryKeySelective(alj);
						
						OperateLog operateLog = new OperateLog();
						operateLog.setOperateEmpId(alj.getAuditorEmployeeId().trim());
						operateLog.setOperateEmpCompanyId(alj.getCompanyId().trim());
						operateLog.setOperateType("3");
						operateLog.setOperateContent("假期统计：年假一键生成定时任务");
						String sendRequet = HttpRequestFactory.sendRequet(sendUrl, operateLog);
						
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
				}
			}
		}
    } 
	
}
