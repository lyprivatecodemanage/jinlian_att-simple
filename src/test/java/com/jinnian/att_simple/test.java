package com.jinnian.att_simple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xiangshangban.att_simple.bean.MessageBean;
import com.xiangshangban.att_simple.utils.TimeUtil;

public class test {
	public static void main(String[] args) {
	/*	ApplicationLeave al = new ApplicationLeave();
		MessageBean<ApplicationLeave> mb = new MessageBean<ApplicationLeave>(al);
		mb.setEmployeeId("123456");
		System.out.println(al.getEmployeeId());*/
		
		/*try {
			Object obj = new test().getName(Class.forName("com.xiangshangban.att_simple.bean.ApplicationLeave"));
			ApplicationLeave applicationLeave = (ApplicationLeave)obj;
			applicationLeave.setApplicationNo("123");
			System.out.println(applicationLeave.getEmployeeId());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> list = new ArrayList<Integer>();
		new test().setValue(list);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}*/
		/*try{
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	        String str1 = "2012-02";  
	        String str2 = "2010-01";  
	        Calendar bef = Calendar.getInstance();  
	        Calendar aft = Calendar.getInstance();  
	        bef.setTime(sdf.parse(str1));  
	        aft.setTime(sdf.parse(str2));  
	        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);  
	        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;  
	        System.out.println(Math.abs(month + result));
		}catch(Exception e){
			e.printStackTrace();
		}*/

		/*try{
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			
			String date = "2018-1-18";
			
			
			int time= (int)Math.ceil((double)((dfs.parse("2018-1-18"+" 24:00:00").getTime()-dfs.parse("2018-1-18 06:10:00").getTime())/1000/60/30)/2);
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(getTimesnight())));
			System.out.println(time);
			System.out.println(dfs.format(sdf1.parse(date)));
		}catch(Exception e){
			e.printStackTrace();

		}*/
		System.out.println(TimeUtil.getDateBefore(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())),"7"));

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
