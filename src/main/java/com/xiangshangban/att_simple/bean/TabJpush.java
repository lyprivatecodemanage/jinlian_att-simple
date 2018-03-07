package com.xiangshangban.att_simple.bean;

public class TabJpush {
	public static final String application="0";
	public static final String approver="1";
	private String id;//通知id
	private String companyId;//公司id
	private String pushPersonId;//推送人id
	private String accessPersonId;//接收人id
	private String notificationType;//通知类型
	private String notificationContent;//通知内容
	private String notificationAssociatedObj;//通知关联对象(如:申请id)
	private String createTime;//创建时间
	
	public TabJpush(){}
	
	public TabJpush(String id, String companyId, String pushPersonId, String accessPersonId, String notificationType,
			String notificationContent, String notificationAssociatedObj, String createTime) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.pushPersonId = pushPersonId;
		this.accessPersonId = accessPersonId;
		this.notificationType = notificationType;
		this.notificationContent = notificationContent;
		this.notificationAssociatedObj = notificationAssociatedObj;
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPushPersonId() {
		return pushPersonId;
	}
	public void setPushPersonId(String pushPersonId) {
		this.pushPersonId = pushPersonId;
	}
	public String getAccessPersonId() {
		return accessPersonId;
	}
	public void setAccessPersonId(String accessPersonId) {
		this.accessPersonId = accessPersonId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public String getNotificationAssociatedObj() {
		return notificationAssociatedObj;
	}
	public void setNotificationAssociatedObj(String notificationAssociatedObj) {
		this.notificationAssociatedObj = notificationAssociatedObj;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
