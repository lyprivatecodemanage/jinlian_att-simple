package com.xiangshangban.att_simple.bean;

/**
 * 年假时长计算表
 * @author mian
 */
public class AnnualLeaveDateCalculate {
	//UUID
	private String annualLeaveId;
	//年假调整类型(0.申请年假休息,1:管理员微调)
    private String annualLeaveType;
    //调整时长
    private String annualLeaveDuration;
    //调整状态(0:收入,1:支出)
    private String annualLeaveMold;
    //调整时间
    private String annualLeaveDate;
    //调整原因
    private String annualLeaveReason;
    //年假总额
    private String annualLeaveTotal;
    //年假余额
    private String annualLeaveBalance;

    public String getAnnualLeaveId() {
        return annualLeaveId;
    }

    public void setAnnualLeaveId(String annualLeaveId) {
        this.annualLeaveId = annualLeaveId;
    }

    public String getAnnualLeaveType() {
        return annualLeaveType;
    }

    public void setAnnualLeaveType(String annualLeaveType) {
        this.annualLeaveType = annualLeaveType;
    }

    public String getAnnualLeaveDuration() {
        return annualLeaveDuration;
    }

    public void setAnnualLeaveDuration(String annualLeaveDuration) {
        this.annualLeaveDuration = annualLeaveDuration;
    }

    public String getAnnualLeaveMold() {
        return annualLeaveMold;
    }

    public void setAnnualLeaveMold(String annualLeaveMold) {
        this.annualLeaveMold = annualLeaveMold;
    }

    public String getAnnualLeaveDate() {
        return annualLeaveDate;
    }

    public void setAnnualLeaveDate(String annualLeaveDate) {
        this.annualLeaveDate = annualLeaveDate;
    }

    public String getAnnualLeaveReason() {
        return annualLeaveReason;
    }

    public void setAnnualLeaveReason(String annualLeaveReason) {
        this.annualLeaveReason = annualLeaveReason;
    }

    public String getAnnualLeaveTotal() {
        return annualLeaveTotal;
    }

    public void setAnnualLeaveTotal(String annualLeaveTotal) {
        this.annualLeaveTotal = annualLeaveTotal;
    }

    public String getAnnualLeaveBalance() {
        return annualLeaveBalance;
    }

    public void setAnnualLeaveBalance(String annualLeaveBalance) {
        this.annualLeaveBalance = annualLeaveBalance;
    }
}