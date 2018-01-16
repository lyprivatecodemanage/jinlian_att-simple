package com.xiangshangban.att_simple.bean;

public class ClassesEmployee {
	
    private String id; //主键

    private String empId;//人员ID

    private String empCompanyId;//人员公司ID

    private String classesId;//该人员使用的班次类型

    private String onDutySchedulingDate;//该班次的上班时间（一个人的班次类型是不能够更改的，但是班次的上下班时间是可以进行微调的）

    private String offDutySchedulingDate;//该班次下班时间
    
    private String restStartTime;//当天的休息时间段：开始时间
    
    private String restEndTime;//当天的休息时间段：结束时间
    
    private String theDate;//当前日期
    
    private String week;//上班时间所在日期的星期
    
    private ClassesType classesType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpCompanyId() {
		return empCompanyId;
	}

	public void setEmpCompanyId(String empCompanyId) {
		this.empCompanyId = empCompanyId;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getOnDutySchedulingDate() {
		return onDutySchedulingDate;
	}

	public void setOnDutySchedulingDate(String onDutySchedulingDate) {
		this.onDutySchedulingDate = onDutySchedulingDate;
	}

	public String getOffDutySchedulingDate() {
		return offDutySchedulingDate;
	}

	public void setOffDutySchedulingDate(String offDutySchedulingDate) {
		this.offDutySchedulingDate = offDutySchedulingDate;
	}

	public String getRestStartTime() {
		return restStartTime;
	}

	public void setRestStartTime(String restStartTime) {
		this.restStartTime = restStartTime;
	}

	public String getRestEndTime() {
		return restEndTime;
	}

	public void setRestEndTime(String restEndTime) {
		this.restEndTime = restEndTime;
	}

	public String getTheDate() {
		return theDate;
	}

	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public ClassesType getClassesType() {
		return classesType;
	}

	public void setClassesType(ClassesType classesType) {
		this.classesType = classesType;
	}

}