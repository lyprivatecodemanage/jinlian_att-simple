package com.xiangshangban.att_simple.bean;

import java.util.List;
import java.util.Map;

/**
 * 人员班次信息实体bean
 * @author 王勇辉
 *
 */
public class ClassesEmpInfo {
	//部门名称
	private String deptName;
	//岗位名称
	private String postName;
	//人员ID
	private String empId;
	//人员名称
	private String empName;
	//本周工时
	private String thisWeekHours;
	//人员班次信息集合
	private List<Map<String,String>> classesList;
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getThisWeekHours() {
		return thisWeekHours;
	}
	public void setThisWeekHours(String thisWeekHours) {
		this.thisWeekHours = thisWeekHours;
	}
	public List<Map<String, String>> getClassesList() {
		return classesList;
	}
	public void setClassesList(List<Map<String, String>> classesList) {
		this.classesList = classesList;
	}
	
}
