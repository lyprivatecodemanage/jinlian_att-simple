package com.jinnian.att_simple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xiangshangban.att_simple.bean.MessageBean;
import com.xiangshangban.att_simple.utils.TimeUtil;

public class test {
	public static void main(String[] args) {
		
	}
	
	public void setValue(List<Integer> list){
		for(int i=0;i<10;i++){
			list.add(i);
		}
	}
	public <T>T getName(Class<T> c){
		System.out.println(c.getName());
		
		try {
			T t = c.newInstance();
			MessageBean<T> mb = new MessageBean<T>(t);
			mb.setEmployeeId("123456");
			return t;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static int getTimesnight(){ 
		  Calendar cal = Calendar.getInstance(); 
		  cal.set(Calendar.HOUR_OF_DAY, 24); 
		  cal.set(Calendar.SECOND, 0); 
		  cal.set(Calendar.MINUTE, 0); 
		  cal.set(Calendar.MILLISECOND, 0); 
		  
		  return (int) (cal.getTimeInMillis()/1000); 
		 }
}
