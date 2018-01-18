package com.jinnian.att_simple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.xiangshangban.att_simple.bean.MessageBean;

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
		try{
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
		}
		
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
}
