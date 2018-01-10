package com.xiangshangban.att_simple.bean;

public class ApplicationTransferRecord {
    private String id;

    private String applicationNo;

    private String transferPersonId;

    private String transferPersionAccessId;

    private String transferTimes;

    private String operaterTime;

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
}