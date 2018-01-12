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
	private List<LeaveApplicationRecord> algorithmLeave = new ArrayList<LeaveApplicationRecord>();//请假记录
	private List<BusinessTravelApplicationRecord> evectionList = new ArrayList<BusinessTravelApplicationRecord>();// 出差申请
	private List<OutgoingApplicationRecord> outList = new ArrayList<OutgoingApplicationRecord>();// 外出申请
	private List<OvertimeApplicationRecord> overWorkList = new ArrayList<OvertimeApplicationRecord>();// 有效加班申请
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
	public List<LeaveApplicationRecord> getAlgorithmLeave() {
		return algorithmLeave;
	}
	public void setAlgorithmLeave(List<LeaveApplicationRecord> algorithmLeave) {
		this.algorithmLeave = algorithmLeave;
	}
	public List<BusinessTravelApplicationRecord> getEvectionList() {
		return evectionList;
	}
	public void setEvectionList(List<BusinessTravelApplicationRecord> evectionList) {
		this.evectionList = evectionList;
	}
	public List<OutgoingApplicationRecord> getOutList() {
		return outList;
	}
	public void setOutList(List<OutgoingApplicationRecord> outList) {
		this.outList = outList;
	}
	public List<OvertimeApplicationRecord> getOverWorkList() {
		return overWorkList;
	}
	public void setOverWorkList(List<OvertimeApplicationRecord> overWorkList) {
		this.overWorkList = overWorkList;
	}
	
}
