package com.xiangshangban.att_simple.bean;

public class ReportExcept {
    private String exceptId;

    private String employeeId;

    private String exceptDate;//异常日期

    private String exceptTime;//异常具体时间（迟到、早退）

    private String exceptType;//异常类型： 1迟到  ;2早退；3未签到; 4未签退 ； 5	无效; 6旷工； 7	过早打卡; 8过晚打卡；9重复打卡;10无排班打卡；11其它

    private String lackTime;

    private String lackBeginTime;

    private String lackEndTime;

    private String createTime;

    private String remark;

    private String planType;

    private String planId;

    private String companyId;

    public String getExceptId() {
        return exceptId;
    }

    public void setExceptId(String exceptId) {
        this.exceptId = exceptId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getExceptDate() {
        return exceptDate;
    }

    public void setExceptDate(String exceptDate) {
        this.exceptDate = exceptDate;
    }

    public String getExceptTime() {
        return exceptTime;
    }

    public void setExceptTime(String exceptTime) {
        this.exceptTime = exceptTime;
    }

    public String getExceptType() {
        return exceptType;
    }

    public void setExceptType(String exceptType) {
        this.exceptType = exceptType;
    }

    public String getLackTime() {
        return lackTime;
    }

    public void setLackTime(String lackTime) {
        this.lackTime = lackTime;
    }

    public String getLackBeginTime() {
        return lackBeginTime;
    }

    public void setLackBeginTime(String lackBeginTime) {
        this.lackBeginTime = lackBeginTime;
    }

    public String getLackEndTime() {
        return lackEndTime;
    }

    public void setLackEndTime(String lackEndTime) {
        this.lackEndTime = lackEndTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}