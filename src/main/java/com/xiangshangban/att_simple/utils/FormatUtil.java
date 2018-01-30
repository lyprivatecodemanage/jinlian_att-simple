package com.xiangshangban.att_simple.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Pattern;



public class FormatUtil {

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String createUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 获取当前时间一定长度时间之后的时间
	 * 
	 * @param distance
	 * @param type
	 *            Calendar.MONTH Calendar.YEAR Calendar.DATE Calendar.MINUTE
	 *            Calendar.SECOND
	 * @return
	 */
	public static String getTimeAfterNow(int distance, int type) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		now.set(type, now.get(type) + distance);
		return formatter.format(now.getTime());
	}
	
	public static String getTimeAfterTime(String date,int distance,int type)throws Exception{
		boolean flag =Pattern.matches("\\d{4}\\-\\d{2}\\-\\d{2}", date);
		SimpleDateFormat formatter;
		if(flag){
		 formatter = new SimpleDateFormat("yyyy-MM-dd");
		}else{
		 formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		Calendar now = Calendar.getInstance();
		now.setTime(formatter.parse(date));
		now.set(type, now.get(type) + distance);
		return formatter.format(now.getTime());
	}

	

	/*
	 * public static String formatDate(String str){
	 * 
	 * String [] strArray = str.split("-"); if(strArray.length==2){
	 * 
	 * }
	 * 
	 * }
	 */
	/*
	 * Calendar cal1 = Calendar.getInstance();
	 * cal1.setTime(sdf.parse(startDate)); Calendar cal2 =
	 * Calendar.getInstance(); cal2.setTime(sdf.parse(endDate)); int
	 * startDay = cal1.get(Calendar.DAY_OF_MONTH); int startMonth =
	 * cal1.get(Calendar.MONTH) + 1; int startYear =
	 * cal1.get(Calendar.YEAR); int endDay =
	 * cal2.get(Calendar.DAY_OF_MONTH); int endMonth =
	 * cal2.get(Calendar.MONTH) + 1; int endYear =
	 * cal2.get(Calendar.YEAR);
	 * 
	 * int startNum = startDay; int endNum = endDay; boolean flag =
	 * true; int daynum = 0; FormatUtil.getResult(flag, daynum, endYear,
	 * startYear, startDay, endDay, startMonth, endMonth, shiftDay,
	 * listAttendancePlanShiftDay, map, attendancePlanService);
	 */
	public static void main(String[] args) {
		int i=0;
		while(i<200){
			System.out.println(createUuid());
			i++;
		}
		//System.out.println("67".contains("6"));
	}
}
