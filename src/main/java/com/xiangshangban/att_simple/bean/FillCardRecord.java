package com.xiangshangban.att_simple.bean;

public class FillCardRecord {
	private String companyId;//公司id
	private String employeeId;//员工id
	private String attTime;//考勤时间
	private String place;//考勤地点详情
	private String customerName;//客户名称
	private String description;//详情描述
	private String imgKey;//上传的照片
	private String longitude;//位置的经度
	private String latitude;//位置的纬度
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
	public String getAttTime() {
		return attTime;
	}
	public void setAttTime(String attTime) {
		this.attTime = attTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	public String getImgKey() {
		return imgKey;
	}
	public void setImgKey(String imgKey) {
		this.imgKey = imgKey;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
