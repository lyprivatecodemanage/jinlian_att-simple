package com.xiangshangban.att_simple.service;

import java.util.List;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;

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
	   List<ApplicationTotalRecord> approverIndexPage(String employeeId,String companyId,String page,String count,String applicationType,String statusDescription,String applicationTimeDescription,String applicatrionPersonName);
	   /**
	    * 审批详情
	    * @param applicationNo
	    * @return
	    */
	   Application approverDetails(String applicationNo,String statusDescription);
	   /**
	    * 待审批数目
	    * @param employeeId
	    * @param companyId
	    * @return
	    */
	   int willApproverCount(String employeeId,String companyId);

}
