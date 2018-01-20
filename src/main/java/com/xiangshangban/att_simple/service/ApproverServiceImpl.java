package com.xiangshangban.att_simple.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationTotalRecord;
import com.xiangshangban.att_simple.dao.ApplicationBusinessTravelMapper;
import com.xiangshangban.att_simple.dao.ApplicationCommonContactPeopleMapper;
import com.xiangshangban.att_simple.dao.ApplicationFillCardMapper;
import com.xiangshangban.att_simple.dao.ApplicationLeaveMapper;
import com.xiangshangban.att_simple.dao.ApplicationOutgoingMapper;
import com.xiangshangban.att_simple.dao.ApplicationOvertimeMapper;
import com.xiangshangban.att_simple.dao.ApplicationToCopyPersonMapper;
import com.xiangshangban.att_simple.dao.ApplicationTotalRecordMapper;
import com.xiangshangban.att_simple.dao.ApplicationTransferRecordMapper;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
import com.xiangshangban.att_simple.dao.EmployeeDao;
import com.xiangshangban.att_simple.dao.VacationMapper;
@Service("approverService")
public class ApproverServiceImpl implements ApproverService {
	private static final Logger logger = Logger.getLogger(ApproverServiceImpl.class);
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;//申请类型dao
	@Autowired
	private ApplicationLeaveMapper  applicationLeaveMapper;//请假记录dao
	@Autowired
	private ApplicationOvertimeMapper applicationOvertimeMapper;//加班记录dao
	@Autowired
	private ApplicationBusinessTravelMapper applicationBusinessTravelMapper;//出差记录dao
	@Autowired
	private ApplicationOutgoingMapper applicationOutgoingMapper;//外出记录dao
	@Autowired
	private ApplicationFillCardMapper applicationFillCardMapper;//补卡记录dao
	@Autowired
	private ApplicationToCopyPersonMapper  applicationToCopyPersonMapper;//抄送dao
	@Autowired
	private ApplicationTotalRecordMapper applicationTotalRecordMapper;//申请汇总记录dao
	@Autowired
	private VacationMapper vacationMapper;//假期dao
	@Autowired
	private EmployeeDao employeeDao;//员工dao
	@Autowired
	private ApplicationCommonContactPeopleMapper  applicationCommonContactPeopleMapper;//常用联系人dao
	@Autowired
	private ApplicationTransferRecordMapper applicationTransferRecordMapper;//转移记录dao
	
	/**
	 * 审批首页列表/历史列表/条件筛选
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public List<Application> approverIndexPage(String employeeId,String companyId,String page,String count,
			String applicationType,String statusDescription,
			String applicationTimeDescription,String applicatrionPersonName) {
		String isComplete = "";
		String isTranser = "";
		String isReject = "";
		String isCopy = "";
		page = String.valueOf((Integer.valueOf(page)-1)*Integer.valueOf(count));
		//申请类型
		if(StringUtils.isEmpty(applicationType)||"全部".equals(applicationType)){
			applicationType = null;
		}else if("请假".equals(applicationType)){
			applicationType = "1";
		}else if("加班".equals(applicationType)){
			applicationType = "2";
		}else if("出差".equals(applicationType)){
			applicationType = "3";
		}else if("外出".equals(applicationType)){
			applicationType = "4";
		}else if("补卡".equals(applicationType)){
			applicationType = "5";
		}
		//申请状态
		if(StringUtils.isEmpty(statusDescription)||"全部".equals(statusDescription)){
			isCopy="1";
		}else if("未审批".equals(statusDescription)){
			isComplete="0";
		}else if("已转移".equals(statusDescription)){
			isTranser = "1";
		}else if("已完成".equals(statusDescription)){
			isComplete = "1";
		}else if("已驳回".equals(statusDescription)){
			isComplete = "1";
			isReject = "1";
		}else if("抄送".equals(statusDescription)){
			isCopy="1";
		}
		
		if("未审批".equals(applicationType)){
			List<ApplicationTotalRecord> approverList = applicationTotalRecordMapper.selectApproverList(employeeId,
					companyId, page, count, applicationType, isComplete, isTranser, isReject, isCopy);
		}
		return null;
	}
	

}
