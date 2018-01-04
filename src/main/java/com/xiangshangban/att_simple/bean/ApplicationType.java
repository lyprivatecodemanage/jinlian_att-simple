package com.xiangshangban.att_simple.bean;

public class ApplicationType {
    private String description;
    
    private String applicationType;

    private String applicationChildrenType;

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationChildrenType() {
        return applicationChildrenType;
    }

    public void setApplicationChildrenType(String applicationChildrenType) {
        this.applicationChildrenType = applicationChildrenType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}