package com.xiangshangban.att_simple.bean;

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
    
    public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getApplicationHourRank() {
		return applicationHourRank;
	}

	public void setApplicationHourRank(String applicationHourRank) {
		this.applicationHourRank = applicationHourRank;
	}

	public String getApplicationTimeRank() {
		return applicationTimeRank;
	}

	public void setApplicationTimeRank(String applicationTimeRank) {
		this.applicationTimeRank = applicationTimeRank;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getPageExcludeNumber() {
		return pageExcludeNumber;
	}

	public void setPageExcludeNumber(String pageExcludeNumber) {
		this.pageExcludeNumber = pageExcludeNumber;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getVarPageNo() {
		return varPageNo;
	}

	public void setVarPageNo(String varPageNo) {
		this.varPageNo = varPageNo;
	}
	
	public String getAnnualLeaveTotalRank() {
		return annualLeaveTotalRank;
	}

	public void setAnnualLeaveTotalRank(String annualLeaveTotalRank) {
		this.annualLeaveTotalRank = annualLeaveTotalRank;
	}

	public String getAnnualLeaveBalanceRank() {
		return annualLeaveBalanceRank;
	}

	public void setAnnualLeaveBalanceRank(String annualLeaveBalanceRank) {
		this.annualLeaveBalanceRank = annualLeaveBalanceRank;
	}

	public String getAdjustRestTotalRank() {
		return adjustRestTotalRank;
	}

	public void setAdjustRestTotalRank(String adjustRestTotalRank) {
		this.adjustRestTotalRank = adjustRestTotalRank;
	}

	public String getAdjustRestBalanceRank() {
		return adjustRestBalanceRank;
	}

	public void setAdjustRestBalanceRank(String adjustRestBalanceRank) {
		this.adjustRestBalanceRank = adjustRestBalanceRank;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
