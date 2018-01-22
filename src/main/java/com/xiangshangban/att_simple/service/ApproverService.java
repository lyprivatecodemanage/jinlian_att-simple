package com.xiangshangban.att_simple.service;

import java.util.List;

import com.xiangshangban.att_simple.bean.Application;

public interface ApproverService {
		/**
		 * 审批首页列表/历史列表/条件筛选
		 * @param employeeId
		 * @param companyId
		 * @param page
		 * @param count
		 * @param applicationType
		 * @param statusDescription
		 * @param applicationTimeDescription
		 * @param applicatrionPersonName
		 * @return
		 */
	   List<Application> approverIndexPage(String employeeId,String companyId,String page,String count,String applicationType,String statusDescription,String applicationTimeDescription,String applicatrionPersonName);
	   

}
