package com.xiangshangban.att_simple.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiangshangban.att_simple.bean.ApplicationType;
import com.xiangshangban.att_simple.dao.ApplicationTypeMapper;
import com.xiangshangban.att_simple.dao.ApplicationToCopyPersonMapper;
import com.xiangshangban.att_simple.dao.ApplicationLeaveMapper;
import com.xiangshangban.att_simple.dao.ApplicationTotalRecordMapper;
import com.xiangshangban.att_simple.dao.VacationMapper;

@Service("applicationTypeService")
@Transactional 
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationTypeMapper applicationTypeMapper;
	@Autowired
	private ApplicationLeaveMapper  leaveApplicationRecordDao;
	@Autowired
	private ApplicationToCopyPersonMapper  copyPersonWithApplicationMapper;
	@Autowired
	private ApplicationTotalRecordMapper totalApplicationRecordMapper;
	@Autowired
	private VacationMapper vacationMapper;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> applicationIndexPage(String employeeId, String companyId) {
		Map<String, Object> result = new HashMap<String,Object>();
		//查询申请类型
		List<ApplicationType> applicationTypeList = applicationTypeMapper.getApplicationTypeList();
		//查询年假剩余,年假额度,调休剩余,调休额度
		vacationMapper.SelectEmployeeVacation(companyId, deparmentId, employeeId);
		result.put("applicaitonTypeList", applicationTypeList);
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
	public Map<String, Object> leaveApplication(Map<String, String> params) {
		Map<String, Object> result = new HashMap<String,Object>();
		
		
		return result;
	}

	@Override
	public Map<String, Object> overTimeApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> businessTravelApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> outgoingApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> fillCardApplication(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
