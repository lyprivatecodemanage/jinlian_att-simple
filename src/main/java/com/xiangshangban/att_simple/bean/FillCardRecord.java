package com.xiangshangban.att_simple.bean;

public class FillCardRecord {
	private String companyId;//公司id
	private String employeeId;//员工id
	private String fillCardType;//打卡类型
	private String fillCardTime;//打卡时间
	private String fillCardAddress;//打卡地点
	private String customerName;//客户名称
	private String description;//详情描述
	private String imgUrl;//凭证key
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	public String getFillCardAddress() {
		return fillCardAddress;
	}
	public void setFillCardAddress(String fillCardAddress) {
		this.fillCardAddress = fillCardAddress;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
