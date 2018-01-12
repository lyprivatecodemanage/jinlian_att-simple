package com.xiangshangban.att_simple.bean;

import java.io.Serializable;
/**
 * 请假申请记录
 * @author 韦友弟
 *
 */
public class AlgorithmLeave implements Serializable {
	private static final long serialVersionUID = -1609582415713105293L;
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String leaveDeleteType; //哺乳假、孕期工间休息假 抵消方式，0：晚来，1：早走
	private String childNum; //哺乳假胎数
	private String userId;
	private String customerId;
	private String vacationModalId;//假期模板ID(即请假类型)
	private String vacationId;//假期ID
	
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getVacationModalId() {
		return vacationModalId;
	}
	public void setVacationModalId(String vacationModalId) {
		this.vacationModalId = vacationModalId;
	}
	public String getVacationId() {
		return vacationId;
	}
	public void setVacationId(String vacationId) {
		this.vacationId = vacationId;
	}
	public String getLeaveDeleteType() {
		return leaveDeleteType;
	}
	public void setLeaveDeleteType(String leaveDeleteType) {
		this.leaveDeleteType = leaveDeleteType;
	}
	public String getChildNum() {
		return childNum;
	}
	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}
	
	
}
