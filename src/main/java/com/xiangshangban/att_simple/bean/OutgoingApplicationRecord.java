package com.xiangshangban.att_simple.bean;

/**
 * 外出申请记录表
 * @author mian
 */
public class OutgoingApplicationRecord {
	//uuid
    private String id;
    //申请单号(uuid)
    private String applicationNo;
    //申请人id
    private String employeeId;
    //申请人部门id
    private String departmentId;
    //申请人部门id
    private String companyId;
    //外出地点
    private String outgoningLocation;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //外出说明
    private String reason;
    //上传的出差凭证(key)
    private String uploadVoucher;
    //审批人id
    private String approver;
    //操作时间
    private String operaterTime;
    //是否转移(0:否;1:是)
    private String isTransfer;
    //申请小时数(单位:小时)
    private String applicationHour;
    //申请发起时间
    private String applicationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOutgoningLocation() {
        return outgoningLocation;
    }

    public void setOutgoningLocation(String outgoningLocation) {
        this.outgoningLocation = outgoningLocation;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUploadVoucher() {
        return uploadVoucher;
    }

    public void setUploadVoucher(String uploadVoucher) {
        this.uploadVoucher = uploadVoucher;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getApplicationHour() {
        return applicationHour;
    }

    public void setApplicationHour(String applicationHour) {
        this.applicationHour = applicationHour;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }
}