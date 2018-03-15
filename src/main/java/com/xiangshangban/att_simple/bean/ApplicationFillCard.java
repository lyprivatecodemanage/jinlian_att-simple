package com.xiangshangban.att_simple.bean;

public class ApplicationFillCard {
    private String applicationNo;

    private String employeeId;

    private String departmentId;

    private String companyId;

    private String fillCardType;

    private String fillCardTime;

    private String reason;

    private String uploadVoucher;

    private String approver;

    private String operaterTime;

    private String isTransfer;

    private String applicationTime;

 /***************************************扩展属性*****************************************/
    
    private String departmentName;
    
    private String employeeeName;
    
    public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmployeeeName() {
		return employeeeName;
	}

	public void setEmployeeeName(String employeeeName) {
		this.employeeeName = employeeeName;
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

    public String getFillCardType() {
        return fillCardType;
    }

    public void setFillCardType(String fillCardType) {
        this.fillCardType = fillCardType;
    }

    public String getFillCardTime() {
        return fillCardTime;
    }

    public void setFillCardTime(String fillCardTime) {
        this.fillCardTime = fillCardTime;
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

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }
}