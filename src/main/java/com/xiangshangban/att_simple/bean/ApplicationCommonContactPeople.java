package com.xiangshangban.att_simple.bean;

/**
 * 常用提交人记录表
 * @author mian
 */
public class ApplicationCommonContactPeople {
	//uuid
    private String id;
    //员工id
    private String employeeId;
    //公司id
    private String companyId;
    //常用提交人id
    private String commonContactPeopleId;
    //常用提交人姓名
    private String commonContactPeopleName;
    
    private String type;//常用人类型:{1:常用提交人;2:常用抄送人}

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

    public String getCommonContactPeopleId() {
        return commonContactPeopleId;
    }

    public void setCommonContactPeopleId(String commonContactPeopleId) {
        this.commonContactPeopleId = commonContactPeopleId;
    }
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommonContactPeopleName() {
        return commonContactPeopleName;
    }

    public void setCommonContactPeopleName(String commonContactPeopleName) {
        this.commonContactPeopleName = commonContactPeopleName;
    }
}