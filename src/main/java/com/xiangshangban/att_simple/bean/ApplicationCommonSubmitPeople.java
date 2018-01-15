package com.xiangshangban.att_simple.bean;

/**
 * 常用提交人记录表
 * @author mian
 */
public class ApplicationCommonSubmitPeople {
	//uuid
    private String id;
    //员工id
    private String employeeId;
    //公司id
    private String companyId;
    //常用提交人id
    private String commonCommitPeopleId;
    //常用提交人姓名
    private String commonCommitPeopleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCommonCommitPeopleId() {
        return commonCommitPeopleId;
    }

    public void setCommonCommitPeopleId(String commonCommitPeopleId) {
        this.commonCommitPeopleId = commonCommitPeopleId;
    }

    public String getCommonCommitPeopleName() {
        return commonCommitPeopleName;
    }

    public void setCommonCommitPeopleName(String commonCommitPeopleName) {
        this.commonCommitPeopleName = commonCommitPeopleName;
    }
}