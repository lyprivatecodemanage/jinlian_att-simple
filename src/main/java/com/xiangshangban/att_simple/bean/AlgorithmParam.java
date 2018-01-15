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
	private String employeeId;//人员ID
	private Employee employee = new Employee();//员工详细信息
	private String companyId;//公司ID
	private String countDate;//计算日期
	private boolean noCheckAtt;//是否为不考勤人员，true:不考勤， false：考勤
	private ClassesEmployee ClassesEmployee = new ClassesEmployee();//当天排班
	private String centerLine;//区分签到签退的中间线
	private String lateLine;//迟到线
	private String earlyLine;//早退线
	private String attBeginLine;//应在岗的最早时间
	private String attEndLine;//应在岗的最晚时间
	private String signInLimitLine;//上班打卡开始时间
	private String signOutLimitLine;//下班班打卡结束时间
	private String restBeginTime;//休息开始时间
	private String restEndTime;//休息结束时间
	private List<AlgorithmAttLog> algorithmAttLog = new ArrayList<AlgorithmAttLog>();//门禁记录+补勤记录
	private List<ApplicationLeave> algorithmLeave = new ArrayList<ApplicationLeave>();//请假记录
	private List<ApplicationBusinessTravel> evectionList = new ArrayList<ApplicationBusinessTravel>();// 出差申请
	private List<ApplicationOutgoing> outList = new ArrayList<ApplicationOutgoing>();// 外出申请
	private List<ApplicationOvertime> overWorkList = new ArrayList<ApplicationOvertime>();// 有效加班申请
	
	public String getRestBeginTime() {
		return restBeginTime;
	}
	public void setRestBeginTime(String restBeginTime) {
		this.restBeginTime = restBeginTime;
	}
	public String getRestEndTime() {
		return restEndTime;
	}
	public void setRestEndTime(String restEndTime) {
		this.restEndTime = restEndTime;
	}
	public String getAttBeginLine() {
		return attBeginLine;
	}
	public void setAttBeginLine(String attBeginLine) {
		this.attBeginLine = attBeginLine;
	}
	public String getAttEndLine() {
		return attEndLine;
	}
	public void setAttEndLine(String attEndLine) {
		this.attEndLine = attEndLine;
	}
	public String getCenterLine() {
		return centerLine;
	}
	public void setCenterLine(String centerLine) {
		this.centerLine = centerLine;
	}
	public String getLateLine() {
		return lateLine;
	}
	public void setLateLine(String lateLine) {
		this.lateLine = lateLine;
	}
	public String getEarlyLine() {
		return earlyLine;
	}
	public void setEarlyLine(String earlyLine) {
		this.earlyLine = earlyLine;
	}
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
	public boolean isNoCheckAtt() {
		return noCheckAtt;
	}
	public void setNoCheckAtt(boolean noCheckAtt) {
		this.noCheckAtt = noCheckAtt;
	}
	public ClassesEmployee getClassesEmployee() {
		return ClassesEmployee;
	}
	public void setClassesEmployee(ClassesEmployee classesEmployee) {
		ClassesEmployee = classesEmployee;
	}
	public String getSignInLimitLine() {
		return signInLimitLine;
	}
	public void setSignInLimitLine(String signInLimitLine) {
		this.signInLimitLine = signInLimitLine;
	}
	public String getSignOutLimitLine() {
		return signOutLimitLine;
	}
	public void setSignOutLimitLine(String signOutLimitLine) {
		this.signOutLimitLine = signOutLimitLine;
	}
	
}
