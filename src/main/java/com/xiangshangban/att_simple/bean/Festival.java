package com.xiangshangban.att_simple.bean;

public class Festival {
    private String festivalId; //主键（节假日ID）

    private String festivalDate;//节假日的日期

    private String festivalName;//节假日名称

    private String workType;//放假类型（0：法定节日，1：法定休息日，2：工作日）

    public String getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(String festivalId) {
        this.festivalId = festivalId;
    }

    public String getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(String festivalDate) {
        this.festivalDate = festivalDate;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}