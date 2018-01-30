package com.xiangshangban.att_simple.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页
 * @author mian
 */
public class Paging {
	//年份
	private String year;
	//月份
	private String month;
	//请假类型
	private String leaveType;
	//请假时长
	private String applicationHourRank;
	//发起时间
	private String applicationTimeRank;
	//公司ID
    private String companyId;
	//部门ID
    private String departmentId;
    //姓名
    private String employeeName;
	//年假总额排序
    private String annualLeaveTotalRank;
    //年假余额排序
    private String annualLeaveBalanceRank;
    //调休总额排序
    private String adjustRestTotalRank;
    //调休余额排序
    private String adjustRestBalanceRank;
	//当前页码
    private String varPageNo;
    //排除条数
    private String pageExcludeNumber;
    //每页显示数据条数
    private String pageNum;
    //签到时间
    private String signInTimeRank;
    //签退时间
    private String signOutTimeRank;
    //实际上班时长
    private String realWorkTimeRank;
    //开始时间
    private String beginDate;
    //结束时间
    private String endDate;
    //异常类型
    private String exceptionMark;
    //时间
    private String attDate;
    
    public String getAttDate() {
		return attDate;
	}

	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}

	public String getExceptionMark() {
		return exceptionMark;
	}

	public void setExceptionMark(String exceptionMark) {
		this.exceptionMark = StringUtils.isEmpty(exceptionMark)?null:exceptionMark;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = StringUtils.isEmpty(beginDate)?null:beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = StringUtils.isEmpty(endDate)?null:endDate;
	}

	public String getSignInTimeRank() {
		return signInTimeRank;
	}

	public void setSignInTimeRank(String signInTimeRank) {
		this.signInTimeRank = StringUtils.isEmpty(signInTimeRank)?null:signInTimeRank;
	}

	public String getSignOutTimeRank() {
		return signOutTimeRank;
	}

	public void setSignOutTimeRank(String signOutTimeRank) {
		this.signOutTimeRank = StringUtils.isEmpty(signOutTimeRank)?null:signOutTimeRank;
	}

	public String getRealWorkTimeRank() {
		return realWorkTimeRank;
	}

	public void setRealWorkTimeRank(String realWorkTimeRank) {
		this.realWorkTimeRank = StringUtils.isEmpty(realWorkTimeRank)?null:realWorkTimeRank;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = StringUtils.isEmpty(leaveType)?null:leaveType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = StringUtils.isEmpty(year)?null:year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = StringUtils.isEmpty(month)?null:month;
	}

	public String getApplicationHourRank() {
		return applicationHourRank;
	}

	public void setApplicationHourRank(String applicationHourRank) {
		this.applicationHourRank = StringUtils.isEmpty(applicationHourRank)?null:applicationHourRank;
	}

	public String getApplicationTimeRank() {
		return applicationTimeRank;
	}

	public void setApplicationTimeRank(String applicationTimeRank) {
		this.applicationTimeRank = StringUtils.isEmpty(applicationTimeRank)?null:applicationTimeRank;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = StringUtils.isEmpty(companyId)?null:companyId;
	}
	
	public String getPageExcludeNumber() {
		return pageExcludeNumber;
	}

	public void setPageExcludeNumber(String pageExcludeNumber) {
		this.pageExcludeNumber = StringUtils.isEmpty(pageExcludeNumber)?null:pageExcludeNumber;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = StringUtils.isEmpty(pageNum)?null:pageNum;
	}

	public String getVarPageNo() {
		return varPageNo;
	}

	public void setVarPageNo(String varPageNo) {
		this.varPageNo = StringUtils.isEmpty(varPageNo)?null:varPageNo;
	}
	
	public String getAnnualLeaveTotalRank() {
		return annualLeaveTotalRank;
	}

	public void setAnnualLeaveTotalRank(String annualLeaveTotalRank) {
		this.annualLeaveTotalRank = StringUtils.isEmpty(annualLeaveTotalRank)?null:annualLeaveTotalRank;
	}

	public String getAnnualLeaveBalanceRank() {
		return annualLeaveBalanceRank;
	}

	public void setAnnualLeaveBalanceRank(String annualLeaveBalanceRank) {
		this.annualLeaveBalanceRank = StringUtils.isEmpty(annualLeaveBalanceRank)?null:annualLeaveBalanceRank;
	}

	public String getAdjustRestTotalRank() {
		return adjustRestTotalRank;
	}

	public void setAdjustRestTotalRank(String adjustRestTotalRank) {
		this.adjustRestTotalRank = StringUtils.isEmpty(adjustRestTotalRank)?null:adjustRestTotalRank;
	}

	public String getAdjustRestBalanceRank() {
		return adjustRestBalanceRank;
	}

	public void setAdjustRestBalanceRank(String adjustRestBalanceRank) {
		this.adjustRestBalanceRank = StringUtils.isEmpty(adjustRestBalanceRank)?null:adjustRestBalanceRank;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = StringUtils.isEmpty(departmentId)?null:departmentId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = StringUtils.isEmpty(employeeName)?null:employeeName;
	}

}
