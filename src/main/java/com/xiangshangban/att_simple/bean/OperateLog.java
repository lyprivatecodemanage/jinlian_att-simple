package com.xiangshangban.att_simple.bean;

/**
 * 添加操作日志业务逻辑bean
 * @author 王勇辉 
 *
 */
public class OperateLog {
	//操作人ID
	private String operateEmpId;
	
	//操作人公司ID
	private String operateEmpCompanyId;
	
	//操作类型(1：设备管理 2：门禁管理 3：考勤管理 4：审批管理 5：制卡管理 6：费用管理 7：媒体管理 8：活动管理)
	private String operateType;
	
	//操作内容(格式：在xxx界面给yyy进行了zzz操作)
	private String operateContent;

	public String getOperateEmpId() {
		return operateEmpId;
	}

	public void setOperateEmpId(String operateEmpId) {
		this.operateEmpId = operateEmpId;
	}

	public String getOperateEmpCompanyId() {
		return operateEmpCompanyId;
	}

	public void setOperateEmpCompanyId(String operateEmpCompanyId) {
		this.operateEmpCompanyId = operateEmpCompanyId;
	}

	public String getOperateType() {
		return operateType;
	}
	
	/**
	 * 设置操作类型
	 * (1：设备管理 2：门禁管理 3：考勤管理 4：审批管理 5：制卡管理 6：费用管理 7：媒体管理 8：活动管理)
	 * @param operateType
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
	public String getOperateContent() {
		return operateContent;
	}
	
	/**
	 * 设置操作内容
	 * (格式：在xxx界面给yyy进行了zzz操作)
	 * @param operateContent
	 */
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	
}
