package com.xiangshangban.att_simple.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class computeVacation {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 计算工作时长年
	 * @param nowTime 
	 * @param workTime	
	 * @return
	 */
	public int workAge(Date nowTime, Date workTime){
	    int year = 0;
	    //结束工作时间的年月日
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
	 * 计算企业工龄时长
	 * 输入试用期结束日期计算年假天数
	 * @param workTime
	 * @return
	 */
	public Double computeAnnualVacation(Date workTime){
		double AVday = 0;
		
		//获取系统当前时间
		Date date = new Date();
		//计算工龄
		int year = workAge(date,workTime);
		
		AVday = AnnualLeave(year);
		
		return AVday;
	}
	
	
	/**
	 * 根据工龄时长  按照国家年假计算规定 计算相应年假天数
	 * 职工累计工作已满1年不满10年的，年休假5天;已满10年不满20年的，年休假10天;已满20年的，年休假15天
	 * @param year
	 * @return
	 */
	public double AnnualLeave(int year){
		double AVday = 0;
		
		if(year<0){
			AVday = -1;
		}else if(year<1){
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
	
	/**
	 * 根据  社会工龄年假数*设置系数+公司工龄年假数*设置系数+基数 = 实际年假数
	 * @param socialService
	 * @param socialServicePercent
	 * @param companyService
	 * @param companyServicePercent
	 * @param cardinalNumber
	 * @return  
	 * @throws ParseException 
	 */
	public double ABCAnnualFormula(String socialService,double socialServicePercent,String workTime,double companyServicePercent,Integer cardinalNumber) throws ParseException{
		
		//根据传入社会工龄值计算享有年假数
		double socialAnnualVacationDay = AnnualLeave(Integer.parseInt(socialService));
		
		//传入试用结束日期得到享有年假数
		double companyAnnualVacationDay = computeAnnualVacation(sdf.parse(workTime));
		
		//试用未结束 返回0天年假
		if(companyAnnualVacationDay < 0){
			return -1;
		}
		
		double b = socialAnnualVacationDay*socialServicePercent+companyAnnualVacationDay*companyServicePercent+cardinalNumber;
		
		double t = (double)Math.floor(b);
		
		return t;
	}
	
	
}
