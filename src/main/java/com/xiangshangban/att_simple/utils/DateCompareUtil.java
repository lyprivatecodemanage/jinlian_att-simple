package com.xiangshangban.att_simple.utils;

import java.util.Date;

public class DateCompareUtil {
	/**
	 * 比较日期大小
	 * @param date1
	 * @param date2
	 * @return 0:{ date1小于date2 },1:{ date1大于date2 },2:{ date1等于date2 },3:{ 未知,检查参数 }
	 */
	public static int compareTowDate(Date date1,Date date2){
		if(date1.getTime()>date2.getTime()){
			return 0;
		}else if(date1.getTime()<date2.getTime()){
			return 1;
		}else if(date1.getTime() == date2.getTime()){
			return 2;
		}else{
			return 3;
		}
	}
}
