package com.xiangshangban.att_simple.bean;
/**
 * 请求返回数据模型
 * @author 韦友弟
 *
 */
public class ReturnData {
	private String employeeId;
	private Object data;//数据
	private Object totalPages;//总记录数
	private String message;//请求状态描述
	private String returnCode;//请求状态编码
	private Object pagecountNum;//总页数
	private String companyName;//公司名称
	private Object classesTopInfo;//班次使用人数排行榜
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Object getPagecountNum() {
		return pagecountNum;
	}
	public void setPagecountNum(Object pagecountNum) {
		this.pagecountNum = pagecountNum;
	}
	public Object getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Object totalPages) {
		this.totalPages = totalPages;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Object getClassesTopInfo() {
		return classesTopInfo;
	}
	public void setClassesTopInfo(Object classesTopInfo) {
		this.classesTopInfo = classesTopInfo;
	}
	
	
	
}
