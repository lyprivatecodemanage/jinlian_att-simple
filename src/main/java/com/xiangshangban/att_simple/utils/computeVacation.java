package com.xiangshangban.att_simple.utils;

import java.util.Calendar;
import java.util.Date;

public class computeVacation {

	/**
	 * 计算工龄
	 * @param nowTime
	 * @param workTime
	 * @return
	 */
	public int workAge(Date nowTime, Date workTime){
	    int year = 0;
	    //当前时间的年月日
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(nowTime);
	    int nowYear = cal.get(Calendar.YEAR);
	    int nowMonth = cal.get(Calendar.MONTH);
	    int nowDay = cal.get(Calendar.DAY_OF_MONTH);

	    //开始工作时间的年月日
	    cal.setTime(workTime);
	    int workYear = cal.get(Calendar.YEAR);
	    int workMonth = cal.get(Calendar.MONTH);
	    int workDay = cal.get(Calendar.DAY_OF_MONTH);

	    //得到工龄
	    year = nowYear - workYear; //得到年差
	    //若目前月数少于开始工作时间的月数，年差-1
	    if (nowMonth < workMonth){
	        year  = year - 1;
	    }else if (nowMonth == workMonth){
	        //当月数相等时，判断日数，若当月的日数小于开始工作时间的日数，年差-1
	        if (nowDay < workDay){
	            year = year - 1;
	        }
	    }
	    return year;
	}
	
	/**
	 * 计算年假天数
	 * @param workTime
	 * @return
	 */
	public int computeAnnualVacation(Date workTime){
		int AVday = 0;
		
		//获取系统当前时间
		Date date = new Date();
		//计算工龄
		int year = workAge(date,workTime);
		
		//职工累计工作已满1年不满10年的，年休假5天;已满10年不满20年的，年休假10天;已满20年的，年休假15天
		if(year<1){
			AVday = 0;
		}else if(year<10){
			AVday = 5;
		}else if(year<20){
			AVday = 10;
		}else{
			AVday = 15;
		}
		
		return AVday;
	}
}
