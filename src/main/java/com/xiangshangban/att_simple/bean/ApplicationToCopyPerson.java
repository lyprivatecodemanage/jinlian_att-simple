package com.xiangshangban.att_simple.bean;

/**
 * 申请单抄送人联系表
 * @author mian
 */
public class ApplicationToCopyPerson {
	//uuid
    private String id;
    //申请单号
    private String applicationNo;
    //抄送人id
    private String appCopyPersonId;
    //抄送人姓名
    private String appCopyPersonName;
    //操作时间
    private String operaterTime;
    private String companyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppCopyPersonName() {
		return appCopyPersonName;
	}

	public void setAppCopyPersonName(String appCopyPersonName) {
		this.appCopyPersonName = appCopyPersonName;
	}

	public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getAppCopyPersonId() {
        return appCopyPersonId;
    }

    public void setAppCopyPersonId(String appCopyPersonId) {
        this.appCopyPersonId = appCopyPersonId;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
    
}