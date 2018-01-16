package com.xiangshangban.att_simple.bean;

import java.lang.reflect.Field;

import org.jboss.logging.Logger;

public class MessageBean<T> {
	private static final Logger logger = Logger.getLogger(MessageBean.class);
	private T t;
	public MessageBean() {
		
	}
	public MessageBean(T t) {
		this.t = t;
	}
	public void setEmployeeId(String employeeId){
		this.setValue("employeeId", employeeId);
	}
	public void setDepartmentId(String departmentId){
		this.setValue("departmentId", departmentId);
	}
	public void setCompanyId(String companyId){
		this.setValue("companyId", companyId);
	}
	public void setApplicationNo(String applicationNo){
		this.setValue("applicationNo", applicationNo);
	}
	public void setLeaveType(String leaveType){
		this.setValue("leaveType", leaveType);
	}
	public void setStartTime(String startTime) {
		this.setValue("startTime", startTime);
	}
	public void setEndTime(String endTime) {
		this.setValue("endTime", endTime);
	}
	public void setReason(String reason) {
		this.setValue("reason", reason);
	}
	public void setUploadVoucher(String uploadVoucher) {
		this.setValue("uploadVoucher", uploadVoucher);
	}
	public void setApprover(String approver) {
		this.setValue("approver", approver);
	}
	public void setCopyToPerson(String copyToPerson) {
		this.setValue("copyToPerson", copyToPerson);
	}
	public void setOperaterTime(String operaterTime) {
		this.setValue("operaterTime", operaterTime);
	}
	public void setIsTransfer(String isTransfer) {
		this.setValue("isTransfer", isTransfer);
	}
	public void setApplicationHour(String applicationHour) {
		this.setValue("applicationHour", applicationHour);
	}
	public void setApplicationTime(String applicationTime) {
		this.setValue("applicationTime", applicationTime);
	}
	public void setOvertimeType(String overtimeType) {
		this.setValue("overtimeType", overtimeType);
	}
    public void setBusinessTravelType(String businessTravelType) {
    	this.setValue("businessTravelType", businessTravelType);
    }
    public void setFillCardType(String fillCardType) {
    	this.setValue("fillCardType", fillCardType);
    }
    public void setFillCardTime(String fillCardTime) {
    	this.setValue("fillCardTime", fillCardTime);
    }
    public void setOutgoningLocation(String outgoningLocation) {
    	this.setValue("outgoningLocation", outgoningLocation);
    }
    public void setCommonContactPeopleId(String commonContactPeopleId){
    	this.setValue("commonContactPeopleId", commonContactPeopleId);
    }
    public void setCommonContactPeopleName(String commonContactPeopleName){
    	this.setValue("commonContactPeopleName", commonContactPeopleName);
    }
	
	
	private void setValue(String key,String value){
		Class c = t.getClass();
		 Field[] fs = c.getDeclaredFields();
		 for(int i=0;i<fs.length;i++){
			 Field f = fs[i];
			 f.setAccessible(true); //设置些属性是可以访问的
			if(key.equals(f.getName())){
				 try {
					f.set(this.t, value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
	}
}
