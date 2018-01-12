package com.xiangshangban.att_simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.Application;
import com.xiangshangban.att_simple.bean.ApplicationToCopyPerson;
import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.bean.Vacation;
import com.xiangshangban.att_simple.dao.ApplicationLeaveMapper;
import com.xiangshangban.att_simple.dao.ApplicationToCopyPersonMapper;
import com.xiangshangban.att_simple.dao.ApplicationTotalRecordMapper;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;

@Service("applicationService")
@Transactional 
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;//申请类型dao
	@Autowired
	private ApplicationLeaveMapper  applicationLeaveMapper;//请假记录dao
	@Autowired
	private ApplicationToCopyPersonMapper  applicationToCopyPersonMapper;//抄送dao
	@Autowired
	private ApplicationTotalRecordMapper applicationtotalRecordMapper;//申请汇总记录dao
	@Autowired
	private VacationMapper vacationMapper;//假期dao

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> applicationIndexPage(String employeeId, String companyId) {
		Map<String, Object> result = new HashMap<String,Object>();
		//查询申请类型
		List<ApplicationType> applicationTypeList = applicationTypeMapper.getApplicationTypeList();
		//查询年假剩余,年假额度,调休剩余,调休额度
		Vacation vacation = vacationMapper.SelectEmployeeVacation(companyId, null, employeeId);
		result.put("applicaitonTypeList", applicationTypeList);
		result.put("vacation", vacation);
		return result;
	}
	/**
	 * 请假申请
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> leaveApplication(Application application) {
		Map<String, Object> result = new HashMap<String,Object>();
		//判断请假类型是否是年假和调休
		if("2".equals(application.getApplicaitonChildrenType())||"3".equals(application.getApplicaitonChildrenType())){
			//查询额度
			Vacation vacation = vacationMapper.SelectEmployeeVacation(application.getCompanyId(), null, application.getApplicationId());
			if("2".equals(application.getApplicaitonChildrenType())&&Integer.valueOf(application.getApplicationHour())>Integer.valueOf(vacation.getAnnualLeaveBalance())){
				result.put("message", "年假剩余不足");
				result.put("returnCode", "4400");
				return result;
			}
			if("3".equals(application.getApplicaitonChildrenType())&&Integer.valueOf(application.getApplicationHour())>Integer.valueOf(vacation.getAdjustRestBalance())){
				result.put("message", "调休剩余不足");
				result.put("returnCode", "4401");
				return result;
			}
		}
		//生成申请记录
		applicationtotalRecordMapper.insertApplicationRecord(application);
		applicationLeaveMapper.insertApplicationRecord(application);
		//是否抄送
		if("1".equals(application.getIsCopy())){
			//添加抄送记录
			for(ApplicationToCopyPerson applicationToCopyPerson :application.getCopyPersonList()){
				applicationToCopyPersonMapper.insertSelective(applicationToCopyPerson);
			}
		}
		result.put("message", "成功");
		result.put("returnCode", "3000");
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> overTimeApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> businessTravelApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> outgoingApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> fillCardApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
}
