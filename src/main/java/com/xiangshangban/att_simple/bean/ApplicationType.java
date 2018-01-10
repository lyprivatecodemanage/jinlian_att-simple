package com.xiangshangban.att_simple.bean;

/**
 * 申请类型表
 * @author mian
 */
public class ApplicationType {
	//申请类型
	private String applicationType;
	//申请子类型
    private String applicationChildrenType;
    //申请类型描述
    private String description;
    //子类型描述
    private String children_description;

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

	public String getChildren_description() {
		return children_description;
	}

	public void setChildren_description(String children_description) {
		this.children_description = children_description;
	}
    
    
}