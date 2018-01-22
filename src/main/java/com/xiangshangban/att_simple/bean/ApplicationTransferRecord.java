package com.xiangshangban.att_simple.bean;

/**
 * 申请转移记录表
 * @author mian
 */
public class ApplicationTransferRecord {
	//ID
    private String id;
    //申请单号
    private String applicationNo;
    //申请转移发起人id
    private String transferPersonId;
    //转移发起人name
    private String transferPersonName;
    //申请转移接收人id
    private String transferPersionAccessId;
    //转移接收人name
    private String transferPersionAccessName;
    //转移次数
    private String transferTimes;
    //操作时间
    private String operaterTime;
    //移交说明
    private String transferExplain;

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

    public String getTransferPersonId() {
        return transferPersonId;
    }

    public void setTransferPersonId(String transferPersonId) {
        this.transferPersonId = transferPersonId;
    }

    public String getTransferPersionAccessId() {
        return transferPersionAccessId;
    }

    public void setTransferPersionAccessId(String transferPersionAccessId) {
        this.transferPersionAccessId = transferPersionAccessId;
    }

    public String getTransferTimes() {
        return transferTimes;
    }

    public void setTransferTimes(String transferTimes) {
        this.transferTimes = transferTimes;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }

    public String getTransferExplain() {
        return transferExplain;
    }

    public void setTransferExplain(String transferExplain) {
        this.transferExplain = transferExplain;
    }

	public String getTransferPersonName() {
		return transferPersonName;
	}

	public void setTransferPersonName(String transferPersonName) {
		this.transferPersonName = transferPersonName;
	}

	public String getTransferPersionAccessName() {
		return transferPersionAccessName;
	}

	public void setTransferPersionAccessName(String transferPersionAccessName) {
		this.transferPersionAccessName = transferPersionAccessName;
	}
    
}