package com.xiangshangban.att_simple.bean;

import java.io.Serializable;
/**
 * 门禁记录+外勤记录
 * @author 韦友弟
 *
 */
public class AlgorithmAttLog implements Serializable {
	private static final long serialVersionUID = -1609582415713105293L;
	private String attTime;//记录时间
	private String userId;
	private String empId;
	private String startTime;
	private String endTime;
	private String companyId;
	public String getAttTime() {
		return attTime;
	}
	public void setAttTime(String attTime) {
		this.attTime = attTime;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
}
