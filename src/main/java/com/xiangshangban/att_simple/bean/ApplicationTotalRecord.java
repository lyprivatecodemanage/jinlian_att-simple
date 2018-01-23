package com.xiangshangban.att_simple.bean;

import java.util.List;

/**
 * 申请记录汇总表
 * @author mian
 */
public class ApplicationTotalRecord {

	//申请单号(uuid)
    private String applicationNo;
    //操作时间
    private String operaterTime;
    
    private String previousOperaterTime;
    //操作人id
    private String operaterId;
    //申请类型(1:请假;2:加班;3:出差;4:外出;5:补卡;6:年假微调;7:调休微调)
    private String applicationType;
    //最后一次审批人id
    private String lastApprover;
    //是否抄送(0:否;1:是)
    private String isCopy;
    //申请人id
    private String applicationId;
    
    private String applicationName;
    //公司id
    private String companyId;
    //是否驳回(0:否;1:是)
    private String isReject;
    //申请单状态(0:删除;1:正常;2:撤回)
    private String applicationStatus;

    private String rejectReason;

    private String isTransfer;
    
    private String isComplete;
    
    private String isSkipRestDay;
    
    private String statusDescription;//审批中,已完成,已驳回
    
    private String copyPersonId;
    
    private String transferPersonId;
    
    private List<ApplicationTransferRecord> applicationTransferRecordList;
    
    private List<ApplicationToCopyPerson> applicationToCopyPersonList;
    
    public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
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

    public String getLastApprover() {
        return lastApprover;
    }

    public void setLastApprover(String lastApprover) {
        this.lastApprover = lastApprover;
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

    public String getIsReject() {
        return isReject;
    }

    public void setIsReject(String isReject) {
        this.isReject = isReject;
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

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

	public String getIsSkipRestDay() {
		return isSkipRestDay;
	}

	public void setIsSkipRestDay(String isSkipRestDay) {
		this.isSkipRestDay = isSkipRestDay;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getCopyPersonId() {
		return copyPersonId;
	}

	public void setCopyPersonId(String copyPersonId) {
		this.copyPersonId = copyPersonId;
	}

	public String getTransferPersonId() {
		return transferPersonId;
	}

	public void setTransferPersonId(String transferPersonId) {
		this.transferPersonId = transferPersonId;
	}

	public List<ApplicationTransferRecord> getApplicationTransferRecordList() {
		return applicationTransferRecordList;
	}

	public void setApplicationTransferRecordList(List<ApplicationTransferRecord> applicationTransferRecordList) {
		this.applicationTransferRecordList = applicationTransferRecordList;
	}

	public List<ApplicationToCopyPerson> getApplicationToCopyPersonList() {
		return applicationToCopyPersonList;
	}

	public void setApplicationToCopyPersonList(List<ApplicationToCopyPerson> applicationToCopyPersonList) {
		this.applicationToCopyPersonList = applicationToCopyPersonList;
	}

	public String getPreviousOperaterTime() {
		return previousOperaterTime;
	}

	public void setPreviousOperaterTime(String previousOperaterTime) {
		this.previousOperaterTime = previousOperaterTime;
	}
    
    
}