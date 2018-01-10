package com.xiangshangban.att_simple.bean;

/**
 * 申请单抄送人联系表
 * @author mian
 */
public class CopyPersonWithApplication {
	//uuid
    private String id;
    //申请单号
    private String applicationNo;
    //抄送人id
    private String copyPersonId;
    //操作时间
    private String operaterTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getCopyPersonId() {
        return copyPersonId;
    }

    public void setCopyPersonId(String copyPersonId) {
        this.copyPersonId = copyPersonId;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }
}