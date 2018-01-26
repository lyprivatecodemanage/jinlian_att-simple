package com.xiangshangban.att_simple.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class TimeUtil {

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static String getDateBefore(String time,String d){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try {
			Date date = null;
			try{
			date = format.parse(time);
			}catch(Exception e){
				date = format1.parse(time);
			}
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			int day = now.get(Calendar.DAY_OF_WEEK) - Integer.valueOf(d);
			now.set(Calendar.DATE, now.get(Calendar.DATE) - Integer.valueOf(d));
			return format.format(now.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param time 时间基数
	 * @param days 在时间基数上要推迟的天数 
	 * @return
	 */
	public static String getDateAfter(String time,int days) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(time);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			int day = now.get(Calendar.DAY_OF_WEEK) - days;
			now.set(Calendar.DATE, now.get(Calendar.DATE) - day + 6);
			return format.format(now.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 返回星期几
	 * @param dayOfWeek
	 * @return
	 */
	public static String getWeek(int dayOfWeek){
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return weekDays[ dayOfWeek - 1 ];
	}
	
	/**
	 * 根据两个时间返回显示格式
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getFormatTime(String begin,String end){
		if( StringUtils.isEmpty(end)){
			return begin.substring(0, 16);
		}else{
			if(begin.equals(end)){
				return begin.substring(0, 16);
			}else if(begin.substring(0, 10).equals(end.substring(0, 10))){
				return begin.substring(0, 10)+ " " +begin.substring(11, 16)+"~"+end.substring(11, 16);
			}else{
				return begin.substring(0, 16)+"~"+end.substring(0, 16);
			}
		}
	}
	
	/**
	 * 或取上个月第一天日期yyyy-MM-dd
	 * @return
	 */
	public static String getLastMonthFirstDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
	    //取得上一个月时间
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
	    String lastMonth= sdf.format(calendar.getTime()); 
	    return lastMonth+"-01";
	}
	/**
	 * 获取本月1日日期
	 * @return
	 */
	public static String getCurrentMonthFirstDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar.getTime());
    }
	/**
	 * ture:取当月最大日期;false:取当月第一天日期
	 * @param flag
	 * @return
	 */
	public static String getCurrentMonthFirstDateOrMaxDate(boolean flag){
		int i = 1;
		if(flag){
			i = getCurrentMaxDate();
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, i);
        return sdf.format(calendar.getTime());
	}
	/**
	 * 获取下月1日日期
	 * @return
	 */
	public static String getNextMonthFirstDate() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return sdf.format(calendar.getTime());
    }
	/**
	 * 或取上个月最后一天日期yyyy-MM-dd
	 * @return
	 */
	public static String getLastMonthEndDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
	    //取得上一个月时间
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
	    String lastMonth= sdf.format(calendar.getTime());
	    int num = calendar.getActualMaximum(Calendar.DATE);
	    return lastMonth+"-"+num;
	}
	/**
	 * 或取上个月月份
	 * @return
	 */
	public static String getLastMonth(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
	    //取得上一个月时间
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
	    String lastMonth= sdf.format(calendar.getTime());
	    return lastMonth;
	}
	/**
	 * 或取当前日期字符串
	 * @return
	 */
	public static String getCurrentDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(new Date());
	}
	/**
	 * 或取当前时间字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return sdf.format(new Date());
	}
	/**
	 * 或取本月最后一天日期yyyy-MM-dd
	 * @return
	 */
	public static int getCurrentMaxDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
	    int num = calendar.getActualMaximum(Calendar.DATE);
	    return num;
	}
	/**
	 * 或取某一年的某月最后一天日期yyyy-MM-dd
	 * @param month 月份  格式：yyyy-MM
	 * @return
	 */
	public static int getCurrentMaxDate(String month){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(month+"-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
	    int num = calendar.getActualMaximum(Calendar.DATE);
	    return num;
	}
	/**
	 * 或取某一年的某月的前一个月yyyy-MM
	 * @param month 月份  格式：yyyy-MM
	 * @return
	 */
	public static String getLastMonth(String month){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(month+"-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
	    return fomat.format(calendar.getTime());
	}
	/**
	 * 或取某个日期的前一天日期(默认返回当前日期前一天)
	 * @param date 设定日期字符串
	 * @return
	 */
	public static String getLastDayDate(String date){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
	    return fomat.format(calendar.getTime());
	}
	/**
	 * 或取某个日期的前一天日期(默认返回当前日期前一天)
	 * @param date 设定日期字符串
	 * @return
	 */
	public static String getNextDayDate(String date){
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
	    return fomat.format(calendar.getTime());
	}
	/**
	 * 两个日期相差的月份
	 * @param date1 较大日期
	 * @param date2 较小日期
	 * @return
	 */
	public static int monthOfDate(String date1, String date2){//date1>=date2
		int month=0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try {
			calendar1.setTime(df.parse(date1));
			calendar2.setTime(df.parse(date2));
			int dateY1=calendar1.get(Calendar.YEAR);
			int dateM1=calendar1.get(Calendar.MONTH);
			int dateDay1=calendar1.get(Calendar.DATE);
			int dateY2=calendar2.get(Calendar.YEAR);
			int dateM2=calendar2.get(Calendar.MONTH);
			int dateDay2=calendar2.get(Calendar.DATE);
			if(dateY1-dateY2>1){
				if(dateM1-dateM2>=0){
					if((dateM1-dateM2==0) && (dateDay1-dateDay2<0)){
						month=(dateY1-dateY2-1)*12+dateM1+(11-dateM2);
					}else{
						month=(dateY1-dateY2)*12+dateM1-dateM2;
					}
				}else{
					month=(dateY1-dateY2-1)*12+dateM1+(11-dateM2);
				}
			}else{
				if(dateM1-dateM2>=0){
					if(dateM1-dateM2==0){
						month=0;
					}else{
						month=dateM1-dateM2;
					}
				}else{
					month=dateM1-dateM2;
				}
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}
	/**
	 * 两个日期相差的星期数
	 * @param date1 较大日期
	 * @param date2 较小日期
	 * @return
	 */
	public static int weekOfDate(String date1, String date2){//date1>=date2
		int week = dayOfDate(date1, date2)/7;
		return week;
	}
	/**
	 * 两个日期相差的天数
	 * @param date1 较大日期
	 * @param date2 较小日期
	 * @return
	 */
	public static int dayOfDate(String date1, String date2){//date1>=date2
		long day=0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			day=(df.parse(date1).getTime()-df.parse(date2).getTime())/1000/60/60/24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) day;
	}
	/**
	 * 两个日期相差的小时数
	 * @param date1 较大日期
	 * @param date2 较小日期
	 * @return
	 */
	public static int hourOfTime(String date1, String date2){//date1>=date2
		long hour=0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			hour=(df.parse(date1).getTime()-df.parse(date2).getTime())/1000/60/60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) hour;
	}
	/**
	 * 两个日期相差的小时数
	 * @param date1 较大日期
	 * @param date2 较小日期
	 * @return
	 */
	public static int minuteOfTime(String date1, String date2){//date1>=date2
		long minute=0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			minute=(df.parse(date1).getTime()-df.parse(date2).getTime())/1000/60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) minute;
	}
	/**
	 * 查看time2是否在time1后的24小时内
	 * @param time1
	 * @param time2
	 * @return boolean
	 */
	public static boolean isIn24Hour(String time1, String time2){
		long hour=24*1000*60*60+1;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			hour=(df.parse(time2).getTime()-df.parse(time1).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(hour<=24*1000*60*60){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取一定长度时间之后的时间
	 * @param distance
	 * @param type Calendar.MONTH Calendar.YEAR Calendar.DATE Calendar.MINUTE Calendar.SECOND 
	 * @return
	 */
	public static String getTimeAfterNow(int distance,int type) {  
		 	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        Calendar now = Calendar.getInstance();  
	        now.set(type, now.get(type) + distance);  
	        return formatter.format(now.getTime());  
	}
	
	/**
	 * 获取一定长度时间之后的时间
	 * @param distance
	 * @param type Calendar.MONTH Calendar.YEAR Calendar.DATE Calendar.MINUTE Calendar.SECOND 
	 * @return
	 */
	public static long getLongAfterNow(int distance,int type) {  
	        Calendar now = Calendar.getInstance();  
	        now.set(type, now.get(type) + distance);  
	        return now.getTimeInMillis();  
	}
	/**
	 * 获取某个时间点一定长度时间之后的时间
	 * @param sourceTime 原始时间 yyyy-MM-dd HH:mm:ss
	 * @param distance 长度
	 * @param type 时间单位， 年：Calendar.YEAR，月：Calendar.MONTH，日： Calendar.DATE，分钟： Calendar.MINUTE，秒： Calendar.SECOND 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getLongAfter(String sourceTime, int distance,int type) {  
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(sourceTime));
			calendar.set(type, calendar.get(type) + distance);  
	        return fomat.format(calendar.getTime());  
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return sourceTime;
	}
	/**
	 * 获取某个日期多少天之后的日期
	 * @param sourceTime 原始时间 yyyy-MM-dd HH:mm:ss
	 * @param distance 长度
	 * @param type 时间单位， 年：Calendar.YEAR，月：Calendar.MONTH，日： Calendar.DATE
	 * @return
	 */
	public static String getLongAfterDate(String sourceTime, int distance,int type) {  
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(fomat.parse(sourceTime+" 00:00:00"));
			calendar.set(type, calendar.get(type) + distance);  
			SimpleDateFormat fomatnew = new SimpleDateFormat("yyyy-MM-dd");
	        return fomatnew.format(calendar.getTime());  
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return sourceTime;
	}
	/**
	 * 获得当前年份
	 * @return
	 */
	public static String getCurentYear(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); 
		return sdf.format(new Date());
	}
	/**
	 * 获得当前年月，格式yyyy-MM
	 * @return
	 */
	public static String getCurrentMonth() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
		return sdf.format(new Date());
	}
	
	/**
	 * 日期转换：将形如“2016-01-01”转成“2016年1月1日”
	 * @param sourceDate 原始日期串
	 * @param split 分隔符
	 * @return
	 */
	public static String getDateString(String sourceDate, String split){
		String [] dateArr = sourceDate.split(split);
		return dateArr[0]+"年"+Integer.parseInt(dateArr[1])+"月"+Integer.parseInt(dateArr[2])+"日";
	}
	/**
	 * 获取指定日期在当前日期之前的时间差，如：刚刚，n分钟前，n天前，n月前，n年前
	 * @param sourceTime 原始时间串
	 * @param format 字符串格式  如：sourceDate="2016-05-26 00:00:00"时format="yyyy-MM-dd HH:mm:ss"
	 * @return 小于当前时间：返回  刚刚/n分钟前/n天前/n月前/n年前
	 */
	public static String getTimeBefore(String sourceTime, String format){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curRentTime = formatter.format(System.currentTimeMillis());
		int minute = minuteOfTime(curRentTime,sourceTime);
		if(minute==0){
			return "刚刚";
		}
		int hour = hourOfTime(curRentTime,sourceTime);
		if(hour==0){
			return minute+"分钟前";
		}
		int chaDay = dayOfDate(curRentTime,sourceTime);
		if(chaDay==0){
			return hour+"小时前";
		}
		
		int chaMon = monthOfDate(curRentTime,sourceTime);
		if(chaMon==0){
			return chaDay+"天前";
		}
		if(chaMon>=12){
			return chaMon/12+"年前";
		}else{
			return chaMon+"月前";
		}
		
	}
	/**
	 * 比较某个时间点之后的一段时间的时间点是否大于当前时间点
	 * @param time 某个时间点
	 * @param type 距离单位Calendar.MONTH Calendar.YEAR Calendar.DATE Calendar.MINUTE Calendar.SECOND 
	 * @param distance 距离
	 * @return true:小于，
	 */
	public static boolean compareTimeWithNow(String time, int type, int distance){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        String currentDate = formatter1.format(now.getTime());
        String compareTime = currentDate+" "+time;
        try {
			Date date = formatter.parse(compareTime);
			 Calendar compare = Calendar.getInstance();
			 compare.setTime(date);
			 compare.set(type, compare.get(type) + distance);  
		     return now.getTimeInMillis()<compare.getTimeInMillis();  
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断某个日期是否等于当前日期
	 * @param date
	 * @return true:是/false：否/error：转换出错
	 */
	public static String isToday(String dateS){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateS = formatter.format(formatter.parse(dateS));
		} catch (ParseException e) {
			return "error";
		}
		String today = formatter.format(new Date());
		return today.equals(dateS)?"true":"false";
	}
	/**
	 * 两个时间long值比较大小
	 * @param newTime 新的时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param oldTime 老的时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return 若newTime大于oldTime则true，否则false
	 */
	public static boolean compareTime(String newTime, String oldTime){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			 return formatter.parse(newTime).getTime()>formatter.parse(oldTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
       return false;
	}
	/**
	 * 获取两个时间点的中间点
	 * @param time1   时间1 ， 格式：yyyy-MM-dd HH:mm:ss
	 * @param time2   时间2 ，晚于time1， 格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCenter(String time1, String time2) {
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long distance = (formatter.parse(time2).getTime()-formatter.parse(time1).getTime())/2;
			return formatter.format(formatter.parse(time1).getTime()+distance);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取两个时间的间隔多少秒(绝对值)
	 * @param time1 时间1，yyyy-MM-dd HH:mm:ss
	 * @param time2 时间2，yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long getTimeLength(String time1, String time2) {
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t1 = formatter.parse(time1).getTime();
			long t2 = formatter.parse(time2).getTime();
			long distance = (t1>t2?(t1-t2):(t2-t1))/1000;
			return distance;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 两个时间段的交叉关系
	 * @param beginTime1 时段1的开始，yyyy-MM-dd HH:mm:ss
	 * @param endTime1   时段1的结束，yyyy-MM-dd HH:mm:ss
	 * @param beginTime2 时段2的开始，yyyy-MM-dd HH:mm:ss
	 * @param endTime2 时段2的结束，yyyy-MM-dd HH:mm:ss
	 * @return  “1”：时段2早于时段1； “2”：时段2开始小于等于时段1开始，并且时段2结束大于时段1开始，时段2结束时间小于时段1结束；
	 * “3”：时段2包含在时段1内，但时段2不等于时段1； “4”：时段1开始小于时段2开始，时段1结束时间大于时段1开始，并且时段2结束大于时段1结束；
	 * “5”：时段1包含在时段2内，时段2可以等于时段1； “6”：时段1早于时段2
	 */
	public static int timeRelation(String beginTime1, String endTime1, 
			String beginTime2, String endTime2) {
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long b1 = formatter.parse(beginTime1).getTime();
			long e1 = formatter.parse(endTime1).getTime();
			long b2 = formatter.parse(beginTime2).getTime();
			long e2 = formatter.parse(endTime2).getTime();
			if(e2<b1){
				return 1;
			}
			if(b2<=b1 && e2>b1 && e2<e1){
				return 2;
			}
			if(b2>b1 && e2<e1){
				return 3;
			}
			if(b2>b1 && b2<e1 && e2>=e1){
				return 4;
			}
			if(b2<=b1 && e2>=e1){
				return 5;
			}
			if(b2>e1){
				return 6;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * 交叉多少秒
	 * @param beginTime1
	 * @param endTime1
	 * @param beginTime2
	 * @param endTime2
	 * @return 时段1和时段2
	 */
	public static long containTimeLength(String beginTime1, String endTime1, 
			String beginTime2, String endTime2) {
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long b1 = formatter.parse(beginTime1).getTime();
			long e1 = formatter.parse(endTime1).getTime();
			long b2 = formatter.parse(beginTime2).getTime();
			long e2 = formatter.parse(endTime2).getTime();
			if(e2<b1){//1
				return 0;
			}
			if(b2<=b1 && e2>b1 && e2<e1){//2
				return (e2-b1)/1000;
			}
			if(b2>b1 && e2<e1){//3
				return (e2-b2)/1000;
			}
			if(b2>b1 && b2<e1 && e2>=e1){//4
				return (e1-b2)/1000;
			}
			if(b2<=b1 && e2>=e1){//5
				return (e1-b1)/1000;
			}
			if(b2>e1){//6
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 检查是否跨天
	 * @param beginTime
	 * @param endTime
	 * @return true：跨天， false：不跨天
	 */
	public static boolean isCrossDay(String beginTime, String endTime) {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		try {
			String beginDate = formatter.format(formatter.parse(beginTime));
			String endDate = formatter.format(formatter.parse(beginTime));
			if(beginDate.equals(endDate)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取指定日期所在周的周一和周日的日期
	 * @param date 日期字符串:2018-01-18
	 * @return
	 * key:monday   value:2018-01-15    (周一日期)
	 * key:weekend  value:2018-01-21	(周末日期)
	 * @throws ParseException 
	 */
	public static Map<String,String> getMondayAndWeekendDate(String date){
		//初始化要返回的数据
		Map<String,String> returnData = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date.trim()));
			//获取当前日期对应的星期
			int currentWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
			if(currentWeek==0){
				currentWeek = 7;
			}
			//获取周一日期
			calendar.add(Calendar.DAY_OF_WEEK,-currentWeek+1);
			returnData.put("monday",new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
			//获取周日日期
			calendar.add(Calendar.DAY_OF_WEEK,6);
			returnData.put("weekend",new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnData;
	}
	/**
	 * 秒化为分
	 * @param seconds
	 * @return
	 */
	public static String parseSecondToMinute(String seconds) {
		if(StringUtils.isEmpty(seconds)){
			return "0";
		}
		return Integer.parseInt(seconds)/60+"";
	}
	/**
	 * 秒化为分，半小时为单位向上取整
	 * @param seconds
	 * @return
	 */
	public static String parseSecondToMinuteHalfHourUnit(String seconds) {
		if(StringUtils.isEmpty(seconds)){
			return "0";
		}else{
			double resultDouble = Math.ceil(Double.parseDouble(seconds)/60.0/30.0)*30.0;
			String result = (int)resultDouble+"";
			return result;
		}
	}
	/**
	 * 秒化为分，半小时为单位向下取整
	 * @param seconds
	 * @return
	 */
	public static String parseSecondToMinuteHalfHourFloorUnit(String seconds) {
		if(StringUtils.isEmpty(seconds)){
			return "0";
		}else{
			double resultDouble = Math.floor(Double.parseDouble(seconds)/60.0/30.0)*30.0;
			String result = (int)resultDouble+"";
			return result;
		}
	}
	/**
	 * 秒化为分，半天(4小时)为单位向上取整
	 * @param seconds
	 * @return
	 */
	public static String parseSecondToMinuteHalfDayUnit(String seconds) {
		if(StringUtils.isEmpty(seconds)){
			return "0";
		}else{
			double halfDayHour = 4.0*60.0;//分
			double resultDouble = Math.ceil(Double.parseDouble(seconds)/60.0/halfDayHour)*halfDayHour;
			String result = (int)resultDouble+"";
			return result;
		}
	}
	public static void main(String[] args) {
//		System.out.println(containTimeLength("2017-10-16 10:00:00", "2017-10-16 10:05:00","2017-10-16 10:00:00", "2017-10-16 10:05:00")/60);
//		System.out.println(parseSecondToMinuteHalfDayUnit("14450"));
//		System.out.println(getCurrentMaxDate());
//		System.out.println(compareTime("2018-01-15 21:00:01","2018-01-15 01:00:01"));
		/*Map map = getMondayAndWeekendDate("2018-01-10");
		System.out.println(map.get("monday"));
		System.out.println(map.get("weekend"));*/
		System.out.println(compareTime("2018-01-23 00:00:00","2018-01-22 00:00:00"));
	}
}
