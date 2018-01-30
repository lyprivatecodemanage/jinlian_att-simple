package com.xiangshangban.att_simple.bean;

import org.apache.poi.ss.formula.functions.T;

/**
 * 请假申请记录表
 * @author mian
 */
public class ApplicationLeave{
    //申请单号(uuid)
    private String applicationNo;
    //申请人id
    private String employeeId;
    //申请人部门id
    private String departmentId;
    //申请人公司id
    private String companyId;
    //请假类型(1:事假;2:年假;3:调休假;4:婚假;5:产假;6:丧假)
    private String leaveType;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //请假说明
    private String reason;
    //上传的凭证
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
    
    /*----------------------------------扩展属性------------------------------------*/

    //部门名称
    private String departmentName;
    //员工姓名
    private String employeeName;
    //请假类型名称
    private String leaveTypeName;
    //审核人姓名
    private String approverName;
    //假期次数
    private String leaveCount;
    
    public String getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(String leaveCount) {
		this.leaveCount = leaveCount;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
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