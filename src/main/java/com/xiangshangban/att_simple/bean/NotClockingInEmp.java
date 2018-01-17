package com.xiangshangban.att_simple.bean;

/**
 * 无需考勤人员实体类
 * @author Administrator
 *
 */
public class NotClockingInEmp {
	
	//主键
	private String id;
	
	//人员ID
	private String empId;
	
	//人员公司ID
	private String empCompanyId;

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
}
