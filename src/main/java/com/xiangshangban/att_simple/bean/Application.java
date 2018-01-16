package com.xiangshangban.att_simple.bean;

import java.util.List;

public class Application {
	private String applicationNo;//申请单号
	private String applicationId;//申请人id
	private String applicatrionPersonName;//申请人姓名
	private String departmentId;//部门id
	private String departmentName;//部门名称
	private String companyId;//公司id
	private String companyName;//公司名称
	private String applicationType;//申请类型(申请类型(请假:'1';加班:'2';出差'3';外出:'4';'补卡5'))
	private String applicationChildrenType;//申请子类型
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String reason;//申请说明
	private String uploadVoucher;//申请凭证
	private String approver;//审批人id
	private String approverNmae;//审批人姓名
	//private String copyPersonId;//抄送人id
	//private String copyPersonName;//抄送人姓名
	private List<ApplicationCommonContactPeople> commonContactPeopleList;//常用联系人集合
	private List<ApplicationToCopyPerson> copyPersonList;//抄送人集合
	private String isCopy;//是否抄送(0:否;1:是)
	private String isReject;//是否驳回(0:否;1:是)
	private String isTransfer;//是否转移(0:否;1:是)
	private String applicationStatus;//申请单状态(0:删除;1:正常;2:撤回)
	private String rejectReason;//驳回说明
	private List<ApplicationTransferRecord> transferRecordList;//移交列表
	//private String transferExplain;//移交说明
	private String applicationTime;//申请发起时间
	private String operaterId;//last操作人id
	private String operaterName;//操作人姓名
	private String operaterTime;//操作时间
	private String outgoningLocation;//外出地点
	private String applicationHour;//申请小时数
	private String isComplete;//0:未完成,1:已完成
	private String isSetCommonContactPeople;//是否设置常用联系人 0:否;1是
	private String fillCardTime;//补卡时间
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicatrionPersonName() {
		return applicatrionPersonName;
	}
	public void setApplicatrionPersonName(String applicatrionPersonName) {
		this.applicatrionPersonName = applicatrionPersonName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getApplicationChildrenType() {
		return applicationChildrenType;
	}
	public void setApplicationChildrenType(String applicationChildrenType) {
		this.applicationChildrenType = applicationChildrenType;
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
	public String getApproverNmae() {
		return approverNmae;
	}
	public void setApproverNmae(String approverNmae) {
		this.approverNmae = approverNmae;
	}
	/*public String getCopyPersonId() {
		return copyPersonId;
	}
	public void setCopyPersonId(String copyPersonId) {
		this.copyPersonId = copyPersonId;
	}
	public String getCopyPersonName() {
		return copyPersonName;
	}
	public void setCopyPersonName(String copyPersonName) {
		this.copyPersonName = copyPersonName;
	}*/
	public String getIsCopy() {
		return isCopy;
	}
	public void setIsCopy(String isCopy) {
		this.isCopy = isCopy;
	}
	public String getIsReject() {
		return isReject;
	}
	public void setIsReject(String isReject) {
		this.isReject = isReject;
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
	/*public String getTransferExplain() {
		return transferExplain;
	}
	public void setTransferExplain(String transferExplain) {
		this.transferExplain = transferExplain;
	}*/
	public String getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getOperaterTime() {
		return operaterTime;
	}
	public void setOperaterTime(String operaterTime) {
		this.operaterTime = operaterTime;
	}
	public String getOutgoningLocation() {
		return outgoningLocation;
	}
	public void setOutgoningLocation(String outgoningLocation) {
		this.outgoningLocation = outgoningLocation;
	}
	public String getApplicationHour() {
		return applicationHour;
	}
	public void setApplicationHour(String applicationHour) {
		this.applicationHour = applicationHour;
	}
	public List<ApplicationToCopyPerson> getCopyPersonList() {
		return copyPersonList;
	}
	public void setCopyPersonList(List<ApplicationToCopyPerson> copyPersonList) {
		this.copyPersonList = copyPersonList;
	}
	public List<ApplicationTransferRecord> getTransferRecordList() {
		return transferRecordList;
	}
	public void setTransferRecordList(List<ApplicationTransferRecord> transferRecordList) {
		this.transferRecordList = transferRecordList;
	}
	public String getOperaterId() {
		return operaterId;
	}
	public void setOperaterId(String operaterId) {
		this.operaterId = operaterId;
	}
	public String getOperaterName() {
		return operaterName;
	}
	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	public List<ApplicationCommonContactPeople> getCommonContactPeopleList() {
		return commonContactPeopleList;
	}
	public void setCommonContactPeopleList(List<ApplicationCommonContactPeople> commonContactPeopleList) {
		this.commonContactPeopleList = commonContactPeopleList;
	}
	public String getIsSetCommonContactPeople() {
		return isSetCommonContactPeople;
	}
	public void setIsSetCommonContactPeople(String isSetCommonContactPeople) {
		this.isSetCommonContactPeople = isSetCommonContactPeople;
	}
	public String getFillCardTime() {
		return fillCardTime;
	}
	public void setFillCardTime(String fillCardTime) {
		this.fillCardTime = fillCardTime;
	}
	
	
}
