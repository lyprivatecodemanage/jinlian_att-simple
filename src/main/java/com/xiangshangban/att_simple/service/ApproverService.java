package com.xiangshangban.att_simple.service;

import java.util.List;
import java.util.Map;

import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.bean.ReturnData;

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
	   ApplicationTotalRecord approverDetails(String applicationNo,String companyId);
	   /**
	    * 待审批数目
	    * @param employeeId
	    * @param companyId
	    * @return
	    */
	   int willApproverCount(String employeeId,String companyId);
	   /**
	    * 审批申请
	    * @param employeeId
	    * @param companyId
	    * @param applicationNo
	    * @param approverDescription
	    * @param postscriptason
	    * @param transferPersonId
	    * @param transferPersionAccessId
	    * @return
	    */
	   ReturnData approverApplication(String employeeId,String companyId,
			   String applicationNo,String approverDescription,String postscriptason,
			   String transferPersonId, String transferPersionAccessId);
	   /**
	    * web审批中心-未完成工单数和本月已完成工单数
	    * @param companyId
	    * @param employeeId
	    * @return
	    */
	   Map<String,String> webApproverCentreHeader(String companyId,String employeeId);
	   /**
	    * web审批中心-列表分页,条件搜索
	    * @param params
	    * @return
	    */
	   List<ApplicationTotalRecord> webApproverCentreList(String companyId,String page,String count,
				String departmentId,String applicationType,String isComplete,
				String employeeName,String startTime,String endTime);
	   
	   
}
