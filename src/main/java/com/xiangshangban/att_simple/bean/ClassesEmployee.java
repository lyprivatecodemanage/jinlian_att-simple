package com.xiangshangban.att_simple.bean;

public class ClassesEmployee {
	
    private String id; //主键

    private String empId;//人员ID

    private String empCompanyId;//人员公司ID

    private String classesId;//该人员使用的班次类型
    
    private String classesName;//该人员使用的班次名称

    private String onDutySchedulingDate;//该班次的上班时间（一个人的班次类型是不能够更改的，但是班次的上下班时间是可以进行微调的）

    private String offDutySchedulingDate;//该班次下班时间
    
    private String restStartTime;//当天的休息时间段：开始时间
    
    private String restEndTime;//当天的休息时间段：结束时间
    
    private String signInRule;//签到晚xx分钟不算迟到
    
    private String signOutRule;//签退早xx分钟不算早退
    
    private String onPunchCardRule;//上班打卡允许提前xx分钟
    
    private String offPunchCardRule;//下班打卡允许推迟xx分钟
    
    private String theDate;//当前日期
    
    private String week;//上班时间所在日期的星期
    
    private String divideColor;//分隔色（每更新一次班次就，切换一种颜色）
    
    private ClassesType classesType;

    private String mustAttendanceTime;
    
	public String getMustAttendanceTime() {
		return mustAttendanceTime;
	}

	public void setMustAttendanceTime(String mustAttendanceTime) {
		this.mustAttendanceTime = mustAttendanceTime;
	}

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

	public String getClassesName() {
		return classesName;
	}

	public void setClassesName(String classesName) {
		this.classesName = classesName;
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

	public String getSignInRule() {
		return signInRule;
	}

	public void setSignInRule(String signInRule) {
		this.signInRule = signInRule;
	}

	public String getSignOutRule() {
		return signOutRule;
	}

	public void setSignOutRule(String signOutRule) {
		this.signOutRule = signOutRule;
	}

	public String getOnPunchCardRule() {
		return onPunchCardRule;
	}

	public void setOnPunchCardRule(String onPunchCardRule) {
		this.onPunchCardRule = onPunchCardRule;
	}

	public String getOffPunchCardRule() {
		return offPunchCardRule;
	}

	public void setOffPunchCardRule(String offPunchCardRule) {
		this.offPunchCardRule = offPunchCardRule;
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

	public String getDivideColor() {
		return divideColor;
	}

	public void setDivideColor(String divideColor) {
		this.divideColor = divideColor;
	}

	public ClassesType getClassesType() {
		return classesType;
	}

	public void setClassesType(ClassesType classesType) {
		this.classesType = classesType;
	}
}