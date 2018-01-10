package com.xiangshangban.att_simple.bean;

/**
 * 已完成申请记录汇总表
 * @author mian
 */
public class TotalWillCompleteApplication {
	//申请单号(uuid)
    private String applcationNo;
    //操作时间
    private String operaterTime;
    //操作人id
    private String operaterId;
    //申请类型(1:请假;2:加班;3:出差;4:外出;5:补卡)
    private String applicationType;
    //审批人id
    private String approver;
    //是否抄送(0:否;1:是)
    private String isCopy;
    //申请人id
    private String applicationId;
    //公司id
    private String companyId;
    //是否转移(0:否;1:是)
    private String isTransfer;
    //申请单状态(0:删除;1:正常;2:撤回)
    private String applicationStatus;
    //驳回说明
    private String rejectReason;

    public String getApplcationNo() {
        return applcationNo;
    }

    public void setApplcationNo(String applcationNo) {
        this.applcationNo = applcationNo;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }

    public String getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(String operaterId) {
        this.operaterId = operaterId;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(String isCopy) {
        this.isCopy = isCopy;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
    
}