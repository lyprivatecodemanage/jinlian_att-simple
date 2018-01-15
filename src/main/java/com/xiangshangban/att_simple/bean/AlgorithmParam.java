package com.xiangshangban.att_simple.bean;

import java.util.ArrayList;
import java.util.List;

import com.xiangshangban.organization.bean.Employee;

/**
 * 考勤计算参数
 * @author 韦友弟
 *
 */
public class AlgorithmParam {
	private String employeeId;
	private Employee employee = new Employee();//
	private String companyId;
	private String countDate;//计算日期
	private ClassesType attendancePlan = new ClassesType();//当天排班
	private List<AlgorithmAttLog> algorithmAttLog = new ArrayList<AlgorithmAttLog>();//门禁记录+外勤记录
	private List<ApplicationLeave> algorithmLeave = new ArrayList<ApplicationLeave>();//请假记录
	private List<ApplicationBusinessTravel> evectionList = new ArrayList<ApplicationBusinessTravel>();// 出差申请
	private List<ApplicationOutgoing> outList = new ArrayList<ApplicationOutgoing>();// 外出申请
	private List<ApplicationOvertime> overWorkList = new ArrayList<ApplicationOvertime>();// 有效加班申请
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCountDate() {
		return countDate;
	}
	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}
	public ClassesType getAttendancePlan() {
		return attendancePlan;
	}
	public void setAttendancePlan(ClassesType attendancePlan) {
		this.attendancePlan = attendancePlan;
	}
	public List<AlgorithmAttLog> getAlgorithmAttLog() {
		return algorithmAttLog;
	}
	public void setAlgorithmAttLog(List<AlgorithmAttLog> algorithmAttLog) {
		this.algorithmAttLog = algorithmAttLog;
	}
	public List<ApplicationLeave> getAlgorithmLeave() {
		return algorithmLeave;
	}
	public void setAlgorithmLeave(List<ApplicationLeave> algorithmLeave) {
		this.algorithmLeave = algorithmLeave;
	}
	public List<ApplicationBusinessTravel> getEvectionList() {
		return evectionList;
	}
	public void setEvectionList(List<ApplicationBusinessTravel> evectionList) {
		this.evectionList = evectionList;
	}
	public List<ApplicationOutgoing> getOutList() {
		return outList;
	}
	public void setOutList(List<ApplicationOutgoing> outList) {
		this.outList = outList;
	}
	public List<ApplicationOvertime> getOverWorkList() {
		return overWorkList;
	}
	public void setOverWorkList(List<ApplicationOvertime> overWorkList) {
		this.overWorkList = overWorkList;
	}
	
}
